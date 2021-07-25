package net.silentchaos512.gear.data.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.loot.GiftLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.functions.SetLoreFunction;
import net.minecraft.world.level.storage.loot.functions.SetNameFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.gear.part.LazyPartData;
import net.silentchaos512.gear.init.ModItems;
import net.silentchaos512.gear.init.Registration;
import net.silentchaos512.gear.loot.function.SelectGearTierLootFunction;
import net.silentchaos512.gear.loot.function.SetPartsFunction;
import net.silentchaos512.lib.util.NameUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.BiConsumer;

public class ModGiftLootTables extends GiftLoot {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> p_accept_1_) {
        p_accept_1_.accept(ModItems.BLUEPRINT_PACKAGE.get().getDefaultLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.ROD_BLUEPRINT)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.PICKAXE_BLUEPRINT)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.SHOVEL_BLUEPRINT)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.AXE_BLUEPRINT)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.KNIFE_BLUEPRINT)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.SWORD_BLUEPRINT)
                                .setWeight(11))
                        .add(LootItem.lootTableItem(ModItems.KATANA_BLUEPRINT)
                                .setWeight(5))
                        .add(LootItem.lootTableItem(ModItems.MACHETE_BLUEPRINT)
                                .setWeight(7))
                        .add(LootItem.lootTableItem(ModItems.SPEAR_BLUEPRINT)
                                .setWeight(8)))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.SHIELD_BLUEPRINT))));

        for (Item item : Registration.getItems(item -> item instanceof ICoreItem)) {
            p_accept_1_.accept(SilentGear.getId("random_gear/" + NameUtils.from(item).getPath()), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(item)
                                    .setWeight(3)
                                    .apply(SelectGearTierLootFunction.builder(1)))
                            .add(LootItem.lootTableItem(item)
                                    .setWeight(5)
                                    .apply(SelectGearTierLootFunction.builder(2)))
                            .add(LootItem.lootTableItem(item)
                                    .setWeight(2)
                                    .apply(SelectGearTierLootFunction.builder(3)))));
        }

        // FIXME
        p_accept_1_.accept(SilentGear.getId("test/ldf_mallet"), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModItems.HAMMER)
                                .apply(SetPartsFunction.builder(ImmutableList.of(
                                        new LazyPartData(SilentGear.getId("main/diamond")),
                                        new LazyPartData(SilentGear.getId("main/diamond")),
                                        new LazyPartData(SilentGear.getId("main/emerald")),
                                        new LazyPartData(SilentGear.getId("rod/blaze")),
                                        new LazyPartData(SilentGear.getId("tip/redstone")))))
                                .apply(() -> setName(new TextComponent("Loliberty Defense Force Mallet")))
                                .apply(() -> setLore(ImmutableList.of(
                                        new TextComponent("Standard Issue"),
                                        new TextComponent("Protectors of Free Speech")))))));
    }

    @Nonnull
    private static SetNameFunction setName(Component text) {
        Constructor<SetNameFunction> constructor = ObfuscationReflectionHelper.findConstructor(SetNameFunction.class, LootItemCondition[].class, Component.class, LootContext.EntityTarget.class);
        constructor.setAccessible(true);
        try {
            return constructor.newInstance(new LootItemCondition[0], text, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    private static SetLoreFunction setLore(List<Component> lore) {
        return new SetLoreFunction(new LootItemCondition[0], false, lore, null);
    }
}
