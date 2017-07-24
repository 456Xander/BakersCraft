package at.crimsonbit.bakerscraft.main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BakersCreaTab extends CreativeTabs {
	private Item logo;

	public BakersCreaTab(int index, String label, Item logoItem) {
		super(index, label);
		this.logo = logoItem;
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(logo);
	}
}
