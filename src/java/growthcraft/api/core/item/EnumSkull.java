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
package growthcraft.api.core.item;

import growthcraft.api.core.definition.IItemStackFactory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Locale;

/**
 * Skulls!
 */
public enum EnumSkull implements IItemStackFactory {
    SKELETON,
    WITHER,
    ZOMBIE,
    CHAR,
    CREEPER;

    public static final EnumSkull[] VALUES = values();

    public final int meta;
    public final String name;

    EnumSkull() {
        this.name = name().toLowerCase(Locale.ENGLISH);
        this.meta = ordinal();
    }

    public static EnumSkull getByMeta(int meta) {
        if (meta < 0 || meta >= VALUES.length) {
            return SKELETON;
        }
        return VALUES[meta];
    }

    public ItemStack asStack(int size) {
        return new ItemStack(Items.SKULL, size, meta);
    }

    public ItemStack asStack() {
        return asStack(1);
    }
}
