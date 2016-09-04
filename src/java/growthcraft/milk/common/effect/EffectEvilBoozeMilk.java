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
package growthcraft.milk.common.effect;

import java.util.List;
import java.util.Random;

import growthcraft.api.core.CoreRegistry;
import growthcraft.api.core.effect.EffectAddPotionEffect;
import growthcraft.api.core.effect.EffectChance;
import growthcraft.api.core.effect.EffectList;
import growthcraft.api.core.effect.IEffect;
import growthcraft.api.core.effect.SimplePotionEffectFactory;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.init.MobEffects;

/**
 * If milk removes effects, then evil booze milk will add them.
 */
public class EffectEvilBoozeMilk implements IEffect
{
	private EffectList effects;

	public EffectEvilBoozeMilk()
	{
		this.effects = new EffectList();

		addEvilEffect(0.1f, MobEffects.BLINDNESS.id, 900, 0);
		addEvilEffect(0.1f, MobEffects.WITHER.id, 900, 0);
		addEvilEffect(0.2f, MobEffects.NAUSEA.id, 900, 0);
		addEvilEffect(0.2f, MobEffects.MINING_FATIGUE.id, 900, 0);
		addEvilEffect(0.2f, MobEffects.POISON.id, 900, 0);
		addEvilEffect(0.3f, MobEffects.HUNGER.id, 900, 0);
		addEvilEffect(0.5f, MobEffects.SLOWNESS.id, 900, 0);
		addEvilEffect(0.6f, MobEffects.INSTANT_DAMAGE.id, 20, 0);
		addEvilEffect(1.0f, MobEffects.WEAKNESS.id, 900, 0);
	}

	private void addEvilEffect(float chance, int id, int time, int lv)
	{
		effects.add(
			new EffectChance()
				.setChance(chance)
				.setEffect(
					new EffectAddPotionEffect()
						.setPotionFactory(new SimplePotionEffectFactory(id, time, lv))
				)
		);
	}

	@Override
	public void apply(World world, Entity en, Random r, Object d)
	{
		effects.apply(world, en, r, d);
	}

	@Override
	public void getDescription(List<String> list)
	{
		effects.getDescription(list);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, String name)
	{
		this.effects = null;
		if (nbt.hasKey(name))
		{
			final NBTTagCompound tag = nbt.getCompoundTag(name);
			if (tag.hasKey("effects"))
			{
				this.effects = (EffectList)CoreRegistry.instance().getEffectsRegistry().loadEffectFromNBT(tag, "effects");
			}
		}
		else
		{
			// log error
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, String name)
	{
		final NBTTagCompound tag = new NBTTagCompound();
		if (effects != null)
		{
			effects.writeToNBT(tag, "effects");
		}
		nbt.setTag(name, tag);
	}
}
