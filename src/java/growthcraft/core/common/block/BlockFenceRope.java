package growthcraft.core.common.block;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.api.core.util.BlockKey;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.client.renderer.RenderFenceRope;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFenceRope extends GrcBlockBase implements IBlockRope
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private BlockKey fenceBlockKey;

	public BlockFenceRope(BlockKey srcKey, String name)
	{
		super(srcKey.getBlockState().getMaterial());
		this.fenceBlockKey = srcKey;
		setStepSound(soundTypeWood);
		setBlockName(name);
		setCreativeTab(null);
	}

	public BlockFenceRope(Block block, String name)
	{
		this(new BlockKey(block), name);
	}

	public Block getFenceBlock()
	{
		return fenceBlockKey.getBlockState();
	}

	public int getFenceBlockMetadata()
	{
		return fenceBlockKey.getMetadata();
	}

	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		return getFenceBlock().getBlockHardness(world, x, y, z);
	}

	@Override
	public float getExplosionResistance(Entity entity)
	{
		return getFenceBlock().getExplosionResistance(entity);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int EnumFacing, float par7, float par8, float par9)
	{
		if (player.inventory.getCurrentItem() != null && GrowthCraftCore.items.rope.equals(player.inventory.getCurrentItem().getItem()))
		{
			return false;
		}
		else
		{
			if (!world.isRemote)
			{
				world.setBlockState(x, y, z, getFenceBlock(), getFenceBlockMetadata(), BlockFlags.UPDATE_AND_SYNC);
				this.dropBlockAsItem(world, x, y, z, GrowthCraftCore.items.rope.asStack());
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return Item.getItemFromBlock(getFenceBlock());
	}

	@Override
	public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(x, y, z) instanceof IBlockRope;
	}

	@Override
	public Item getItemDropped(int meta, Random random, int par3)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, BlockPos pos, int metadata, int fortune)
	{
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(getFenceBlock()));
		ret.add(GrowthCraftCore.items.rope.asStack());
		return ret;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		final boolean flag = this.canConnectRopeTo(world, x, y, z - 1);
		final boolean flag1 = this.canConnectRopeTo(world, x, y, z + 1);
		final boolean flag2 = this.canConnectRopeTo(world, x - 1, y, z);
		final boolean flag3 = this.canConnectRopeTo(world, x + 1, y, z);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, BlockPos pos, AxisAlignedBB aabb, List list, Entity entity)
	{
		final boolean flag = this.canConnectRopeTo(world, x, y, z - 1);
		final boolean flag1 = this.canConnectRopeTo(world, x, y, z + 1);
		final boolean flag2 = this.canConnectRopeTo(world, x - 1, y, z);
		final boolean flag3 = this.canConnectRopeTo(world, x + 1, y, z);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag || flag1)
		{
			this.setBlockBounds(f, 0.4375F, f2, f1, 0.5625F, f3);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		}

		f2 = 0.375F;
		f3 = 0.625F;

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		if (flag2 || flag3 || !flag && !flag1)
		{
			this.setBlockBounds(f, 0.4375F, f2, f1, 0.5625F, f3);
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		}

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		this.setBlockBoundsBasedOnState(world, x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[2];

		icons[0] = reg.registerIcon("grccore:rope_0");
		icons[1] = reg.registerIcon("grccore:rope_1");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconByIndex(int index)
	{
		if (index == 0)
		{
			return getFenceBlock().getIcon(0, 0);
		}
		return icons[index - 1];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return getFenceBlock().getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, BlockPos pos, int side)
	{
		return getFenceBlock().getIcon(world, x, y, z, side);
	}

	@Override
	public int getRenderType()
	{
		return RenderFenceRope.RENDER_ID;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, int par5)
	{
		return true;
	}
}
