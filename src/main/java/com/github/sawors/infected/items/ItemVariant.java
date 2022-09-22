package com.github.sawors.infected.items;

public enum ItemVariant {
    DEFAULT,
    OAK, SPRUCE, DARK_OAK, BIRCH, ACACIA, JUNGLE, CRIMSON, WARPED,
    IRON, GOLD, DIAMOND, NETHERITE, WOOD, STONE, EMERALD, LAPIS, REDSTONE, COAL, COPPER, AMETHYST;
    
    public String getFormatted(){
        return InfctdItem.formatTextToId(this.toString());
    }
}
