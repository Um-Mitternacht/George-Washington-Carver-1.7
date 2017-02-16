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
package growthcraft.bees.integration;

import growthcraft.bees.GrowthCraftBees;
import growthcraft.bees.common.block.BlockBeeBoxBotania;
import growthcraft.bees.common.item.ItemBlockBeeBox;
import growthcraft.core.integration.ModIntegrationBase;
import growthcraft.core.integration.botania.BotaniaPlatform;
import growthcraft.core.integration.botania.EnumBotaniaWoodType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BotaniaModule extends ModIntegrationBase {
    public BotaniaModule() {
        super(GrowthCraftBees.MOD_ID, BotaniaPlatform.MOD_ID);
    }

    @Override
    public void doPreInit() {
        GrowthCraftBees.blocks.beeBoxBotania = GrowthCraftBees.blocks.newTypedDefinition(new BlockBeeBoxBotania());
    }

    @Override
    public void doRegister() {
        GrowthCraftBees.blocks.beeBoxBotania.register("grc.BeeBox.Botania", ItemBlockBeeBox.class);
    }

    @Override
    protected void doLateRegister() {
        for (EnumBotaniaWoodType type : EnumBotaniaWoodType.VALUES) {
            final ItemStack planks = type.asPlanksItemStack();
            if (planks != null) {
                GameRegistry.addShapedRecipe(GrowthCraftBees.blocks.beeBoxBotania.asStack(1, type.meta), " A ", "A A", "AAA", 'A', planks);
            }
        }
    }
}
