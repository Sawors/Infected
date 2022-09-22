package com.github.sawors.infected.items;

import java.util.Locale;

public enum ItemTag {
    
    
    USABLE,
    DISABLE_ORIGINAL_FUNCTION;
    
    
    
    public String tagString(){
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
