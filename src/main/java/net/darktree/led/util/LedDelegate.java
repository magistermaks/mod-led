package net.darktree.led.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;

import java.util.List;

public class LedDelegate extends LedType {

    public final Identifier id;
    public final Block block;
    public final Item item;
    public final LedVariant.RecipeFactory factory;

    public LedDelegate(LedType type, Block block, Item item, Identifier id, LedVariant.RecipeFactory factory) {
        super(type.fixture, type.variant, type.color);
        this.block = block;
        this.item = item;
        this.id = id;
        this.factory = factory;
    }

    public void appendBlockTransformerRule(RuleBasedStateProvider.Builder face, RuleBasedStateProvider.Builder center) {
        if (variant.isShaded() && fixture.isVariable()) {

            // drop item from the face only for the full fixture, otherwise it looks better
            // when dropped form the center
            RuleBasedStateProvider.Builder builder = (fixture == LedFixture.FULL) ? face : center;

            final Block target = variant.withShaded(false).getBlock(fixture, color);
            builder.ifTrueThenProvide(BlockPredicate.matchesBlocks(block), new CopyPropertiesProvider(target));
        }
    }

    public record RecipeInfo (Recipe<?> recipe, ResourceKey<Recipe<?>> key) {}

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
