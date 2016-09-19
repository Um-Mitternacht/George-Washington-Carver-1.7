package growthcraft.cellar.common.village;

import java.util.List;
import java.util.Random;

import growthcraft.cellar.GrowthCraftCellar;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentVillageTavern extends StructureVillagePieces.Village
{
	public ComponentVillageTavern() {}

	public ComponentVillageTavern(Start startPiece, int par2, Random random, StructureBoundingBox boundingBox, int par5)
	{
		super(startPiece, par2);
		this.coordBaseMode = par5;
		this.boundingBox = boundingBox;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static ComponentVillageTavern buildComponent(Start startPiece, List list, Random rand, int par3, int par4, int par5, int par6, int par7)
	{
		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 13, 9, 8, par6);
		if (canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(list, structureboundingbox) == null)
		{
			return new ComponentVillageTavern(startPiece, par7, rand, structureboundingbox, par6);
		}
		return null;
	}

	public boolean addComponentParts(World world, Random random, StructureBoundingBox box)
	{
		if (this.averageGroundLvl < 0)
		{
			this.averageGroundLvl = this.getAverageGroundLevel(world, box);

			if (this.averageGroundLvl < 0)
			{
				return true;
			}

			this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 9 - 1, 0);
		}

		int x;
		int z;

		this.fillWithBlocks(world, box, 1, 1, 1, 11, 4, 6, Blocks.AIR, Blocks.AIR, false);
		this.fillWithBlocks(world, box, 0, 0, 0, 12, 0, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 0, 6, 3, 12, 6, 3, Blocks.PLANKS, Blocks.PLANKS, false);

		for (z = -1; z <= 1; ++z)
		{
			this.fillWithBlocks(world, box, 0, 4 - z, 1 - z, 12, 4 - z, 1 - z, Blocks.PLANKS, Blocks.PLANKS, false);
			this.fillWithBlocks(world, box, 0, 4 - z, 5 + z, 12, 4 - z, 5 + z, Blocks.PLANKS, Blocks.PLANKS, false);
		}

		final int m1 = this.getMetadataWithOffset(Blocks.OAK_STAIRS, 3);
		final int m2 = this.getMetadataWithOffset(Blocks.OAK_STAIRS, 2);

		for (z = -1; z <= 2; ++z)
		{
			for (x = 0; x <= 12; ++x)
			{
				this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, m1, x, 4 + z, z, box);
				this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, m2, x, 4 + z, 6 - z, box);
			}
		}

		this.fillWithBlocks(world, box, 0, 1, 0, 0, 1, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 1, 1, 6, 12, 1, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 12, 1, 0, 12, 1, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 1, 1, 0, 11, 1, 0, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 0, 2, 0, 0, 2, 6, Blocks.PLANKS, Blocks.PLANKS, false);
		this.fillWithBlocks(world, box, 0, 3, 1, 0, 3, 5, Blocks.PLANKS, Blocks.PLANKS, false);
		this.fillWithBlocks(world, box, 0, 4, 2, 0, 4, 4, Blocks.PLANKS, Blocks.PLANKS, false);
		this.placeBlockAtCurrentPosition(world, Blocks.PLANKS, 0, 0, 5, 3, box);
		this.fillWithBlocks(world, box, 12, 2, 0, 12, 2, 6, Blocks.PLANKS, Blocks.PLANKS, false);
		this.fillWithBlocks(world, box, 12, 3, 1, 12, 3, 5, Blocks.PLANKS, Blocks.PLANKS, false);
		this.fillWithBlocks(world, box, 12, 4, 2, 12, 4, 4, Blocks.PLANKS, Blocks.PLANKS, false);
		this.placeBlockAtCurrentPosition(world, Blocks.PLANKS, 0, 12, 5, 3, box);
		this.fillWithBlocks(world, box, 1, 2, 0, 11, 2, 0, Blocks.PLANKS, Blocks.PLANKS, false);
		this.fillWithBlocks(world, box, 1, 2, 6, 11, 2, 6, Blocks.PLANKS, Blocks.PLANKS, false);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 0, 2, 1, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 0, 2, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 8, 2, 6, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 11, 2, 6, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 12, 4, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 12, 4, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 1, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 3, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 7, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.LOG, 0, 11, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 0, 2, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 0, 2, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 0, 2, 4, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 9, 2, 6, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 10, 2, 6, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 12, 4, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 2, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 8, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 9, 2, 0, box);
		this.placeBlockAtCurrentPosition(world, Blocks.GLASS_PANE, 0, 10, 2, 0, box);

		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 2), 2, 1, 1, box);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 2), 3, 1, 1, box);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 3), 2, 1, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 3), 3, 1, 3, box);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_FENCE, 0, 2, 1, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.OAK_FENCE, 0, 3, 1, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.WOODEN_PRESSURE_PLATE, 0, 2, 2, 2, box);
		this.placeBlockAtCurrentPosition(world, Blocks.WOODEN_PRESSURE_PLATE, 0, 3, 2, 2, box);

		for (z = 1; z <= 5; ++z)
		{
			if (z < 4)
			{
				this.placeBlockAtCurrentPosition(world, Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 1), 8, 1, z, box);
			}

			if (z != 5)
			{
				this.placeBlockAtCurrentPosition(world, Blocks.OAK_FENCE, 0, 9, 1, z, box);
				this.placeBlockAtCurrentPosition(world, Blocks.WOODEN_PRESSURE_PLATE, 0, 9, 2, z, box);
				this.placeBlockAtCurrentPosition(world, GrowthCraftCellar.blocks.fermentBarrel.getBlockState(), this.getMetadataWithOffset(Blocks.LADDER, 4), 11, 2, z, box);
			}

			this.placeBlockAtCurrentPosition(world, Blocks.PLANKS, 0, 11, 1, z, box);
		}

		for (z = -1; z <= 1; ++z)
		{
			this.fillWithBlocks(world, box, 3, 4 - z, 6 + z, 7, 4 - z, 6 + z, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		}

		this.fillWithBlocks(world, box, 3, 2, 6, 7, 3, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 4, 0, 7, 6, 2, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 4, 4, 6, 4, 8, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 5, 4, 7, 5, 8, 7, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 6, 4, 6, 6, 8, 6, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 5, 4, 5, 5, 8, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 4, 1, 5, 4, 2, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.fillWithBlocks(world, box, 6, 1, 5, 6, 2, 5, Blocks.COBBLESTONE, Blocks.COBBLESTONE, false);
		this.placeBlockAtCurrentPosition(world, Blocks.STONE_STAIRS, this.getMetadataWithOffset(Blocks.STONE_STAIRS, 0), 3, 1, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.STONE_STAIRS, this.getMetadataWithOffset(Blocks.STONE_STAIRS, 1), 7, 1, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.STONE_STAIRS, this.getMetadataWithOffset(Blocks.STONE_STAIRS, 0), 4, 3, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.STONE_STAIRS, this.getMetadataWithOffset(Blocks.STONE_STAIRS, 1), 6, 3, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.STONE_SLAB, 3 ^ 8, 5, 3, 5, box);
		this.fillWithBlocks(world, box, 5, 1, 6, 5, 9, 6, Blocks.AIR, Blocks.AIR, false);
		this.placeBlockAtCurrentPosition(world, Blocks.IRON_BARS, 0, 5, 1, 5, box);
		this.placeBlockAtCurrentPosition(world, Blocks.NETHERRACK, 0, 5, 0, 6, box);
		this.placeBlockAtCurrentPosition(world, Blocks.FIRE, 0, 5, 1, 6, box);

		this.placeDoorAtCurrentPosition(world, box, random, 5, 1, 0, this.getMetadataWithOffset(Blocks.OAK_DOOR, 1));

		if (this.getBlockAtCurrentPosition(world, 5, 0, -1, box) == Blocks.AIR && this.getBlockAtCurrentPosition(world, 5, -1, -1, box) != Blocks.AIR)
		{
			this.placeBlockAtCurrentPosition(world, Blocks.STONE_STAIRS, this.getMetadataWithOffset(Blocks.STONE_STAIRS, 3), 5, 0, -1, box);
		}

		for (z = 0; z < 8; ++z)
		{
			for (x = 0; x < 13; ++x)
			{
				this.clearCurrentPositionBlocksUpwards(world, x, 9, z, box);
				this.func_151554_b(world, Blocks.COBBLESTONE, 0, x, -1, z, box);
			}
		}

		this.spawnVillagers(world, box, 10, 1, 2, 1);
		return true;
	}

	@Override
	protected int getVillagerType(int par1)
	{
		return GrowthCraftCellar.getConfig().villagerBrewerID;
	}
}
