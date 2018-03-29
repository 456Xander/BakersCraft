package at.crimsonbit.bakerscraft.block;

import net.minecraft.block.BlockCake;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCustomCake extends BlockCake {

	private int hunger;
	private float saturation;

	public BlockCustomCake(int hunger, float saturation) {
		super();
		this.setTickRandomly(false);
		this.hunger = hunger;
		this.saturation = saturation;
		this.setSoundType(SoundType.CLOTH);
		disableStats();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			return this.eatCake(worldIn, pos, state, playerIn);
		} else {
			ItemStack itemstack = playerIn.getHeldItem(hand);
			return this.eatCake(worldIn, pos, state, playerIn) || itemstack.isEmpty();
		}
	}

	protected boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!player.canEat(false)) {
			return false;
		} else {
			player.addStat(StatList.CAKE_SLICES_EATEN);
			player.getFoodStats().addStats(hunger, saturation);
			int i = state.getValue(BITES).intValue();

			if (i < 6) {
				worldIn.setBlockState(pos, state.withProperty(BITES, Integer.valueOf(i + 1)), 3);
			} else {
				worldIn.setBlockToAir(pos);
			}
			return true;
		}
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}
}
