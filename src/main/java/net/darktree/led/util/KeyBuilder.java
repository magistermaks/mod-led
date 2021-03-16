package net.darktree.led.util;

import com.google.gson.JsonObject;
import net.minecraft.util.DyeColor;

import java.util.HashMap;
import java.util.Map;

public class KeyBuilder {

    private final HashMap<String, JsonObject> keys = new HashMap<>();

    public KeyBuilder() {

    }

    public static KeyBuilder make() {
        return new KeyBuilder();
    }

    public KeyBuilder addStainedGlass(char key, DyeColor color) {
        addItem(key, "minecraft:" + color.getName() + "_stained_glass");
        return this;
    }

    public KeyBuilder addItem(char key, String item) {
        JsonObject entry = new JsonObject();
        entry.addProperty("item", item);
        keys.put(key + "", entry);
        return this;
    }

    public JsonObject get() {
        JsonObject json = new JsonObject();

        for (Map.Entry<String, JsonObject> entry : keys.entrySet()) {
            json.add(entry.getKey(), entry.getValue());
        }

        return json;
    }

}
