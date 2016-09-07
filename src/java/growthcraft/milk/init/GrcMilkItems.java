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

import growthcraft.api.core.item.EnumDye;
import growthcraft.core.common.definition.ItemDefinition;
import growthcraft.core.common.GrcModuleItems;
import growthcraft.milk.common.item.EnumButter;
import growthcraft.milk.common.item.EnumCheeseType;
import growthcraft.milk.common.item.EnumIceCream;
import growthcraft.milk.common.item.EnumYogurt;
import growthcraft.milk.common.item.ItemButter;
import growthcraft.milk.common.item.ItemCheese;
import growthcraft.milk.common.item.ItemCheeseCloth;
import growthcraft.milk.common.item.ItemIceCream;
import growthcraft.milk.common.item.ItemSeedThistle;
import growthcraft.milk.common.item.ItemStarterCulture;
import growthcraft.milk.common.item.ItemStomach;
import growthcraft.milk.common.item.ItemYogurt;
import growthcraft.milk.GrowthCraftMilk;

import net.minecraft.init.Items;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class GrcMilkItems extends GrcModuleItems
{
	public ItemDefinition butter;
	public ItemDefinition cheese;
	public ItemDefinition cheeseCloth;
	public ItemDefinition iceCream;
	public ItemDefinition seedThistle;
	public ItemDefinition starterCulture;
	public ItemDefinition stomach;
	public ItemDefinition yogurt;

	@Override
	public void preInit()
	{
		this.butter = newDefinition(new ItemButter());
		this.cheese = newDefinition(new ItemCheese());
		this.cheeseCloth = newDefinition(new ItemCheeseCloth());
		this.iceCream = newDefinition(new ItemIceCream());
		this.yogurt = newDefinition(new ItemYogurt());
		if (GrowthCraftMilk.getConfig().thistleSeedEnabled)
		{
			this.seedThistle = newDefinition(new ItemSeedThistle());
		}
		this.stomach = newDefinition(new ItemStomach());
		this.starterCulture = newDefinition(new ItemStarterCulture());
	}

	@Override
	public void register()
	{
		butter.register("grcmilk.Butter");
		cheese.register("grcmilk.Cheese");
		cheeseCloth.register("grcmilk.CheeseCloth");
		iceCream.register("grcmilk.IceCream");
		yogurt.register("grcmilk.Yogurt");
		stomach.register("grcmilk.Stomach");
		starterCulture.register("grcmilk.StarterCulture");
		if (seedThistle != null)
		{
			seedThistle.register("grcmilk.SeedThistle");
		}
	}

	@Override
	public void init()
	{
		// Salted Butter
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumButter.SALTED.asStack(), EnumButter.UNSALTED.asStack(), "foodSalt"));

		// Yogurt - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.PLAIN.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL
		));

		// Yogurt - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.CHOCOLATE.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Yogurt - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.GRAPE.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL,
			"foodGrapes"
		));

		// Yogurt - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.APPLE.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL,
			"foodApple"
		));

		// Yogurt - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.HONEY.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL,
			"honeyDrop"
		));

		// Yogurt - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumYogurt.WATERMELON.asStack(),
			Items.MILK_BUCKET,
			starterCulture.asStack(),
			Items.BOWL,
			"foodMelon"
		));

		// Ice Cream - Plain
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.PLAIN.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR
		));

		// Ice Cream - Chocolate
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.CHOCOLATE.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			EnumDye.COCOA_BEANS.asStack()
		));

		// Ice Cream - Grape
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.GRAPE.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodGrapes"
		));

		// Ice Cream - Apple
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.APPLE.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodApple"
		));

		// Ice Cream - Honey
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.HONEY.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"honeyDrop"
		));

		// Ice Cream - Melon
		GameRegistry.addRecipe(new ShapelessOreRecipe(EnumIceCream.WATERMELON.asStack(),
			GrowthCraftMilk.fluids.cream.bucket.asStack(),
			Items.MILK_BUCKET,
			Items.BOWL,
			Items.SUGAR,
			"foodMelon"
		));

		// Cheese Cloth
		GameRegistry.addRecipe(new ShapedOreRecipe(cheeseCloth.asStack(),
			"sss",
			"s s",
			"sss",
			's', Items.STRING
		));

		if (seedThistle != null && GrowthCraftMilk.blocks.thistle != null)
		{
			GameRegistry.addShapelessRecipe(seedThistle.asStack(2), GrowthCraftMilk.blocks.thistle.getBlock());
		}

		for (EnumIceCream e : EnumIceCream.VALUES)
		{
			OreDictionary.registerOre("foodIceCream", e.asStack());
		}

		for (EnumButter e : EnumButter.VALUES)
		{
			OreDictionary.registerOre("foodButter", e.asStack());
		}

		for (EnumCheeseType e : EnumCheeseType.VALUES)
		{
			OreDictionary.registerOre("foodCheese", e.asStack());
		}

		OreDictionary.registerOre("foodYogurt", yogurt.asStack());
		OreDictionary.registerOre("materialStomach", stomach.asStack());
		OreDictionary.registerOre("rennetSource", stomach.asStack());
		OreDictionary.registerOre("foodOffal", stomach.asStack());
		OreDictionary.registerOre("materialStarterCulture", starterCulture.asStack());
		OreDictionary.registerOre("materialCheeseCloth", cheeseCloth.asStack());

		if (seedThistle != null)
		{
			OreDictionary.registerOre("listAllseed", seedThistle.asStack());
		}

		OreDictionary.registerOre("foodMelon", Items.MELON);
		OreDictionary.registerOre("foodFruit", Items.MELON);
	}
}
