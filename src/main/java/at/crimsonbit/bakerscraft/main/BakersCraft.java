package at.crimsonbit.bakerscraft.main;

import static at.crimsonbit.bakerscraft.item.AllItems.blockApplePie;
import static at.crimsonbit.bakerscraft.item.AllItems.blockCarrotCake;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_bar;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_cake;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_cake_dough;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_cookie;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_muffin;
import static at.crimsonbit.bakerscraft.item.AllItems.chocolate_muffin_dough;
import static at.crimsonbit.bakerscraft.item.AllItems.cookieDough;
import static at.crimsonbit.bakerscraft.item.AllItems.dough;
import static at.crimsonbit.bakerscraft.item.AllItems.doughTab;
import static at.crimsonbit.bakerscraft.item.AllItems.flour;
import static at.crimsonbit.bakerscraft.item.AllItems.iron_mortar;
import static at.crimsonbit.bakerscraft.item.AllItems.itemApplePie;
import static at.crimsonbit.bakerscraft.item.AllItems.itemCarrotCake;
import static at.crimsonbit.bakerscraft.item.AllItems.obsidian_mortar;
import static at.crimsonbit.bakerscraft.item.AllItems.potato_bread;
import static at.crimsonbit.bakerscraft.item.AllItems.potato_dough;
import static at.crimsonbit.bakerscraft.item.AllItems.stone_mortar;

import at.crimsonbit.bakerscraft.block.BlockCustomCake;
import at.crimsonbit.bakerscraft.item.CraftingTool;
import at.crimsonbit.bakerscraft.item.CraftingTool.CraftingToolType;
import at.crimsonbit.bakerscraft.item.GenericItem;
import at.crimsonbit.bakerscraft.item.ItemFlour;
import at.crimsonbit.bakerscraft.item.QuickFood;
import at.crimsonbit.bakerscraft.item.RegisterHandler;
import at.crimsonbit.bakerscraft.proxy.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = BakersCraft.modid, name = BakersCraft.name, version = BakersCraft.version)
public class BakersCraft {
	private static final String CFG_USE_VAN_COOKIES_DESC = "true to use vanilla cookies instead of this mods cookies, all other cookie config will not change anything if this is true";
	public static final String modid = "bakerscraft";
	public static final String name = "Bakers Craft";
	public static final String version = "1.0.2";

	@Instance
	public static BakersCraft instance;

	@SidedProxy(clientSide = "at.crimsonbit.bakerscraft.proxy.ClientProxy", serverSide = "at.crimsonbit.bakerscraft.proxy.ServerProxy")
	public static ServerProxy proxy;

	public BakersCraft() {
		MinecraftForge.EVENT_BUS.register(RegisterHandler.instance);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		initConfigAndItems(e);
	}

