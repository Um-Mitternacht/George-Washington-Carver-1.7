package growthcraft.cellar.common.block;

import java.util.Random;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.cellar.client.render.RenderFruitPresser;
import growthcraft.cellar.common.tileentity.TileEntityFruitPresser;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.common.block.IRotatableBlock;
import growthcraft.core.common.block.IWrenchable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

public class BlockFruitPresser extends BlockCellarContainer implements IWrenchable, IRotatableBlock
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	public BlockFruitPresser()
	{
		super(Material.PISTON);
		this.isBlockContainer = true;
		setTileEntityType(TileEntityFruitPresser.class);
		setHardness(0.5F);
		setStepSound(soundTypePiston);
		setBlockName("grc.fruitPresser");
		setCreativeTab(null);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.9375F, 0.8125F);
	}

	public String getPressStateName(int meta)
	{
		switch(meta)
		{
			case 0:
			case 1:
				return "unpressed";
			case 2:
			case 3:
				return "pressed";
			default:
				return "invalid";
		}
	}

	/************
	 * TRIGGERS
	 ************/

	/* IRotatableBLock */
	@Override
	public boolean isRotatable(IBlockAccess world, int x, int y, int z, EnumFacing side)
	{
		final Block below = world.getBlockState(x, y - 1, z);
		if (below instanceof IRotatableBlock)
		{
			return ((IRotatableBlock)below).isRotatable(world, x, y - 1, z, side);
		}
		return false;
	}

	@Override
	public boolean rotateBlock(World world, int x, int y, int z, EnumFacing side)
	{
		if (isRotatable(world, x, y, z, side))
		{
			final Block below = world.getBlockState(x, y - 1, z);
			return below.rotateBlock(world, x, y - 1, z, side);
		}
		return false;
	}

	/* IWrenchable */
	@Override
	public boolean wrenchBlock(World world, int x, int y, int z, EntityPlayer player, ItemStack wrench)
	{
		final Block below = world.getBlockState(x, y - 1, z);
		if (below instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)below).wrenchBlock(world, x, y - 1, z, player, wrench);
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float par7, float par8, float par9)
	{
		if (world.isRemote) return true;
		final Block below = world.getBlockState(x, y - 1, z);
		if (below instanceof BlockFruitPress)
		{
			return ((BlockFruitPress)below).tryWrenchItem(player, world, x, y - 1, z);
		}
		return false;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		final int m = world.getBlockState(x,  y - 1, z);
		world.setBlockMetadataWithNotify(x, y, z, m, BlockFlags.UPDATE_AND_SYNC);

		if (!world.isRemote)
		{
			this.updatePressState(world, x, y, z);
		}
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		final int m = world.getBlockState(x,  y - 1, z);
		world.setBlockMetadataWithNotify(x, y, z, m, BlockFlags.UPDATE_AND_SYNC);

		if (!world.isRemote)
		{
			this.updatePressState(world, x, y, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			world.func_147480_a(x, y, z, true);
		}

		if (!world.isRemote)
		{
			this.updatePressState(world, x, y, z);
		}
	}

	private void updatePressState(World world, int x, int y, int z)
	{
		final int     meta = world.getBlockState(x, y, z);
		final boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z);

		if (flag && (meta == 0 || meta == 1))
		{
			world.setBlockMetadataWithNotify(x, y, z, meta | 2, BlockFlags.UPDATE_AND_SYNC);
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.out", 0.5F, world.rand.nextFloat() * 0.25F + 0.6F);
		}
		else if (!flag && (meta == 2 || meta == 3))
		{
			world.setBlockMetadataWithNotify(x, y, z, meta & 1, BlockFlags.UPDATE_AND_SYNC);
			world.playSoundEffect((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, "tile.piston.in", 0.5F, world.rand.nextFloat() * 0.15F + 0.6F);
		}

		world.markBlockForUpdate(x, y, z);
	}

	/************
	 * CONDITIONS
	 ************/
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return GrowthCraftCellar.blocks.fruitPress.getBlockState() == world.getBlockState(x, y - 1, z);
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, EnumFacing side)
	{
		final int meta = world.getBlockState(x, y, z);

		if (meta == 0 || meta == 2)
		{
			return side == EnumFacing.EAST || side == EnumFacing.WEST;
		}
		else if (meta == 1 || meta == 3)
		{
			return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
		}

		return isNormalCube(world, x, y, z);
	}

	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return GrowthCraftCellar.blocks.fruitPress.getItem();
	}

	/************
	 * DROPS
	 ************/
	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return null;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		icons = new IIcon[4];

		icons[0] = reg.registerIcon("grccellar:fruitpresser_0");
		icons[1] = reg.registerIcon("grccellar:fruitpresser_1");
		icons[2] = reg.registerIcon("grccellar:fruitpresser_2");
		icons[3] = reg.registerIcon("planks_oak");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconByIndex(int index)
	{
		return icons[index];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 0 ? icons[0] : (side == 1 ? icons[1] : icons[2]);
	}

	/************
	 * RENDERS
	 ************/
	@Override
	public int getRenderType()
	{
		return RenderFruitPresser.RENDER_ID;
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
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}
}
