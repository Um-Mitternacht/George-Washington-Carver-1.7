package growthcraft.core.common.block;

import java.util.List;
import java.util.Random;

import growthcraft.core.util.ItemUtils;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.client.ClientProxy;
import growthcraft.core.client.renderer.RenderPaddy;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public abstract class BlockPaddyBase extends GrcBlockBase implements IPaddy
{
	public BlockPaddyBase(Material material)
	{
		super(material);
		this.setTickRandomly(true);
	}

	@Override
	public boolean isFilledWithFluid(IBlockAccess world, BlockPos pos, int meta)
	{
		return meta >= getMaxPaddyMeta(world, x, y, z);
	}

	public void drainPaddy(World world, BlockPos pos)
	{
		final int meta = world.getBlockState(x, y, z);
		if (meta > 0)
		{
			world.setBlockState(x, y, z, meta - 1, BlockFlags.UPDATE_AND_SYNC);
		}
	}

	public void fillPaddy(World world, BlockPos pos)
	{
		world.setBlockState(x, y, z, getMaxPaddyMeta(world, x, y, z), BlockFlags.UPDATE_AND_SYNC);
	}

	/************
	 * TICK
	 ************/

	@Override
	public void updateTick(World world, BlockPos pos, Random random)
	{
		if (isBelowFillingFluid(world, x, y, z) && world.canLightningStrikeAt(x, y + 1, z))
		{
			fillPaddy(world, x, y, z);
		}
	}

	/************
	 * TRIGGERS
	 ************/
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, EntityPlayer player, int meta, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			final ItemStack itemstack = player.inventory.getCurrentItem();
			if (itemstack != null)
			{
				if (FluidContainerRegistry.isFilledContainer(itemstack))
				{
					final FluidStack addF = FluidContainerRegistry.getFluidForFilledItem(itemstack);
					if (addF != null)
					{
						int radius = addF.amount * 2 / FluidContainerRegistry.BUCKET_VOLUME;
						if (radius % 2 == 0)
						{
							radius -= 1;
						}

						if (addF.getFluid() == getFillingFluid() && radius > 0)
						{
							for (int lx = x - radius; lx <= x + radius; ++lx)
							{
								for (int lz = z - radius; lz <= z + radius; ++lz)
								{
									if (world.getBlockState(lx, y, lz) == this)
									{
										fillPaddy(world, lx, y, lz);
									}
								}
							}

							if(!player.capabilities.isCreativeMode)
							{
								player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtils.consumeStack(itemstack));
							}

							return true;
						}
					}
				}
			}

			return false;
		}
	}

	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entity, float par6)
	{
		if (!world.isRemote && world.rand.nextFloat() < par6 - 0.5F)
		{
			if (!(entity instanceof EntityPlayer) && !world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
			{
				return;
			}

			final Block plant = world.getBlockState(x, y + 1, z);
			if (plant instanceof IPaddyCrop)
			{
				plant.dropBlockAsItem(world, x, y + 1, z, world.getBlockState(x, y + 1, z), 0);
				world.setBlockToAir(x, y + 1, z);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, Block par5)
	{
		super.onNeighborBlockChange(world, x, y, z, par5);
		if (isBelowFillingFluid(world, x, y, z))
		{
			fillPaddy(world, x, y, z);
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int i, int j, int k, EnumFacing side)
	{
		return EnumFacing.UP != side;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, BlockPos pos, int metadata)
	{
		return false;
	}

	/************
	 * RENDERS
	 ************/
	@Override
	public int getRenderType()
	{
		return RenderPaddy.id;
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
	public boolean canRenderInPass(int pass)
	{
		ClientProxy.paddyRenderPass = pass;
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}

	/************
	 * BOXES
	 ************/
	@Override
	public void setBlockBoundsForItemRender()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB axis, List list, Entity entity)
	{
		final int meta = world.getBlockState(i, j, k);

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.875F, 1.0F);
		super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);

		final float thick = 0.125F;
		final float j1 = 0.875F;
		final float j2 = 1.0F;
		float i1 = 0.0F;
		float i2 = 1.0F;
		float k1 = 0.0F;
		float k2 = 1.0F;

		final boolean boolXPos = canConnectPaddyTo(world, i + 1, j, k, meta);
		final boolean boolXNeg = canConnectPaddyTo(world, i - 1, j, k, meta);
		final boolean boolYPos = canConnectPaddyTo(world, i, j, k + 1, meta);
		final boolean boolYNeg = canConnectPaddyTo(world, i, j, k - 1, meta);

		if (!boolXPos)
		{
			i1 = 1.0F - thick;
			i2 = 1.0F;
			k1 = 0.0F + thick;
			k2 = 1.0F - thick;

			this.setBlockBounds(i1, j1, k1, i2, j2, k2);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if (!boolXNeg)
		{
			i1 = 0.0F;
			i2 = 0.0F + thick;
			k1 = 0.0F + thick;
			k2 = 1.0F - thick;

			this.setBlockBounds(i1, j1, k1, i2, j2, k2);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if (!boolYPos)
		{
			i1 = 0.0F + thick;
			i2 = 1.0F - thick;
			k1 = 1.0F - thick;
			k2 = 1.0F;

			this.setBlockBounds(i1, j1, k1, i2, j2, k2);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if (!boolYNeg)
		{
			i1 = 0.0F + thick;
			i2 = 1.0F - thick;
			k1 = 0.0F;
			k2 = 0.0F + thick;

			this.setBlockBounds(i1, j1, k1, i2, j2, k2);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		//corners
		if ((!canConnectPaddyTo(world, i - 1, j, k - 1, meta)) || (!boolXNeg) || (!boolYNeg))
		{
			this.setBlockBounds(0.0F, j1, 0.0F, 0.0F + thick, j2, 0.0F + thick);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if ((!canConnectPaddyTo(world, i + 1, j, k - 1, meta)) || (!boolXPos) || (!boolYNeg))
		{
			this.setBlockBounds(1.0F - thick, j1, 0.0F, 1.0F, j2, 0.0F + thick);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if ((!canConnectPaddyTo(world, i - 1, j, k + 1, meta)) || (!boolXNeg) || (!boolYPos))
		{
			this.setBlockBounds(0.0F, j1, 1.0F - thick, 0.0F + thick, j2, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		if ((!canConnectPaddyTo(world, i + 1, j, k + 1, meta)) || (!boolXPos) || (!boolYPos))
		{
			this.setBlockBounds(1.0F - thick, j1, 1.0F - thick, 1.0F, j2, 1.0F);
			super.addCollisionBoxesToList(world, i, j, k, axis, list, entity);
		}

		this.setBlockBoundsForItemRender();
	}

	public boolean canConnectPaddyTo(IBlockAccess world, int i, int j, int k, int m)
	{
		if (m > 0)
		{
			m = 1;
		}

		int meta = world.getBlockState(i, j, k);

		if (meta > 0)
		{
			meta = 1;
		}

		return this == world.getBlockState(i, j, k) && meta == m;
	}
}
