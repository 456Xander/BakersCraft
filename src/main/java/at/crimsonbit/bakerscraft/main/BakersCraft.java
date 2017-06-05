package at.crimsonbit.bakerscraft.main;

import at.crimsonbit.bakerscraft.proxy.ServerProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static at.crimsonbit.bakerscraft.item.AllItems.*;

import at.crimsonbit.bakerscraft.item.CraftingTool;
import at.crimsonbit.bakerscraft.item.GenericItem;

@Mod(modid = BakersCraft.modid, name = BakersCraft.name, version = BakersCraft.version)
public class BakersCraft {
	public static final String modid = "bakerscraft";
	public static final String name = "Bakers Craft";
	public static final String version = "1.0";

	@Instance
	public static BakersCraft instance;

	@SidedProxy(clientSide = "at.crimsonbit.bakerscraft.proxy.ClientProxy", serverSide = "at.crimsonbit.bakerscraft.proxy.ServerProxy")
	public static ServerProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		dough = new GenericItem("dough");
		proxy.registerTexture(dough, 0, new ModelResourceLocation(modid + ":dough", "inventory"));

		cookieDough = new GenericItem("cookie_dough");
		proxy.registerTexture(cookieDough, 0, new ModelResourceLocation(modid + ":cookie_dough", "inventory"));

		stone_mortar = new CraftingTool("Mortar", 32);
		proxy.registerTexture(stone_mortar, 0, new ModelResourceLocation(modid + ":stone_mortar", "inventory"));
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		GameRegistry.addShapelessRecipe(new ItemStack(dough), Items.WHEAT, stone_mortar);
		GameRegistry.addShapedRecipe(new ItemStack (stone_mortar), "BSB"," B ", 'B', Blocks.STONE, 'S', Items.STICK);
	}

}
