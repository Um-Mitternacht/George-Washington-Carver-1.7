package growthcraft.cellar.common.block;

import growthcraft.cellar.client.render.RenderCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.api.core.util.BBox;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockCultureJar extends BlockCellarContainer
{
	public BlockCultureJar()
	{
		super(Material.GLASS);
		setHardness(0.3F);
		setStepSound(soundTypeGlass);
		setBlockName("grc.fermentJar");
		setBlockTextureName("grccellar:ferment_jar_glass");
		setCreativeTab(GrowthCraftCellar.tab);
		setTileEntityType(TileEntityCultureJar.class);

		final BBox bbox = BBox.newCube(6, 0, 6, 4, 6, 4).scale(1 / 16.0f);
		setBlockBounds(bbox.x0(), bbox.y0(), bbox.z0(), bbox.x1(), bbox.y1(), bbox.z1());
	}

	@Override
	public int getRenderType()
	{
		return RenderCultureJar.RENDER_ID;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int side)
	{
		return true;
	}
}
