package net.silentchaos512.gear.item.gear;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ToolType;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.util.IAoeTool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CoreHammer extends CorePickaxe implements IAoeTool {
    public CoreHammer() {
        super(ImmutableSet.of(Material.GLASS));
    }

    @Override
    public GearType getGearType() {
        return GearType.HAMMER;
    }

    @Nonnull
    @Override
    public ToolType getAoeToolType() {
        return ToolType.PICKAXE;
    }

    @Nullable
    @Override
    public HitResult rayTraceBlocks(Level world, Player player) {
        return getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return IAoeTool.BreakHandler.onBlockStartBreak(itemstack, pos, player);
    }
}
