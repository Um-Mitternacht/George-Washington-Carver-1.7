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

import growthcraft.api.core.definition.IItemStackFactory;
import growthcraft.milk.GrowthCraftMilk;
import net.minecraft.item.ItemStack;

public enum EnumIceCream implements IItemStackFactory {
    PLAIN("plain"),
    CHOCOLATE("chocolate"),
    GRAPE("grape"),
    APPLE("apple"),
    HONEY("honey"),
    WATERMELON("watermelon");

    public static final EnumIceCream[] VALUES = values();

    public final String name;
    public final int meta;

    EnumIceCream(String n) {
        this.name = n;
        this.meta = ordinal();
    }

    public ItemStack asStack(int size) {
        return GrowthCraftMilk.items.iceCream.asStack(size, meta);
    }

    public ItemStack asStack() {
        return asStack(1);
    }
}
