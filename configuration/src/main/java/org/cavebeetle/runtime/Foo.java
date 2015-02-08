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
package org.cavebeetle.runtime;

import static org.cavebeetle.configuration.Config.ORACLE;
import static org.cavebeetle.runtime.Parsers.BOOLEAN_PARSER;
import static org.cavebeetle.runtime.Parsers.INTEGER_PARSER;
import static org.cavebeetle.runtime.Parsers.STRING_PARSER;
import static org.cavebeetle.runtime.Parsers.URL_PARSER;
import java.net.URL;
import org.cavebeetle.configuration.ConfigurationProperty;

public interface Foo
{
    public enum Flag
            implements
                ConfigurationProperty<Boolean>
    {
        WORKING(true),
        //
        ;
        private final ConfigurationProperty<Boolean> property;

        private Flag(
                final boolean defaultValue)
        {
            property = ORACLE.newPropertyWithDefaultValue(Boolean.class, BOOLEAN_PARSER, this, defaultValue);
        }

        @Override
        public Class<?> getEnumType()
        {
            return property.getEnumType();
        }

        @Override
        public Boolean getValue()
        {
            return property.getValue();
        }

        @Override
        public String getDefaultValue()
        {
            return property.getDefaultValue();
        }

        @Override
        public Boolean parse(final String value)
        {
            return property.parse(value);
        }

        @Override
        public String toString()
        {
            return property.toString();
        }
    }

    public interface Bar
    {
        public enum Int
                implements
                    ConfigurationProperty<Integer>
        {
            ABC(1),
            //
            ;
            private final ConfigurationProperty<Integer> property;

            private Int(
                    final int defaultValue)
            {
                property = ORACLE.newPropertyWithDefaultValue(Integer.class, INTEGER_PARSER, this, defaultValue);
            }

            @Override
            public Class<?> getEnumType()
            {
                return property.getEnumType();
            }

            @Override
            public Integer getValue()
            {
                return property.getValue();
            }

            @Override
            public String getDefaultValue()
            {
                return property.getDefaultValue();
            }

            @Override
            public Integer parse(final String value)
            {
                return property.parse(value);
            }

            @Override
            public String toString()
            {
                return property.toString();
            }
        }
    }

    public enum Host
            implements
                ConfigurationProperty<String>
    {
        LOCAL_HOST("localhost"),
        REMOTE_HOST("www"),
        //
        ;
        private final ConfigurationProperty<String> property;

        private Host(
                final String defaultValue)
        {
            property = ORACLE.newProperty(String.class, STRING_PARSER, this, defaultValue);
        }

        @Override
        public Class<?> getEnumType()
        {
            return property.getEnumType();
        }

        @Override
        public String getValue()
        {
            return property.getValue();
        }

        @Override
        public String getDefaultValue()
        {
            return property.getDefaultValue();
        }

        @Override
        public String parse(final String value)
        {
            return property.parse(value);
        }

        @Override
        public String toString()
        {
            return property.toString();
        }
    }

    public enum Port
            implements
                ConfigurationProperty<String>
    {
        DEFAULT(":80"),
        REMOTE(":8080"),
        //
        ;
        private final ConfigurationProperty<String> property;

        private Port(
                final String defaultValue)
        {
            property = ORACLE.newProperty(String.class, STRING_PARSER, this, defaultValue);
        }

        @Override
        public Class<?> getEnumType()
        {
            return property.getEnumType();
        }

        @Override
        public String getValue()
        {
            return property.getValue();
        }

        @Override
        public String getDefaultValue()
        {
            return property.getDefaultValue();
        }

        @Override
        public String parse(final String value)
        {
            return property.parse(value);
        }

        @Override
        public String toString()
        {
            return property.toString();
        }
    }

    public enum Domain
            implements
                ConfigurationProperty<String>
    {
        DEFAULT(""),
        REMOTE(".company.com"),
        //
        ;
        private final ConfigurationProperty<String> property;

        private Domain(
                final String defaultValue)
        {
            property = ORACLE.newProperty(String.class, STRING_PARSER, this, defaultValue);
        }

        @Override
        public Class<?> getEnumType()
        {
            return property.getEnumType();
        }

        @Override
        public String getValue()
        {
            return property.getValue();
        }

        @Override
        public String getDefaultValue()
        {
            return property.getDefaultValue();
        }

        @Override
        public String parse(final String value)
        {
            return property.parse(value);
        }

        @Override
        public String toString()
        {
            return property.toString();
        }
    }

    public enum Url
            implements
                ConfigurationProperty<URL>
    {
        TARGET("http://" + Foo.Host.LOCAL_HOST + "/some/path/"),
        //
        ;
        private final ConfigurationProperty<URL> property;

        private Url(
                final String defaultValue)
        {
            property = ORACLE.newProperty(URL.class, URL_PARSER, this, defaultValue);
        }

        @Override
        public Class<?> getEnumType()
        {
            return property.getEnumType();
        }

        @Override
        public URL getValue()
        {
            return property.getValue();
        }

        @Override
        public String getDefaultValue()
        {
            return property.getDefaultValue();
        }

        @Override
        public URL parse(final String value)
        {
            return property.parse(value);
        }

        @Override
        public String toString()
        {
            return property.toString();
        }
    }
}
