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
package growthcraft.bamboo.init;

import growthcraft.bamboo.common.block.*;
import growthcraft.bamboo.common.item.ItemBambooSlab;
import growthcraft.core.common.GrcModuleBlocks;
import growthcraft.core.common.block.BlockFenceRope;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.BlockTypeDefinition;
import growthcraft.core.integration.NEI;
import growthcraft.core.registry.FenceRopeRegistry;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;

public class GrcBambooBlocks extends GrcModuleBlocks
{
	public BlockTypeDefinition<BlockBamboo> bambooBlock;
	public BlockTypeDefinition<BlockBambooShoot> bambooShoot;
	public BlockTypeDefinition<BlockBambooStalk> bambooStalk;
	public BlockDefinition bambooLeaves;
	public BlockDefinition bambooFence;
	public BlockDefinition bambooFenceRope;
	public BlockDefinition bambooWall;
	public BlockDefinition bambooStairs;
	public BlockTypeDefinition<? extends BlockSlab> bambooSingleSlab;
	public BlockTypeDefinition<? extends BlockSlab> bambooDoubleSlab;
	public BlockDefinition bambooDoor;
	public BlockDefinition bambooFenceGate;
	public BlockDefinition bambooScaffold;

	@Override
	public void preInit()
	{
		bambooBlock      = newTypedDefinition(new BlockBamboo());
		bambooShoot      = newTypedDefinition(new BlockBambooShoot());
		bambooStalk      = newTypedDefinition(new BlockBambooStalk());
		bambooLeaves     = newDefinition(new BlockBambooLeaves());
		bambooFence      = newDefinition(new BlockBambooFence());
		bambooWall       = newDefinition(new BlockBambooWall());
		bambooStairs     = newDefinition(new BlockBambooStairs());
		bambooSingleSlab = newTypedDefinition(new BlockBambooSlab(false));
		bambooDoubleSlab = newTypedDefinition(new BlockBambooSlab(true));
		bambooDoor       = newDefinition(new BlockBambooDoor());
		bambooFenceGate  = newDefinition(new BlockBambooFenceGate());
		bambooScaffold   = newDefinition(new BlockBambooScaffold());
		bambooFenceRope = newDefinition(new BlockFenceRope(bambooFence.getBlockState(), "grc.bambooFenceRope"));
		FenceRopeRegistry.instance().addEntry(bambooFence.getBlockState(), bambooFenceRope.getBlockState());
	}

	@Override
	public void register()
	{
		bambooBlock.register("grc.bambooBlock");
		bambooShoot.register("grc.bambooShoot");
		bambooStalk.register("grc.bambooStalk");
		bambooLeaves.register("grc.bambooLeaves");
		bambooFence.register("grc.bambooFence");
		bambooFenceRope.register("grc.bambooFenceRope");
		bambooWall.register("grc.bambooWall");
		bambooStairs.register("grc.bambooStairs");
		bambooSingleSlab.register("grc.bambooSingleSlab", ItemBambooSlab.class);
		bambooDoubleSlab.register("grc.bambooDoubleSlab", ItemBambooSlab.class);
		bambooDoor.register("grc.bambooDoor");
		bambooFenceGate.register("grc.bambooFenceGate");
		bambooScaffold.register("grc.bambooScaffold");

		//====================
		// ADDITIONAL PROPS.
		//====================
		Blocks.FIRE.setFireInfo(bambooBlock.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooStalk.getBlockState(), 5, 4);
		Blocks.FIRE.setFireInfo(bambooLeaves.getBlockState(), 30, 60);
		Blocks.FIRE.setFireInfo(bambooFence.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooFenceRope.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooWall.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooStairs.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooSingleSlab.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooDoubleSlab.getBlockState(), 5, 20);
		Blocks.FIRE.setFireInfo(bambooScaffold.getBlockState(), 5, 20);

		NEI.hideItem(bambooDoor.asStack());
		NEI.hideItem(bambooFenceRope.asStack());
	}
}
