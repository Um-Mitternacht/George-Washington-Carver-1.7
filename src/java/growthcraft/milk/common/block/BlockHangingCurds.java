/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
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
package growthcraft.milk.common.block;

import growthcraft.api.core.util.BBox;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.RenderType;
import growthcraft.core.common.block.GrcBlockContainer;
import growthcraft.core.util.BlockCheck;
import growthcraft.milk.GrowthCraftMilk;
import growthcraft.milk.common.item.EnumCheeseType;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.common.tileentity.TileEntityHangingCurds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockHangingCurds extends GrcBlockContainer
{
	public BlockHangingCurds()
	{
		super(Material.CAKE);
		// make it god awful difficult to break by hand.
		setHardness(6.0F);
		setTickRandomly(true);
		setBlockName("grcmilk.HangingCurds");
		setTileEntityType(TileEntityHangingCurds.class);
		final BBox bb = BBox.newCube(4f, 0f, 4f, 8f, 16f, 8f).scale(1f / 16f);
		setBlockBounds(bb.x0(), bb.y0(), bb.z0(), bb.x1(), bb.y1(), bb.z1());
		setBlockTextureName("grcmilk:hanging_curds");
		setCreativeTab(GrowthCraftMilk.creativeTab);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, EntityLivingBase entity, ItemStack stack)
	{
		super.onBlockPlacedBy(world, pos, entity, stack);
		world.setBlockState(pos, stack.getItemDamage(), BlockFlags.NONE);
	}

	@Override
	protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack)
	{
		return true;
	}

	@Override
	protected boolean shouldDropTileStack(World world, BlockPos pos, int metadata, int fortune)
	{
		return true;
	}

	@Override
	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, BlockPos pos, int meta)
	{
		final TileEntityHangingCurds te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.asItemStack();
		}
		return new ItemStack(this, 1, meta);
	}

	@Override
	protected void getTileItemStackDrops(List<ItemStack> ret, World world, BlockPos pos, IBlockState state, int metadata, int fortune)
	{
		final TileEntityHangingCurds te = getTileEntity(world, pos);
		if (te != null)
		{
			ret.add(te.asItemStack());
		}
		else
		{
			super.getTileItemStackDrops(ret, world, pos, state, metadata, fortune);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int meta, float par7, float par8, float par9)
	{
		if (super.onBlockActivated(world, pos, player, meta, par7, par8, par9)) return true;
		if (!player.isSneaking())
		{
			final TileEntityHangingCurds hangingCurd = getTileEntity(world, pos);
			if (hangingCurd != null)
			{
				if (hangingCurd.isDried())
				{
					fellBlockAsItem(world, pos);
					return true;
				}
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		if (item instanceof ItemBlockHangingCurds)
		{
			final ItemBlockHangingCurds ib = (ItemBlockHangingCurds)item;
			for (EnumCheeseType cheese : EnumCheeseType.VALUES)
			{
				if (cheese.hasCurdBlock())
				{
					final ItemStack stack = new ItemStack(item, 1, cheese.meta);
					ib.getTileTagCompound(stack);
					list.add(stack);
				}
			}
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntityHangingCurds teHangingCurds = getTileEntity(world, pos);
		if (teHangingCurds != null)
		{
			return teHangingCurds.asItemStack();
		}
		return super.getPickBlock(target, world, pos, player);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return !world.isAirBlock(x, y + 1, z) &&
			BlockCheck.isBlockPlacableOnSide(world, x, y + 1, z, EnumFacing.DOWN);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		return super.canPlaceBlockAt(world, pos) && canBlockStay(world, pos);
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, Block block)
	{
		if (!this.canBlockStay(world, pos))
		{
			fellBlockAsItem(world, pos);
		}
	}

	@Override
	public void updateTick(World world, BlockPos pos, Random random, IBlockState state)
	{
		super.updateTick(world, pos, state, random);
		if (!world.isRemote)
		{
			if (!canBlockStay(world, pos))
			{
				dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
				world.setBlockToAir(pos);
			}
		}
	}

	@Override
	public int damageDropped(int metadata)
	{
		return metadata;
	}

	@Override
	public int getRenderType()
	{
		return RenderType.NONE;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int side)
	{
		return true;
	}
}
