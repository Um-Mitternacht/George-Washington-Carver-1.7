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
package growthcraft.api.cellar.brewing;

import growthcraft.api.cellar.common.Residue;
import growthcraft.api.core.log.ILoggable;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IBrewingRegistry extends ILoggable
{
	/**
	 * Add a new BrewingRecipe
	 *
	 * @param recipe - the recipe to add
	 */
	void addRecipe(@Nonnull BrewingRecipe recipe);

	/**
	 * @param sourceFluid - The source Fluid.
	 * @param raw         - The source/input ItemStack, or MultiItemStack.
	 * @param resultFluid - The resulting Fluid.
	 * @param time        - The time needed for the item/block to be brewed.
	 * @param residue     - The residue that will be produced
	 */
	void addRecipe(@Nonnull FluidStack sourceFluid, @Nonnull Object raw, @Nonnull FluidStack resultFluid, int time, @Nullable Residue residue);

	/**
	 * Get an existing recipe given the ingredients
	 *
	 * @param fluidstack - fluid ingredient
	 * @param itemstack - item ingredient
	 * @return null, or recipe
	 */
	@Nullable BrewingRecipe findRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack);

	List<BrewingRecipe> findRecipes(@Nullable FluidStack fluidstack);
	List<BrewingRecipe> findRecipes(@Nullable ItemStack fermenter);

	boolean isBrewingRecipe(@Nullable FluidStack fluidstack, @Nullable ItemStack itemstack);
	boolean isItemBrewingIngredient(@Nullable ItemStack itemstack);
}
