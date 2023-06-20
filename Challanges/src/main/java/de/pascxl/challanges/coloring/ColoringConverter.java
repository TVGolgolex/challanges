package de.pascxl.challanges.coloring;

import de.pascxl.challanges.utils.config.PaperInternalConverter;
import de.pascxl.challanges.utils.config.converter.Converter;
import net.kyori.adventure.text.format.NamedTextColor;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Pascal K. on 20.06.2023.
 */
public class ColoringConverter implements Converter {

    public ColoringConverter(PaperInternalConverter paperInternalConverter) {
    }

    @Override
    public Object toConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        Coloring coloring = (Coloring) obj;
        return coloring.getFirstGradientColor() + ":" + coloring.getSecondGradientColor() + ":" + coloring.getNamedTextColor().value();
    }

    @Override
    public Object fromConfig(Class<?> type, Object obj, ParameterizedType parameterizedType) throws Exception {
        String s = (String) obj;
        String[] split = s.split(":");
        return new Coloring(
                split[0],
                split[1],
                NamedTextColor.namedColor(Integer.parseInt(split[2]))
        );
    }

    @Override
    public boolean supports(Class<?> type) {
        return Coloring.class.isAssignableFrom(type);
    }
}
