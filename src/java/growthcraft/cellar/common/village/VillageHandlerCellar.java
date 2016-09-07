package growthcraft.cellar.common.village;

import java.util.List;
import java.util.Random;

import net.minecraftforge.fml.common.registry.VillagerRegistry.IVillageCreationHandler;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.StructureVillagePieces.PieceWeight;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class VillageHandlerCellar implements IVillageCreationHandler
{
	@Override
	public PieceWeight getVillagePieceWeight(Random random, int i)
	{
		return new PieceWeight(ComponentVillageTavern.class, 18, MathHelper.getRandomIntegerInRange(random, 0 + i, 2 + i));
	}

	@Override
	public Class<?> getComponentClass()
	{
		return ComponentVillageTavern.class;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Object buildComponent(PieceWeight villagePiece, Start startPiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
	{
		return ComponentVillageTavern.buildComponent(startPiece, pieces, random, p1, p2, p3, p4, p5);
	}
}
