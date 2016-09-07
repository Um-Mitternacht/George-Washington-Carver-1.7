package growthcraft.grapes.event;

import growthcraft.core.util.BlockCheck;
import growthcraft.api.core.util.BlockFlags;
import growthcraft.grapes.common.block.BlockGrapeVine0;
import growthcraft.grapes.common.block.BlockGrapeVine1;
import growthcraft.grapes.common.block.BlockGrapeLeaves;
import growthcraft.grapes.GrowthCraftGrapes;

import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraft.util.EnumFacing;

public class BonemealEventGrapes
{
	private void bonemealGrapeVine0(BonemealEvent event)
	{
		final BlockGrapeVine0 vine = (BlockGrapeVine0)event.block;
		final int meta = event.world.getBlockMetadata(event.x, event.y, event.z);

		if (!event.world.isRemote)
		{
			final int i = MathHelper.getRandomIntegerInRange(event.world.rand, 1, 2);
			if (meta == 0)
			{
				if (i == 1)
				{
					vine.incrementGrowth(event.world, event.x, event.y, event.z, meta);
				}
				else if (i == 2)
				{
					event.world.setBlock(event.x, event.y, event.z, GrowthCraftGrapes.blocks.grapeVine1.getBlock(), 0, BlockFlags.ALL);
				}
			}
			else if (meta == 1)
			{
				if (i == 1)
				{
					event.world.setBlock(event.x, event.y, event.z, GrowthCraftGrapes.blocks.grapeVine1.getBlock(), 0, BlockFlags.ALL);
				}
				else if (i == 2)
				{
					if (BlockCheck.isRope(event.world.getBlock(event.x, event.y + 1, event.z)))
					{
						event.world.setBlock(event.x, event.y, event.z, GrowthCraftGrapes.blocks.grapeVine1.getBlock(), 1, BlockFlags.ALL);
						event.world.setBlock(event.x, event.y + 1, event.z, GrowthCraftGrapes.blocks.grapeLeaves.getBlock(), 0, BlockFlags.ALL);
					}
					else
					{
						event.world.setBlock(event.x, event.y, event.z, GrowthCraftGrapes.blocks.grapeVine1.getBlock(), 0, BlockFlags.ALL);
					}
				}
			}
		}
		event.setResult(Result.ALLOW);
	}

	private void bonemealGrapeVine1(BonemealEvent event)
	{
		final BlockGrapeVine1 vine = (BlockGrapeVine1)event.block;
		final int meta = event.world.getBlockMetadata(event.x, event.y, event.z);
		if (meta == 0 && BlockCheck.isRope(event.world.getBlock(event.x, event.y + 1, event.z)))
		{
			if (!event.world.isRemote)
			{
				vine.incrementGrowth(event.world, event.x, event.y, event.z, meta);
				event.world.setBlock(event.x, event.y + 1, event.z, GrowthCraftGrapes.blocks.grapeLeaves.getBlock(), 0, BlockFlags.ALL);
			}
			event.setResult(Result.ALLOW);
		}
		if (meta == 0 && event.world.isAirBlock(event.x, event.y + 1, event.z))
		{
			if (!event.world.isRemote)
			{
				vine.incrementGrowth(event.world, event.x, event.y, event.z, meta);
				event.world.setBlock(event.x, event.y + 1, event.z, GrowthCraftGrapes.blocks.grapeVine1.getBlock(), 0, BlockFlags.ALL);
			}
			event.setResult(Result.ALLOW);
		}
		else if (meta == 0 && event.world.getBlock(event.x, event.y + 1, event.z) ==  GrowthCraftGrapes.blocks.grapeLeaves.getBlock())
		{
			if (!event.world.isRemote)
			{
				vine.incrementGrowth(event.world, event.x, event.y, event.z, meta);
			}
			event.setResult(Result.ALLOW);
		}
		else
		{
			event.setResult(Result.DENY);
		}
	}

	public boolean growGrapeLeavesOutwards(BonemealEvent event)
	{
		boolean grewOutwards = false;
		final BlockGrapeLeaves leaves = GrowthCraftGrapes.blocks.grapeLeaves.getBlock();
		if (leaves.canGrowOutwardsOnRope(event.world, event.x, event.y, event.z))
		{
			// random, grow 1 or 2 blocks outwards, but at least 1
			int allowedGrowthCount = 1 + event.world.rand.nextInt(2);
			// to give the expansion a sense of randomness, offset the array start position
			final int start = event.world.rand.nextInt(4);
			for (int i = 0; i < BlockCheck.DIR4.length; ++i)
			{
				if (allowedGrowthCount <= 0) break;

				final EnumFacing dir = BlockCheck.DIR4[(start + i) % BlockCheck.DIR4.length];
				final int x = event.x + dir.offsetX;
				final int z = event.z + dir.offsetZ;

				if (leaves.canGrowHere(event.world, x, event.y, z))
				{
					event.world.setBlock(x, event.y, z, leaves, 0, BlockFlags.UPDATE_AND_SYNC);
					grewOutwards = true;
					allowedGrowthCount--;
				}
			}
		}
		return grewOutwards;
	}

	private void bonemealGrapeLeaves(BonemealEvent event)
	{
		if (growGrapeLeavesOutwards(event))
		{
			event.setResult(Result.ALLOW);
		}
		else if (GrowthCraftGrapes.blocks.grapeLeaves.getBlock().growGrapeBlock(event.world, event.x, event.y, event.z))
		{
			event.setResult(Result.ALLOW);
		}
		else
		{
			event.setResult(Result.DENY);
		}
	}

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event)
	{
		if (GrowthCraftGrapes.blocks.grapeVine0.getBlock() == event.block)
		{
			bonemealGrapeVine0(event);
		}
		else if (GrowthCraftGrapes.blocks.grapeVine1.getBlock() == event.block)
		{
			bonemealGrapeVine1(event);
		}
		else if (GrowthCraftGrapes.blocks.grapeLeaves.getBlock() == event.block)
		{
			bonemealGrapeLeaves(event);
		}
	}
}
