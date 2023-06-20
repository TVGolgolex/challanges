package de.pascxl.challanges.utils.config.converter;

import de.pascxl.challanges.utils.config.PaperInternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;

public class Primitive implements Converter {
    private final HashSet<String> types = new HashSet<>() {{
        add("boolean");
        add("char");
        add("byte");
        add("short");
        add("int");
        add("long");
        add("float");
        add("double");
    }};

    public Primitive(PaperInternalConverter paperInternalConverter) {
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        return obj;
    }

    @Override
    public Object fromConfig(Class type, Object section, ParameterizedType genericType) throws Exception {
        switch (type.getSimpleName()) {
            case "short" -> {
                return (section instanceof Short) ? section : (short) section;
            }
            case "byte" -> {
                return (section instanceof Byte) ? section : (byte) section;
            }
            case "float" -> {
                if (section instanceof Integer) {
                    return section;
                }
                return (section instanceof Float) ? section : (float) section;
            }
            case "char" -> {
                return (section instanceof Character) ? section : ((String) section).charAt(0);
            }
            default -> {
                return section;
            }
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        return types.contains(type.getName());
    }
}
