package growthcraft.cellar.common.inventory.slot;

import growthcraft.core.common.inventory.slot.GrcSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotFruitPressResidue extends GrcSlot
{
	public SlotFruitPressResidue(IInventory inv, BlockPos pos)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}
}
