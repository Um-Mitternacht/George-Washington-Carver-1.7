/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015, 2016 IceDragon200
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
package growthcraft.core.common.tileentity;

import java.io.IOException;

import growthcraft.api.core.fluids.FluidTest;
import growthcraft.core.common.tileentity.device.FluidTanks;
import growthcraft.core.common.tileentity.device.IFluidTanks;
import growthcraft.core.common.tileentity.event.EventHandler;

import io.netty.buffer.ByteBuf;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Extend this base class if you want a base class with an `Inventory` and `Fluid Tanks`
 */
public abstract class GrcTileDeviceBase extends GrcTileInventoryBase implements IFluidHandler, IFluidTanks
{
	private FluidTanks tanks;

	public GrcTileDeviceBase()
	{
		super();
		this.tanks = new FluidTanks(createTanks());
	}

	protected void markFluidDirty()
	{
	}

	protected FluidTank[] createTanks()
	{
		return new FluidTank[] {};
	}

	@Override
	public boolean canFill(EnumFacing from, Fluid fluid)
	{
		return true;
	}

	@Override
	public boolean canDrain(EnumFacing from, Fluid fluid)
	{
		return true;
	}

	protected FluidStack doDrain(EnumFacing dir, int amount, boolean shouldDrain)
	{
		return null;
	}

	protected FluidStack doDrain(EnumFacing dir, FluidStack stack, boolean shouldDrain)
	{
		return null;
	}

	@Override
	public FluidStack drain(EnumFacing dir, int amount, boolean shouldDrain)
	{
		final FluidStack result = doDrain(dir, amount, shouldDrain);
		if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
		return result;
	}

	@Override
	public FluidStack drain(EnumFacing dir, FluidStack stack, boolean shouldDrain)
	{
		if (!FluidTest.isValid(stack)) return null;
		final FluidStack result = doDrain(dir, stack, shouldDrain);
		if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
		return result;
	}

	protected int doFill(EnumFacing dir, FluidStack stack, boolean shouldFill)
	{
		return 0;
	}

	@Override
	public int fill(EnumFacing dir, FluidStack stack, boolean shouldFill)
	{
		final int result = doFill(dir, stack, shouldFill);
		if (shouldFill && result != 0) markFluidDirty();
		return result;
	}

	@Override
	public FluidTankInfo[] getTankInfo(EnumFacing from)
	{
		return tanks.getTankInfo(from);
	}

	public IFluidTanks getTanks()
	{
		return tanks;
	}

	@Override
	public int getTankCount()
	{
		return tanks.getTankCount();
	}

	@Override
	public FluidTank[] getFluidTanks()
	{
		return tanks.getFluidTanks();
	}

	@Override
	public int getFluidAmountScaled(int scalar, int slot)
	{
		return tanks.getFluidAmountScaled(scalar, slot);
	}

	@Override
	public float getFluidAmountRate(int slot)
	{
		return tanks.getFluidAmountRate(slot);
	}

	@Override
	public boolean isFluidTankFilled(int slot)
	{
		return tanks.isFluidTankFilled(slot);
	}

	@Override
	public boolean isFluidTankFull(int slot)
	{
		return tanks.isFluidTankFull(slot);
	}

	@Override
	public boolean isFluidTankEmpty(int slot)
	{
		return tanks.isFluidTankEmpty(slot);
	}

	@Override
	public int getFluidAmount(int slot)
	{
		return tanks.getFluidAmount(slot);
	}

	@Override
	public FluidTank getFluidTank(int slot)
	{
		return tanks.getFluidTank(slot);
	}

	@Override
	public FluidStack getFluidStack(int slot)
	{
		return tanks.getFluidStack(slot);
	}

	@Override
	public FluidStack drainFluidTank(int slot, int amount, boolean shouldDrain)
	{
		final FluidStack result = tanks.drainFluidTank(slot, amount, shouldDrain);
		if (shouldDrain && FluidTest.isValid(result)) markFluidDirty();
		return result;
	}

	@Override
	public int fillFluidTank(int slot, FluidStack fluid, boolean shouldFill)
	{
		final int result = tanks.fillFluidTank(slot, fluid, shouldFill);
		if (shouldFill && result != 0) markFluidDirty();
		return result;
	}

	@Override
	public void setFluidStack(int slot, FluidStack stack)
	{
		tanks.setFluidStack(slot, stack);
		markFluidDirty();
	}

	@Override
	public Fluid getFluid(int slot)
	{
		return tanks.getFluid(slot);
	}

	@Override
	public void clearTank(int slot)
	{
		tanks.clearTank(slot);
		markFluidDirty();
	}

	protected void readTanksFromNBT(NBTTagCompound nbt)
	{
		if (tanks != null)
			tanks.readFromNBT(nbt);
	}

	@Override
	public void readFromNBTForItem(NBTTagCompound nbt)
	{
		super.readFromNBTForItem(nbt);
		readTanksFromNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_READ)
	public void readFromNBT_DeviceBase(NBTTagCompound nbt)
	{
		readTanksFromNBT(nbt);
	}

	private void writeTanksToNBT(NBTTagCompound nbt)
	{
		if (tanks != null)
			tanks.writeToNBT(nbt);
	}

	@Override
	public void writeToNBTForItem(NBTTagCompound nbt)
	{
		super.writeToNBTForItem(nbt);
		writeTanksToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NBT_WRITE)
	public void writeToNBT_DeviceBase(NBTTagCompound nbt)
	{
		writeTanksToNBT(nbt);
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_FluidTanks(ByteBuf stream) throws IOException
	{
		if (tanks != null)
			tanks.readFromStream(stream);
		return true;
	}

	@EventHandler(type=EventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_FluidTanks(ByteBuf stream) throws IOException
	{
		if (tanks != null)
			tanks.writeToStream(stream);
		return false;
	}
}
