package io.github.apace100.origins.mixin;

import io.github.apace100.origins.power.PowerTypes;
import io.github.apace100.origins.registry.ModTags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public abstract class AbstractBlockMixin {

    @Inject(at = @At("HEAD"), method = "calcBlockBreakingDelta", cancellable = true)
    private void modifyBlockBreakSpeed(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        if(state.getBlock().isIn(ModTags.NATURAL_STONE)) {
            int adjacent = 0;
            for(Direction d : Direction.values()) {
                if(world.getBlockState(pos.offset(d)).getBlock().isIn(ModTags.NATURAL_STONE)) {
                    adjacent++;
                }
            }
            // crude patch to make it possible to break blocks more easily as a feline
            // blocks with high adjacency break pretty slowly, and you get a small time penalty for ones without.
            if(adjacent > 3) {
                if (PowerTypes.WEAK_ARMS.isActive(player) && !player.hasStatusEffect(StatusEffects.STRENGTH)) {
                    info.setReturnValue(0.025repo
                            F);
                }
            } else {
                if(PowerTypes.WEAK_ARMS.isActive(player) && !player.hasStatusEffect(StatusEffects.STRENGTH)) {
                    info.setReturnValue(0.035F);
                }
            }
            if(PowerTypes.STRONG_ARMS.isActive(player) && !player.inventory.getMainHandStack().isEffectiveOn(state)) {
                info.setReturnValue(0.09F);
            }
        }
    }
}
