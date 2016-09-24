/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.core.common.tileentity;

import growthcraft.api.core.nbt.INBTSerializable;
import growthcraft.api.core.stream.IStreamable;
import growthcraft.core.common.tileentity.event.TileEventFunction;
import growthcraft.core.common.tileentity.event.TileEventHandler;
import growthcraft.core.common.tileentity.event.TileEventHandlerMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Extend this base class if you just need a Base tile with the event system.
 *
 * Event handling system is a stripped version of the one seen in AE2, I've
 * copied the code for use in YATM, but I've ported it over to Growthcraft as
 * well.
 */
public abstract class GrcTileBase extends TileEntity implements IStreamable, INBTSerializable
{
	protected static TileEventHandlerMap<GrcTileBase> HANDLERS = new TileEventHandlerMap<GrcTileBase>();

	public void markForUpdate()
	{
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void markDirtyAndUpdate()
	{
		markDirty();
		markForUpdate();
	}

	protected List<TileEventFunction> getHandlersFor(@Nonnull TileEventHandler.EventType event)
	{
		return HANDLERS.getEventFunctionsForClass(getClass(), event);
	}

	@Override
	public final boolean writeToStream(ByteBuf stream)
	{
		final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NETWORK_WRITE);
		if (handlers != null)
		{
			for (TileEventFunction func : handlers)
			{
				func.writeToStream(this, stream);
			}
		}
		return false;
	}

	@Override
	public Packet getDescriptionPacket()
	{
		final NBTTagCompound data = new NBTTagCompound();
		final ByteBuf stream = Unpooled.buffer();

		try
		{
			writeToStream(stream);
			if (stream.readableBytes() == 0)
			{
				return null;
			}
		}
		catch (Throwable t)
		{
			System.err.println(t);
		}


		// P, for payload
		data.setByteArray("P", stream.array());

		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 127, data);
	}

	@Override
	public final boolean readFromStream(ByteBuf stream)
	{
		boolean shouldUpdate = false;
		final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NETWORK_READ);
		if (handlers != null)
		{
			for (TileEventFunction func : handlers)
			{
				if (func.readFromStream(this, stream))
				{
					shouldUpdate = true;
				}
			}
		}
		return shouldUpdate;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		if (packet.func_148853_f() == 127)
		{
			final NBTTagCompound tag = packet.func_148857_g();
			boolean dirty = false;
			if (tag != null)
			{
				final ByteBuf stream = Unpooled.copiedBuffer(tag.getByteArray("P"));
				if (readFromStream(stream))
				{
					dirty = true;
				}
			}
			if (dirty) markForUpdate();
		}
	}

	public void readFromNBTForItem(NBTTagCompound tag)
	{
	}

	public void writeToNBTForItem(NBTTagCompound tag)
	{
	}

	@Override
	public final void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NBT_READ);
		if (handlers != null)
		{
			for (TileEventFunction func : handlers)
			{
				func.readFromNBT(this, nbt);
			}
		}
	}

	@Override
	public final void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		final List<TileEventFunction> handlers = getHandlersFor(TileEventHandler.EventType.NBT_WRITE);
		if (handlers != null)
		{
			for (TileEventFunction func : handlers)
			{
				func.writeToNBT(this, nbt);
			}
		}
	}
}
