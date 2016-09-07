/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 IceDragon200
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package growthcraft.milk.common.tileentity;

import java.util.List;
import java.io.IOException;

import growthcraft.api.core.nbt.INBTItemSerializable;
import growthcraft.core.common.tileentity.event.EventHandler;
import growthcraft.core.common.tileentity.feature.IItemHandler;
import growthcraft.core.common.tileentity.GrcTileBase;
import growthcraft.core.util.ItemUtils;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.struct.Cheese;
import growthcraft.milk.GrowthCraftMilk;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCheeseBlock extends GrcTileBase implements IItemHandler, INBTItemSerializable
{
	private Cheese cheese = new Cheese();

	public List<ItemStack> populateDrops(List<ItemStack> list)
	{
		if (cheese.isAged())
		{
			final ItemStack stack = cheese.asFullStack();
			if (stack != null) list.add(stack);
		}
		return list;
	}

	public Cheese getCheese()
	{
		return cheese;
	}

	public int getCheeseId()
	{
		return getCheese().getId();
	}

	public int getCheeseStageId()
	{
		return getCheese().getStageId();
	}

	protected void readCheeseFromNBT(NBTTagCompound nbt)
	{
		cheese.readFromNBT(nbt);
	}

	/**
	 * When the tileentity is reloaded from an ItemStack
	 *
	 * @param nbt  tag compound to read
	 */
	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readCheeseFromNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_CheeseBlock(NBTTagCompound nbt)
	{
		readCheeseFromNBT(nbt);
	}

	protected void writeCheeseToNBT(NBTTagCompound nbt)
	{
		cheese.writeToNBT(nbt);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeCheeseToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_CheeseBlock(NBTTagCompound nbt)
	{
		writeCheeseToNBT(nbt);
	}

	public ItemStack asItemStack()
	{
		final ItemStack stack = GrowthCraftMilk.blocks.cheeseBlock.asStack();
		final NBTTagCompound tag = ItemBlockCheeseBlock.openNBT(stack);
		writeToNBTForItem(tag);
		return stack;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_CheeseBlock(ByteBuf stream) throws IOException
	{
		cheese.readFromStream(stream);
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_CheeseBlock(ByteBuf stream) throws IOException
	{
		cheese.writeToStream(stream);
		return true;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if (!worldObj.isRemote)
		{
			cheese.update();
			if (cheese.needClientUpdate)
			{
				cheese.needClientUpdate = false;
				if (cheese.hasSlices())
				{
					markForUpdate();
				}
				else
				{
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
				}
			}
		}
	}

	@Override
	public boolean tryPlaceItem(EntityPlayer player, ItemStack onHand)
	{
		return cheese.tryWaxing(onHand);
	}

	@Override
	public boolean tryTakeItem(EntityPlayer player, ItemStack onHand)
	{
		if (cheese.isAged())
		{
			final ItemStack stack = cheese.yankSlices(1, true);
			if (stack != null)
			{
				ItemUtils.addStackToPlayer(stack, player, false);
			}
			cheese.needClientUpdate |= true;
			return true;
		}
		return false;
	}
}
