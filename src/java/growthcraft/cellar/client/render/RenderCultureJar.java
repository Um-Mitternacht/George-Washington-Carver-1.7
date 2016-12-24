package growthcraft.cellar.client.render;

import growthcraft.api.core.util.BBox;
import growthcraft.api.core.util.ColorUtils;
import growthcraft.cellar.client.model.ModelCultureJar;
import growthcraft.cellar.common.block.BlockCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import net.minecraft.block.Block;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderCultureJar implements ISimpleBlockRenderingHandler
{
	public static int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
	private static final BBox fluidBBox = BBox.newCube(7, 1, 7, 2, 4, 2).scale(ModelCultureJar.SCALE);

	public int getRenderId()
	{
		return RENDER_ID;
	}

	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
	{
		// pfft wrong neck of the neighbourhood mate.
	}

	public boolean renderWorldBlock(IBlockAccess world, BlockPos pos, Block block, int modelId, RenderBlocks renderer)
	{
		if (RENDER_ID != modelId) return false;

		// This only draws in the fluid inside the Jar, the jar itself is a model
		if (block instanceof BlockCultureJar)
		{
			final Tessellator tes = Tessellator.instance;
			final BlockCultureJar fermentJar = (BlockCultureJar)block;
			final TileEntityCultureJar te = fermentJar.getTileEntity(world, pos);
			final Fluid fluid = te.getFluid(0);

			if (fluid != null)
			{
				final double fluidRate = (double)te.getFluidAmountRate(0);
				if (fluidRate > 0)
				{
					final IIcon icon = fluid.getIcon();

					if (icon != null)
					{
						final int color = fluid.getColor();
						final float[] tempFloatColor = new float[3];
						ColorUtils.rgb24FloatArray(tempFloatColor, color);
						tes.setColorOpaque_F(tempFloatColor[0], tempFloatColor[1], tempFloatColor[2]);

						renderer.setRenderBounds(fluidBBox.x0(), fluidBBox.y0(), fluidBBox.z0(), fluidBBox.x1(), fluidBBox.y0() + (fluidBBox.y1() - fluidBBox.y0()) * fluidRate, fluidBBox.z1());
						{
							renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
							renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
							renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
							renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
							renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
							renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
						}
						renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
						tes.setColorOpaque_F(1f, 1f, 1f);
					}
				}
			}
		}
		return true;
	}
}
