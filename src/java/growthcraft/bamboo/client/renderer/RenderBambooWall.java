package growthcraft.bamboo.client.renderer;

import growthcraft.bamboo.common.block.BlockBambooWall;

import net.minecraftforge.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderBambooWall implements ISimpleBlockRenderingHandler
{
	public static final int id = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		if (modelID == id)
		{
			final Tessellator tessellator = Tessellator.instance;

			if (renderer.useInventoryTint)
			{
				final int color = block.getRenderColor(metadata);
				final float r = (float)(color >> 16 & 255) / 255.0F;
				final float g = (float)(color >> 8 & 255) / 255.0F;
				final float b = (float)(color & 255) / 255.0F;
				GL11.glColor4f(r * 1.0F, g * 1.0F, b * 1.0F, 1.0F);
			}

			renderer.setRenderBoundsFromBlock(block);

			renderer.setRenderBounds(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);

			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 0));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 1));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 2));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 3));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 4));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 5));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);

			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, BlockPos pos, Block block, int modelId, RenderBlocks renderer)
	{
		if (modelId == id)
		{
			double x1 = 0.375D;
			double x2 = 0.625D;
			final double y1 = 0.0D;
			final double y2 = 1.0D;
			double z1 = 0.375D;
			double z2 = 0.625D;
			renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
			renderer.renderStandardBlock(block, x, y, z);

			final BlockBambooWall blk = (BlockBambooWall) block;

			int tm;

			final Block idXneg = world.getBlockState(x - 1, y, z);
			final Block idXpos = world.getBlockState(x + 1, y, z);
			final Block idZneg = world.getBlockState(x, y, z - 1);
			final Block idZpos = world.getBlockState(x, y, z + 1);

			int metaXneg = world.getBlockState(x - 1, y, z);
			int metaXpos = world.getBlockState(x + 1, y, z);
			int metaZneg = world.getBlockState(x, y, z - 1);
			int metaZpos = world.getBlockState(x, y, z + 1);

			final boolean flagXneg = blk.canConnectWallTo(world, x - 1, y, z) || (idXneg instanceof BlockStairs && (metaXneg & 3) == 0);
			final boolean flagXpos = blk.canConnectWallTo(world, x + 1, y, z) || (idXpos instanceof BlockStairs && (metaXpos & 3) == 1);
			final boolean flagZneg = blk.canConnectWallTo(world, x, y, z - 1) || (idZneg instanceof BlockStairs && (metaZneg & 3) == 2);
			final boolean flagZpos = blk.canConnectWallTo(world, x, y, z + 1) || (idZpos instanceof BlockStairs && (metaZpos & 3) == 3);

			//XNEG
			if (flagXneg)
			{
				x1 = 0.0D;
				x2 = 0.375D;
				z1 = 0.375D;
				z2 = 0.625D;

				renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
				renderer.renderStandardBlock(block, x, y, z);
			}
			else if (idXneg instanceof BlockDoor)
			{
				if ((metaXneg & 8) > 7)
				{
					metaXneg = world.getBlockState(x - 1, y - 1, z);
				}

				tm = metaXneg & 3;

				if (tm == 1 || tm == 3)
				{
					x1 = 0.0D;
					x2 = 0.375D;
					z1 = 0.375D;
					z2 = 0.625D;

					renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
					renderer.renderStandardBlock(block, x, y, z);

					if (tm == 1)
					{

						x1 = 0.0D;
						x2 = 0.25D;
						z1 = 0.0D;
						z2 = 0.375D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}

					if (tm == 3)
					{

						x1 = 0.0D;
						x2 = 0.25D;
						z1 = 0.625D;
						z2 = 1.0D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
			}

			//XPOS
			if (flagXpos)
			{
				x1 = 0.625D;
				x2 = 1.0D;
				z1 = 0.375D;
				z2 = 0.625D;

				renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
				renderer.renderStandardBlock(block, x, y, z);
			}
			else if (idXpos instanceof BlockDoor)
			{
				if ((metaXpos & 8) > 7)
				{
					metaXpos = world.getBlockState(x + 1, y - 1, z);
				}

				tm = metaXpos & 3;

				if (tm == 1 || tm == 3)
				{
					x1 = 0.625D;
					x2 = 1.0D;
					z1 = 0.375D;
					z2 = 0.625D;

					renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
					renderer.renderStandardBlock(block, x, y, z);

					if (tm == 1)
					{
						x1 = 0.75D;
						x2 = 1.0D;
						z1 = 0.0D;
						z2 = 0.375D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}

					if (tm == 3)
					{
						x1 = 0.75D;
						x2 = 1.0D;
						z1 = 0.625D;
						z2 = 1.0D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
			}

			//ZNEG
			if (flagZneg)
			{
				x1 = 0.375D;
				x2 = 0.625D;
				z1 = 0.0D;
				z2 = 0.375D;

				renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
				renderer.renderStandardBlock(block, x, y, z);
			}
			else if (idZneg instanceof BlockDoor)
			{
				if ((metaZneg & 8) > 7)
				{
					metaZneg = world.getBlockState(x, y - 1, z - 1);
				}

				tm = metaZneg & 3;

				if (tm == 0 || tm == 2)
				{
					x1 = 0.375D;
					x2 = 0.625D;
					z1 = 0.0D;
					z2 = 0.375D;

					renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
					renderer.renderStandardBlock(block, x, y, z);

					if (tm == 0)
					{
						x1 = 0.0D;
						x2 = 0.375D;
						z1 = 0.0D;
						z2 = 0.25D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}

					if (tm == 2)
					{
						x1 = 0.625D;
						x2 = 1.0D;
						z1 = 0.0D;
						z2 = 0.25D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
			}

			//ZPOS
			if (flagZpos)
			{
				x1 = 0.375D;
				x2 = 0.625D;
				z1 = 0.625D;
				z2 = 1.0D;

				renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
				renderer.renderStandardBlock(block, x, y, z);
			}
			else if (idZpos instanceof BlockDoor)
			{
				if ((metaZpos & 8) > 7)
				{
					metaZpos = world.getBlockState(x, y - 1, z + 1);
				}

				tm = metaZpos & 3;

				if (tm == 0 || tm == 2)
				{
					x1 = 0.375D;
					x2 = 0.625D;
					z1 = 0.625D;
					z2 = 1.0D;

					renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
					renderer.renderStandardBlock(block, x, y, z);

					if (tm == 0)
					{
						x1 = 0.0D;
						x2 = 0.375D;
						z1 = 0.75D;
						z2 = 1.0D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}

					if (tm == 2)
					{
						x1 = 0.625D;
						x2 = 1.0D;
						z1 = 0.75D;
						z2 = 1.0D;

						renderer.setRenderBounds(x1, y1, z1, x2, y2, z2);
						renderer.renderStandardBlock(block, x, y, z);
					}
				}
			}

			blk.setBlockBoundsBasedOnState(world, x, y, z);
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
