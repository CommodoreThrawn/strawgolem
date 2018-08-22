package nivoridocs.strawgolem.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nivoridocs.strawgolem.StrawgolemConfig;

public class EntityAIHarvest extends EntityAIMoveToBlock {
	private EntityStrawGolem strawgolem;

	public EntityAIHarvest(EntityStrawGolem strawgolem, double speedIn) {
		super(strawgolem, speedIn, 16);
		this.strawgolem = strawgolem;
	}

	@Override
	public boolean shouldExecute() {
		if (super.shouldExecute()) {
			this.runDelay = 0;
			return true;
		} else return false;
	}

	@Override
	public void updateTask() {
		super.updateTask();
		this.strawgolem.getLookHelper().setLookPosition(
				this.destinationBlock.getX() + 0.5D,
				this.destinationBlock.getY() + 1.0D,
				this.destinationBlock.getZ() + 0.5D,
				10.0F,
				(float) this.strawgolem.getVerticalFaceSpeed());

		if (this.getIsAboveDestination()) {
			World worldIn = this.strawgolem.world;
			BlockPos pos = this.destinationBlock.up();
			Block block = worldIn.getBlockState(pos).getBlock();
			if (shouldMoveTo(worldIn, this.destinationBlock) && worldIn.destroyBlock(pos, true)
					&& StrawgolemConfig.isReplantEnabled())
				worldIn.setBlockState(pos, block.getDefaultState());
		}
	}

	@Override
	protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
		IBlockState block = worldIn.getBlockState(pos);
		if (block.getBlock() == Blocks.FARMLAND) {
			IBlockState up = worldIn.getBlockState(pos.up());
			return up.getBlock() instanceof BlockCrops
					&& ((BlockCrops) up.getBlock()).isMaxAge(up);
		} else if (block.getBlock() == Blocks.NETHERRACK) {
			IBlockState up = worldIn.getBlockState(pos.up());
			return up.getBlock() == Blocks.NETHER_WART
					&& up.getValue(BlockNetherWart.AGE) == 3;
		} else return false;
	}

}
