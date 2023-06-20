package de.pascxl.challanges.utils.config.converter;

import de.pascxl.challanges.utils.config.PaperConfigSection;
import de.pascxl.challanges.utils.config.PaperInternalConverter;
import de.pascxl.challanges.utils.config.YamlConfigPaper;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

public class Config implements Converter {
    private final PaperInternalConverter paperInternalConverter;

    public Config( PaperInternalConverter paperInternalConverter) {
        this.paperInternalConverter = paperInternalConverter;
    }

    @Override
    public Object toConfig( Class<?> type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        return ( obj instanceof Map ) ? obj : ( (YamlConfigPaper) obj ).saveToMap( obj.getClass() );
    }

    @Override
    public Object fromConfig( Class type, Object section, ParameterizedType genericType ) throws Exception {
        YamlConfigPaper obj = (YamlConfigPaper) newInstance( type );

        // Inject Converter stack into subconfig
        for ( Class aClass : paperInternalConverter.getCustomConverters() ) {
            obj.addConverter( aClass );
        }

        obj.loadFromMap( ( section instanceof Map ) ? (Map) section : ( (PaperConfigSection) section ).getRawMap(), type );
        return obj;
    }

    // recursively handles enclosed classes
    public Object newInstance( Class type ) throws Exception {
        Class enclosingClass = type.getEnclosingClass();
        if ( enclosingClass != null ) {
            Object instanceOfEnclosingClass = newInstance( enclosingClass );
            return type.getConstructor( enclosingClass ).newInstance( instanceOfEnclosingClass );
        } else {
            return type.newInstance();
        }
    }

    @Override
    public boolean supports( Class<?> type ) {
        return YamlConfigPaper.class.isAssignableFrom( type );
    }
}
