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
package growthcraft.cellar.init;

import growthcraft.cellar.common.block.*;
import growthcraft.cellar.common.itemblock.ItemBlockFermentBarrel;
import growthcraft.core.common.GrcModuleBlocks;
import growthcraft.core.common.definition.BlockDefinition;


public class GrcCellarBlocks extends GrcModuleBlocks
{
	public BlockDefinition brewKettle;
	public BlockDefinition cultureJar;
	public BlockDefinition fermentBarrel;
	public BlockDefinition fruitPress;
	public BlockDefinition fruitPresser;

	@Override
	public void preInit()
	{
		this.brewKettle    = newDefinition(new BlockBrewKettle());
		this.cultureJar    = newDefinition(new BlockCultureJar());
		this.fermentBarrel = newDefinition(new BlockFermentBarrel());
		this.fruitPress    = newDefinition(new BlockFruitPress());
		this.fruitPresser  = newDefinition(new BlockFruitPresser());
	}

	@Override
	public void register()
	{
		fruitPress.register("grc.fruitPress");
		fruitPresser.register("grc.fruitPresser");
		brewKettle.register("grc.brewKettle");
		fermentBarrel.register("grc.fermentBarrel", ItemBlockFermentBarrel.class);
		cultureJar.register("grc.fermentJar");

		NEI.hideItem(fruitPresser.asStack());
	}
}
