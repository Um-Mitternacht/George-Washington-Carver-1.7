package growthcraft.core.common.item;

import growthcraft.api.core.item.ItemKey;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.registry.FenceRopeRegistry.FenceRopeEntry;
import growthcraft.core.registry.FenceRopeRegistry;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRope extends GrcItemBase
{
	public ItemRope()
	{
		super();
		setUnlocalizedName("grc.rope");
		setCreativeTab(GrowthCraftCore.creativeTab);
		setTextureName("grccore:rope");
	}

	/************
	 * MAIN
	 ************/
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int dir, float par8, float par9, float par10)
	{
		final Block block = world.getBlock(x, y, z);
		final int blockMeta = world.getBlockMetadata(x, y, z);

		if (Blocks.SNOW_LAYER == block && (blockMeta & 7) < 1)
		{
			dir = 1;
		}
		else
		{
			final FenceRopeEntry entry = FenceRopeRegistry.instance().getEntry(block, blockMeta);
			if (entry != null)
			{
				if (!player.canPlayerEdit(x, y, z, dir, stack))
				{
					return false;
				}
				else if (stack.stackSize == 0)
				{
					return false;
				}

				int targetMeta = entry.getFenceRopeBlockMetadata();
				if (targetMeta == ItemKey.WILDCARD_VALUE) targetMeta = blockMeta;

				world.setBlock(x, y, z, entry.getFenceRopeBlock(), targetMeta, BlockFlags.UPDATE_AND_SYNC);
				--stack.stackSize;
				return true;
			}
			else if (block != Blocks.VINE && block != Blocks.TALLGRASS && block != Blocks.DEADBUSH)
			{
				if (dir == 0)
				{
					--y;
				}

				if (dir == 1)
				{
					++y;
				}

				if (dir == 2)
				{
					--z;
				}

				if (dir == 3)
				{
					++z;
				}

				if (dir == 4)
				{
					--x;
				}

				if (dir == 5)
				{
					++x;
				}
			}
		}

		if (!player.canPlayerEdit(x, y, z, dir, stack))
		{
			return false;
		}
		else if (stack.stackSize == 0)
		{
			return false;
		}
		else
		{
			final Block block2 = GrowthCraftCore.blocks.ropeBlock.getBlock();
			if (world.canPlaceEntityOnSide(block2, x, y, z, false, dir, (Entity)null, stack))
			{
				final int meta = block2.onBlockPlaced(world, x, y, z, dir, par8, par9, par10, 0);

				if (world.setBlock(x, y, z, block2, meta, 3))
				{
					if (world.getBlock(x, y, z) == block2)
					{
						block2.onBlockPlacedBy(world, x, y, z, player, stack);
						block2.onPostBlockPlaced(world, x, y, z, meta);
					}

					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block2.stepSound.func_150496_b(), (block2.stepSound.getVolume() + 1.0F) / 2.0F, block2.stepSound.getPitch() * 0.8F);
					--stack.stackSize;
				}
			}

			return true;
		}
	}
}
