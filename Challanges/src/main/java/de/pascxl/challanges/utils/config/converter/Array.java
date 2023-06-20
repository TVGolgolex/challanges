package de.pascxl.challanges.utils.config.converter;

import de.pascxl.challanges.utils.config.PaperInternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;

public class Array implements Converter {

    private final PaperInternalConverter paperInternalConverter;

    public Array( PaperInternalConverter paperInternalConverter) {
        this.paperInternalConverter = paperInternalConverter;
    }

    @Override
    public Object toConfig( Class<?> type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        Class<?> singleType = type.getComponentType();
        Converter conv = paperInternalConverter.getConverter( singleType );
        if ( conv == null ) {
            return obj;
        }

        Object[] ret = new Object[java.lang.reflect.Array.getLength( obj )];
        for ( int i = 0; i < ret.length; i++ ) {
            ret[i] = conv.toConfig( singleType, java.lang.reflect.Array.get( obj, i ), parameterizedType );
        }
        return ret;
    }

    @Override
    public Object fromConfig( Class type, Object section, ParameterizedType genericType ) throws Exception {
        Class<?> singleType = type.getComponentType();
        java.util.List values;

        if ( section instanceof java.util.List ) {
            values = (java.util.List) section;
        } else {
            values = new ArrayList();
            Collections.addAll( values, (Object[]) section );
        }

        Object ret = java.lang.reflect.Array.newInstance( singleType, values.size() );
        Converter conv = paperInternalConverter.getConverter( singleType );
        if ( conv == null ) {
            return values.toArray( (Object[]) ret );
        }

        for ( int i = 0; i < values.size(); i++ ) {
            java.lang.reflect.Array.set( ret, i, conv.fromConfig( singleType, values.get( i ), genericType ) );
        }
        return ret;
    }

    @Override
    public boolean supports( Class<?> type ) {
        return type.isArray();
    }
}
