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
package growthcraft.api.core.fluids;

import growthcraft.api.core.definition.IFluidStackFactory;
import growthcraft.api.core.nbt.NBTHelper;
import growthcraft.api.core.util.HashKey;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * As the name implies, this class is used in place of a List for Fluid keys
 */
public class FluidKey extends HashKey implements IFluidStackFactory {
    public final Fluid fluid;
    public final NBTTagCompound compoundTag;

    /**
     * @param pFluid - the fluid to set
     * @param tag    - a tag compound to set
     *               If the tag hasNoTags(), the FluidKey will set the compoundTag to null
     */
    public FluidKey(@Nonnull Fluid pfluid, @Nullable NBTTagCompound tag) {
        super();
        this.fluid = pfluid;
        this.compoundTag = NBTHelper.compoundTagPresence(tag);
        generateHashCode();
    }

    public FluidKey(@Nonnull Fluid pfluid) {
        this(pfluid, null);
    }

    public FluidKey(@Nonnull FluidStack pfluidStack) {
        this(pfluidStack.getFluid(), pfluidStack.tag);
    }

    private void generateHashCode() {
        this.hash = fluid.hashCode();
        this.hash = 31 * hash + (compoundTag != null ? compoundTag.hashCode() : 0);
    }

    @Override
    public FluidStack asFluidStack(int size) {
        return new FluidStack(fluid, size, NBTHelper.copyCompoundTag(compoundTag));
    }

    @Override
    public FluidStack asFluidStack() {
        return asFluidStack(1);
    }
}
