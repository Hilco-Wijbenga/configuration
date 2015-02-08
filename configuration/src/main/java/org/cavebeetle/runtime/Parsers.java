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

import java.net.MalformedURLException;
import java.net.URL;
import org.cavebeetle.configuration.Parser;

public interface Parsers
{
    Parser<Boolean> BOOLEAN_PARSER = new Parser<Boolean>()
    {
        @Override
        public Boolean parse(final String text)
        {
            return Boolean.valueOf(text);
        }
    };
    Parser<Integer> INTEGER_PARSER = new Parser<Integer>()
    {
        @Override
        public Integer parse(final String text)
        {
            return Integer.decode(text);
        }
    };
    Parser<String> STRING_PARSER = new Parser<String>()
    {
        @Override
        public String parse(final String text)
        {
            return text;
        }
    };
    Parser<URL> URL_PARSER = new Parser<URL>()
    {
        @Override
        public URL parse(final String text)
        {
            try
            {
                return new URL(text);
            }
            catch (final MalformedURLException e)
            {
                throw new IllegalStateException(e);
            }
        }
    };
}
