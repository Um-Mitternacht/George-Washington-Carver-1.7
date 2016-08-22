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
package growthcraft.bees.integration;

import java.util.ArrayList;
import com.google.common.collect.ImmutableMap;

import growthcraft.api.bees.BeesFluidTag;
import growthcraft.api.cellar.booze.BoozeTag;
import growthcraft.api.core.CoreRegistry;
import growthcraft.bees.GrowthCraftBees;
import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.block.BlockBeeBoxForestry;
import growthcraft.bees.common.block.EnumBeeBoxForestry;
import growthcraft.bees.common.item.ItemBlockBeeBox;
import growthcraft.core.common.definition.BlockTypeDefinition;
import growthcraft.core.integration.forestry.ForestryFluids;
import growthcraft.core.integration.forestry.ForestryItems;
import growthcraft.core.integration.ForestryModuleBase;

import cpw.mods.fml.common.registry.GameRegistry;

import cpw.mods.fml.common.Optional;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForestryModule extends ForestryModuleBase
{
	public ForestryModule()
	{
		super(GrowthCraftBees.MOD_ID);
	}

	private void maybeAddBee(Item item)
	{
		if (item != null)
		{
			GrowthCraftBees.getUserBeesConfig().addDefault(item).setComment("From Forestry");
		}
	}

	@Override
	protected void doPreInit()
	{
		final int beeboxCount = EnumBeeBoxForestry.VALUES.length;
		GrowthCraftBees.blocks.beeBoxesForestry = new ArrayList<BlockTypeDefinition<? extends BlockBeeBox>>();
		GrowthCraftBees.blocks.beeBoxesForestryFireproof = new ArrayList<BlockTypeDefinition<? extends BlockBeeBox>>();

		int i = 0;
		int offset = 0;
		for (EnumBeeBoxForestry[] row : EnumBeeBoxForestry.ROWS)
		{
			final BlockTypeDefinition<? extends BlockBeeBox> beeBox = GrowthCraftBees.blocks.newTypedDefinition(new BlockBeeBoxForestry(row, offset, i, false));
			final BlockTypeDefinition<? extends BlockBeeBox> beeBoxFP = GrowthCraftBees.blocks.newTypedDefinition(new BlockBeeBoxForestry(row, offset, i, true));
			beeBox.getBlock().setFlammability(20).setFireSpreadSpeed(5).setHarvestLevel("axe", 0);
			beeBoxFP.getBlock().setHarvestLevel("axe", 0);
			GrowthCraftBees.blocks.beeBoxesForestry.add(beeBox);
			GrowthCraftBees.blocks.beeBoxesForestryFireproof.add(beeBoxFP);
			beeBox.register(String.format("grc.BeeBox.Forestry.%d.%s", i, "Normal"), ItemBlockBeeBox.class);
			beeBoxFP.register(String.format("grc.BeeBox.Forestry.%d.%s", i, "Fireproof"), ItemBlockBeeBox.class);
			i++;
			offset += row.length;
		}
	}

	@Override
	protected void doInit()
	{
		maybeAddBee(GameRegistry.findItem(modID, "beeQueenGE"));
		maybeAddBee(GameRegistry.findItem(modID, "beeDroneGE"));
		maybeAddBee(GameRegistry.findItem(modID, "beePrincessGE"));
	}

	@Override
	protected void doLateRegister()
	{
		for (EnumBeeBoxForestry en : EnumBeeBoxForestry.VALUES)
		{
			if (en == null) continue;

			{
				final BlockTypeDefinition<? extends BlockBeeBox> beeBox = GrowthCraftBees.blocks.beeBoxesForestry.get(en.row);
				if (beeBox != null)
				{
					final ItemStack planks = en.getForestryPlanksStack();
					if (planks != null)
					{
						GameRegistry.addShapedRecipe(beeBox.asStack(1, en.col), " A ", "A A", "AAA", 'A', planks);
					}
				}
			}
			{
				final BlockTypeDefinition<? extends BlockBeeBox> beeBoxFP = GrowthCraftBees.blocks.beeBoxesForestryFireproof.get(en.row);
				if (beeBoxFP != null)
				{
					final ItemStack planks = en.getForestryFireproofPlanksStack();
					if (planks != null)
					{
						GameRegistry.addShapedRecipe(beeBoxFP.asStack(1, en.col), " A ", "A A", "AAA", 'A', planks);
					}
				}
			}
		}
	}

	@Override
	@Optional.Method(modid="Forestry")
	protected void integrate()
	{
		if (ForestryFluids.SHORT_MEAD.exists()) CoreRegistry.instance().fluidDictionary().addFluidTags(ForestryFluids.SHORT_MEAD.getFluid(), BoozeTag.YOUNG, BeesFluidTag.MEAD);

		final ItemStack emptyComb = GrowthCraftBees.items.honeyCombEmpty.asStack();
		final ItemStack fullComb = GrowthCraftBees.items.honeyCombFilled.asStack();
		if (ForestryItems.BEESWAX.exists()) recipes().centrifugeManager.addRecipe(20, emptyComb, ImmutableMap.of(ForestryItems.BEESWAX.asStack(), 1.0f));

		if (ForestryItems.BEESWAX.exists() && ForestryItems.HONEY_DROP.exists() && ForestryItems.HONEYDEW.exists())
		{
			recipes().centrifugeManager.addRecipe(20, fullComb,
				ImmutableMap.of(
					ForestryItems.BEESWAX.asStack(), 1.0f,
					ForestryItems.HONEY_DROP.asStack(), 0.9f,
					ForestryItems.HONEYDEW.asStack(), 0.1f
				)
			);
		}
	}
}

