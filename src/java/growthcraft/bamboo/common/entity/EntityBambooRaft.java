package growthcraft.bamboo.common.entity;

import growthcraft.bamboo.GrowthCraftBamboo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityBambooRaft extends Entity
{
	/** true if no player in boat */
	private boolean isBoatEmpty;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	public EntityBambooRaft(World par1World, double par2, double par4, double par6)
	{
		this(par1World);
		this.setPosition(par2, par4 + (double)this.yOffset, par6);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = par2;
		this.prevPosY = par4;
		this.prevPosZ = par6;
	}

	public EntityBambooRaft(World par1World)
	{
		super(par1World);
		this.isBoatEmpty = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void entityInit()
	{
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
	 * pushable on contact, like boats or minecarts.
	 */
	public AxisAlignedBB getCollisionBox(Entity par1Entity)
	{
		return par1Entity.boundingBox;
	}

	/**
	 * returns the bounding box for this entity
	 */
	public AxisAlignedBB getBoundingBox()
	{
		return this.boundingBox;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities when colliding.
	 */
	public boolean canBePushed()
	{
		return true;
	}

	/**
	 * Returns the Y offset from the entity's position for any entity riding this one.
	 */
	public double getMountedYOffset()
	{
		return (double)this.height * 0.0D - 0.30000001192092896D;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (this.isEntityInvulnerable())
		{
			return false;
		}
		else if (!this.worldObj.isRemote && !this.isDead)
		{
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + par2 * 10.0F);
			this.setBeenAttacked();
			final boolean flag = par1DamageSource.getEntity() instanceof EntityPlayer &&
				((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 40.0F)
			{
				if (this.riddenByEntity != null)
				{
					this.riddenByEntity.mountEntity(this);
				}

				if (!flag)
				{
					this.dropItemWithOffset(GrowthCraftBamboo.items.bambooRaft.getItem(), 1, 0.0F);
				}

				this.setDead();
			}

			return true;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
	 */
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation()
	{
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}

	/**
	 * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
	 * posY, posZ, yaw, pitch
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
	{
		if (this.isBoatEmpty)
		{
			this.boatPosRotationIncrements = par9 + 5;
		}
		else
		{
			final double d3 = par1 - this.posX;
			final double d4 = par3 - this.posY;
			final double d5 = par5 - this.posZ;
			final double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if (d6 <= 1.0D)
			{
				return;
			}

			this.boatPosRotationIncrements = 3;
		}

		this.boatX = par1;
		this.boatY = par3;
		this.boatZ = par5;
		this.boatYaw = (double)par7;
		this.boatPitch = (double)par8;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.velocityX = this.motionX;
		this.motionY = par3;
		this.velocityY = this.motionY;
		this.motionZ = par5;
		this.velocityZ = this.motionZ;
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	@SuppressWarnings("rawtypes")
	public void onUpdate()
	{
		super.onUpdate();

		if (this.getTimeSinceHit() > 0)
		{
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F)
		{
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		final byte b0 = 5;
		double d0 = 0.0D;

		for (int i = 0; i < b0; ++i)
		{
			final double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 0) / (double)b0 - 0.125D;
			final double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double)(i + 1) / (double)b0 - 0.125D;
			final AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);

			if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.WATER))
			{
				d0 += 1.0D / (double)b0;
			}
		}

		final double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		double d2;
		double d4;
		int j;

		if (d10 > 0.26249999999999996D)
		{
			d2 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D);
			d4 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D);

			for (j = 0; (double)j < 1.0D + d10 * 60.0D; ++j)
			{
				final double d5 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
				final double d6 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double d8;
				double d9;

				if (this.rand.nextBoolean())
				{
					d8 = this.posX - d2 * d5 * 0.8D + d4 * d6;
					d9 = this.posZ - d4 * d5 * 0.8D - d2 * d6;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
				else
				{
					d8 = this.posX + d2 + d4 * d5 * 0.7D;
					d9 = this.posZ + d4 - d2 * d5 * 0.7D;
					this.worldObj.spawnParticle("splash", d8, this.posY - 0.125D, d9, this.motionX, this.motionY, this.motionZ);
				}
			}
		}

		double d11;
		double d12;

		if (this.worldObj.isRemote && this.isBoatEmpty)
		{
			if (this.boatPosRotationIncrements > 0)
			{
				d2 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
				d4 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
				d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
				d12 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
				this.rotationYaw = (float)((double)this.rotationYaw + d12 / (double)this.boatPosRotationIncrements);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(d2, d4, d11);
				this.setRotation(this.rotationYaw, this.rotationPitch);
			}
			else
			{
				d2 = this.posX + this.motionX;
				d4 = this.posY + this.motionY;
				d11 = this.posZ + this.motionZ;
				this.setPosition(d2, d4, d11);

				if (this.onGround)
				{
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}

				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}
		}
		else
		{
			if (d0 < 1.0D)
			{
				d2 = d0 * 2.0D - 1.0D;
				this.motionY += 0.03999999910593033D * d2;
			}
			else
			{
				if (this.motionY < 0.0D)
				{
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}

			if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
			{
				final EntityLivingBase entitylivingbase = (EntityLivingBase)this.riddenByEntity;
				final float f = this.riddenByEntity.rotationYaw + -entitylivingbase.moveStrafing * 90.0F;
				this.motionX += -Math.sin((double)(f * (float)Math.PI / 180.0F)) * this.speedMultiplier * (double)entitylivingbase.moveForward * 0.05000000074505806D;
				this.motionZ += Math.cos((double)(f * (float)Math.PI / 180.0F)) * this.speedMultiplier * (double)entitylivingbase.moveForward * 0.05000000074505806D;
			}

			d2 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if (d2 > 0.35D)
			{
				d4 = 0.35D / d2;
				this.motionX *= d4;
				this.motionZ *= d4;
				d2 = 0.35D;
			}

			if (d2 > d10 && this.speedMultiplier < 0.35D)
			{
				this.speedMultiplier += (0.35D - this.speedMultiplier) / 35.0D;

				if (this.speedMultiplier > 0.35D)
				{
					this.speedMultiplier = 0.35D;
				}
			}
			else
			{
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if (this.speedMultiplier < 0.07D)
				{
					this.speedMultiplier = 0.07D;
				}
			}

			int l;

			for (l = 0; l < 4; ++l)
			{
				final int i1 = MathHelper.floor_double(this.posX + ((double)(l % 2) - 0.5D) * 0.8D);
				j = MathHelper.floor_double(this.posZ + ((double)(l / 2) - 0.5D) * 0.8D);

				for (int j1 = 0; j1 < 2; ++j1)
				{
					final int k = MathHelper.floor_double(this.posY) + j1;
					final Block block = this.worldObj.getBlockState(i1, k, j);

					if (block == Blocks.SNOW_LAYER)
					{
						this.worldObj.setBlockToAir(i1, k, j);
						this.isCollidedHorizontally = false;
					}
					else if (block == Blocks.WATERLILY)
					{
						this.worldObj.destroyBlock(i1, k, j, true);
						this.isCollidedHorizontally = false;
					}
				}
			}

			if (this.onGround)
			{
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			if (this.isCollidedHorizontally && d10 > 0.2D)
			{
				if (!this.worldObj.isRemote && !this.isDead)
				{
					this.setDead();

					for (l = 0; l < 3; ++l)
					{
						this.dropItemWithOffset(GrowthCraftBamboo.blocks.bambooBlock.getItem(), 1, 0.0F);
					}

					for (l = 0; l < 2; ++l)
					{
						this.dropItemWithOffset(GrowthCraftBamboo.items.bamboo.getItem(), 1, 0.0F);
					}
				}
			}
			else
			{
				this.motionX *= 0.9900000095367432D;
				this.motionY *= 0.949999988079071D;
				this.motionZ *= 0.9900000095367432D;
			}

			this.rotationPitch = 0.0F;
			d4 = (double)this.rotationYaw;
			d11 = this.prevPosX - this.posX;
			d12 = this.prevPosZ - this.posZ;

			if (d11 * d11 + d12 * d12 > 0.001D)
			{
				d4 = (double)((float)(Math.atan2(d12, d11) * 180.0D / Math.PI));
			}

			double d7 = MathHelper.wrapAngleTo180_double(d4 - (double)this.rotationYaw);

			if (d7 > 20.0D)
			{
				d7 = 20.0D;
			}

			if (d7 < -20.0D)
			{
				d7 = -20.0D;
			}

			this.rotationYaw = (float)((double)this.rotationYaw + d7);
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if (!this.worldObj.isRemote)
			{
				final List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

				if (list != null && !list.isEmpty())
				{
					for (int k1 = 0; k1 < list.size(); ++k1)
					{
						final Entity entity = (Entity)list.get(k1);

						if (entity != this.riddenByEntity && entity.canBePushed() && entity instanceof EntityBoat)
						{
							entity.applyEntityCollision(this);
						}
					}
				}

				if (this.riddenByEntity != null && this.riddenByEntity.isDead)
				{
					this.riddenByEntity = null;
				}
			}
		}
	}

	public void updateRiderPosition()
	{
		if (this.riddenByEntity != null)
		{
			final double d0 = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			final double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + d0, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	/**
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer par1EntityPlayer)
	{
		if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != par1EntityPlayer)
		{
			return true;
		}
		else
		{
			if (!this.worldObj.isRemote)
			{
				par1EntityPlayer.mountEntity(this);
			}

			return true;
		}
	}

	/**
	 * Takes in the distance the entity has fallen this tick and whether its on the ground to update the fall distance
	 * and deal fall damage if landing on the ground.  Args: distanceFallenThisTick, onGround
	 */
	protected void updateFallState(double par1, boolean par3)
	{
		final int i = MathHelper.floor_double(this.posX);
		final int j = MathHelper.floor_double(this.posY);
		final int k = MathHelper.floor_double(this.posZ);

		if (par3)
		{
			if (this.fallDistance > 3.0F)
			{
				this.fall(this.fallDistance);

				if (!this.worldObj.isRemote && !this.isDead)
				{
					this.setDead();
					int l;

					for (l = 0; l < 3; ++l)
					{
						this.dropItemWithOffset(GrowthCraftBamboo.blocks.bambooBlock.getItem(), 1, 0.0F);
					}

					for (l = 0; l < 2; ++l)
					{
						this.dropItemWithOffset(GrowthCraftBamboo.items.bamboo.getItem(), 1, 0.0F);
					}
				}

				this.fallDistance = 0.0F;
			}
		}
		else if (this.worldObj.getBlockState(i, j - 1, k).getMaterial() != Material.WATER && par1 < 0.0D)
		{
			this.fallDistance = (float)((double)this.fallDistance - par1);
		}
	}

	/**
	 * Sets the damage taken from the last hit.
	 * @param damage - Damage taken
	 */
	public void setDamageTaken(float damage)
	{
		this.dataWatcher.updateObject(19, Float.valueOf(damage));
	}

	/**
	 * Gets the damage taken from the last hit.
	 * @return damage taken
	 */
	public float getDamageTaken()
	{
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 * @param time - since last hit
	 */
	public void setTimeSinceHit(int time)
	{
		this.dataWatcher.updateObject(17, Integer.valueOf(time));
	}

	/**
	 * Gets the time since the last hit.
	 * @return time since last hit
	 */
	public int getTimeSinceHit()
	{
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	/**
	 * Sets the forward direction of the entity.
	 * @param direction - forward direction
	 */
	public void setForwardDirection(int direction)
	{
		this.dataWatcher.updateObject(18, Integer.valueOf(direction));
	}

	/**
	 * Gets the forward direction of the entity.
	 * @return forward direction
	 */
	public int getForwardDirection()
	{
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	/**
	 * true if no player in boat
	 * @param state - is boat empty
	 */
	@SideOnly(Side.CLIENT)
	public void setIsBoatEmpty(boolean state)
	{
		this.isBoatEmpty = state;
	}
}
