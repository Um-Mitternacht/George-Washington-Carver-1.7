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
package growthcraft.api.milk.churn;

import growthcraft.api.core.log.ILoggable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IChurnRegistry extends ILoggable
{
	void addRecipe(@Nonnull IChurnRecipe recipe);

	/**
	 * @param inputFluid - input fluid
	 * @param outputFluid - fluid output in churn
	 * @param outputItem - item output in churn
	 * @param churns - how many times must a user churn the fluid to produce results?
	 */
	void addRecipe(@Nonnull FluidStack inputFluid, @Nonnull FluidStack outputFluid, @Nullable ItemStack outputItem, int churns);

	/**
	 * Determines if the provided fluid is an input ingredient
	 *
	 * @param fluid - fluid to look for
	 * @return true, the fluid is an input ingredient, false otherwise
	 */
	boolean isFluidIngredient(@Nullable Fluid fluid);

	/**
	 * Determines if the provided fluid is an input ingredient
	 *
	 * @param fluid - fluid stack to check
	 * @return true, the fluid is an input ingredient, false otherwise
	 */
	boolean isFluidIngredient(@Nullable FluidStack fluid);

	/**
	 * @param inputFluid - recipe fluid
	 * @return recipe if matched
	 */
	@Nullable IChurnRecipe getRecipe(@Nullable FluidStack inputFluid);
}
