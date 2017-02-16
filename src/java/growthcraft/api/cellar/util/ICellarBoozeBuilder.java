/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package growthcraft.api.cellar.util;

import growthcraft.api.cellar.booze.BoozeEffect;
import growthcraft.api.cellar.common.Residue;
import growthcraft.api.core.fluids.FluidTag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Now you can make the same awesome we use internally
 */
public interface ICellarBoozeBuilder {
    ICellarBoozeBuilder tags(FluidTag... tags);

    ICellarBoozeBuilder brewsTo(@Nonnull FluidStack result, @Nonnull Object stack, int time, @Nullable Residue residue);

    ICellarBoozeBuilder brewsFrom(@Nonnull FluidStack src, @Nonnull Object stack, int time, @Nullable Residue residue);

    ICellarBoozeBuilder fermentsTo(@Nonnull FluidStack resultFluid, @Nonnull Object stack, int time);

    ICellarBoozeBuilder fermentsFrom(@Nonnull Object srcFluid, @Nonnull Object stack, int time);

    ICellarBoozeBuilder pressesFrom(@Nonnull Object inputStack, int time, int amount, @Nullable Residue residue);

    ICellarBoozeBuilder culturesTo(int amount, @Nonnull ItemStack stack, float heat, int time);

    BoozeEffect getEffect();
}
