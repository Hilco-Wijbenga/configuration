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

import static com.google.common.base.Preconditions.checkNotNull;
import org.cavebeetle.configuration.ConfigurationProperty;
import org.cavebeetle.configuration.PropertyType;

public enum PropertyTypes
        implements
            PropertyType
{
    FOO_FLAG(Foo.Flag.class),
    FOO_HOST(Foo.Host.class),
    FOO_DOMAIN(Foo.Domain.class),
    DEFAULT_TEXT(Default.Text.class),
    //
    ;
    private final Class<ConfigurationProperty<?>> enumType;

    private <T, E extends Enum<? extends ConfigurationProperty<T>> & ConfigurationProperty<T>> PropertyTypes(
            final Class<E> enumType)
    {
        checkNotNull(enumType.getEnumConstants()[0], "This should never fail. It simply triggers initialization.");
        // Variable 'rawEnumType' is a workaround for a bug in JDK 1.6's javac.
        final Class<?> rawEnumType = enumType;
        @SuppressWarnings("unchecked")
        final Class<ConfigurationProperty<?>> enumType_ = (Class<ConfigurationProperty<?>>) rawEnumType;
        this.enumType = enumType_;
    }

    @Override
    public void init()
    {
        checkNotNull(enumType.getEnumConstants()[0].getValue());
    }

    @Override
    public <T> boolean matches(final Class<T> type)
    {
        return enumType == type;
    }
}
