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
package growthcraft.milk.client.render.item;

import growthcraft.core.client.render.item.GrcItemRenderer;
import growthcraft.milk.client.model.ModelCheeseBlock;
import growthcraft.milk.client.resource.GrcMilkResources;
import growthcraft.milk.common.item.EnumCheeseStage;
import growthcraft.milk.common.item.EnumCheeseType;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ItemRendererCheeseBlock extends GrcItemRenderer {
    @Override
    protected void render(ItemRenderType type, ItemStack stack, Object... data) {
        final Item item = stack.getItem();
        if (item instanceof ItemBlockCheeseBlock) {
            final ItemBlockCheeseBlock cheeseBlock = (ItemBlockCheeseBlock) item;
            final EnumCheeseType cheese = cheeseBlock.getCheeseType(stack);
            final EnumCheeseStage cheeseStage = cheeseBlock.getCheeseStage(stack);
            final ResourceLocation texture = GrcMilkResources.INSTANCE.texturesCheeseBlock.get(cheese).get(cheeseStage);
            GL11.glPushMatrix();
            {
                bindTexture(texture);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.0f, -1.5f, 0.0f);
                GrcMilkResources.INSTANCE.modelCheeseBlock.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ModelCheeseBlock.SCALE);
            }
            GL11.glPopMatrix();
        }
    }
}
