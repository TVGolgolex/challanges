package de.pascxl.challanges.utils.config;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PaperBaseConfig {
    protected transient File CONFIG_FILE = null;
    protected transient String[] CONFIG_HEADER = null;
    protected transient ConfigMode CONFIG_MODE = ConfigMode.DEFAULT;
    protected transient boolean skipFailedObjects = false;

    protected transient PaperInternalConverter converter = new PaperInternalConverter();

    /**
     * This function gets called after the File has been loaded and before the Converter gets it.
     * This is used to manually edit the configSection when you updated the config or something
     *
     * @param paperConfigSection The root ConfigSection with all Subnodes loaded into
     */
    public void update( PaperConfigSection paperConfigSection) {

    }

    /**
     * Add a Custom Converter. A Converter can take Objects and return a pretty Object which gets saved/loaded from
     * the Converter. How a Converter must be build can be looked up in the Converter Interface.
     *
     * @param addConverter Converter to be added
     * @throws InvalidConverterException If the Converter has any errors this Exception tells you what
     */
    public void addConverter( Class addConverter ) throws InvalidConverterException
    {
        converter.addCustomConverter( addConverter );
    }

    protected boolean doSkip( Field field ) {
        if ( Modifier.isTransient( field.getModifiers() ) || Modifier.isFinal( field.getModifiers() ) ) {
            return true;
        }

        if ( Modifier.isStatic( field.getModifiers() ) ) {
            if ( !field.isAnnotationPresent( PreserveStatic.class ) ) {
                return true;
            }

            PreserveStatic presStatic = field.getAnnotation( PreserveStatic.class );
            return !presStatic.value();
        }

        return false;
    }

    protected void configureFromSerializeOptionsAnnotation() {
        if ( !getClass().isAnnotationPresent( SerializeOptions.class ) ) {
            return;
        }

        SerializeOptions options = getClass().getAnnotation( SerializeOptions.class );
        CONFIG_HEADER = options.configHeader();
        CONFIG_MODE = options.configMode();
        skipFailedObjects = options.skipFailedObjects();
    }
}
