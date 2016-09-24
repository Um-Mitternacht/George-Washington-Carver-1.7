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
package growthcraft.bees.common.block;

import growthcraft.core.integration.HIGHLANDS.EnumHIGHLANDSWoodType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class BlockBeeBoxHighlands extends BlockBeeBox
{
	public BlockBeeBoxHighlands()
	{
		super();
		setHardness(2f);
		setBlockName("grc.beeBox.Highlands");
	}

	@Override
	public String getMetaname(int meta)
	{
		if (meta >= 0 && meta < EnumHIGHLANDSWoodType.VALUES.length)
		{
			return EnumHIGHLANDSWoodType.VALUES[meta].name;
		}
		return super.getMetaname(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubBlocks(Item block, CreativeTabs tab, List list)
	{
		for (int i = 0; i < EnumHIGHLANDSWoodType.VALUES.length; ++i)
		{
			list.add(new ItemStack(block, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[4 * EnumHIGHLANDSWoodType.VALUES.length];
		for (EnumHIGHLANDSWoodType type : EnumHIGHLANDSWoodType.VALUES)
		{
			registerBeeBoxIcons(reg, String.format("/highlands/%s/", type.name), type.meta);
		}
	}
}
