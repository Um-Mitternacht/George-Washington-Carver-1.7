package growthcraft.bamboo.common.item;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.common.item.GrcItemBase;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBambooDoor extends GrcItemBase
{
	public ItemBambooDoor()
	{
		super();
		this.maxStackSize = 1;
		setUnlocalizedName("grc.bambooDoor");
		setCreativeTab(GrowthCraftBamboo.creativeTab);
	}

	/************
	 * MAIN
	 ************/
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
	{
		if (side != 1)
		{
			return false;
		}
		else
		{
			++y;
			final Block block = GrowthCraftBamboo.blocks.bambooDoor.getBlock();

			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
			{
				if (!block.canPlaceBlockAt(world, x, y, z))
				{
					return false;
				}
				else
				{
					final int i1 = MathHelper.floor_double((double)((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
					placeDoorBlock(world, x, y, z, i1, block);
					--stack.stackSize;
					return true;
				}
			}
			else
			{
				return false;
			}
		}
	}

	public static void placeDoorBlock(World world, int i, int j, int k, int side, Block block)
	{
		byte b0 = 0;
		byte b1 = 0;

		if (side == 0)
		{
			b1 = 1;
		}

		if (side == 1)
		{
			b0 = -1;
		}

		if (side == 2)
		{
			b1 = -1;
		}

		if (side == 3)
		{
			b0 = 1;
		}

		final int i1 = (world.getBlock(i - b0, j, k - b1).isNormalCube(world, i - b0, j, k - b1) ? 1 : 0) + (world.getBlock(i - b0, j + 1, k - b1).isNormalCube(world, i - b0, j + 1, k - b1) ? 1 : 0);
		final int j1 = (world.getBlock(i + b0, j, k + b1).isNormalCube(world, i + b0, j, k + b1) ? 1 : 0) + (world.getBlock(i + b0, j + 1, k + b1).isNormalCube(world, i + b0, j + 1, k + b1) ? 1 : 0);
		final boolean flag = world.getBlock(i - b0, j, k - b1) == block || world.getBlock(i - b0, j + 1, k - b1) == block;
		final boolean flag1 = world.getBlock(i + b0, j, k + b1) == block || world.getBlock(i + b0, j + 1, k + b1) == block;
		boolean flag2 = false;

		if (flag && !flag1)
		{
			flag2 = true;
		}
		else if (j1 > i1)
		{
			flag2 = true;
		}

		world.setBlock(i, j, k, block, side, 2);
		world.setBlock(i, j + 1, k, block, 8 | (flag2 ? 1 : 0), 2);
		world.notifyBlocksOfNeighborChange(i, j, k, block);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, block);
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		this.itemIcon = reg.registerIcon("grcbamboo:door");
	}
}
