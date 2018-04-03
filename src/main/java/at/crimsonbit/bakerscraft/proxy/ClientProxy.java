package at.crimsonbit.bakerscraft.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends ServerProxy {

	@Override
	public void registerTexture(Item item, int meta, ModelResourceLocation location) {
		Logger.getLogger("Bakerscraft").log(Level.INFO, "Registering " + item.getRegistryName().toString() + ":" + meta + ": Texture=" + location.toString());
		ModelLoader.setCustomModelResourceLocation(item, meta, location);
	}

}
