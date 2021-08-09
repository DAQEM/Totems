package me.daqem.totems.util.modifier;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ModHealthModifier {

    public static final UUID MAX_HEALTH_MODIFIER = UUID.fromString("4ca2225e-f936-11eb-9a03-0242ac130003");

    public static void applyMaxHealthModifier(PlayerEntity player, float amount) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            attribute.applyPersistentModifier(new AttributeModifier(MAX_HEALTH_MODIFIER, "MaxHealth", amount * 2, AttributeModifier.Operation.ADDITION));
        }
    }
}