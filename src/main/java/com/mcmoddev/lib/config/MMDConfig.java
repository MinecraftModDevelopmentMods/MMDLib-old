package com.mcmoddev.lib.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.mcmoddev.lib.annotations.ConfigProperty;
import com.mcmoddev.lib.annotations.ConfigProperty.IntBool;
import com.mcmoddev.lib.annotations.ConfigProperty.RangeInteger;
import com.mcmoddev.lib.annotations.ConfigProperty.StringLimit;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

public abstract class MMDConfig {

    public final File file;
    public boolean setup;
    private final Configuration config;

    public MMDConfig(File file) {
        this.file = file;
        this.config = new Configuration(file);
        this.config.load();
    }

    public abstract ResourceLocation getIdentifier ();

    public void setup () {
        this.setup = true;
        this.save(true);
    }

    public void read () {
        this.readFields(false);
    }

    public void save (boolean readFields) {
        if (readFields)
            this.readFields(false);
        if (this.config.hasChanged())
            this.config.save();
    }

    public void readFields (boolean write) {
        final Field[] fields = this.getClass().getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isTransient(field.getModifiers()) && field.isAnnotationPresent(ConfigProperty.class))
                this.readProperty(field, write);
        }
    }

    public void readProperty (Field field, boolean write) {
        final ConfigProperty propInfo = field.getAnnotation(ConfigProperty.class);
        final Class clz = field.getType();
        String comment = propInfo.comment().equals("undefined") ? I18n.translateToLocal(this.getIdentifier().getResourceDomain().toLowerCase() + ".config.prop." + field.getName() + ".comment") : I18n.translateToLocal(propInfo.comment());
        final String fieldName = propInfo.nameOverride().equals("") ? field.getName() : propInfo.nameOverride();
        try {
            if (clz.equals(int.class)) {
                int min = Integer.MIN_VALUE;
                int max = Integer.MAX_VALUE;
                if (field.isAnnotationPresent(RangeInteger.class)) {
                    final RangeInteger minMax = field.getAnnotation(RangeInteger.class);
                    min = minMax.min();
                    max = minMax.max();
                }
                if (field.isAnnotationPresent(IntBool.class)) {
                    min = 0;
                    max = 1;
                    comment += "\n0=false | 1 = true";
                }
                if (write) {
                    this.config.get(propInfo.category(), fieldName, field.getInt(this)).set(field.getInt(this));
                    this.config.get(propInfo.category(), fieldName, field.getInt(this)).setMinValue(min);
                    this.config.get(propInfo.category(), fieldName, field.getInt(this)).setMaxValue(max);
                    this.config.get(propInfo.category(), fieldName, field.getInt(this)).setComment(comment);
                }
                else
                    field.set(this, this.config.getInt(fieldName, propInfo.category(), field.getInt(this), min, max, comment));
            }
            else if (clz.equals(int[].class)) {
                int min = Integer.MIN_VALUE;
                int max = Integer.MAX_VALUE;
                if (field.isAnnotationPresent(RangeInteger.class)) {
                    final RangeInteger minMax = field.getAnnotation(RangeInteger.class);
                    min = minMax.min();
                    max = minMax.max();
                }
                if (write) {
                    this.config.get(propInfo.category(), fieldName, (int[]) field.get(this)).set((int[]) field.get(this));
                    this.config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setMinValue(min);
                    this.config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setMaxValue(max);
                    this.config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setComment(comment);
                }
                else
                    field.set(this, this.config.get(propInfo.category(), fieldName, (int[]) field.get(this), comment, min, max).getIntList());
            }
            else if (clz.equals(String.class)) {
                if (write) {
                    this.config.get(propInfo.category(), fieldName, (String) field.get(this)).set((String) field.get(this));
                    this.config.get(propInfo.category(), fieldName, (String) field.get(this)).setComment(comment);
                    if (field.isAnnotationPresent(StringLimit.class))
                        this.config.get(propInfo.category(), fieldName, (String) field.get(this)).setValidValues(field.getAnnotation(StringLimit.class).value());
                }
                else if (field.isAnnotationPresent(StringLimit.class))
                    field.set(this, this.config.getString(fieldName, propInfo.category(), (String) field.get(this), comment, field.getAnnotation(StringLimit.class).value()));
                else
                    field.set(this, this.config.getString(fieldName, propInfo.category(), (String) field.get(this), comment));
            }
            else if (clz.equals(String[].class)) {
                if (write) {
                    this.config.get(propInfo.category(), fieldName, (String[]) field.get(this)).set((String[]) field.get(this));
                    this.config.get(propInfo.category(), fieldName, (String[]) field.get(this)).setComment(comment);
                    if (field.isAnnotationPresent(StringLimit.class))
                        this.config.get(propInfo.category(), fieldName, (String[]) field.get(this)).setValidValues(field.getAnnotation(StringLimit.class).value());
                }
                else if (field.isAnnotationPresent(StringLimit.class))
                    field.set(this, this.config.getStringList(fieldName, propInfo.category(), (String[]) field.get(this), comment, field.getAnnotation(StringLimit.class).value()));
                else
                    field.set(this, this.config.getStringList(fieldName, propInfo.category(), (String[]) field.get(this), comment));
            }
            else if (clz.equals(boolean.class)) {
                if (write) {
                    this.config.get(propInfo.category(), fieldName, field.getBoolean(this)).set(field.getBoolean(this));
                    this.config.get(propInfo.category(), fieldName, field.getBoolean(this)).setComment(comment);
                }
                else
                    field.set(this, this.config.getBoolean(fieldName, propInfo.category(), field.getBoolean(this), comment));
            }
            else if (clz.equals(boolean[].class)) {
                if (write) {
                    this.config.get(propInfo.category(), fieldName, (boolean[]) field.get(this)).set((boolean[]) field.get(this));
                    this.config.get(propInfo.category(), fieldName, (boolean[]) field.get(this)).setComment(comment);
                }
                else
                    field.set(this, this.config.get(propInfo.category(), fieldName, (boolean[]) field.get(this), comment).getBooleanList());
            }
            else
                return;
        }
        catch (final Exception ignored) {
        }
    }

    public class CategoryInfo implements Comparable<CategoryInfo> {

        public final String category;
        public String name;

        public CategoryInfo(String cat) {
            this.category = this.name = cat;
        }

        @Override
        public int compareTo (CategoryInfo cfg) {
            return this.category.compareTo(cfg.category);
        }
    }
}