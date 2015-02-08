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

public final class CacheValue<T>
{
    private final Class<T> type;
    private final String configuredValue;
    private final T value;

    public CacheValue(
            final Class<T> type,
            final String configuredValue,
            final T value)
    {
        this.type = type;
        this.configuredValue = configuredValue;
        this.value = value;
    }

    public Class<T> getType()
    {
        return type;
    }

    public String getConfiguredValue()
    {
        return configuredValue;
    }

    public T getValue()
    {
        return value;
    }
}
