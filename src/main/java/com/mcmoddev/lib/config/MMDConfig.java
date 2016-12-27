package com.mcmoddev.lib.config;

import com.mcmoddev.lib.api.annotations.ConfigProperty;
import com.mcmoddev.lib.api.annotations.ConfigProperty.IntBool;
import com.mcmoddev.lib.api.annotations.ConfigProperty.RangeInteger;
import com.mcmoddev.lib.api.annotations.ConfigProperty.StringLimit;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class MMDConfig {
    public final File file;
    public boolean setup;
    private Configuration config;

    public MMDConfig(File file) {
        this.file = file;
        config = new Configuration(file);
        config.load();
    }

    public abstract ResourceLocation getIdentifier();

    public void setup() {
        setup = true;
        save(true);
    }

    public void read() {
        readFields(false);
    }

    public void save(boolean readFields) {
        if (readFields) {
            readFields(false);
        }
        if (config.hasChanged()) {
            config.save();
        }
    }

    public void readFields(boolean write) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isTransient(field.getModifiers()) && field.isAnnotationPresent(ConfigProperty.class)) {
                readProperty(field, write);
            }
        }
    }

    public void readProperty(Field field, boolean write) {
        ConfigProperty propInfo = field.getAnnotation(ConfigProperty.class);
        Class clz = field.getType();
        String comment = propInfo.comment().equals("undefined") ? I18n.translateToLocal(getIdentifier().getResourceDomain().toLowerCase() + ".config.prop." + field.getName() + ".comment") : I18n.translateToLocal(propInfo.comment());
        String fieldName = propInfo.nameOverride().equals("") ? field.getName() : propInfo.nameOverride();
        try {
            if (clz.equals(int.class)) {
                int min = Integer.MIN_VALUE;
                int max = Integer.MAX_VALUE;
                if (field.isAnnotationPresent(RangeInteger.class)) {
                    RangeInteger minMax = field.getAnnotation(RangeInteger.class);
                    min = minMax.min();
                    max = minMax.max();
                }
                if (field.isAnnotationPresent(IntBool.class)) {
                    min = 0;
                    max = 1;
                    comment += "\n0=false | 1 = true";
                }
                if (write) {
                    config.get(propInfo.category(), fieldName, field.getInt(this)).set(field.getInt(this));
                    config.get(propInfo.category(), fieldName, field.getInt(this)).setMinValue(min);
                    config.get(propInfo.category(), fieldName, field.getInt(this)).setMaxValue(max);
                    config.get(propInfo.category(), fieldName, field.getInt(this)).setComment(comment);
                } else {
                    field.set(this, config.getInt(fieldName, propInfo.category(), field.getInt(this), min, max, comment));
                }
            } else if (clz.equals(int[].class)) {
                int min = Integer.MIN_VALUE;
                int max = Integer.MAX_VALUE;
                if (field.isAnnotationPresent(RangeInteger.class)) {
                    RangeInteger minMax = field.getAnnotation(RangeInteger.class);
                    min = minMax.min();
                    max = minMax.max();
                }
                if (write) {
                    config.get(propInfo.category(), fieldName, (int[]) field.get(this)).set((int[]) field.get(this));
                    config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setMinValue(min);
                    config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setMaxValue(max);
                    config.get(propInfo.category(), fieldName, (int[]) field.get(this)).setComment(comment);
                } else {
                    field.set(this, config.get(propInfo.category(), fieldName, (int[]) field.get(this), comment, min, max).getIntList());
                }
            } else if (clz.equals(String.class)) {
                if (write) {
                    config.get(propInfo.category(), fieldName, (String) field.get(this)).set((String) field.get(this));
                    config.get(propInfo.category(), fieldName, (String) field.get(this)).setComment(comment);
                    if (field.isAnnotationPresent(StringLimit.class))
                        config.get(propInfo.category(), fieldName, (String) field.get(this)).setValidValues(field.getAnnotation(StringLimit.class).value());
                } else {
                    if (field.isAnnotationPresent(StringLimit.class))
                        field.set(this, config.getString(fieldName, propInfo.category(), (String) field.get(this), comment, field.getAnnotation(StringLimit.class).value()));
                    else
                        field.set(this, config.getString(fieldName, propInfo.category(), (String) field.get(this), comment));
                }
            } else if (clz.equals(String[].class)) {
                if (write) {
                    config.get(propInfo.category(), fieldName, (String[]) field.get(this)).set((String[]) field.get(this));
                    config.get(propInfo.category(), fieldName, (String[]) field.get(this)).setComment(comment);
                    if (field.isAnnotationPresent(StringLimit.class))
                        config.get(propInfo.category(), fieldName, (String[]) field.get(this)).setValidValues(field.getAnnotation(StringLimit.class).value());
                } else {
                    if (field.isAnnotationPresent(StringLimit.class))
                        field.set(this, config.getStringList(fieldName, propInfo.category(), (String[]) field.get(this), comment, field.getAnnotation(StringLimit.class).value()));
                    else
                        field.set(this, config.getStringList(fieldName, propInfo.category(), (String[]) field.get(this), comment));
                }
            } else if (clz.equals(boolean.class)) {
                if (write) {
                    config.get(propInfo.category(), fieldName, field.getBoolean(this)).set(field.getBoolean(this));
                    config.get(propInfo.category(), fieldName, field.getBoolean(this)).setComment(comment);
                } else {
                    field.set(this, config.getBoolean(fieldName, propInfo.category(), field.getBoolean(this), comment));
                }
            } else if (clz.equals(boolean[].class)) {
                if (write) {
                    config.get(propInfo.category(), fieldName, (boolean[]) field.get(this)).set((boolean[]) field.get(this));
                    config.get(propInfo.category(), fieldName, (boolean[]) field.get(this)).setComment(comment);
                } else {
                    field.set(this, config.get(propInfo.category(), fieldName, (boolean[]) field.get(this), comment).getBooleanList());
                }
            } else {
                return;
            }
        } catch (Exception ignored) {
        }
    }

    public class CategoryInfo implements Comparable<CategoryInfo> {
        public final String category;
        public String name;

        public CategoryInfo(String cat) {
            category = name = cat;
        }

        @Override
        public int compareTo(CategoryInfo cfg) {
            return category.compareTo(cfg.category);
        }
    }
}