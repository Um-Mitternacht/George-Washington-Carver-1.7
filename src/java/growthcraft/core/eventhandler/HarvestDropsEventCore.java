package growthcraft.core.eventhandler;

import growthcraft.api.core.CoreRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class HarvestDropsEventCore
{
	private final int r = 5;

	@SubscribeEvent
	public void onHarvestDrops(HarvestDropsEvent event)
	{
		if (CoreRegistry.instance().vineDrops().isVine(event.block, event.blockMetadata) && !event.isSilkTouching && event.harvester != null)
		{
			if (event.harvester.getHeldItem() == null)
			{
				doDrops(event);
			}
			else if (event.harvester.getHeldItem().getItem() != Items.SHEARS)
			{
				doDrops(event);
			}
		}
	}

	private void doDrops(HarvestDropsEvent event)
	{
		if (CoreRegistry.instance().vineDrops().hasVineDrops())
		{
			if (new Random().nextInt(r) == 0)
			{
				event.drops.clear();
				event.dropChance = 1.0F;
				final ItemStack stack = CoreRegistry.instance().vineDrops().getVineDropItem(event.world);
				if (stack != null)
				{
					final ItemStack result = stack.copy();
					result.stackSize = event.world.rand.nextInt(stack.stackSize) + 1;
					event.drops.add(result);
				}
			}
		}
	}
}
