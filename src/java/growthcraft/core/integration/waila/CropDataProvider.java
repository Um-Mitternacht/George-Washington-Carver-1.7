package growthcraft.core.integration.waila;

import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.core.common.block.ICropDataProvider;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import java.util.List;

public class CropDataProvider implements IWailaDataProvider
{
	@Override
	@Optional.Method(modid="Waila")
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return accessor.getStack();
	}

	@Override
	@Optional.Method(modid="Waila")
	public List<String> getWailaHead(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return tooltip;
	}

	@Override
	@Optional.Method(modid="Waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		final Block block = accessor.getBlockState();
		if (block instanceof ICropDataProvider)
		{
			final ICropDataProvider	prov = (ICropDataProvider)block;
			final MovingObjectPosition pos = accessor.getPosition();
			final float growth = prov.getGrowthProgress(accessor.getWorld(), pos.blockX, pos.blockY, pos.blockZ, accessor.getMetadata());
			String content = TextFormatting.GRAY + GrcI18n.translate("grc.format.crop.growth_prefix") + " " + TextFormatting.WHITE;
			if (growth >= 1.0f)
			{
				content += GrcI18n.translate("grc.format.crop.mature");
			}
			else
			{
				content += GrcI18n.translate("grc.format.crop.progress_format", (int)(growth * 100));
			}
			tooltip.add(content);
		}
		return tooltip;
	}

	@Override
	@Optional.Method(modid="Waila")
	public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		return tooltip;
	}

	@Override
	@Optional.Method(modid="Waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
	{
		return tag;
	}
}
