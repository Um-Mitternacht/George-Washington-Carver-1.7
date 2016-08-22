package growthcraft.cellar.network;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import growthcraft.cellar.GrowthCraftCellar;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

@ChannelHandler.Sharable
public class PacketPipeline extends MessageToMessageCodec<FMLProxyPacket, AbstractPacket>
{
	class PacketList extends LinkedList<Class<? extends AbstractPacket>>
	{
		public static final long serialVersionUID = 0x47433031L;
	}

	private EnumMap<Side, FMLEmbeddedChannel> channels;
	private PacketList packets = new PacketList();
	private boolean isPostInitialised;

	/**
	 * Register your packet with the pipeline. Discriminators are automatically set.
	 *
	 * @param clazz the class to register
	 *
	 * @return whether registration was successful. Failure may occur if 256 packets have been registered or if the registry already contains this packet
	 */
	public boolean registerPacket(Class<? extends AbstractPacket> clazz)
	{
		if (this.packets.size() > 256)
		{
			// You should log here!!
			return false;
		}

		if (this.packets.contains(clazz))
		{
			// You should log here!!
			return false;
		}

		if (this.isPostInitialised)
		{
			// You should log here!!
			return false;
		}

		this.packets.add(clazz);
		return true;
	}

	// In line encoding of the packet, including discriminator setting
	@Override
	protected void encode(ChannelHandlerContext ctx, AbstractPacket msg, List<Object> out) throws Exception
	{
		final ByteBuf buffer = Unpooled.buffer();
		final Class<? extends AbstractPacket> clazz = msg.getClass();
		if (!this.packets.contains(msg.getClass())) {
			throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
		}

		final byte discriminator = (byte) this.packets.indexOf(clazz);
		buffer.writeByte(discriminator);
		msg.encodeInto(ctx, buffer);
		final FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
		out.add(proxyPacket);
	}

	// In line decoding and handling of the packet
	@Override
	protected void decode(ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception
	{
		final ByteBuf payload = msg.payload();
		final byte discriminator = payload.readByte();
		final Class<? extends AbstractPacket> clazz = this.packets.get(discriminator);

		if (clazz == null)
		{
			throw new NullPointerException("No packet registered for discriminator: " + discriminator);
		}

		final AbstractPacket pkt = clazz.newInstance();
		pkt.decodeInto(ctx, payload.slice());

		EntityPlayer player;
		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
				player = this.getClientPlayer();
				pkt.handleClientSide(player);
				break;

			case SERVER:
				final INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
				player = ((NetHandlerPlayServer) netHandler).playerEntity;
				pkt.handleServerSide(player);
				break;

			default:
				break;
		}

		out.add(pkt);
	}

	// Method to call from FMLInitializationEvent
	public void initialise()
	{
		this.channels = NetworkRegistry.INSTANCE.newChannel("GrCCellar", this);
		if (GrowthCraftCellar.getConfig().enableDiscardButton)
		{
			this.registerPacket(PacketClearTankButton.class);
			this.registerPacket(PacketClearTankButtonWByte.class);
		}
		this.registerPacket(PacketSwitchTankButton.class);
	}

	// Method to call from FMLPostInitializationEvent
	// Ensures that packet discriminators are common between server and client by using logical sorting
	public void postInitialise()
	{
		if (this.isPostInitialised)
		{
			return;
		}

		this.isPostInitialised = true;
		Collections.sort(this.packets, new Comparator<Class<? extends AbstractPacket>>()
			{

				@Override
				public int compare(Class<? extends AbstractPacket> clazz1, Class<? extends AbstractPacket> clazz2)
				{
					int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
					if (com == 0)
					{
						com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
					}

					return com;
				}
			}
		);
	}

	@SideOnly(Side.CLIENT)
	private EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	/**
	 * Send this message to everyone.
	 *
	 * Adapted from CPW's code in net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message The message to send
	 */
	public void sendToAll(AbstractPacket message)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to the specified player.
	 *
	 * Adapted from CPW's code in net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message The message to send
	 * @param player  The player to send it to
	 */
	public void sendTo(AbstractPacket message, EntityPlayerMP player)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to everyone within a certain range of a point.
	 *
	 * Adapted from CPW's code in net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message The message to send
	 * @param point   The {@link net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint} around which to send
	 */
	public void sendToAllAround(AbstractPacket message, NetworkRegistry.TargetPoint point)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to everyone within the supplied dimension.
	 *
	 * Adapted from CPW's code in net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message     The message to send
	 * @param dimensionId The dimension id to target
	 */
	public void sendToDimension(AbstractPacket message, int dimensionId)
	{
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
		this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
		this.channels.get(Side.SERVER).writeAndFlush(message);
	}

	/**
	 * Send this message to the server.
	 *
	 * Adapted from CPW's code in net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
	 *
	 * @param message The message to send
	 */
	public void sendToServer(AbstractPacket message)
	{
		this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channels.get(Side.CLIENT).writeAndFlush(message);
	}
}
