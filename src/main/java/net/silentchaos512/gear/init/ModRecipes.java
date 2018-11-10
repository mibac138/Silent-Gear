package net.silentchaos512.gear.init;

import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.recipe.RecipeBlueprintCrafting;
import net.silentchaos512.gear.recipe.RecipeModularItem;
import net.silentchaos512.gear.recipe.RecipeQuickRepair;
import net.silentchaos512.gear.recipe.RecipeUpgradeModularItem;
import net.silentchaos512.lib.registry.RecipeMaker;
import net.silentchaos512.lib.registry.SRegistry;

import java.util.HashMap;
import java.util.Map;

public class ModRecipes {
    public static final Map<String, RecipeModularItem> gearCrafting = new HashMap<>();

    public static void registerAll(SRegistry reg) {
        RecipeMaker recipes = reg.getRecipeMaker();
        for (ICoreItem item : ModItems.toolClasses.values()) {
            final RecipeModularItem recipe = new RecipeModularItem(item);
            gearCrafting.put(item.getGearClass(), recipe);
            recipes.addCustomRecipe("core_" + item.getGearClass(), recipe);
        }
        recipes.addCustomRecipe("head_blueprint", new RecipeBlueprintCrafting(ModItems.toolHead));
        recipes.addCustomRecipe("upgrade_core_item", new RecipeUpgradeModularItem());
        recipes.addCustomRecipe("quick_gear_repair", new RecipeQuickRepair());
    }
}
