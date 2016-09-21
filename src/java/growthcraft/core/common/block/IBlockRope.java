package growthcraft.core.common.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockRope
{
	/**
	 * @param world - world to interact with
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
	 * @return can the rope be connected at this point?
	 *
	 * <pre>
	 * {@code
	 *	 public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos)
	 *	 {
	 *	 	 return world.getBlockState(x, y, z) instanceof IBlockRope;
	 *	 }
	 * }
	 * </pre>
	 */
	public boolean canConnectRopeTo(IBlockAccess world, BlockPos pos);
}
