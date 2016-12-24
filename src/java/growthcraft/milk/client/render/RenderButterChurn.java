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
package growthcraft.milk.client.render;

import growthcraft.milk.client.model.ModelButterChurn;
import growthcraft.milk.client.resource.GrcMilkResources;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.lwjgl.opengl.GL11;

//import net.minecraft.client.renderer.Tessellator;

public class RenderButterChurn implements ISimpleBlockRenderingHandler
{
	public static int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderId()
	{
		return RENDER_ID;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == RENDER_ID)
		{
			GL11.glPushMatrix();
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(GrcMilkResources.INSTANCE.textureButterChurn);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0f, -1.0f, 0.0f);
				GrcMilkResources.INSTANCE.modelButterChurn.render(null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, ModelButterChurn.SCALE);
			}
			GL11.glPopMatrix();
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, BlockPos pos, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == RENDER_ID)
		{
			return true;
		}
		return false;
	}
}
