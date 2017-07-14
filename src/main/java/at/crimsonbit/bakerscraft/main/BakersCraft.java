package at.crimsonbit.bakerscraft.main;

import static at.crimsonbit.bakerscraft.item.AllItems.*;

import at.crimsonbit.bakerscraft.item.CraftingTool;
import at.crimsonbit.bakerscraft.item.CraftingTool.CraftingToolType;
import at.crimsonbit.bakerscraft.item.GenericItem;
import at.crimsonbit.bakerscraft.item.QuickFood;
import at.crimsonbit.bakerscraft.proxy.ServerProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

@Mod(modid = BakersCraft.modid, name = BakersCraft.name, version = BakersCraft.version)
public class BakersCraft {
	public static final String modid = "bakerscraft";
	public static final String name = "Bakers Craft";
	public static final String version = "1.0";

	@Instance
	public static BakersCraft instance;

	@SidedProxy(clientSide = "at.crimsonbit.bakerscraft.proxy.ClientProxy", serverSide = "at.crimsonbit.bakerscraft.proxy.ServerProxy")
	public static ServerProxy proxy;

	private boolean allowAllStoneForMortar;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigCategory crafting = new ConfigCategory("Crafting");
		crafting.setComment("Configurations for crafting recipes");

		allowAllStoneForMortar = config.getBoolean(name, crafting.getName(), false,
				"Set this to true to allow all kinds of stone to be used for the stone mortar, requires the UnifiedStoneTools mod");
		config.save();

		// Item creation
		dough = new GenericItem("dough");
		registerItem("dough", dough);

		flour = new GenericItem("flour");
		registerItem("flour", flour);

		cookieDough = new GenericItem("cookie_dough");
		registerItem("cookie_dough", cookieDough);

		stone_mortar = new CraftingTool("stone_mortar", 32, CraftingToolType.MORTAR);
		registerItem("stone_mortar", stone_mortar);

		iron_mortar = new CraftingTool("iron_mortar", 128, CraftingToolType.MORTAR);
		registerItem("iron_mortar", iron_mortar);

		obsidian_mortar = new CraftingTool("obsidian_mortar", 1024, CraftingToolType.MORTAR);
		registerItem("obsidian_mortar", obsidian_mortar);

		chocolate_cookie = new QuickFood(3, 0.3f, false, 20, "chocolate_cookies");
		registerItem("chocolate_cookies", chocolate_cookie);
	}

	private void registerItem(String name, Item item) {
		proxy.registerTexture(item, 0, new ModelResourceLocation(modid + ":" + name, "inventory"));
		item.setRegistryName(name);
		GameRegistry.register(item);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {

		proxy.loadTextures();

		// Mortar Recipes
		GameRegistry.addRecipe(new MortarRecipe(new ItemStack(flour, 1), Items.WHEAT));

		// Mortar Crafting
		if (allowAllStoneForMortar) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stone_mortar), "BSB", " B ", 'B', "listAllStone",
					'S', "stickWood"));
		} else {
			GameRegistry.addShapedRecipe(new ItemStack(stone_mortar), "BSB", " B ", 'B', Blocks.STONE, 'S',
					Items.STICK);
		}

		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(iron_mortar), "ISI", " I ", 'I', "ingotIron", 'S', "stickWood"));

		GameRegistry.addRecipe(
				new ShapedOreRecipe(new ItemStack(obsidian_mortar), "OSO", " O ", 'O', "obsidian", 'S', "gemDiamond"));

		// Other Crafting
		GameRegistry.addShapelessRecipe(new ItemStack(dough), flour, Items.WATER_BUCKET);
		GameRegistry.addShapelessRecipe(new ItemStack(cookieDough, 3), dough,
				new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()),
				new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()));

		// Smelting
		GameRegistry.addSmelting(dough, new ItemStack(Items.BREAD), 0.2F);
		GameRegistry.addSmelting(cookieDough, new ItemStack(chocolate_cookie, 2), 0.1f);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {

	}

}
