package at.crimsonbit.bakerscraft.item;

import net.minecraft.item.Item;

public class GenericItem extends Item {
	
	public GenericItem(String unlocalizedName) {
		setUnlocalizedName(unlocalizedName);
	}
	
	@Override
	public String toString() {
		return this.getRegistryName().toString();
	}
	
}
