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
package org.cavebeetle.configuration.impl;

import static org.cavebeetle.configuration.Config.ORACLE;
import static org.cavebeetle.configuration.impl.PropertyName.fromJavaName;
import org.cavebeetle.configuration.ConfigurationProperty;
import org.cavebeetle.configuration.Parser;

public final class DefaultConfigurationProperty<T>
        implements
            ConfigurationProperty<T>
{
    public static final class DefaultBuilder
            implements
                Builder
    {
        @Override
        public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newProperty(final Class<T> type, final Parser<T> parser, final E enumProperty, final String defaultValue)
        {
            return new DefaultConfigurationProperty<T>(type, parser, enumProperty, defaultValue);
        }
    }

    private final Class<T> type;
    private final Parser<T> parser;
    private final Class<?> enumType;
    private final String propertyName;
    private final String defaultValue;

    public <E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> DefaultConfigurationProperty(
            final Class<T> type,
            final Parser<T> parser,
            final E enumProperty,
            final String defaultValue)
    {
        this.type = type;
        this.parser = parser;
        this.enumType = enumProperty.getClass();
        @SuppressWarnings("unchecked")
        final Class<E> enumType = (Class<E>) enumProperty.getClass();
        propertyName = fromJavaName(enumType, enumProperty.name());
        this.defaultValue = defaultValue;
        ORACLE.register(propertyName, this);
    }

    @Override
    public String getDefaultValue()
    {
        return defaultValue;
    }

    @Override
    public T getValue()
    {
        return ORACLE.getValue(type, this);
    }

    @Override
    public Class<?> getEnumType()
    {
        return enumType;
    }

    @Override
    public T parse(final String value)
    {
        return parser.parse(value);
    }

    @Override
    public String toString()
    {
        return "${" + propertyName + "}";
    }
}
