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

import growthcraft.core.integration.totemic.EnumTotemicWoodType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;


public class BlockBeeBoxTotemic extends BlockBeeBox {
    public BlockBeeBoxTotemic() {
        super();
        setHardness(2f);
        setUnlocalizedName("grc.beeBox.Totemic");
    }

    @Override
    public String getMetaname(int meta) {
        if (meta >= 0 && meta < EnumTotemicWoodType.VALUES.length) {
            return EnumTotemicWoodType.VALUES[meta].name;
        }
        return super.getMetaname(meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getSubBlocks(Item block, CreativeTabs tab, List list) {
        for (int i = 0; i < EnumTotemicWoodType.VALUES.length; ++i) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    //@Override
    //@SideOnly(Side.CLIENT)

    //{
    //	this.icons = new IIcon[4 * EnumTotemicWoodType.VALUES.length];
    //	for (EnumTotemicWoodType type : EnumTotemicWoodType.VALUES)
    //	{
    //		registerBeeBoxIcons(reg, String.format("/totemic/%s/", type.name), type.meta);
    //	}
//	}
}
