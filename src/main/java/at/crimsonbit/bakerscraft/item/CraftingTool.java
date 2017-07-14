package at.crimsonbit.bakerscraft.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class CraftingTool extends GenericItem {
	private CraftingToolType type;
	public CraftingTool(String unlocalizedName, int maxUses, CraftingToolType type) {
		super(unlocalizedName);
		setHasSubtypes(false);
		setMaxStackSize(1);
		setMaxDamage(maxUses);
		this.type = type;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return new ItemStack(itemStack.getItem(), 1, itemStack.getItemDamage() + 1);
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return enchantment.type == EnumEnchantmentType.BREAKABLE;
	}
	
	public CraftingToolType getCraftingToolType() {
		return type;
	}
	
	public static enum CraftingToolType{
		MORTAR;
	}
	
	

}
