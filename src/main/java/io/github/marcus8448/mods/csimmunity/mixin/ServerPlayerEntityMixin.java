package io.github.marcus8448.mods.csimmunity.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Unique private DamageSource source;

    @ModifyConstant(method = "damage", constant = @Constant(expandZeroConditions = Constant.Condition.LESS_THAN_OR_EQUAL_TO_ZERO))
    private int ignoreInvulnerability(int i) {
        if (source.bypassesArmor()) return 1000000;
        return i;
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void ignoreInvulnerability(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.source = source;
    }
}
