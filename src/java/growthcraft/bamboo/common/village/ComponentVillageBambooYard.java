package growthcraft.bamboo.common.village;

import java.util.List;
import java.util.Random;
import java.util.HashMap;

import growthcraft.bamboo.common.world.WorldGenBamboo;
import growthcraft.bamboo.GrowthCraftBamboo;
import growthcraft.core.util.SchemaToVillage.BlockEntry;
import growthcraft.core.util.SchemaToVillage.IBlockEntries;
import growthcraft.core.util.SchemaToVillage;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentVillageBambooYard extends StructureVillagePieces.Village implements SchemaToVillage.IVillage
{
	// Design by Ar97x
	private static final String[][] bambooYardSchema = {
		{
			// y: -1
			"           ",
			"           ",
			"           ",
			"  p     p  ",
			"     ~     ",
			"    ~~~    ",
			"   ~~~~~   ",
			"    ~~~    ",
			"     ~     ",
			"  p     p  ",
			"           ",
			"           "
		},
		{
			// y: 0
			"    pDp    ",
			"ppppp ppppp",
			"p         p",
			"p t     t p",
			"p    s    p",
			"p   s s   p",
			"p  s   s  p",
			"p   s s   p",
			"p    s    p",
			"p t     t p",
			"p         p",
			"ppppppppppp"
		},
		{
			// y: 1
			"    WdW    ",
			"W   W W   W",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"W         W"
		},
		{
			// y: 2
			"    WWW    ",
			"WWWWW WWWWW",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"W         W",
			"WWWWWWWWWWW"
		},
	};

	// DO NOT REMOVE
	public ComponentVillageBambooYard() {}

	public ComponentVillageBambooYard(Start startPiece, int par2, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
	{
		super(startPiece, par2);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = boundingBox;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static ComponentVillageBambooYard buildComponent(Start startPiece, List list, Random random, BlockPos pos, int coordBaseMode, int par7)
	{
		// the height of the structure is 15 blocks, since the maximum height of bamboo is 12~14 blocks (+1 for the water layer)
		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 11, 16, 12, coordBaseMode);
		if (canVillageGoDeeper(structureboundingbox)) {
			if (StructureComponent.findIntersecting(list, structureboundingbox) == null) {
				return new ComponentVillageBambooYard(startPiece, par7, random, structureboundingbox, coordBaseMode);
			}
		}
		return null;
	}

	public void placeBlockAtCurrentPositionPub(World world, Block block, int meta, BlockPos pos, StructureBoundingBox box)
	{
		placeBlockAtCurrentPosition(world, block, meta, x, y, z, box);
	}

	protected void placeWorldGenAt(World world, Random random, int tx, int ty, int tz, StructureBoundingBox bb, WorldGenerator generator)
	{
		final int x = this.getXWithOffset(tx, tz);
		final int y = this.getYWithOffset(ty);
		final int z = this.getZWithOffset(tx, tz);

		if (bb.isVecInside(x, y, z))
		{
			generator.generate(world, random, x, y, z);
		}
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

			// the structure is 1 block lower due to the water layer
			this.boundingBox.offset(0, this.averageGroundLvl - this.boundingBox.maxY + 14, 0);
		}

		// clear entire bounding box
		this.fillWithBlocks(world, box, 0, 0, 0, 11, 4, 12, Blocks.AIR, Blocks.AIR, false);
		this.fillWithBlocks(world, box, 0, 0, 0, 11, 0, 12, Blocks.GRASS, Blocks.GRASS, false);

		final HashMap<Character, IBlockEntries> map = new HashMap<Character, IBlockEntries>();

		// okay folks, no BIG D jokes here
		map.put('D', new BlockEntry(GrowthCraftBamboo.blocks.bambooDoor.getBlockState(), this.getMetadataWithOffset(GrowthCraftBamboo.blocks.bambooDoor.getBlockState(), 2)));
		// top of the door brought forward
		map.put('d', new BlockEntry(GrowthCraftBamboo.blocks.bambooDoor.getBlockState(), this.getMetadataWithOffset(GrowthCraftBamboo.blocks.bambooDoor.getBlockState(), 8 | 1)));
		map.put('p', new BlockEntry(GrowthCraftBamboo.blocks.bambooBlock.getBlockState(), 0));
		map.put('s', new BlockEntry(GrowthCraftBamboo.blocks.bambooSingleSlab.getBlockState(), 0));
		map.put('t', new BlockEntry(Blocks.TORCH, 0));
		map.put('~', new BlockEntry(Blocks.WATER, 0));
		map.put('W', new BlockEntry(GrowthCraftBamboo.blocks.bambooWall.getBlockState(), 0));

		SchemaToVillage.drawSchema(this, world, random, box, bambooYardSchema, map);

		// This places the bamboo trees to the best of its ability.
		final WorldGenBamboo genBamboo = new WorldGenBamboo(true);
		placeWorldGenAt(world, random, 4, 1, 4, box, genBamboo);
		placeWorldGenAt(world, random, 6, 1, 4, box, genBamboo);
		placeWorldGenAt(world, random, 3, 1, 5, box, genBamboo);
		placeWorldGenAt(world, random, 7, 1, 5, box, genBamboo);
		placeWorldGenAt(world, random, 3, 1, 7, box, genBamboo);
		placeWorldGenAt(world, random, 7, 1, 7, box, genBamboo);
		placeWorldGenAt(world, random, 4, 1, 8, box, genBamboo);
		placeWorldGenAt(world, random, 6, 1, 8, box, genBamboo);

		for (int row = 0; row < 12; ++row)
		{
			for (int col = 0; col < 11; ++col)
			{
				this.clearCurrentPositionBlocksUpwards(world, col, 16, row, box);
				this.func_151554_b(world, Blocks.DIRT, 0, col, -1, row, box);
			}
		}

		return true;
	}
}
