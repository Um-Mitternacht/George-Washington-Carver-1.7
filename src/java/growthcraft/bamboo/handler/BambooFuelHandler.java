package growthcraft.bamboo.handler;

import growthcraft.bamboo.GrowthCraftBamboo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class BambooFuelHandler implements IFuelHandler {
    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel != null) {
            final Item item = fuel.getItem();
            if (GrowthCraftBamboo.items.bambooCoal.equals(item)) {
                return 800;
            } else if (GrowthCraftBamboo.items.bambooShootFood.equals(item)) {
                return 100;
            } else if (GrowthCraftBamboo.blocks.bambooShoot.equals(item)) {
                return 100;
            }
            // Alatyami added per GitHub Issue #55
            else if (GrowthCraftBamboo.blocks.bambooSingleSlab.equals(item)) {
                return 150;
            }
        }
        return 0;
    }
}
