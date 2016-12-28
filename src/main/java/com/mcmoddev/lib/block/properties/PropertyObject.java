package com.mcmoddev.lib.block.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

/**
 * A generic property which can be used to hold any object in theory. This property will not
 * make any attempt to determine the validity of the object being handled. The value that will
 * be stored by this property is the result of {@link Object#toString()}. If more complexity is
 * needed, a new property should be created.
 * 
 * @author Tyler Hancock (Darkhax)
 *
 * @param <T> The type of object being handled by the property.
 */
public class PropertyObject<T> implements IUnlistedProperty<T> {
    
    /**
     * The name of the property.
     */
    private final String name;
    
    /**
     * The type of class held by it.
     */
    private final Class<T> type;
    
    /**
     * Constructor for the generic unlisted property.
     * 
     * @param name The name for the property.
     * @param type The class for the type of object being handled.
     */
    public PropertyObject(String name, Class<T> type) {
        
        this.name = name;
        this.type = type;
    }
    
    @Override
    public String getName () {
        
        return this.name;
    }
    
    @Override
    public Class<T> getType () {
        
        return this.type;
    }
    
    @Override
    public boolean isValid (T object) {
        
        return true;
    }
    
    @Override
    public String valueToString (T object) {
        
        return object.toString();
    }
}