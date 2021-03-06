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
package growthcraft.core.common;

import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import net.minecraft.item.Item;

import java.util.LinkedList;
import java.util.List;

public class GrcModuleItems extends GrcModuleBase {
    // All items that had defintions created via the interface
    public final List<ItemTypeDefinition<? extends Item>> all = new LinkedList<ItemTypeDefinition<? extends Item>>();

    /**
     * Creates a basic ItemDefintion from the given item
     *
     * @param item the item to wrap
     * @return definition
     */
    public ItemDefinition newDefinition(Item item) {
        final ItemDefinition def = new ItemDefinition(item);
        all.add(def);
        return def;
    }

    /**
     * Creates a ItemTypeDefintion from the given item
     *
     * @param item the item to wrap and type by
     * @return typed definition
     */
    public <T extends Item> ItemTypeDefinition<T> newTypedDefinition(T item) {
        final ItemTypeDefinition<T> def = new ItemTypeDefinition<T>(item);
        all.add(def);
        return def;
    }
}
