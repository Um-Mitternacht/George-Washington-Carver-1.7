package growthcraft.bees.common.world;

import growthcraft.bees.GrowthCraftBees;
import growthcraft.bees.common.block.BlockBeeHive;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenBeeHive extends WorldGenerator
{
	//constants
	private final int density = GrowthCraftBees.getConfig().beeWorldGenDensity;

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		for (int loop = 0; loop < this.density; ++loop)
		{
			final int i = x + random.nextInt(8) - random.nextInt(8);
			final int j = y + random.nextInt(4) - random.nextInt(4);
			final int k = z + random.nextInt(8) - random.nextInt(8);

			final BlockBeeHive beeHive = (BlockBeeHive)GrowthCraftBees.blocks.beeHive.getBlockState();
			if (world.isAirBlock(pos) && beeHive.canBlockStay(world, pos))
			{
				//				System.out.println(x + " " + y + " " + z);
				world.setBlockState(i, j, k, beeHive, new Random().nextInt(4) + 2, 2);
			}
		}
		return true;
	}
}
