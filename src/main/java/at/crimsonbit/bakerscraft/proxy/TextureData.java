package at.crimsonbit.bakerscraft.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class TextureData {

	private final Item item;
	private final int meta;
	private final ModelResourceLocation location;
	
	public TextureData(Item item, int meta, ModelResourceLocation location) {
		super();
		this.item = item;
		this.meta = meta;
		this.location = location;
	}

	public Item getItem() {
		return item;
	}

	public int getMeta() {
		return meta;
	}

	public ModelResourceLocation getLocation() {
		return location;
	}
}
