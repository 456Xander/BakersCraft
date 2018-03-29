package at.crimsonbit.bakerscraft.main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BakersCreaTab extends CreativeTabs {
	private Item logo;

	public BakersCreaTab(int index, String label, Item logoItem) {
		super(index, label);
		this.logo = logoItem;
	}

	@Override
	public Item getTabIconItem() {
		return logo;
	}
}
