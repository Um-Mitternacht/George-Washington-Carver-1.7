package growthcraft.fishtrap.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderFishTrap implements ISimpleBlockRenderingHandler
{
	public static final int id = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		if (modelId == id)
		{

		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, BlockPos pos, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == id)
		{
			final int meta = world.getBlockState(x, y, z);
			renderer.renderStandardBlock(block, x, y, z);
			final Tessellator tes = Tessellator.instance;
			tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
			final float f = 1.0F;
			final int color = block.colorMultiplier(world, x, y, z);
			float r = (float)(color >> 16 & 255) / 255.0F;
			float g = (float)(color >> 8 & 255) / 255.0F;
			float b = (float)(color & 255) / 255.0F;

			if (EntityRenderer.anaglyphEnable)
			{
				final float f5 = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
				final float f4 = (r * 30.0F + g * 70.0F) / 100.0F;
				final float f6 = (r * 30.0F + b * 70.0F) / 100.0F;
				r = f5;
				g = f4;
				b = f6;
			}

			tes.setColorOpaque_F(f * r, f * g, f * b);
			final float f2 = 1.0F - 0.125F;
			renderer.renderFaceXPos(block, (double)((float)x - f2), (double)y, (double)z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			renderer.renderFaceXNeg(block, (double)((float)x + f2), (double)y, (double)z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			renderer.renderFaceYPos(block, (double)x, (double)((float)y - f2), (double)z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			renderer.renderFaceYNeg(block, (double)x, (double)((float)y + f2), (double)z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			renderer.renderFaceZPos(block, (double)x, (double)y, (double)((float)z - f2), renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
			renderer.renderFaceZNeg(block, (double)x, (double)y, (double)((float)z + f2), renderer.getBlockIconFromSideAndMetadata(block, 0, meta));

			renderer.setOverrideBlockTexture(block.getIcon(0, 0));
			renderer.renderCrossedSquares(block, x, y, z);
			renderer.clearOverrideBlockTexture();

			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelID)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return id;
	}
}
