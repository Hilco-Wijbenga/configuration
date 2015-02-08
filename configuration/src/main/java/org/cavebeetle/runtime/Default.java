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
import static org.cavebeetle.runtime.Parsers.STRING_PARSER;
import org.cavebeetle.configuration.ConfigurationProperty;

public interface Default
{
    public enum Text
            implements
                ConfigurationProperty<String>
    {
        PROTOCOL("https"),
        HOST("localhost"),
        PORT(":443"),
        DOMAIN(".company.com"),
        //
        ;
        private final ConfigurationProperty<String> property;

        private Text(
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
}
