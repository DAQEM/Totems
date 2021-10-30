package me.daqem.totems.util.modifier;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ModModifiers {

    public static final UUID MAX_HEALTH_MODIFIER = UUID.fromString("4ca2225e-f936-11eb-9a03-0242ac130003");
    public static final UUID ARMOR_MODIFIER = UUID.fromString("03fcf32c-fa1b-11eb-9a03-0242ac130003");
    public static final UUID MOVEMENT_SPEED_MODIFIER = UUID.fromString("bd9cbcbc-fc1f-11eb-9a03-0242ac130003");
    public static final UUID FLYING_SPEED_MODIFIER = UUID.fromString("c4b917fc-fc1f-11eb-9a03-0242ac130003");

    public static void applyMaxHealthModifier(PlayerEntity player, float amount) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (attribute != null) {
            attribute.removeModifier(MAX_HEALTH_MODIFIER);
            attribute.applyPersistentModifier(new AttributeModifier(MAX_HEALTH_MODIFIER, "totem_of_health", amount * 2, AttributeModifier.Operation.ADDITION));
            if (player.getHealth() > player.getMaxHealth()) {
                player.setHealth(player.getMaxHealth());
            }
        }
    }

    public static void applyArmourModifier(PlayerEntity player, float amount) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.ARMOR);
        if (attribute != null) {
            attribute.removeModifier(ARMOR_MODIFIER);
            attribute.applyPersistentModifier(new AttributeModifier(ARMOR_MODIFIER, "totem_of_flight", amount * 2, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void applyMovementSpeedModifier(PlayerEntity player, float amount) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute != null) {
            attribute.removeModifier(MOVEMENT_SPEED_MODIFIER);
            attribute.applyPersistentModifier(new AttributeModifier(MOVEMENT_SPEED_MODIFIER, "totem_of_speed_walking", amount / 100F, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void applyFlyingSpeedModifier(PlayerEntity player, float amount) {
        ModifiableAttributeInstance attribute = player.getAttribute(Attributes.FLYING_SPEED);
        if (attribute != null) {
            attribute.removeModifier(FLYING_SPEED_MODIFIER);
            attribute.applyPersistentModifier(new AttributeModifier(FLYING_SPEED_MODIFIER, "totem_of_speed_flying", amount / 25F, AttributeModifier.Operation.ADDITION));
        }
    }
}