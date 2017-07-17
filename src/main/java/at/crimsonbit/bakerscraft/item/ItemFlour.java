package at.crimsonbit.bakerscraft.item;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFlour extends GenericItem {
	Random flourRandom;

	public ItemFlour(String unlocalizedName) {
		super(unlocalizedName);
		flourRandom = new Random();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		BlockPos position = entityItem.getPosition();
		World world = entityItem.getEntityWorld();
		if (world.getBlockState(position).getBlock() == Blocks.WATER) {
			ItemStack items = entityItem.getEntityItem();
			entityItem.setEntityItemStack(new ItemStack(AllItems.dough, items.getCount()));
			entityItem.addVelocity((flourRandom.nextDouble() - 0.5) / 2, (flourRandom.nextDouble() / 2) + 0.5,
					(flourRandom.nextDouble() - 0.5) / 2);
			return true;
		}
		return false;
	}

}
