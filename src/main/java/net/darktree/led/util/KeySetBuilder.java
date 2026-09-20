package net.darktree.led.util;

import com.google.gson.JsonObject;

public class KeySetBuilder {

    private final JsonObject json = new JsonObject();

    public KeySetBuilder addItem(char key, String item) {
        json.addProperty(key + "", item);
        return this;
    }

    public JsonObject get() {
        return json;
    }

}
