/*
 * Copyright 2015-2018 Austin Keener & Michael Ritter & Florian Spieß
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.dv8tion.jda.internal.utils.config.sharding;

import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.utils.config.MetaConfig;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentMap;
import java.util.function.IntFunction;

public class ShardingMetaConfig extends MetaConfig
{
    private final boolean enableCompression;
    private final IntFunction<? extends ConcurrentMap<String, String>> contextProvider;

    public ShardingMetaConfig(
            IntFunction<? extends ConcurrentMap<String, String>> contextProvider,
            EnumSet<CacheFlag> cacheFlags, boolean enableMDC,
            boolean useShutdownHook, boolean enableCompression)
    {
        super(null, cacheFlags, enableMDC, useShutdownHook);

        this.enableCompression = enableCompression;
        this.contextProvider = contextProvider;
    }

    public ConcurrentMap<String, String> getContextMap(int shardId)
    {
        return contextProvider == null ? null : contextProvider.apply(shardId);
    }

    public boolean isEnableCompression()
    {
        return enableCompression;
    }

    public IntFunction<? extends ConcurrentMap<String, String>> getContextProvider()
    {
        return contextProvider;
    }

    public static ShardingMetaConfig getDefault()
    {
        return new ShardingMetaConfig(null, null, false, true, true);
    }
}
