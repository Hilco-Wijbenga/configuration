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

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.of;
import static com.google.common.collect.Maps.newConcurrentMap;
import java.util.Iterator;
import java.util.Map;
import com.google.common.base.Optional;

public final class StrictMap<K, V>
        implements
            Iterable<K>
{
    public static final <K, V> StrictMap<K, V> newStrictMap()
    {
        return new StrictMap<K, V>();
    }

    private final Map<K, V> map;

    public StrictMap()
    {
        map = newConcurrentMap();
    }

    public boolean hasKey(final K key)
    {
        return map.containsKey(key);
    }

    public Optional<V> get(final K key)
    {
        if (map.containsKey(key))
        {
            return of(map.get(key));
        }
        else
        {
            return absent();
        }
    }

    public void put(final K key, final V value)
    {
        map.put(key, value);
    }

    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    public void clear()
    {
        map.clear();
    }

    @Override
    public Iterator<K> iterator()
    {
        return map.keySet().iterator();
    }
}
