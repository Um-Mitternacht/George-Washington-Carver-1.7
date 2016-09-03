package growthcraft.rice.common.village;

import java.util.List;
import java.util.Random;
import java.util.HashMap;

import growthcraft.core.util.SchemaToVillage.BlockEntry;
import growthcraft.core.util.SchemaToVillage.IBlockEntries;
import growthcraft.core.util.SchemaToVillage;
import growthcraft.rice.GrowthCraftRice;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;

public class ComponentVillageRiceField extends StructureVillagePieces.Village implements SchemaToVillage.IVillage
{
	// Design by Ar97x, with some minor modifications by IceDragon (very minor)
	protected static final String[][] riceFieldSchema = {
		{
			"    sss    ",
			"x   x|x   x",
			" x-------x ",
			" |ppppppp| ",
			" |p~~~~~p| ",
			" |p~ppp~p| ",
			" |p~ppp~p| ",
			" |p~ppp~p| ",
			" |p~~~~~p| ",
			" |ppppppp| ",
			" x-------x ",
			"x         x"
		},
		{
			"           ",
			"x---x x---x",
			"|         |",
			"| rrrrrrr |",
			"| r     r |",
			"| r rrr r |",
			"| r rrr r |",
			"| r rrr r |",
			"| r     r |",
			"| rrrrrrr |",
			"|         |",
			"x---------x"
		},
		{
			"           ",
			"f   fgf   f",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"f         f"
		},
		{
			"           ",
			"t   t t   t",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"           ",
			"t         t"
		}
	};

	// DO NOT REMOVE
	public ComponentVillageRiceField() {}

	public ComponentVillageRiceField(Start startPiece, int par2, Random random, StructureBoundingBox boundingBox, int coordBaseMode)
	{
		super(startPiece, par2);
		this.coordBaseMode = coordBaseMode;
		this.boundingBox = boundingBox;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public static ComponentVillageRiceField buildComponent(Start startPiece, List list, Random random, int x, int y, int z, int coordBaseMode, int par7)
	{
		final StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0, 0, 11, 5, 12, coordBaseMode);
		if (canVillageGoDeeper(structureboundingbox))
		{
			if (StructureComponent.findIntersecting(list, structureboundingbox) == null)
			{
				return new ComponentVillageRiceField(startPiece, par7, random, structureboundingbox, coordBaseMode);
			}
		}
		return null;
	}

	public void placeBlockAtCurrentPositionPub(World world, Block block, int meta, int x, int y, int z, StructureBoundingBox box)
	{
		placeBlockAtCurrentPosition(world, block, meta, x, y, z, box);
	}

	public boolean addComponentParts(World world, Random random, StructureBoundingBox box)
	{
		if (this.field_143015_k < 0)
		{
			this.field_143015_k = this.getAverageGroundLevel(world, box);

			if (this.field_143015_k < 0)
			{
				return true;
			}

			this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
		}

		// clear entire bounding box
		fillWithBlocks(world, box, 0, 0, 0, 11, 5, 12, Blocks.AIR, Blocks.AIR, false);
		fillWithBlocks(world, box, 0, 0, 0, 11, 0, 12, Blocks.GRASS, Blocks.GRASS, false);

		final boolean vert = coordBaseMode == 0 || coordBaseMode == 2;
		final HashMap<Character, IBlockEntries> map = new HashMap<Character, IBlockEntries>();

		map.put('-', new BlockEntry(Blocks.LOG, vert ? 4 : 8));
		map.put('f', new BlockEntry(Blocks.OAK_FENCE));
		map.put('g', new BlockEntry(Blocks.OAK_FENCE_GATE, this.getMetadataWithOffset(Blocks.OAK_FENCE_GATE, 0)));
		map.put('p', new BlockEntry(GrowthCraftRice.blocks.paddyField.getBlock(), GrowthCraftRice.getConfig().paddyFieldMax));
		map.put('r', new BlockEntry(GrowthCraftRice.blocks.riceBlock.getBlock(), 6));
		map.put('s', new BlockEntry(Blocks.OAK_STAIRS, this.getMetadataWithOffset(Blocks.OAK_STAIRS, 3)));
		map.put('t', new BlockEntry(Blocks.TORCH));
		map.put('x', new BlockEntry(Blocks.LOG));
		map.put('|', new BlockEntry(Blocks.LOG, vert ? 8 : 4));
		map.put('~', new BlockEntry(Blocks.WATER));

		SchemaToVillage.drawSchema(this, world, random, box, riceFieldSchema, map, 0, 1, 0);

		for (int row = 0; row < 12; ++row)
		{
			for (int col = 0; col < 11; ++col)
			{
				this.clearCurrentPositionBlocksUpwards(world, col, 4, row, box);
				this.func_151554_b(world, Blocks.DIRT, 0, col, -1, row, box);
			}
		}

		return true;
	}
}
