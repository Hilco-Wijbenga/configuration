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

public final class PropertyName
{
    public static final String fromJavaName(final Class<?> type, final String name)
    {
        final String packageName = type.getPackage().getName();
        final String canonicalName = type.getCanonicalName().substring(packageName.length() + 1) + "." + name;
        final StringBuilder propertyName = new StringBuilder();
        int index = 0;
        char previousChar = 0;
        while (index < canonicalName.length())
        {
            final char ch = canonicalName.charAt(index++);
            if (Character.isUpperCase(ch))
            {
                if (Character.isLowerCase(previousChar))
                {
                    propertyName.append('-');
                }
                propertyName.append(Character.toLowerCase(ch));
            }
            else if (Character.isLowerCase(ch))
            {
                propertyName.append(ch);
            }
            else if (ch == '.')
            {
                propertyName.append(ch);
            }
            else if (ch == '_')
            {
                propertyName.append('-');
            }
            else
            {
                propertyName.append(ch);
            }
            previousChar = ch;
        }
        return propertyName.toString();
    }
}
