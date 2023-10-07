
# python3 utils/shimmer.py > src/main/resources/assets/shimmer/shimmer.json
import json

color_definitions = {}
light_definitions = []
bloom_definitions = []

lit_state_definitions = {
	"lit": True
}

powered_state_definitions = {
	"powered": True
}

def add_color(name, rgb):
	# the hex numbers in shimmer are incorrectly parsed, making values larger than 7FFFFFFF throw an exception
	# we can circumvent this by using the other color format allowed by shimmer

	r = (rgb & 0xFF0000) >> 16
	g = (rgb & 0xFF00) >> 8
	b = (rgb & 0xFF) >> 0

	color_definitions[name] = {
		"r": r,
		"g": g,
		"b": b,
		"a": 255
	}

# color set
add_color("white", 0xF0F0F0)
add_color("orange", 15435844)
add_color("magenta", 12801229)
add_color("light_blue", 6719955)
add_color("yellow", 14602026)
add_color("lime", 4312372)
add_color("pink", 14188952)
add_color("gray", 0x434343)
add_color("light_gray", 0xABABAB)
add_color("cyan", 2651799)
add_color("purple", 8073150)
add_color("blue", 2437522)
add_color("brown", 5320730)
add_color("green", 3887386)
add_color("red", 0xB51D18)
add_color("black", 0)

colors = [
	"white",
	"orange",
	"magenta",
	"light_blue",
	"yellow",
	"lime",
	"pink",
	"gray",
	"light_gray",
	"cyan",
	"purple",
	"blue",
	"brown",
	"green",
	"red",
	"black"
]

variants = [
	("", 15),
	("reinforced_", 14),
	("shaded_", 3),
	("shaded_reinforced_", 3)
]

types = [
	("clear_full", 1),
	("small_fixture", 0.7),
	("medium_fixture", 0.8),
	("large_fixture", 0.9),
	("flat_fixture", 0.9)
]

buttons = [
	("button", 3),
	("switch", 3),
]

for variant, light in variants:
	for type, mult in types:
		for color in colors:
			id = "led:" + variant + type + "_" + color

			light_definitions.append({
				"block": id,
				"state": lit_state_definitions,
				"color": "#" + color,
				"radius": light
			})

			bloom_definitions.append({
				"block": id,
				"state": lit_state_definitions
			})

for button, light in buttons:
	for color in colors:
		id = "led:" + button + "_" + color

		light_definitions.append({
			"block": id,
			"state": powered_state_definitions,
			"color": "#" + color,
			"radius": light
		})

		bloom_definitions.append({
			"block": id,
			"state": powered_state_definitions
		})

print(json.dumps({
	"ColorReference": color_definitions,
	"LightBlock": light_definitions,
	# "Bloom": bloom_definitions
}, indent=4))