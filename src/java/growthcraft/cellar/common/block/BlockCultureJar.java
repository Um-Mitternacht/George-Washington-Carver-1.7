package growthcraft.cellar.common.block;

import growthcraft.api.core.util.BBox;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.cellar.client.render.RenderCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
		getBoundingBox(bbox.x0(), bbox.y0(), bbox.z0(), bbox.x1(), bbox.y1(), bbox.z1());
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