	private void initConfigAndItems(FMLPreInitializationEvent e) {
		Configuration config = new Configuration(e.getSuggestedConfigurationFile());
		ConfigCategory crafting = config.getCategory("Crafting");
		crafting.setComment("Configurations for crafting recipes");

		ConfigCategory craftingTools = config.getCategory("CraftingTools");
		craftingTools.setComment("Configurations for crafting tools, such as durability");

		ConfigCategory food = config.getCategory("Food");
		food.setComment("Configurations for food");

		int durabilityMortarStone = config.getInt("StoneMortarDurability", craftingTools.getName(), 32, 1,
				Short.MAX_VALUE, "");
		int durabilityMortarIron = config.getInt("IronMortarDurability", craftingTools.getName(), 128, 1,
				Short.MAX_VALUE, "");
		int durabilityMortarObsidian = config.getInt("ObsidianMortarDurability", craftingTools.getName(), 1024, 1,
				Short.MAX_VALUE, "");

		boolean useVanillaCookies = config.getBoolean("useVanillaCookies", food.getName(), false,
				CFG_USE_VAN_COOKIES_DESC);
		int cookieHunger = config.getInt("cookieHunger", food.getName(), 2, 1, 20,
				"How many hunger a cookie refills, a player has 20");
		float cookieSaturation = config.getFloat("cookieSaturation", food.getName(), 0.25f, 0.0f, 1.0f,
				"How much Saturation cookies refill, with 0.5f, saturation is the same as hunger");
		int cookieEatTime = config.getInt("cookieEatTime", food.getName(), 12, 1, 64,
				"how many ticks it takes to eat cookies, vanilla is 32");

		int chocolate_bar_hunger = config.getInt("chocolate_bar_hunger", food.getName(), 2, 1, 20,
				"chocolate bars never give saturation, so no config for that");

		int chocolate_muffin_hunger = config.getInt("chocolate_muffin_hunger", food.getName(), 9, 1, 20, "");

		float chocolate_muffin_saturation = config.getFloat("chocolate_muffin_saturation", food.getName(), 0.5f, 0.0f,
				1.0f, "How much Saturation chocolate muffins refill, with 0.5f, saturation is the same as hunger");

		int chocolate_cake_hunger = config.getInt("chocolate_cake_hunger", food.getName(), 10, 1, 20, "");

		float chocolate_cake_saturation = config.getFloat("chocolate_cake_saturation", food.getName(), 0.45f, 0.0f,
				1.0f, "How much Saturation chocolate cake refills, with 0.5f, saturation is the same as hunger");

		int potato_bread_hunger = config.getInt("potato_bread_hunger", food.getName(), 6, 1, 20, "");

		float potato_bread__saturation = config.getFloat("potato_bread__saturation", food.getName(), 0.6f, 0.0f, 1.0f,
				"How much Saturation potato bread refills, with 0.5f, saturation is the same as hunger");

		int carrot_cake_hunger = config.getInt("carrot_cake_hunger", food.getName(), 2, 1, 20, "");

		float carrot_cake_saturation = config.getFloat("carrot_cake_saturation", food.getName(), 0.1f, 0.0f, 1.0f,
				"How much Saturation carrot cake refills, with 0.5f, saturation is the same as hunger");

		int apple_pie_hunger = config.getInt("apple_pie_hunger", food.getName(), 2, 1, 20, "");

		float apple_pie_saturation = config.getFloat("apple_pie_saturation", food.getName(), 0.1f, 0.0f, 1.0f,
				"How much Saturation apple pie refills, with 0.5f, saturation is the same as hunger");

		config.save();

		// Item creation
		dough = new GenericItem("dough");
		registerItem("dough", dough);

		doughTab = new BakersCreaTab(CreativeTabs.getNextID(), "Dough", dough);
		dough.setCreativeTab(doughTab);

		potato_dough = new GenericItem("potato_dough").setCreativeTab(doughTab);
		registerItem("potato_dough", potato_dough);

		chocolate_muffin_dough = new GenericItem("chocolate_muffin_dough").setCreativeTab(doughTab);
		registerItem("chocolate_muffin_dough", chocolate_muffin_dough);

		chocolate_cake_dough = new GenericItem("chocolate_cake_dough").setCreativeTab(doughTab);
		registerItem("chocolate_cake_dough", chocolate_cake_dough);

		flour = new ItemFlour("flour").setCreativeTab(CreativeTabs.MATERIALS);
		registerItem("flour", flour);

		cookieDough = new GenericItem("cookie_dough").setCreativeTab(doughTab);
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

		chocolate_bar = new QuickFood(chocolate_bar_hunger, 0.0f, false, cookieEatTime, "chocolate_bar");
		registerItem("chocolate_bar", chocolate_bar);

		chocolate_muffin = new QuickFood(chocolate_muffin_hunger, chocolate_muffin_saturation, false, 28,
				"chocolate_muffin");
		registerItem("chocolate_muffin", chocolate_muffin);

		chocolate_cake = new QuickFood(chocolate_cake_hunger, chocolate_cake_saturation, false, 32, "chocolate_cake");
		registerItem("chocolate_cake", chocolate_cake);

		potato_bread = new QuickFood(potato_bread_hunger, potato_bread__saturation, false, 32, "potato_bread");
		registerItem("potato_bread", potato_bread);

		blockApplePie = new BlockCustomCake(apple_pie_hunger, apple_pie_saturation).setHardness(0.5f)
				.setUnlocalizedName("apple_pie");

		itemApplePie = new ItemBlockSpecial(blockApplePie).setMaxStackSize(1).setUnlocalizedName("apple_pie")
				.setCreativeTab(CreativeTabs.FOOD);
		registerItem("apple_pie", itemApplePie);
		registerBlock("apple_pie", blockApplePie);

		blockCarrotCake = new BlockCustomCake(carrot_cake_hunger, carrot_cake_saturation).setHardness(0.5f)
				.setUnlocalizedName("carrot_cake");

		itemCarrotCake = new ItemBlockSpecial(blockCarrotCake).setMaxStackSize(1).setUnlocalizedName("carrot_cake")
				.setCreativeTab(CreativeTabs.FOOD);
		registerItem("carrot_cake", itemCarrotCake);
		registerBlock("carrot_cake", blockCarrotCake);

	}

	private void registerItem(String name, Item item) {
		item.setRegistryName(name);
		// proxy.registerTexture(item, 0, new
		// ModelResourceLocation(item.getRegistryName(), "inventory"));
		RegisterHandler.instance.register(item);
	}

	private void registerBlock(String name, Block block) {
		block.setRegistryName(name);
		RegisterHandler.instance.register(block);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {

		OreDictionary.registerOre("listAllStone", Blocks.COBBLESTONE);
		// Smelting
		GameRegistry.addSmelting(dough, new ItemStack(Items.BREAD), 0.2F);
		GameRegistry.addSmelting(cookieDough, new ItemStack(chocolate_cookie, 2), 0.1f);
		GameRegistry.addSmelting(chocolate_muffin_dough, new ItemStack(chocolate_muffin), 0.2F);
		GameRegistry.addSmelting(chocolate_cake_dough, new ItemStack(chocolate_cake), 0.2f);
		GameRegistry.addSmelting(potato_dough, new ItemStack(potato_bread), 0.2f);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		RegisterHandler.instance.free();
	}

}
