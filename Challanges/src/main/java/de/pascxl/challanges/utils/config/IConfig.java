package de.pascxl.challanges.utils.config;

import java.io.File;

public interface IConfig {
    void save() throws InvalidConfigurationException;

    void save( File file ) throws InvalidConfigurationException;

    void init() throws InvalidConfigurationException;

    void init( File file ) throws InvalidConfigurationException;

    void reload() throws InvalidConfigurationException;

    void load() throws InvalidConfigurationException;

    void load( File file ) throws InvalidConfigurationException;
}
