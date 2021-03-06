package growthcraft.api.core.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;

import java.util.*;

public class FluidUtils {
    private static Map<Fluid, List<FluidContainerData>> fluidData;

    private FluidUtils() {
    }

    public static Map<Fluid, List<FluidContainerData>> getFluidData() {
        if (fluidData == null || fluidData.size() == 0) {
            fluidData = new HashMap<Fluid, List<FluidContainerData>>();
            for (FluidContainerData data : Arrays.asList(FluidContainerRegistry.getRegisteredFluidContainerData())) {
                if (!fluidData.containsKey(data.fluid.getFluid())) {
                    fluidData.put(data.fluid.getFluid(), new ArrayList<FluidContainerData>());
                }
                fluidData.get(data.fluid.getFluid()).add(data);
            }
        }

        return fluidData;
    }

    public static List<ItemStack> getFluidContainers(FluidStack... fluids) {
        if (fluids.length == 1) {
            final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();
            final FluidStack fluidStack = fluids[0];

            for (FluidContainerData data : getFluidData().get(fluidStack.getFluid())) {
                if (data.fluid.amount >= fluidStack.amount) {
                    fluidContainers.add(data.filledContainer);
                }
            }

            return fluidContainers;
        } else {
            return getFluidContainers(Arrays.asList(fluids));
        }
    }

    public static List<ItemStack> getFluidContainers(Collection<FluidStack> fluids) {
        final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();

        for (FluidStack fluidStack : fluids) {
            fluidContainers.addAll(getFluidContainers(fluidStack));
        }

        return fluidContainers;
    }

    public static FluidStack drainFluidBlock(World world, BlockPos pos, boolean doDrain) {
        final IBlockState state = world.getBlockState(pos);
        final Block block = state.getBlock();
        if (block instanceof BlockFluidBase) {
            final BlockFluidBase bfb = (BlockFluidBase) block;
            return bfb.drain(world, pos, doDrain);
        } else if (block == Blocks.LAVA) {
            if (doDrain) world.setBlockToAir(pos);
            return new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
        } else if (block == Blocks.WATER) {
            if (doDrain) world.setBlockToAir(pos);
            return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
        }
        return null;
    }

    public static List<Fluid> getFluidsByNames(List<String> names) {
        final List<Fluid> fluids = new ArrayList<Fluid>();
        for (String name : names) {
            fluids.add(FluidRegistry.getFluid(name));
        }
        return fluids;
    }

    public static FluidStack exchangeFluid(FluidStack stack, Fluid newFluid) {
        return new FluidStack(newFluid, stack.amount);
    }

    public static boolean doesFluidExist(String name) {
        return FluidRegistry.getFluid(name) != null && FluidRegistry.isFluidRegistered(name);
    }

    public static boolean doesFluidExist(Fluid fluid) {
        return fluid != null && FluidRegistry.isFluidRegistered(fluid);
    }

    public static boolean doesFluidsExist(Fluid[] fluid) {
        for (int i = 0; i < fluid.length; ++i) {
            if (!doesFluidExist(fluid[i])) {
                return false;
            }
        }
        return true;
    }

    public static FluidStack replaceFluidStack(int fluidId, FluidStack srcStack) {
        final Fluid fluid = FluidRegistry.getFluid(fluidId);
        if (fluid == null) {
            // An invalid fluid
            return null;
        }

        if (srcStack == null) {
            return new FluidStack(fluid, 0);
        }
        return new FluidStack(fluid, srcStack.amount);
    }

    public static FluidStack updateFluidStackAmount(FluidStack srcStack, int amount) {
        if (srcStack == null) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }
        srcStack.amount = amount;
        return srcStack;
    }
}
