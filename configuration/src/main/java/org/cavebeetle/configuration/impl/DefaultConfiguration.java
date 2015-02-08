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

import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.compile;
import static org.cavebeetle.configuration.impl.StrictMap.newStrictMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cavebeetle.configuration.Configuration;
import org.cavebeetle.configuration.ConfigurationProperty;
import org.cavebeetle.configuration.Parser;
import org.cavebeetle.configuration.PropertyType;
import com.google.common.base.Optional;

public final class DefaultConfiguration
        implements
            Configuration
{
    private static final Pattern EMBEDDED_PROPERTY;
    static
    {
        final String subElement = "[a-z][a-z0-9]*";
        final String element = subElement + "(-" + subElement + ")*";
        final String propertyName = element + "(\\." + element + ")*";
        EMBEDDED_PROPERTY = compile("\\$\\{(" + propertyName + ")\\}");
    }

    public static final InputStream newInputStream(final File file)
    {
        try
        {
            return new FileInputStream(file);
        }
        catch (final Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    public static final Properties loadProperties(final InputStream inputStream)
    {
        try
        {
            final Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
        catch (final Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    private final ConfigurationProperty.Builder builder;
    private final StrictMap<String, ConfigurationProperty<?>> knownProperties;
    private final StrictMap<ConfigurationProperty<?>, CacheValue<?>> cache;
    private Class<?> propertyTypesClass;
    private List<PropertyType> propertyTypes;

    public DefaultConfiguration()
    {
        this(new DefaultConfigurationProperty.DefaultBuilder());
    }

    public DefaultConfiguration(
            final ConfigurationProperty.Builder builder)
    {
        this.builder = builder;
        knownProperties = newStrictMap();
        cache = newStrictMap();
    }

    @Override
    public <E extends Enum<? extends PropertyType> & PropertyType> void init(final Class<E> propertyTypesClass)
    {
        this.propertyTypesClass = propertyTypesClass;
        final List<PropertyType> propertyTypes_ = newArrayList();
        for (final PropertyType propertyType : propertyTypesClass.getEnumConstants())
        {
            propertyTypes_.add(propertyType);
        }
        propertyTypes = propertyTypes_;
        for (final PropertyType propertyType : propertyTypes)
        {
            propertyType.init();
        }
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyWithDefaultValue(final Class<T> type, final Parser<T> parser, final E enumProperty, final T defaultValue)
    {
        return builder.newProperty(type, parser, enumProperty, defaultValue.toString());
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newPropertyFromProperty(final Class<T> type, final Parser<T> parser, final E enumProperty, final ConfigurationProperty<T> defaultValue)
    {
        return builder.newProperty(type, parser, enumProperty, defaultValue.toString());
    }

    @Override
    public <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> ConfigurationProperty<T> newProperty(final Class<T> type, final Parser<T> parser, final E enumProperty, final String defaultValue)
    {
        return builder.newProperty(type, parser, enumProperty, defaultValue);
    }

    @Override
    public <T> void register(final String fullyQualifiedPropertyName, final ConfigurationProperty<T> configurationProperty)
    {
        knownProperties.put(fullyQualifiedPropertyName, configurationProperty);
    }

    @Override
    public void reload()
    {
        synchronized (cache)
        {
            cache.clear();
        }
    }

    @Override
    public <T> T getValue(final Class<T> type, final ConfigurationProperty<T> property)
    {
        boolean okay = false;
        for (final PropertyType propertyType : propertyTypes)
        {
            if (propertyType.matches(property.getEnumType()))
            {
                okay = true;
                break;
            }
        }
        if (!okay)
        {
            final String fqEnumTypeName = property.getEnumType().getCanonicalName();
            final String propertyTypesClassName = propertyTypesClass.getCanonicalName();
            final StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Missing property type: ").append(fqEnumTypeName).append('\n');
            messageBuilder.append('\n');
            messageBuilder.append("Please add type ");
            messageBuilder.append("'").append(fqEnumTypeName).append("'");
            messageBuilder.append(" to the list of known property types in enum ");
            messageBuilder.append("'").append(propertyTypesClassName).append("'");
            messageBuilder.append(".\n");
            messageBuilder.append('\n');
            messageBuilder.append("public enum PropertyTypes\n");
            messageBuilder.append("        implements\n");
            messageBuilder.append("            PropertyType\n");
            messageBuilder.append("{\n");
            messageBuilder.append("    ... other enums ...\n");
            final String enumTypePackageName = property.getEnumType().getPackage().getName();
            final String enumTypeName = fqEnumTypeName.substring(enumTypePackageName.length() + 1);
            final String enumName = enumTypeName.toUpperCase().replace('.', '_');
            messageBuilder.append("    ").append(enumName).append("(").append(enumTypeName).append(".class),\n");
            messageBuilder.append("    ... other enums ...\n");
            messageBuilder.append("    //\n");
            messageBuilder.append("    ;\n");
            messageBuilder.append("    ...\n");
            messageBuilder.append("}\n");
            throw new IllegalStateException(messageBuilder.toString());
        }
        synchronized (cache)
        {
            if (cache.isEmpty())
            {
                final StrictMap<String, String> propertyToConfiguredValueMap = newStrictMap();
                {
                    for (final String propertyName : knownProperties)
                    {
                        final String defaultValue = knownProperties.get(propertyName).get().getDefaultValue();
                        propertyToConfiguredValueMap.put(propertyName, defaultValue);
                    }
                    final File propertiesFile = new File("./src/main/resources/configuration.properties");
                    final InputStream propertiesInputStream = newInputStream(propertiesFile);
                    final Properties properties = loadProperties(propertiesInputStream);
                    for (final Object key : properties.keySet())
                    {
                        final String propertyName = key.toString();
                        final Optional<ConfigurationProperty<?>> maybeKnownProperty = knownProperties.get(propertyName);
                        if (!maybeKnownProperty.isPresent())
                        {
                            throw new IllegalStateException("Unknown key '" + propertyName + "'.");
                        }
                        final String configuredValueAsText = properties.get(propertyName).toString();
                        propertyToConfiguredValueMap.put(propertyName, configuredValueAsText);
                    }
                }
                {
                    for (final String knownPropertyName : knownProperties)
                    {
                        final ConfigurationProperty<?> untypedConfiguredProperty = knownProperties.get(knownPropertyName).get();
                        @SuppressWarnings("unchecked")
                        final ConfigurationProperty<T> configuredProperty = (ConfigurationProperty<T>) untypedConfiguredProperty;
                        final String configuredValueAsText = propertyToConfiguredValueMap.get(knownPropertyName).get();
                        final List<String> propertyNames = newArrayList();
                        final String resolvedValueAsText = resolveValue(propertyToConfiguredValueMap, propertyNames, configuredValueAsText);
                        final T configuredValue = configuredProperty.parse(resolvedValueAsText);
                        final CacheValue<T> cacheValue = new CacheValue<T>(type, configuredValueAsText, configuredValue);
                        cache.put(untypedConfiguredProperty, cacheValue);
                    }
                }
            }
        }
        final Optional<CacheValue<?>> maybeCacheValue_ = cache.get(property);
        if (!maybeCacheValue_.isPresent())
        {
            throw new IllegalStateException("Unknown property: '" + property + "'.");
        }
        final CacheValue<?> cacheValue_ = maybeCacheValue_.get();
        @SuppressWarnings("unchecked")
        final CacheValue<T> cacheValue = (CacheValue<T>) cacheValue_;
        return cacheValue.getValue();
    }

    public String resolveValue(final StrictMap<String, String> fromPropertyNameToValue, final List<String> propertyNames, final String value)
    {
        if (value.indexOf("${") == -1)
        {
            return value;
        }
        final Matcher matcher = EMBEDDED_PROPERTY.matcher(value);
        if (matcher.find())
        {
            final MatchResult matchResult = matcher.toMatchResult();
            final String embeddedPropertyName = matchResult.group(1);
            final Optional<String> maybeEmbeddedPropertyValue = fromPropertyNameToValue.get(embeddedPropertyName);
            if (!maybeEmbeddedPropertyValue.isPresent())
            {
                final StringBuilder builder = new StringBuilder();
                for (final String propertyName : propertyNames)
                {
                    builder.append("While resolving '" + propertyName + "'\n");
                }
                throw new IllegalStateException(builder + "Unknown property encountered: '" + embeddedPropertyName + "'.");
            }
            propertyNames.add(embeddedPropertyName);
            final String embeddedPropertyValue = maybeEmbeddedPropertyValue.get();
            final String resolvedEmbeddedPropertyValue = resolveValue(fromPropertyNameToValue, propertyNames, embeddedPropertyValue);
            final String partiallyResolvedValue = value.substring(0, value.indexOf("${")) + resolvedEmbeddedPropertyValue + value.substring(matchResult.end());
            return resolveValue(fromPropertyNameToValue, propertyNames, partiallyResolvedValue);
        }
        throw new IllegalStateException("Invalid syntax: '" + value + "' (expected a property at offset " + value.indexOf("${") + ": '" + value.substring(value.indexOf("${")) + "').");
    }
}
