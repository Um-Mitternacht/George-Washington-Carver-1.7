package growthcraft.grapes.common.block;

import java.util.List;

import growthcraft.core.util.BlockCheck;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.grapes.client.renderer.RenderGrapeVine1;
import growthcraft.grapes.GrowthCraftGrapes;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class BlockGrapeVine1 extends BlockGrapeVineBase
{
	public boolean graphicFlag;

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockGrapeVine1()
	{
		super();
		setGrowthRateMultiplier(GrowthCraftGrapes.getConfig().grapeVineTrunkGrowthRate);
		setTickRandomly(true);
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
		setBlockName("grc.grapeVine1");
		setCreativeTab(null);
	}

	/************
	 * TICK
	 ************/
	@Override
	protected boolean canUpdateGrowth(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == 0 || world.isAirBlock(x, y + 1, z);
	}

	@Override
	protected void doGrowth(World world, int x, int y, int z, int meta)
	{
		final Block above = world.getBlockState(x, y + 1, z);
		/* Is there a rope block above this? */
		if (BlockCheck.isRope(above))
		{
			incrementGrowth(world, x, y, z, meta);
			world.setBlock(x, y + 1, z, GrowthCraftGrapes.blocks.grapeLeaves.getBlockState(), 0, BlockFlags.UPDATE_AND_SYNC);
		}
		else if (world.isAirBlock(x, y + 1, z))
		{
			incrementGrowth(world, x, y, z, meta);
			world.setBlock(x, y + 1, z, this, 0, BlockFlags.UPDATE_AND_SYNC);
		}
		else if (GrowthCraftGrapes.blocks.grapeLeaves.getBlockState() == above)
		{
			incrementGrowth(world, x, y, z, meta);
		}
	}

	@Override
	protected float getGrowthRate(World world, int x, int y, int z)
	{
		int j = y;
		if (world.getBlockState(x, j - 1, z) == this && world.getBlockState(x, j - 2, z) == Blocks.FARMLAND)
		{
			j = y - 1;
		}
		return super.getGrowthRate(world, x, j, z);
	}

	/************
	 * CONDITIONS
	 ************/
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return BlockCheck.canSustainPlant(world, x, y - 1, z, EnumFacing.UP, this) ||
			this == world.getBlockState(x, y - 1, z);
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
		this.icons = new IIcon[3];

		icons[0] = reg.registerIcon("grcgrapes:trunk");
		icons[1] = reg.registerIcon("grcgrapes:leaves");
		icons[2] = reg.registerIcon("grcgrapes:leaves_opaque");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[0];
	}

	@SideOnly(Side.CLIENT)
	public IIcon getLeafTexture()
	{
		graphicFlag = Blocks.LEAVES.isOpaqueCube();
		return !this.graphicFlag ? icons[1] : icons[2];
	}

	/************
	 * RENDER
	 ************/
	@Override
	public int getRenderType()
	{
		return RenderGrapeVine1.id;
	}

	/************
	 * BOXES
	 ************/
	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
		final int meta = world.getBlockMetadata(x, y, z);
		final float f = 0.0625F;

		if (meta == 0)
		{
			this.setBlockBounds(6*f, 0.0F, 6*f, 10*f, 0.5F, 10*f);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
			this.setBlockBounds(4*f, 0.5F, 4*f, 12*f, 1.0F, 12*f);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		}
		else if (meta == 1)
		{
			this.setBlockBounds(6*f, 0.0F, 6*f, 10*f, 1.0F, 10*f);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		final int meta = world.getBlockMetadata(x, y, z);
		final float f = 0.0625F;

		if (meta == 0)
		{
			this.setBlockBounds(4*f, 0.0F, 4*f, 12*f, 1.0F, 12*f);
		}
		else
		{
			this.setBlockBounds(6*f, 0.0F, 6*f, 10*f, 1.0F, 10*f);
		}
	}
}
