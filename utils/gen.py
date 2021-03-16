
import shutil
import sys

colors = {
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
}

shades = {
    "",
    "shaded_"
}

name = sys.argv[1]

for shade in shades:
    for color in colors:
        shutil.copy2("./input/" + name + ".json", "./output/" + shade + name + "_" + color + ".json")
