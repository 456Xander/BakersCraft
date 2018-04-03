package at.crimsonbit.bakerscraft.item;

import java.util.ArrayList;
import java.util.List;

import at.crimsonbit.bakerscraft.main.BakersCraft;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegisterHandler {
	public static final RegisterHandler instance = new RegisterHandler();

	private List<Item> itemsToRegister = new ArrayList<>();
	private List<Block> blocksToRegister = new ArrayList<>();

	@SubscribeEvent
	public void onItemRegistry(Register<Item> event) {
		for (Item i : itemsToRegister) {
			event.getRegistry().register(i);
			BakersCraft.proxy.registerTexture(i, 0, new ModelResourceLocation(i.getRegistryName(), "inventory"));
		}
	}

	@SubscribeEvent
	public void onBlockRegistry(Register<Block> event) {
		blocksToRegister.forEach(event.getRegistry()::register);
	}

	public void register(Item item) {
		itemsToRegister.add(item);
	}

	public void register(Block block) {
		blocksToRegister.add(block);
	}

	/**
	 * Free Memory needed by the internal Item and Block Lists
	 */
	public void free() {
		itemsToRegister = null;
		blocksToRegister = null;
	}
}
