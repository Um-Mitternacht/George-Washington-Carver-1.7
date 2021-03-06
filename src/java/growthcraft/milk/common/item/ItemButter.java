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

import growthcraft.core.common.item.GrcItemFoodBase;
import growthcraft.milk.GrowthCraftMilk;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemButter extends GrcItemFoodBase {
    @SideOnly(Side.CLIENT)


    public ItemButter() {
        super(2, 0.3F, false);
        setHasSubtypes(true);
        setMaxDamage(0);
        setUnlocalizedName("grcmilk.Butter");
        setCreativeTab(GrowthCraftMilk.creativeTab);
    }

    public EnumButter getEnumButter(ItemStack stack) {
        return EnumButter.VALUES[MathHelper.clamp_int(stack.getItemDamage(), 0, EnumButter.VALUES.length)];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + "." + getEnumButter(stack).name;
    }

    //@Override
    //@SideOnly(Side.CLIENT)
    //public void registerIcons(IIconRegister ir)
    //{
    //	this.icons = new IIcon[EnumButter.VALUES.length];
//
    //	for (EnumButter butter : EnumButter.VALUES)
    //	{
    //		this.icons[butter.meta] = ir.registerIcon("grcmilk:butter/butter_" + butter.name);
    //	}
    //}

    //@Override
    //@SideOnly(Side.CLIENT)
    //public IIcon getIconFromDamage(int meta)
    //{
    //	return icons[MathHelper.clamp_int(meta, 0, icons.length - 1)];
    //}

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void getSubItems(Item item, CreativeTabs ct, List list) {
        for (EnumButter butter : EnumButter.VALUES) {
            list.add(butter.asStack());
        }
    }
}
