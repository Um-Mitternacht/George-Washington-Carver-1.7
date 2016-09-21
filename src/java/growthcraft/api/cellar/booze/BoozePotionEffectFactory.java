/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 IceDragon200
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
package growthcraft.api.cellar.booze;

import growthcraft.api.cellar.CellarRegistry;
import growthcraft.api.core.CoreRegistry;
import growthcraft.api.core.description.Describer;
import growthcraft.api.core.effect.IPotionEffectFactory;
import growthcraft.api.core.fluids.FluidTag;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BoozePotionEffectFactory implements IPotionEffectFactory
{
	private String id;
	private int time;
	private int level;
	private Fluid booze;

	public BoozePotionEffectFactory(@Nonnull Fluid b, String i, int tm, int lvl)
	{
		this.booze = b;
		this.id = i;
		this.time = tm;
		this.level = lvl;
	}


	public String getID()
	{
		return id;
	}

	public int getTime()
	{
		return time;
	}

	public int getLevel()
	{
		return level;
	}

	public PotionEffect createPotionEffect(World world, Entity entity, Random random, Object data)
	{
		final Collection<FluidTag> tags = CoreRegistry.instance().fluidDictionary().getFluidTags(booze);

		if (tags != null)
		{
			int tm = getTime();
			int lv = getLevel();
			for (FluidTag tag : tags)
			{
				final IModifierFunction func = CellarRegistry.instance().booze().getModifierFunction(tag);
				if (func != null)
				{
					tm = func.applyTime(tm);
					lv = func.applyLevel(lv);
				}
			}
			return new PotionEffect(Potion.getPotionById(Integer.parseInt(id)), getTime(), getLevel());
		}
		return null;
	}

	public void getDescription(List<String> list)
	{
		final PotionEffect pe = createPotionEffect(null, null, null, null);
		Describer.getPotionEffectDescription(list, pe);
	}

	private void readFromNBT(NBTTagCompound data)
	{
		this.booze = null;
		this.id = String.valueOf(data.getInteger("id"));
		this.time = data.getInteger("time");
		this.level = data.getInteger("level");
		if (data.hasKey("fluid.name"))
		{
			this.booze = FluidRegistry.getFluid(data.getString("fluid.name"));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound data, String name)
	{
		if (data.hasKey(name))
		{
			final NBTTagCompound subData = data.getCompoundTag(name);
			readFromNBT(subData);
		}
		else
		{
			// LOG error
		}
	}

	private void writeToNBT(NBTTagCompound data)
	{
		data.setInteger("id", Integer.parseInt(getID()));
		data.setInteger("time", getTime());
		data.setInteger("level", getLevel());
		if (booze != null)
		{
			data.setString("fluid.name", booze.getName());
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound target = new NBTTagCompound();
		final String factoryName = CoreRegistry.instance().getPotionEffectFactoryRegistry().getName(this.getClass());

		target.setString("__name__", factoryName);
		writeToNBT(target);

		data.setTag(name, target);
	}
}
