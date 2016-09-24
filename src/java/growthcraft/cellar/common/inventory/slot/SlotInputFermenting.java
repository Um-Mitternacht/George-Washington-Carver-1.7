package growthcraft.cellar.common.inventory.slot;

import growthcraft.core.common.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotInputFermenting extends SlotInput
{
	public SlotInputFermenting(IInventory inv, BlockPos pos)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}
}
