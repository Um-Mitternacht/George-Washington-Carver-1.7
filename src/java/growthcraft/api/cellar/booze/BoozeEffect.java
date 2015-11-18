package growthcraft.api.cellar.booze;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.util.MathHelper;

public class BoozeEffect
{
	private boolean hasTipsyEffect;
	private float tipsyChance;
	private int tipsyTime;
	private List<PotionEntry> potionEntries = new ArrayList<PotionEntry>();

	public BoozeEffect clearTipsy()
	{
		this.hasTipsyEffect = false;
		this.tipsyChance = 0.0f;
		this.tipsyTime = 0;
		return this;
	}

	public BoozeEffect setTipsy(float chance, int time)
	{
		this.hasTipsyEffect = true;
		this.tipsyChance = MathHelper.clamp_float(chance, 0.1F, 1.0F);
		this.tipsyTime = time;
		return this;
	}

	public BoozeEffect clearPotionEntries()
	{
		potionEntries.clear();
		return this;
	}

	public BoozeEffect addPotionEntry(int id, int time, int level)
	{
		potionEntries.add(new PotionEntry(id, time, level));
		return this;
	}

	public Collection<PotionEntry> getPotionEntries()
	{
		return potionEntries;
	}

	public boolean hasPotionEntries()
	{
		return getPotionEntries().size() > 0;
	}

	public boolean canCauseTipsy()
	{
		return hasTipsyEffect;
	}

	public float getTipsyChance()
	{
		return tipsyChance;
	}

	public int getTipsyTime()
	{
		return tipsyTime;
	}

	public boolean isValid()
	{
		return canCauseTipsy() || hasPotionEntries();
	}
}
