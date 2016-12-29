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
package growthcraft.milk.common.item;

import growthcraft.core.common.item.GrcItemBase;
import growthcraft.milk.GrowthCraftMilk;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public abstract class ItemSeedThistle extends GrcItemBase implements IPlantable
{
	public ItemSeedThistle()
	{
		super();
		setUnlocalizedName("grcmilk.seed_thistle");
		setCreativeTab(GrowthCraftMilk.creativeTab);
		//setTextureName("grcmilk:seeds/seed_thistle");
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Plains;
	}

	@Override
	public Block getPlant(IBlockAccess world, BlockPos pos)
	{
		return GrowthCraftMilk.blocks.thistle.getBlockState();
	}

	@Override
	public int getPlantMetadata(IBlockAccess world, BlockPos pos)
	{
		return 0;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, int EnumFacing, float tx, float ty, float tz)
	{
		if (EnumFacing == 1)
		{
			if (player.canPlayerEdit(x, y, z, EnumFacing, stack) && player.canPlayerEdit(x, y + 1, z, EnumFacing, stack))
			{
				final Block soil = world.getBlockState(x, y, z);
				final Block plant = getPlant(world, x, y + 1, z);

				if (plant instanceof IPlantable)
				{
					if (soil != null && !world.isAirBlock(x, y, z) && soil.canSustainPlant(world, x, y + 1, z, EnumFacing.UP, (IPlantable)plant))
					{
						world.setBlockState(x, y + 1, z, plant);
						--stack.stackSize;
						return true;
					}
				}
			}
		}
		return false;
	}
}
