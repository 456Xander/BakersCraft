package at.crimsonbit.bakerscraft.proxy;

import gnu.trove.set.hash.THashSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ClientProxy extends ServerProxy {

	THashSet<TextureData> textureMap = new THashSet<TextureData>();

	@Override
	public void registerTexture(Item item, int meta, ModelResourceLocation location) {
		textureMap.add(new TextureData(item, meta, location));
	}

	@Override
	public void loadTextures() {
		for (TextureData tex : textureMap){
			Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(tex.getItem(), tex.getMeta(), tex.getLocation());
		}
		textureMap = null;
	}
}
