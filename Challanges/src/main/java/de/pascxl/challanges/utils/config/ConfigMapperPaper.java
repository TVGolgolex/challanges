package de.pascxl.challanges.utils.config;

import de.pascxl.challanges.utils.config.converter.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ConfigMapperPaper extends PaperPaperBaseConfigMapper {
    public Map<String, Object> saveToMap(Class clazz) throws Exception
    {
        Map<String, Object> returnMap = new HashMap<>();

        if (!clazz.getSuperclass().equals(YamlConfigPaper.class) && !clazz.getSuperclass().equals(Object.class))
        {
            Map<String, Object> map = saveToMap(clazz.getSuperclass());
            returnMap.putAll(map);
        }

        for (Field field : clazz.getDeclaredFields())
        {
            if (doSkip(field))
            {
                continue;
            }

            String path;

            switch (CONFIG_MODE)
            {
                case PATH_BY_UNDERSCORE -> path = field.getName().replace("_", ".");
                case FIELD_IS_KEY -> path = field.getName();
                default ->
                {
                    String fieldName = field.getName();
                    if (fieldName.contains("_"))
                    {
                        path = field.getName().replace("_", ".");
                    } else
                    {
                        path = field.getName();
                    }
                }
            }

            if (field.isAnnotationPresent(Path.class))
            {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if (Modifier.isPrivate(field.getModifiers()))
            {
                field.setAccessible(true);
            }

            try
            {
                returnMap.put(path, field.get(this));
            } catch (IllegalAccessException e)
            {
            }
        }

        Converter mapConverter = (Converter) converter.getConverter(Map.class);
        return (Map<String, Object>) mapConverter.toConfig(HashMap.class, returnMap, null);
    }

    public void loadFromMap(Map section, Class clazz) throws Exception
    {
        if (!clazz.getSuperclass().equals(YamlConfigPaper.class) && !clazz.getSuperclass().equals(YamlConfigPaper.class))
        {
            loadFromMap(section, clazz.getSuperclass());
        }

        for (Field field : clazz.getDeclaredFields())
        {
            if (doSkip(field))
            {
                continue;
            }

            String path = (CONFIG_MODE.equals(ConfigMode.PATH_BY_UNDERSCORE)) ? field.getName().replaceAll("_", ".") : field.getName();

            if (field.isAnnotationPresent(Path.class))
            {
                Path path1 = field.getAnnotation(Path.class);
                path = path1.value();
            }

            if (Modifier.isPrivate(field.getModifiers()))
            {
                field.setAccessible(true);
            }

            converter.fromConfig((YamlConfigPaper) this, field, PaperConfigSection.convertFromMap(section), path);
        }
    }
}
