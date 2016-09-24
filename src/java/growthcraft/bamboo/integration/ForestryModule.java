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
package growthcraft.bamboo.integration;

import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.integration.ForestryModuleBase;
import growthcraft.core.integration.forestry.ForestryFluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

public class ForestryModule extends ForestryModuleBase
{
	public ForestryModule()
	{
		super(GrowthCraftBamboo.MOD_ID);
	}

	@Override
	@Optional.Method(modid="Forestry")
	protected void integrate()
	{
		final int saplingYield = getActiveMode().getIntegerSetting("fermenter.yield.sapling");

		final ItemStack bamboo = GrowthCraftBamboo.items.bamboo.asStack();
		final ItemStack bambooShoot = GrowthCraftBamboo.blocks.bambooShoot.asStack();
		final ItemStack bambooShootFood = GrowthCraftBamboo.items.bambooShootFood.asStack();
		final ItemStack bambooLeaves = GrowthCraftBamboo.blocks.bambooLeaves.asStack();

		ForestryRecipeUtils.addFermenterRecipes(bamboo, saplingYield, ForestryFluids.BIOMASS.asFluidStack());
		ForestryRecipeUtils.addFermenterRecipes(bambooShoot, saplingYield, ForestryFluids.BIOMASS.asFluidStack());
		ForestryRecipeUtils.addFermenterRecipes(bambooShootFood, saplingYield, ForestryFluids.BIOMASS.asFluidStack());

		Backpack.FORESTERS.add(bamboo);
		Backpack.FORESTERS.add(bambooShoot);
		Backpack.FORESTERS.add(bambooShootFood);
		Backpack.FORESTERS.add(bambooLeaves);
	}
}
