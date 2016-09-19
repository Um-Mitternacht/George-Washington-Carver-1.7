package growthcraft.grapes.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.RenderType;
import growthcraft.grapes.GrowthCraftGrapes;

import net.minecraft.block.SoundType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * This is the Grape Vine sapling block
 */
public abstract class BlockGrapeVine0 extends BlockGrapeVineBase
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockGrapeVine0()
	{
		super();
		setGrowthRateMultiplier(GrowthCraftGrapes.getConfig().grapeVineSeedlingGrowthRate);
		setTickRandomly(true);
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setBlockName("grc.grapeVine0");
		setCreativeTab(null);
	}

	/************
	 * TICK
	 ************/
	@Override
	protected boolean canUpdateGrowth(World world, int x, int y, int z)
	{
		return world.getBlockLightValue(x, y + 1, z) >= 9;
	}

	@Override
	protected void doGrowth(World world, int x, int y, int z, int meta)
	{
		if (meta == 0)
		{
			incrementGrowth(world, x, y, z, meta);
		}
		else
		{
			world.setBlock(x, y, z, GrowthCraftGrapes.blocks.grapeVine1.getBlockState(), 0, BlockFlags.UPDATE_AND_SYNC);
		}
	}

	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return GrowthCraftGrapes.items.grapeSeeds.getItem();
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[2];

		icons[0] = reg.registerIcon("grcgrapes:vine_0");
		icons[1] = reg.registerIcon("grcgrapes:vine_1");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.icons[MathHelper.clamp_int(meta, 0, 1)];
	}

	/************
	 * RENDER
	 ************/
	@Override
	public int getRenderType()
	{
		return RenderType.BUSH;
	}

	/************
	 * BOXES
	 ************/
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		final int meta = world.getBlockState(x, y, z);
		final float f = 0.0625F;

		if (meta == 0)
		{
			this.setBlockBounds(6*f, 0.0F, 6*f, 10*f, 5*f, 10*f);
		}
		else
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
