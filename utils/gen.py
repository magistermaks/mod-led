
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

path = sys.argv[1]
name = sys.argv[2]

for shade in shades:
    for color in colors:
        shutil.copy2("./input/" + path + "/" + name + ".json", "./output/" + shade + name + "_" + color + ".json")
