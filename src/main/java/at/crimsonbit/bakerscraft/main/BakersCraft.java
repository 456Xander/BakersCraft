package at.crimsonbit.bakerscraft.main;

import static at.crimsonbit.bakerscraft.item.AllItems.*;

import at.crimsonbit.bakerscraft.item.CraftingTool;
import at.crimsonbit.bakerscraft.item.CraftingTool.CraftingToolType;
import at.crimsonbit.bakerscraft.item.GenericItem;
import at.crimsonbit.bakerscraft.item.ItemFlour;
import at.crimsonbit.bakerscraft.item.QuickFood;
import at.crimsonbit.bakerscraft.proxy.ServerProxy;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
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
		ConfigCategory crafting = config.getCategory("Crafting");
		crafting.setComment("Configurations for crafting recipes");

		ConfigCategory craftingTools = config.getCategory("CraftingTools");
		craftingTools.setComment("Configurations for crafting tools, such as durability");

		ConfigCategory food = config.getCategory("Food");
		food.setComment("Configurations for food");

		allowAllStoneForMortar = config.getBoolean("AllowAllStone", crafting.getName(), false,
				"Set this to true to allow all kinds of stone to be used for the stone mortar, requires the UnifiedStoneTools mod");
		int durabilityMortarStone = config.getInt("StoneMortarDurability", craftingTools.getName(), 32, 1,
				Short.MAX_VALUE, "");
		int durabilityMortarIron = config.getInt("IronMortarDurability", craftingTools.getName(), 128, 1,
				Short.MAX_VALUE, "");
		int durabilityMortarObsidian = config.getInt("ObsidianMortarDurability", craftingTools.getName(), 1024, 1,
				Short.MAX_VALUE, "");

		boolean useVanillaCookies = config.getBoolean("useVanillaCookies", food.getName(), false,
				"true to use vanilla cookies instead of this mods cookies, all other cookie config will not change anything if this is true");
		int cookieHunger = config.getInt("cookieHunger", food.getName(), 2, 1, 20,
				"How many hunger a cookie refills, a player has 20");
		float cookieSaturation = config.getFloat("cookieSaturation", food.getName(), 0.25f, 0.0f, 1.0f,
				"How much Saturation cookies refill, with 0.5f, saturation is the same as hunger");
		int cookieEatTime = config.getInt("cookieEatTime", food.getName(), 12, 1, 64,
				"how many ticks it takes to eat cookies, vanilla is 32");

		config.save();

		// Item creation
		dough = new GenericItem("dough").setCreativeTab(CreativeTabs.FOOD);
		registerItem("dough", dough);

		flour = new ItemFlour("flour").setCreativeTab(CreativeTabs.MATERIALS);
		registerItem("flour", flour);

		cookieDough = new GenericItem("cookie_dough").setCreativeTab(CreativeTabs.FOOD);
		registerItem("cookie_dough", cookieDough);

		stone_mortar = new CraftingTool("stone_mortar", durabilityMortarStone, CraftingToolType.MORTAR)
				.setCreativeTab(CreativeTabs.TOOLS);
		registerItem("stone_mortar", stone_mortar);

		iron_mortar = new CraftingTool("iron_mortar", durabilityMortarIron, CraftingToolType.MORTAR)
				.setCreativeTab(CreativeTabs.TOOLS);
		registerItem("iron_mortar", iron_mortar);

		obsidian_mortar = new CraftingTool("obsidian_mortar", durabilityMortarObsidian, CraftingToolType.MORTAR)
				.setCreativeTab(CreativeTabs.TOOLS);
		registerItem("obsidian_mortar", obsidian_mortar);
		if (!useVanillaCookies) {
			chocolate_cookie = new QuickFood(cookieHunger, cookieSaturation, false, cookieEatTime, "chocolate_cookies");
			registerItem("chocolate_cookies", chocolate_cookie);
		} else {
			chocolate_cookie = Items.COOKIE;
		}
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
