package net.darktree.led.util;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;

public class ClientDelegate {

    public final Identifier id;
    public final Block block;
    public final Item item;
    public final DyeColor color;
    public final LedFixture fixture;
    public final LedVariant variant;
    public final LedVariant.RecipeFactory factory;

    public ClientDelegate(DyeColor color, LedFixture fixture, LedVariant variant, Block block, Item item, Identifier id, LedVariant.RecipeFactory factory) {
        this.color = color;
        this.fixture = fixture;
        this.variant = variant;
        this.block = block;
        this.item = item;
        this.id = id;
        this.factory = factory;
    }

    public record RecipeInfo (Recipe<?> recipe, ResourceKey<Recipe<?>> key) {}

    public int getTint() {
        return color.getFireworkColor();
    }

    public Identifier getItemModelPath() {
        return RegistryHelper.id("item/" + id.getPath());
    }

    public ResourceKey<Block> getBlockKey() {
        return ResourceKey.create(Registries.BLOCK, id);
    }

    public void addRecipes(List<RecipeInfo> recipes) {
        factory.apply((recipe, id) -> recipes.add(new RecipeInfo(recipe, ResourceKey.create(Registries.RECIPE, id))), item, color);
    }

}
