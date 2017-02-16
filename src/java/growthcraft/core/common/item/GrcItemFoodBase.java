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
package growthcraft.core.common.item;

import growthcraft.api.core.effect.IEffect;
import growthcraft.api.core.i18n.GrcI18n;
import growthcraft.core.lib.GrcCoreState;
import growthcraft.core.util.ItemUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class GrcItemFoodBase extends ItemFood {
    private IEffect effect;
    private EnumAction action = EnumAction.EAT;

    public GrcItemFoodBase(int hunger, float saturation, boolean isWolfFav) {
        super(hunger, saturation, isWolfFav);
    }

    public GrcItemFoodBase(int hunger, boolean isWolfFav) {
        super(hunger, isWolfFav);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return action;
    }

    public GrcItemFoodBase setItemUseAction(EnumAction act) {
        this.action = act;
        return this;
    }

    public IEffect getEffect() {
        return effect;
    }

    public GrcItemFoodBase setEffect(IEffect ef) {
        this.effect = ef;
        return this;
    }

    protected void applyIEffects(ItemStack itemStack, World world, EntityPlayer player) {
        if (effect != null) {
            effect.apply(world, player, world.rand, itemStack);
        }
    }

    @Override
    protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
        super.onFoodEaten(itemStack, world, player);
        if (!world.isRemote) {
            applyIEffects(itemStack, world, player);
        }
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            if (!world.isRemote) {
                final ItemStack result = ItemUtils.consumeStack(stack.splitStack(1));
                ItemUtils.addStackToPlayer(result, player, world, false);
            }
        }

        player.getFoodStats().func_151686_a(this, stack);
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);

        return stack.stackSize <= 0 ? null : stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean showAdvanced) {
        super.addInformation(stack, player, list, showAdvanced);
        GrcItemBase.addDescription(this, stack, player, list, showAdvanced);
        if (effect != null) {
            final List<String> tempList = new ArrayList<String>();
            effect.getDescription(tempList);
            if (tempList.size() > 0) {
                if (GrcCoreState.showDetailedInformation()) {
                    list.addAll((List) tempList);
                } else {
                    list.add(TextFormatting.GRAY +
                            GrcI18n.translate("grc.tooltip.detailed_information",
                                    TextFormatting.WHITE + GrcCoreState.detailedKey + TextFormatting.GRAY));
                }
            }
        }
    }
}
