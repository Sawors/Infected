package com.github.sawors.infected.items.itemlist;

import com.github.sawors.infected.items.InfctdItem;
import com.github.sawors.infected.items.ItemTag;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public abstract class HealItem extends InfctdItem {
    
    public HealItem() {
        super();
        
        setMaterial(Material.SHIELD);
        setDisplayName(Component.translatable(ChatColor.GRAY+formatTextToName(getClass().getSimpleName())));
        addTag(ItemTag.DISABLE_ORIGINAL_FUNCTION);
    }
}
