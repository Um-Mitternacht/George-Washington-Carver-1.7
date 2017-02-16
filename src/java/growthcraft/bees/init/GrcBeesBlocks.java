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
package growthcraft.bees.init;

import growthcraft.bees.common.block.BlockBeeBox;
import growthcraft.bees.common.block.BlockBeeHive;
import growthcraft.bees.common.item.ItemBlockBeeBox;
import growthcraft.core.common.GrcModuleBlocks;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.BlockTypeDefinition;

import java.util.List;

public class GrcBeesBlocks extends GrcModuleBlocks {
    public BlockTypeDefinition<? extends BlockBeeBox> beeBox;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxBamboo;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxNatura;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxBiomesOPlenty;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxBotania;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxNether;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxThaumcraft;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxFossilsAndArchaeology;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxArsMagica2;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxTotemic;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxExtraBiomesXL;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxHighlands;
    public BlockTypeDefinition<? extends BlockBeeBox> beeBoxJurassicraft;
    public List<BlockTypeDefinition<? extends BlockBeeBox>> beeBoxesForestry;
    public List<BlockTypeDefinition<? extends BlockBeeBox>> beeBoxesForestryFireproof;
    public BlockDefinition beeHive;

    @Override
    public void preInit() {
        this.beeBox = newTypedDefinition(new BlockBeeBox());
        beeBox.getBlockState().setFlammability(20).setFireSpreadSpeed(5).setHarvestLevel("axe", 0);
        this.beeHive = newDefinition(new BlockBeeHive());
    }

    @Override
    public void register() {
        beeBox.register("grc.beeBox", ItemBlockBeeBox.class);
        beeHive.register("grc.beeHive");
    }
}
