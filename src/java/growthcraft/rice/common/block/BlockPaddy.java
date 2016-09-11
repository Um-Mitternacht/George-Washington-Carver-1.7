package growthcraft.rice.common.block;

import java.util.Random;

import javax.annotation.Nonnull;

import growthcraft.api.core.util.BlockFlags;
import growthcraft.core.common.block.BlockPaddyBase;
import growthcraft.rice.GrowthCraftRice;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockPaddy extends BlockPaddyBase
{
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;

	private final int paddyFieldMax = GrowthCraftRice.getConfig().paddyFieldMax;

	public BlockPaddy()
	{
		super(Material.GROUND);
		this.setHardness(0.5F);
		this.setStepSound(soundTypeGravel);
		this.setBlockName("grc.paddyField");
		this.setCreativeTab(null);
	}

	@Override
	public void fillWithRain(World world, int x, int y, int z)
	{
		if (world.rand.nextInt(20) == 0)
		{
			final int meta = world.getBlockMetadata(x, y, z);
			if (meta < paddyFieldMax)
			{
				world.setBlockMetadataWithNotify(x, y, z, meta + 1, BlockFlags.UPDATE_AND_SYNC);
			}
		}
	}

	/**
	 * Returns the fluid block used to fill this paddy
	 *
	 * @return fluid block
	 */
	@Override
	@Nonnull public Block getFluidBlock()
	{
		return Blocks.WATER;
	}

	@Override
	@Nonnull public Fluid getFillingFluid()
	{
		return FluidRegistry.WATER;
	}

	@Override
	public int getMaxPaddyMeta(IBlockAccess world, int x, int y, int z)
	{
		return paddyFieldMax;
	}

	@Override
	public boolean isBelowFillingFluid(IBlockAccess world, int x, int y, int z)
	{
		return world.getBlockState(x, y + 1, z).getMaterial() == Material.WATER;
	}

	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(Blocks.DIRT);
	}

	/************
	 * DROPS
	 ************/
	@Override
	public Item getItemDropped(int meta, Random random, int par3)
	{
		return Item.getItemFromBlock(Blocks.DIRT);
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
		icons = new IIcon[3];

		icons[0] = reg.registerIcon("dirt");
		icons[1] = reg.registerIcon("farmland_dry");
		icons[2] = reg.registerIcon("farmland_wet");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)
		{
			if (meta == 0)
			{
				return icons[1];
			}
			else
			{
				return icons[2];
			}
		}
		return icons[0];
	}
}
