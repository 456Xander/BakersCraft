package at.crimsonbit.bakerscraft.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class QuickFood extends ItemFood{
	private int time;
	/**
	 * 
	 * @param amount
	 * @param saturation
	 * @param isWolfFood
	 * @param time the time it takes to eat the food, vanilla is 32
	 */
	public QuickFood(int amount, float saturation, boolean isWolfFood, int time, String unlocName) {
		super(amount, saturation, isWolfFood);
		this.time = time;
		setUnlocalizedName(unlocName);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return time;
	}

}
