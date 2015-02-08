/*
 * Copyright (C) 2015 H.C. Wijbenga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cavebeetle.configuration;

import org.cavebeetle.configuration.impl.DefaultConfiguration;

public enum Config
        implements
            Configuration
{
    ORACLE,
    //
    ;
    private final Configuration configuration;

    private Config()
    {
        configuration = new DefaultConfiguration();
    }

    @Override
    public <E extends Enum<? extends PropertyType> & PropertyType> void init(final Class<E> propertyTypesClass)
    {
        configuration.init(propertyTypesClass);
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyWithDefaultValue(final Class<T> type, final Parser<T> parser, final E enumProperty, final T defaultValue)
    {
        return configuration.newPropertyWithDefaultValue(type, parser, enumProperty, defaultValue);
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyFromProperty(final Class<T> type, final Parser<T> parser, final E enumProperty, final ConfigurationProperty<T> defaultValue)
    {
        return configuration.newPropertyFromProperty(type, parser, enumProperty, defaultValue);
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newProperty(final Class<T> type, final Parser<T> parser, final E enumProperty, final String defaultValue)
    {
        return configuration.newProperty(type, parser, enumProperty, defaultValue);
    }

    @Override
    public <T> void register(final String fullyQualifiedPropertyName, final ConfigurationProperty<T> configurationProperty)
    {
        configuration.register(fullyQualifiedPropertyName, configurationProperty);
    }

    @Override
    public void reload()
    {
        configuration.reload();
    }

    @Override
    public <T> T getValue(final Class<T> type, final ConfigurationProperty<T> property)
    {
        return configuration.getValue(type, property);
    }
}
