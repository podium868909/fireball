package com.podium868909.fireball.item;

import com.podium868909.fireball.Fireball;
import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;
import xyz.nucleoid.packettweaker.PacketContext;

public class fireball extends Item implements PolymerItem {

    public fireball(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("Fireball");
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return Items.FIRE_CHARGE;
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context) {
        return PolymerItem.super.getPolymerItemModel(Items.FIRE_CHARGE.getDefaultStack(), context);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getWorld();

        summonFireball(world, user);
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity user = context.getPlayer();
        if (user == null) {
            return ActionResult.PASS;
        }
        Hand hand = context.getHand();
        World world = context.getWorld();

        summonFireball(world, user);
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        summonFireball(world, user);
        return super.use(world, user, hand);
    }

    @Unique
    public void summonFireball(World world, PlayerEntity user) {

        ItemStack stackInHand = user.getStackInHand(user.getActiveHand());

        if (Fireball.getConfig().fireballCooldownTicks > 0) {
            user.getItemCooldownManager().set(stackInHand, 5);
        }

        FireballEntity fireballEntity = new FireballEntity(world, user, new Vec3d(0,0,0), Fireball.getConfig().fireballExplosionPower);
        fireballEntity.setPosition(user.getEyePos());
        fireballEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, Fireball.getConfig().fireballSpeed, Fireball.getConfig().fireballDivergence);
        world.spawnEntity(fireballEntity);

        stackInHand.decrementUnlessCreative(1, user);
    }


}
