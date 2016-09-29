package growthcraft.cellar.common.inventory.slot;

import growthcraft.api.cellar.CellarRegistry;
import growthcraft.core.common.inventory.slot.SlotInput;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class SlotInputPressing extends SlotInput
{
	public SlotInputPressing(IInventory inv, BlockPos pos)
	{
		super(inv, x, y, z);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return CellarRegistry.instance().pressing().hasPressingRecipe(stack);
	}
}
