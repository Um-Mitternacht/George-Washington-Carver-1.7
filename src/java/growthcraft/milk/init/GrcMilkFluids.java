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
package growthcraft.milk.init;

import growthcraft.api.cellar.booze.Booze;
import growthcraft.api.cellar.booze.BoozeTag;
import growthcraft.api.cellar.common.Residue;
import growthcraft.api.cellar.util.ICellarBoozeBuilder;
import growthcraft.api.core.CoreRegistry;
import growthcraft.api.core.GrcFluid;
import growthcraft.api.core.effect.EffectExtinguish;
import growthcraft.api.core.effect.EffectList;
import growthcraft.api.core.effect.EffectUtils;
import growthcraft.api.core.effect.IEffect;
import growthcraft.api.core.fluids.TaggedFluidStacks;
import growthcraft.api.core.item.OreItemStacks;
import growthcraft.api.core.util.StringUtils;
import growthcraft.api.core.util.TickUtils;
import growthcraft.api.milk.MilkFluidTags;
import growthcraft.api.milk.MilkRegistry;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.cellar.common.definition.BlockBoozeDefinition;
import growthcraft.cellar.common.definition.ItemBucketBoozeDefinition;
import growthcraft.cellar.common.item.EnumYeast;
import growthcraft.cellar.common.item.ItemBoozeBottle;
import growthcraft.cellar.util.BoozeRegistryHelper;
import growthcraft.cellar.util.BoozeUtils;
import growthcraft.core.common.GrcModuleBase;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.definition.ItemTypeDefinition;
import growthcraft.core.common.item.ItemFoodBottleFluid;
import growthcraft.core.eventhandler.EventHandlerBucketFill;
import growthcraft.core.integration.forestry.ForestryFluids;
import growthcraft.core.util.FluidFactory;
import growthcraft.milk.GrowthCraftMilk;
import growthcraft.milk.common.effect.EffectEvilBoozeMilk;
import growthcraft.milk.common.effect.EffectMilk;
import growthcraft.milk.common.item.EnumCheeseType;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrcMilkFluids extends GrcModuleBase
{
	private static final String kumisBasename = "grcmilk.Kumis";

	public FluidFactory.FluidDetails butterMilk;
	public FluidFactory.FluidDetails cream;
	public FluidFactory.FluidDetails milk;
	public FluidFactory.FluidDetails curds;
	public FluidFactory.FluidDetails rennet;
	public FluidFactory.FluidDetails skimMilk;
	public FluidFactory.FluidDetails whey;
	public FluidFactory.FluidDetails pasteurizedMilk;
	public Map<EnumCheeseType, FluidFactory.FluidDetails> cheeses = new HashMap<EnumCheeseType, FluidFactory.FluidDetails>();
	public Map<Fluid, EnumCheeseType> fluidToCheeseType = new HashMap<Fluid, EnumCheeseType>();
	public Booze[] kumisFluids = new Booze[6];
	public BlockBoozeDefinition[] kumisFluidBlocks = new BlockBoozeDefinition[kumisFluids.length];
	public ItemBucketBoozeDefinition[] kumisFluidBuckets = new ItemBucketBoozeDefinition[kumisFluids.length];
	public ItemDefinition kumisBottle;

	private void preInitCheeseFluids()
	{
		for (EnumCheeseType cheese : EnumCheeseType.VALUES)
		{
			final String fluidName = "grcmilk.Cheese" + StringUtils.capitalize(cheese.name);
			final Fluid fluid = new GrcFluid(fluidName).setColor(cheese.getColor());
			final FluidFactory.FluidDetails details = FluidFactory.instance().create(fluid, FluidFactory.FEATURE_NONE);
			cheeses.put(cheese, details);
			if (details.block != null) details.block.getBlockState().setColor(cheese.getColor()).//setBlockTextureName("grcmilk:fluids/milk");
			details.setItemColor(cheese.getColor());
			fluidToCheeseType.put(fluid, cheese);
		}
	}

	private void preInitKumisFluids()
	{
		this.kumisBottle = new ItemDefinition(new ItemBoozeBottle(kumisFluids));
		BoozeRegistryHelper.initializeBoozeFluids(kumisBasename, kumisFluids);
		for (Booze booze : kumisFluids)
		{
			booze.setColor(GrowthCraftMilk.getConfig().kumisColor).setDensity(1030).setViscosity(3000);
		}
		BoozeRegistryHelper.initializeBooze(kumisFluids, kumisFluidBlocks, kumisFluidBuckets);
		BoozeRegistryHelper.setBoozeFoodStats(kumisFluids, 1, -0.2f);
		BoozeRegistryHelper.setBoozeFoodStats(kumisFluids[0], 1, 0.2f);
		kumisFluids[5].setColor(GrowthCraftMilk.getConfig().poisonedKumisColor);
		kumisFluidBlocks[5].getBlockState().refreshColor();
		for (BlockBoozeDefinition def : kumisFluidBlocks)
		{
			def.getBlockState().//setBlockTextureName("grcmilk:fluids/milk");
		}
	}

	private void preInitFluids()
	{
		final IEffect milkEffect = EffectMilk.create(GrowthCraftCellar.potionTipsy);
		if (GrowthCraftMilk.getConfig().milkEnabled)
		{
			this.milk = FluidFactory.instance().create(
				new GrcFluid("grcmilk.Milk").setDensity(1030).setViscosity(3000),
				FluidFactory.FEATURE_FOOD_BOTTLE | FluidFactory.FEATURE_BLOCK);
			milk.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>(new ItemFoodBottleFluid(milk.getFluid(), 4, 0.3f, false));
			milk.foodBottle.getItem().setEffect(milkEffect).setAlwaysEdible();
			milk.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFFFF);
			milk.block.getBlockState().//setBlockTextureName("grcmilk:fluids/milk");
		}

		this.butterMilk = FluidFactory.instance().create(new GrcFluid("grcmilk.ButterMilk"), FluidFactory.FEATURE_ALL_EDIBLE);
		butterMilk.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>(new ItemFoodBottleFluid(butterMilk.getFluid(), 6, 0.4f, false));
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthCraftMilk.getConfig().fantasyMilkEffects)
			{
				// Idea from here: http://www.altmedicine101.com/buttermilk
				list.add(new EffectExtinguish());
				list.add(EffectUtils.createAddPotionEffect(MobEffects.FIRE_RESISTANCE, TickUtils.seconds(15), 0));
			}
			butterMilk.foodBottle.getItem().setEffect(list).setAlwaysEdible();
		}
		butterMilk.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFEE7);
		butterMilk.block.getBlockState().//setBlockTextureName("grcmilk:fluids/buttermilk");

		this.cream = FluidFactory.instance().create(new GrcFluid("grcmilk.Cream"));
		cream.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFDD0);
		cream.block.getBlockState().//setBlockTextureName("grcmilk:fluids/cream");

		this.curds = FluidFactory.instance().create(new GrcFluid("grcmilk.Curds"));
		curds.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFFF6);
		curds.block.getBlockState().//setBlockTextureName("grcmilk:fluids/milk");

		this.rennet = FluidFactory.instance().create(new GrcFluid("grcmilk.Rennet"));
		rennet.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0x877243);
		rennet.block.getBlockState().//setBlockTextureName("grcmilk:fluids/rennet");

		this.skimMilk = FluidFactory.instance().create(new GrcFluid("grcmilk.SkimMilk"), FluidFactory.FEATURE_ALL_EDIBLE);
		skimMilk.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>(new ItemFoodBottleFluid(skimMilk.getFluid(), 2, 0.2f, false));
		{
			final EffectList list = new EffectList();
			list.add(milkEffect);
			if (GrowthCraftMilk.getConfig().fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.SPEED, TickUtils.seconds(15), 0));
			}
			skimMilk.foodBottle.getItem().setEffect(list).setAlwaysEdible();
		}
		skimMilk.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFFFA);
		skimMilk.block.getBlockState().//setBlockTextureName("grcmilk:fluids/skimmilk");

		this.whey = FluidFactory.instance().create(new GrcFluid("grcmilk.Whey"), FluidFactory.FEATURE_ALL_EDIBLE);
		whey.foodBottle = new ItemTypeDefinition<ItemFoodBottleFluid>(new ItemFoodBottleFluid(whey.getFluid(), 1, 0.1f, false));
		{
			final EffectList list = new EffectList();
			if (GrowthCraftMilk.getConfig().fantasyMilkEffects)
			{
				list.add(EffectUtils.createAddPotionEffect(MobEffects.STRENGTH, TickUtils.seconds(10), 0));
				list.add(EffectUtils.createAddPotionEffect(MobEffects.RESISTANCE, TickUtils.seconds(10), 0));
			}
			whey.foodBottle.getItem().setEffect(list).setAlwaysEdible();
		}
		whey.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0x94a860);
		whey.block.getBlockState().//setBlockTextureName("grcmilk:fluids/whey");

		this.pasteurizedMilk = FluidFactory.instance().create(new GrcFluid("grcmilk.PasteurizedMilk"));
		pasteurizedMilk.setCreativeTab(GrowthCraftMilk.creativeTab).setItemColor(0xFFFFFA);
		pasteurizedMilk.block.getBlockState().//setBlockTextureName("grcmilk:fluids/milk");

		preInitCheeseFluids();
		preInitKumisFluids();
	}

	@Override
	public void preInit()
	{
		preInitFluids();
	}

	public List<Fluid> getMilkFluids()
	{
		final List<Fluid> milks = new ArrayList<Fluid>();
		if (milk != null) milks.add(milk.getFluid());
		if (ForestryFluids.MILK.exists()) milks.add(ForestryFluids.MILK.getFluid());
		// Automagy Milk
		final Fluid fluidmilk = FluidRegistry.getFluid("fluidmilk");
		if (fluidmilk != null) milks.add(fluidmilk);
		return milks;
	}

	private void registerOres()
	{
		if (milk != null)
		{
			OreDictionary.registerOre("bottleMilk", milk.foodBottle.asStack());
		}
		// Milk bucket is the vanilla milk bucket, derp
		OreDictionary.registerOre("bucketMilk", Items.MILK_BUCKET);
		if (skimMilk != null)
		{
			OreDictionary.registerOre("bottleSkimmilk", skimMilk.foodBottle.asStack());
			OreDictionary.registerOre("bucketSkimmilk", skimMilk.bucket.asStack());
		}
		if (butterMilk != null)
		{
			OreDictionary.registerOre("bottleButtermilk", butterMilk.foodBottle.asStack());
			OreDictionary.registerOre("bucketButtermilk", butterMilk.bucket.asStack());
		}
		if (whey != null)
		{
			OreDictionary.registerOre("bottleWhey", whey.foodBottle.asStack());
			OreDictionary.registerOre("bucketWhey", whey.bucket.asStack());
			// https://github.com/GrowthcraftCE/Growthcraft-1.7/issues/419
			OreDictionary.registerOre("foodStock", whey.foodBottle.asStack());
		}
		if (cream != null)
		{
			OreDictionary.registerOre("bottleCream", cream.bottle.asStack());
			OreDictionary.registerOre("bucketCream", cream.bucket.asStack());
		}
	}

	private void registerFermentations()
	{
		final IEffect milkEffect = EffectMilk.create(GrowthCraftCellar.potionTipsy);
		final IEffect evilMilkEffect = new EffectEvilBoozeMilk();

		final FluidStack[] fs = new FluidStack[kumisFluids.length];
		for (int i = 0; i < kumisFluids.length; ++i)
		{
			fs[i] = new FluidStack(kumisFluids[i], 1);
		}

		final int fermentTime = GrowthCraftCellar.getConfig().fermentTime;
		final ICellarBoozeBuilder builder = GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[0]);
		builder
			.tags(BoozeTag.FERMENTED)
			.getEffect()
				.setTipsy(0.10f, 900)
				.addEffect(milkEffect);

		final TaggedFluidStacks milkStacks = new TaggedFluidStacks(1, "milk");
		builder.fermentsFrom(milkStacks, EnumYeast.BREWERS.asStack(), fermentTime);
		builder.fermentsFrom(milkStacks, new ItemStack(Items.NETHER_WART), (int)(fermentTime * 0.66));

		GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[1])
			.tags(BoozeTag.FERMENTED, BoozeTag.POTENT)
			.fermentsFrom(fs[0], new OreItemStacks("dustGlowstone"), fermentTime)
			.fermentsFrom(fs[2], new OreItemStacks("dustGlowstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.05f), 900)
				.addEffect(milkEffect);

		GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[2])
			.tags(BoozeTag.FERMENTED, BoozeTag.EXTENDED)
			.fermentsFrom(fs[0], new OreItemStacks("dustRedstone"), fermentTime)
			.fermentsFrom(fs[1], new OreItemStacks("dustRedstone"), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.02f), 900)
				.addEffect(milkEffect);

		GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[3])
			.tags(BoozeTag.FERMENTED, BoozeTag.HYPER_EXTENDED)
			.fermentsFrom(fs[1], EnumYeast.ETHEREAL.asStack(), fermentTime)
			.fermentsFrom(fs[2], EnumYeast.ETHEREAL.asStack(), fermentTime)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.02f), 900)
				.addEffect(milkEffect);

		GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[4])
			.tags(BoozeTag.FERMENTED, BoozeTag.INTOXICATED)
			.fermentsFrom(fs[1], EnumYeast.ORIGIN.asStack(), fermentTime)
			.fermentsFrom(fs[2], EnumYeast.ORIGIN.asStack(), fermentTime)
			.getEffect()
				.setTipsy(0.50f, 900)
				.addEffect(milkEffect);

		GrowthCraftCellar.boozeBuilderFactory.create(kumisFluids[5])
			.tags(BoozeTag.FERMENTED, BoozeTag.POISONED)
			.getEffect()
				.setTipsy(BoozeUtils.alcoholToTipsy(0.02f), 900)
				.addEffect(evilMilkEffect);
	}

	@Override
	public void register()
	{
		kumisBottle.register("grcmilk.KumisBottle");

		if (milk != null)
		{
			milk.registerObjects("grcmilk", "Milk");
			// ensure that we don't already have some variation of milk present
			if (FluidRegistry.getFluid("milk") == null)
			{
				FluidContainerRegistry.registerFluidContainer(milk.getFluid(), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.BUCKET, 1));
				EventHandlerBucketFill.instance().register(milk.getFluidBlock(), new ItemStack(Items.MILK_BUCKET, 1));
			}
		}
		butterMilk.registerObjects("grcmilk", "ButterMilk");
		cream.registerObjects("grcmilk", "Cream");
		curds.registerObjects("grcmilk", "Curds");
		rennet.registerObjects("grcmilk", "Rennet");
		skimMilk.registerObjects("grcmilk", "SkimMilk");
		whey.registerObjects("grcmilk", "Whey");
		pasteurizedMilk.registerObjects("grcmilk", "PasteurizedMilk");

		for (Map.Entry<EnumCheeseType, FluidFactory.FluidDetails> pair : cheeses.entrySet())
		{
			pair.getValue().registerObjects("grcmilk", "Cheese" + StringUtils.capitalize(pair.getKey().name));
		}

		BoozeRegistryHelper.registerBooze(kumisFluids, kumisFluidBlocks, kumisFluidBuckets, kumisBottle, kumisBasename, null);

		CoreRegistry.instance().fluidDictionary().addFluidTags(cream.getFluid(), MilkFluidTags.CREAM);
		CoreRegistry.instance().fluidDictionary().addFluidTags(curds.getFluid(), MilkFluidTags.MILK_CURDS);
		CoreRegistry.instance().fluidDictionary().addFluidTags(rennet.getFluid(), MilkFluidTags.RENNET);
		CoreRegistry.instance().fluidDictionary().addFluidTags(whey.getFluid(), MilkFluidTags.WHEY);

		GrowthCraftCellar.boozeBuilderFactory.create(rennet.fluid.getFluid())
			.brewsFrom(new FluidStack(FluidRegistry.WATER, 1000), new OreItemStacks("rennetSource"), TickUtils.minutes(1), null);

		GrowthCraftCellar.boozeBuilderFactory.create(pasteurizedMilk.fluid.getFluid())
			.brewsFrom(skimMilk.fluid.asFluidStack(250), new ItemStack(Items.SUGAR), TickUtils.minutes(1), new Residue(GrowthCraftMilk.items.starterCulture.asStack(1), 1.0f));

		GrowthCraftCellar.boozeBuilderFactory.create(skimMilk.getFluid())
			.culturesTo(250, GrowthCraftMilk.items.starterCulture.asStack(), 0.7f, TickUtils.seconds(10));

		GrowthCraftMilk.userApis.churnRecipes.addDefault(
			cream.fluid.asFluidStack(1000),
			butterMilk.fluid.asFluidStack(500),
			GrowthCraftMilk.items.butter.asStack(2),
			16);

		for (Map.Entry<EnumCheeseType, FluidFactory.FluidDetails> pair : cheeses.entrySet())
		{
			CoreRegistry.instance().fluidDictionary().addFluidTags(pair.getValue().getFluid(), MilkFluidTags.CHEESE);
		}

		registerOres();
		registerFermentations();
	}

	@Override
	public void init()
	{
		final List<Fluid> milks = getMilkFluids();
		for (Fluid f : milks)
		{
			CoreRegistry.instance().fluidDictionary().addFluidTags(f, MilkFluidTags.MILK);

			MilkRegistry.instance().pancheon().addRecipe(
				new FluidStack(f, 1000),
				cream.fluid.asFluidStack(333), skimMilk.fluid.asFluidStack(666),
				TickUtils.minutes(1));
		}
	}
}
