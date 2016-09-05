package growthcraft.cellar.common.block;

import java.util.List;
import java.util.Random;

import growthcraft.api.core.util.BBox;
import growthcraft.cellar.client.render.RenderBrewKettle;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.cellar.GrowthCraftCellar;
import growthcraft.core.Utils;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraft.util.EnumFacing;

public class BlockBrewKettle extends BlockCellarContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private final BBox kettleContentsBB = BBox.newCube(2, 4, 2, 12, 10, 12).scale(1f / 16f);
	private final boolean dropItemsInBrewKettle = GrowthCraftCellar.getConfig().dropItemsInBrewKettle;
	private final boolean fillsWithRain = GrowthCraftCellar.getConfig().brewKettleFillsWithRain;
	private final boolean setFireToFallenLivingEntities = GrowthCraftCellar.getConfig().setFireToFallenLivingEntities;
	private final int rainFillPerUnit = GrowthCraftCellar.getConfig().brewKettleRainFillPerUnit;

	public BlockBrewKettle()
	{
		super(Material.IRON);
		setStepSound(soundTypeMetal);
		setTileEntityType(TileEntityBrewKettle.class);
		setHardness(2.0F);
		setBlockName("grc.brewKettle");
		setCreativeTab(GrowthCraftCellar.tab);
	}

	@Override
	public void fillWithRain(World world, int x, int y, int z)
	{
		if (fillsWithRain)
		{
			final TileEntityBrewKettle te = getTileEntity(world, x, y, z);
			if (te != null)
			{
				te.fill(ForgeDirection.UP, new FluidStack(FluidRegistry.WATER, rainFillPerUnit), true);
			}
		}
		super.fillWithRain(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (!world.isRemote)
		{
			final TileEntityBrewKettle te = getTileEntity(world, x, y, z);
			if (te != null)
			{
				if (dropItemsInBrewKettle)
				{
					if (entity instanceof EntityItem)
					{
						final EntityItem item = (EntityItem)entity;
						if (te.tryMergeItemIntoMainSlot(item.getEntityItem()) != null)
						{
							world.playSoundEffect((double)x, (double)y, (double)z, "liquid.splash", 0.3f, 0.5f);
						}
					}
				}
				if (setFireToFallenLivingEntities)
				{
					if (entity instanceof EntityLivingBase)
					{
						if (te.getHeatMultiplier() >= 0.5f)
						{
							final float ex = (float)entity.posX - x;
							final float ey = (float)entity.posY - y;
							final float ez = (float)entity.posZ - z;
							if (kettleContentsBB.contains(ex, ey, ez))
							{
								entity.setFire(1);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected boolean playerDrainTank(World world, int x, int y, int z, IFluidHandler fh, ItemStack is, EntityPlayer player)
	{
		final FluidStack fs = Utils.playerDrainTank(world, x, y, z, fh, is, player);
		return fs != null && fs.amount > 0;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[4];

		icons[0] = reg.registerIcon("grccellar:brewkettle_0");
		icons[1] = reg.registerIcon("grccellar:brewkettle_1");
		icons[2] = reg.registerIcon("grccellar:brewkettle_2");
		icons[3] = reg.registerIcon("grccellar:brewkettle_3");
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
		if (side == 1)
		{
			return icons[3];
		}
		else if (side == 0)
		{
			return icons[0];
		}
		return icons[2];
	}

	/************
	 * RENDERS
	 ************/
	@Override
	public int getRenderType()
	{
		return RenderBrewKettle.RENDER_ID;
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
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		final float f = 0.125F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
		this.setBlockBoundsForItemRender();
	}

	/************
	 * COMPARATOR
	 ************/
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int par5)
	{
		final TileEntityBrewKettle te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			return te.getFluidAmountScaled(15, 1);
		}
		return 0;
	}
}
