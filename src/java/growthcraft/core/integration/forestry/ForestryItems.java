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
package growthcraft.core.integration.forestry;

import growthcraft.api.core.definition.IItemStackFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public enum ForestryItems implements IItemStackFactory {
    BEESWAX("beeswax"),
    HONEY_DROP("honeyDrop"),
    HONEYDEW("honeydew");

    public final String name;
    public final int meta;

    ForestryItems(String n) {
        this.name = n;
        this.meta = 0;
    }

    public Item getItem() {
        return GameRegistry.findItem("Forestry", name);
    }

    @Override
    public ItemStack asStack(int size) {
        final Item item = getItem();
        if (item == null) return null;
        return new ItemStack(item, size, meta);
    }

    @Override
    public ItemStack asStack() {
        return asStack(1);
    }

    public boolean exists() {
        return getItem() != null;
    }
}
