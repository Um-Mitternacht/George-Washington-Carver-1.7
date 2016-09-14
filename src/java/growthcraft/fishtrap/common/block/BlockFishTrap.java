package growthcraft.fishtrap.common.block;

import java.util.Random;

import growthcraft.api.fishtrap.FishTrapRegistry;
import growthcraft.core.GrowthCraftCore;
import growthcraft.core.common.block.GrcBlockContainer;
import growthcraft.core.util.BlockCheck;
import growthcraft.core.Utils;
import growthcraft.fishtrap.common.tileentity.TileEntityFishTrap;
import growthcraft.fishtrap.GrowthCraftFishTrap;
import growthcraft.fishtrap.creativetab.CreativeTabsGrowthcraftFishtrap;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeDictionary;

public class BlockFishTrap extends GrcBlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private final float chance = GrowthCraftFishTrap.getConfig().fishTrapCatchRate;
	private Random rand = new Random();

	public BlockFishTrap()
	{
		super(Material.WOOD);
		setTickRandomly(true);
		setHardness(0.4F);
		setStepSound(soundTypeWood);
		setBlockName("grc.fishTrap");
		setTileEntityType(TileEntityFishTrap.class);
		setCreativeTab(GrowthCraftFishtrap.creativeTab);
	}

	private float getCatchRate(World world, int x, int y, int z)
	{
		final int checkSize = 3;
		final int i = x - ((checkSize - 1) / 2);
		final int j = y - ((checkSize - 1) / 2);
		final int k = z - ((checkSize - 1) / 2);
		float f = 1.0F;

		for (int loopy = 0; loopy <= checkSize; loopy++)
		{
			for (int loopx = 0; loopx <= checkSize; loopx++)
			{
				for (int loopz = 0; loopz <= checkSize; loopz++)
				{
					final Block water = world.getBlockState(i + loopx, j + loopy, k + loopz);
					float f1 = 0.0F;
					//1.038461538461538;

					if (water != null && isWater(water))
					{
						//f1 = 1.04F;
						f1 = 3.0F;
						//f1 = 17.48F;
					}

					f1 /= 4.0F;

					f += f1;
				}
			}
		}

		return f;
	}

	private void doCatch(World world, int x, int y, int z, Random random, TileEntityFishTrap te, boolean debugFlag)
	{
		float f = this.getCatchRate(world, x, y, z);
		boolean flag;
		if (GrowthCraftFishTrap.getConfig().useBiomeDict)
		{
			final Biome biome = world.getBiomeGenForCoords(x, z);
			flag = BiomeDictionary.isBiomeOfType(biome, Type.WATER);
		}
		else
		{
			flag = Utils.isIDInList(world.getBiomeGenForCoords(x, z).biomeID, GrowthCraftFishTrap.getConfig().biomesList);
		}

		if (flag)
		{
			f *= 1 + (75 / 100);
		}

		if (random.nextInt((int)(this.chance / f) + 1) == 0 || debugFlag)
		{
			final ItemStack item = pickCatch(world);
			if (item != null)
			{
				te.addStack(item);
			}
		}
	}

	private ItemStack pickCatch(World world)
	{
		float f1 = world.rand.nextFloat();
		final float f2 = 0.1F;
		final float f3 = 0.05F;

		if (f1 < f2)
		{
			return FishTrapRegistry.instance().getJunkList(world);
		}
		f1 -= f2;

		if (f1 < f3)
		{
			return FishTrapRegistry.instance().getTreasureList(world);
		}
		f1 -= f3;

		return FishTrapRegistry.instance().getFishList(world);
	}

	private boolean isWater(Block block)
	{
		return BlockCheck.isWater(block);
	}

	private boolean canCatch(World world, int x, int y, int z)
	{
		return isWater(world.getBlockState(x, y, z - 1)) ||
			isWater(world.getBlockState(x, y, z + 1)) ||
			isWater(world.getBlockState(x - 1, y, z)) ||
			isWater(world.getBlockState(x + 1, y, z));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);

		final TileEntityFishTrap te = getTileEntity(world, x, y, z);

		if (te != null)
		{
			if (canCatch(world, x, y, z))
			{
				doCatch(world, x, y, z, random, te, false);
			}
		}
	}

	/************
	 * TRIGGERS
	 ************/
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			player.openGui(GrowthCraftFishTrap.instance, 0, world, x, y, z);
		}
		return true;
	}

	/************
	 * TEXTURES
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg)
	{
		this.icons = new IIcon[1];

		icons[0] = reg.registerIcon("grcfishtrap:fishtrap");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[0];
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
		if (this == world.getBlockState(x, y, z)) return false;
		return super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 0;
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
		final TileEntityFishTrap te = getTileEntity(world, x, y, z);
		if (te != null)
		{
			return Container.calcRedstoneFromInventory(te);
		}
		return 0;
	}
}
