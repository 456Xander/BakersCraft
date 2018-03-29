package at.crimsonbit.bakerscraft.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.crimsonbit.bakerscraft.item.CraftingTool;
import at.crimsonbit.bakerscraft.item.CraftingTool.CraftingToolType;
import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class MortarRecipe implements IRecipe {
	private final ItemStack result;
	private final List<ItemStack> ingredients;

	public MortarRecipe(ItemStack result, Object... crafting) {
		this.result = result;
		ingredients = new ArrayList<ItemStack>();
		for (Object o : crafting) {
			if (o instanceof Item) {
				ingredients.add(new ItemStack((Item) o));
			} else if (o instanceof ItemStack) {
				ingredients.add((ItemStack) o);
			} else if (o instanceof Block) {
				ingredients.add(new ItemStack((Block) o));
			} else {
				throw new IllegalArgumentException(
						"Invalid shapeless recipe: unknown type " + o.getClass().getName() + "!");
			}
		}
	}

	public MortarRecipe(ItemStack result, List<ItemStack> crafting) {
		this.result = result;
		ingredients = crafting;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean hasMortar = false;
		List<ItemStack> list = new ArrayList<ItemStack>(ingredients);
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack != null && stack.stackSize != 0) {

				if (stack.getItem() instanceof CraftingTool && !hasMortar
						&& ((CraftingTool) (stack.getItem())).getCraftingToolType() == CraftingToolType.MORTAR) {
					hasMortar = true;
				} else {
					boolean flag = false;
					Iterator<ItemStack> iter = list.iterator();
					while (iter.hasNext()) {
						ItemStack s = iter.next();
						if (s.getItem() == stack.getItem() && (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE
								|| (s.getItemDamage() == stack.getItemDamage()))) {
							flag = true;
							iter.remove();
							break;
						}
					}
					if (!flag) {
						// Item did not match any in ingredients
						return false;
					}
				}

			}
		}
		return hasMortar && list.isEmpty();
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return this.result.copy();
	}

	@Override
	public int getRecipeSize() {
		return ingredients.size() + 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.result;
	}

	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		ItemStack[] invArr = new ItemStack[inv.getSizeInventory()];
		for (int i = 0; i < invArr.length; ++i) {
			ItemStack itemstack = inv.getStackInSlot(i);
			invArr[i] = net.minecraftforge.common.ForgeHooks.getContainerItem(itemstack);
		}

		return invArr;
	}

}
