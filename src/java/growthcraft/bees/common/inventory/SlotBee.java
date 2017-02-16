package growthcraft.bees.common.inventory;

import growthcraft.api.bees.BeesRegistry;
import growthcraft.core.common.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SlotBee extends SlotInput {
    final ContainerBeeBox container;

    public SlotBee(ContainerBeeBox cont, IInventory inv, BlockPos pos, int index) {
        super(inv, index, pos.getX(), pos.getZ());
        this.container = cont;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack != null && BeesRegistry.instance().isItemBee(stack);
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
