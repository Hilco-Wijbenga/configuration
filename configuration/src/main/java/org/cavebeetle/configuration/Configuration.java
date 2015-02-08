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

public interface Configuration
{
    <E extends Enum<? extends PropertyType> & PropertyType> void init(Class<E> propertyTypesClass);

    <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyWithDefaultValue(Class<T> type, Parser<T> parser, E enumProperty, T defaultValue);

    <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyFromProperty(Class<T> type, Parser<T> parser, E enumProperty, ConfigurationProperty<T> defaultValue);

    <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newProperty(Class<T> type, Parser<T> parser, E enumProperty, String defaultValue);

    <T> void register(String fullyQualifiedPropertyName, ConfigurationProperty<T> configurationProperty);

    void reload();

    <T> T getValue(Class<T> type, ConfigurationProperty<T> property);
}
