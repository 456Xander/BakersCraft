package at.crimsonbit.bakerscraft.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class CraftingTool extends GenericItem {
	
	public CraftingTool(String unlocalizedName, int maxUses) {
		super(unlocalizedName);
		setHasSubtypes(false);
		setMaxStackSize(1);
		setMaxDamage(maxUses);
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(itemStack.getItem(), itemStack.getItemDamage() + 1);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return this.getDamage(stack) < this.getMaxDamage(stack);
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.type == EnumEnchantmentType.BREAKABLE;
	}
	
	

}
