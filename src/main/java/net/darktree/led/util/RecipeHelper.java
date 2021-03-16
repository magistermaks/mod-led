package net.darktree.led.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeHelper {

    private static final ArrayList<JsonObject> recipes = new ArrayList<>();

    public static void createShaped( ItemStack stack, String pattern, JsonObject keys ) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shaped");
        json.add("result", getRecipeResult(stack));
        json.add("pattern", getPattern(pattern));
        json.add("key", keys);

        recipes.add(json);
    }

    public static void createShapeless( ItemStack stack, String... items ) {
        JsonObject json = new JsonObject();
        json.addProperty("type", "minecraft:crafting_shapeless");
        json.add("result", getRecipeResult(stack));

        JsonArray ingredients = new JsonArray();
        for( String item : items ) {
            JsonObject ingredient = new JsonObject();
            ingredient.addProperty("item", item);
            ingredients.add(ingredient);
        }

        json.add("ingredients", ingredients);
        recipes.add(json);
    }

    private static JsonObject getRecipeResult( ItemStack stack ) {
        JsonObject json = new JsonObject();
        json.addProperty("item", getIdentifier( stack.getItem() ));
        json.addProperty("count", stack.getCount());
        return json;
    }

    private static JsonArray getPattern( String pattern ) {
        JsonArray jsonArray = new JsonArray();
        String[] lines = pattern.split(",");

        for( String line : lines ) {
            jsonArray.add(line);
        }

        return jsonArray;
    }

    private static String getIdentifier( Item item ) {
        return Registry.ITEM.getId( item ).toString();
    }

    public static void register( Map<Identifier, JsonElement> map ) {
        for( JsonObject recipe : recipes ) {
            Identifier id = new Identifier( recipe.getAsJsonObject("result").getAsJsonPrimitive("item").getAsString() );
            map.put( id, recipe );
        }
    }

    public static KeyBuilder getKeyBuilder() {
        return new KeyBuilder(new HashMap<>());
    }

}
