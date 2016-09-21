package growthcraft.bamboo.common.world;

import growthcraft.bamboo.GrowthCraftBamboo;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenBamboo extends WorldGenAbstractTree
{
	private final int density = GrowthCraftBamboo.getConfig().bambooWorldGenDensity;
	private final int minTreeHeight = GrowthCraftBamboo.getConfig().bambooTreeMinHeight;
	private final int maxTreeHeight = GrowthCraftBamboo.getConfig().bambooTreeMaxHeight;
	private final Block leaves = GrowthCraftBamboo.blocks.bambooLeaves.getBlockState();
	private final Block log = GrowthCraftBamboo.blocks.bambooStalk.getBlockState();

	public WorldGenBamboo(boolean doblocknotify)
	{
		super(doblocknotify);
	}

	public boolean generateClumps(World world, Random rand, int i, int j, int k)
	{
		for (int loop = 0; loop < this.density; ++loop)
		{
			final int x = i + rand.nextInt(8) - rand.nextInt(8);
			final int y = j + rand.nextInt(4) - rand.nextInt(4);
			final int z = k + rand.nextInt(8) - rand.nextInt(8);
			this.generate(world, rand, x, y, z);
		}

		return true;
	}

	@Override
	public boolean generate(World world, Random rand, int i, int j, int k)
	{
		final int height = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (j >= 1 && j + height + 1 <= maxTreeHeight)
		{
			int y;
			byte b0;
			int x;
			int z;
			Block checkBlock;

			for (y = j; y <= j + 1 + height; ++y)
			{
				b0 = 0;

				if (y >= j + height - 3)
				{
					b0 = 1;
				}

				for (x = i - b0; x <= i + b0 && flag; ++x)
				{
					for (z = k - b0; z <= k + b0 && flag; ++z)
					{
						if (y >= 0 && y < maxTreeHeight)
						{
							checkBlock = world.getBlockState(x, y, z);

							if (!this.isReplaceable(world, x, y, z))
							{
								flag = false;
							}
						}
						else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			}
			else
			{
				final Block soil = world.getBlockState(i, j - 1, k);
				final boolean isSoil = soil != null &&
					soil.canSustainPlant(world, i, j - 1, k, EnumFacing.UP, (BlockSapling)Blocks.SAPLING);

				int it;
				Block block;

				if (isSoil && j < maxTreeHeight - height - 1)
				{
					soil.onPlantGrow(world, i, j - 1, k, i, j, k);

					for (it = 0; it <= 3; ++it)
					{
						y = (j + 5 + height - this.minTreeHeight) + (it * 2);

						for (x = i - 1; x <= i + 1; ++x)
						{
							final int x2 = x - i;

							for (z = k - 1; z <= k + 1; ++z)
							{
								final int z2 = z - k;

								block = world.getBlockState(x, y, z);

								if ((Math.abs(x2) != 1 || Math.abs(z2) != 1 || 1 <= 0) && (block == null || block.canBeReplacedByLeaves(world, x, y, z)))
								{
									this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
								}
							}
						}
					}

					y = j + height - 2;
					for (x = i - 1; x <= i + 1; ++x)
					{
						for (z = k - 1; z <= k + 1; ++z)
						{
							block = world.getBlockState(x, y, z);

							if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
							{
								this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
							}
						}
					}

					x = i;
					z = k;
					for (it = 0; it <= 3; ++it)
					{
						switch(it)
						{
							case 0: x = i - 2; z = k; break;
							case 1: x = i + 2; z = k; break;
							case 2: x = i; z = k - 2; break;
							case 3: x = i; z = k + 2; break;
							default: break;
						}

						block = world.getBlockState(x, y, z);

						if (block == null || block.canBeReplacedByLeaves(world, x, y, z))
						{
							this.setBlockAndNotifyAdequately(world, x, y, z, this.leaves, 0);
						}
					}

					y = j + height;
					block = world.getBlockState(i, y, k);

					if (block == null || block.canBeReplacedByLeaves(world, i, y, k))
					{
						this.setBlockAndNotifyAdequately(world, i, y, k, this.leaves, 0);
					}

					for (y = 0; y < height - 1; ++y)
					{
						block = world.getBlockState(i, j + y, k);

						if (block.isAir(world, it, j + y, k) || block.isLeaves(world, i, j + y, k))
						{
							this.setBlockAndNotifyAdequately(world, i, j + y, k, this.log, 0);
						}
					}

					return true;
				}
				else
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	protected boolean func_150523_a(Block block)
	{
		return block.getMaterial() == Material.air ||
			block.getMaterial() == Material.LEAVES ||
			block == Blocks.GRASS ||
			block == Blocks.DIRT ||
			block == Blocks.LOG ||
			block == Blocks.LOG2 ||
			block == Blocks.SAPLING ||
			block == Blocks.VINE;
	}

	@Override
	protected boolean isReplaceable(World world, BlockPos pos)
	{
		final Block block = world.getBlockState(x, y, z);
		return block.isAir(world, x, y, z) ||
			block.isLeaves(world, x, y, z) ||
			block.isWood(world, x, y, z) ||
			func_150523_a(block);
	}
}
