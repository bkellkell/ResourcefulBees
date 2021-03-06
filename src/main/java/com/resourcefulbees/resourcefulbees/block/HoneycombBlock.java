
package com.resourcefulbees.resourcefulbees.block;

import com.resourcefulbees.resourcefulbees.api.beedata.ColorData;
import com.resourcefulbees.resourcefulbees.registry.BeeRegistry;
import com.resourcefulbees.resourcefulbees.utils.color.RainbowColor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class HoneycombBlock extends Block {

    protected final ColorData colorData;
    protected final String beeType;

    public HoneycombBlock(String beeType, ColorData colorData, AbstractBlock.Properties properties) {
        super(properties);
        this.colorData = colorData;
        this.beeType = beeType;
    }

    public int getHoneycombColor() { return colorData.getHoneycombColorInt(); }

    public String getBeeType() { return beeType; }

    public static int getBlockColor(BlockState state, @Nullable IBlockReader world, @Nullable BlockPos pos, int tintIndex){
        HoneycombBlock honeycombBlock = ((HoneycombBlock) state.getBlock());
        return honeycombBlock.colorData.isRainbowBee() ? RainbowColor.getRGB() : honeycombBlock.getHoneycombColor();
    }

    public static int getItemColor(ItemStack stack, int tintIndex){
        BlockItem blockItem = (BlockItem) stack.getItem();
        HoneycombBlock honeycombBlock = (HoneycombBlock) blockItem.getBlock();
        return honeycombBlock.colorData.isRainbowBee() ? RainbowColor.getRGB() : honeycombBlock.getHoneycombColor();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder) {
        List<ItemStack> drops = super.getDrops(state, builder);
        drops.add(BeeRegistry.getRegistry().getBeeData(beeType).getCombBlockItemRegistryObject().get().getDefaultInstance());
        return drops;
    }

    @Override
    public void animateTick(@Nonnull BlockState stateIn, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (colorData.isRainbowBee())
            world.notifyBlockUpdate(pos, stateIn, stateIn, 2);
        super.animateTick(stateIn, world, pos, rand);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
        return BeeRegistry.getRegistry().getBeeData(beeType).getCombBlockItemRegistryObject().get().getDefaultInstance();
    }
}

