/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.plugin.lakehouse;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.airlift.configuration.testing.ConfigAssertions.assertFullMapping;
import static io.airlift.configuration.testing.ConfigAssertions.assertRecordedDefaults;
import static io.airlift.configuration.testing.ConfigAssertions.recordDefaults;
import static io.trino.plugin.lakehouse.TableType.HIVE;
import static io.trino.plugin.lakehouse.TableType.ICEBERG;

public class TestLakehouseConfig
{
    @Test
    public void testDefaults()
    {
        assertRecordedDefaults(recordDefaults(LakehouseConfig.class)
                .setTableType(ICEBERG));
    }

    @Test
    public void testExplicitPropertyMappings()
    {
        Map<String, String> properties = ImmutableMap.of("lakehouse.table-type", "hive");

        LakehouseConfig expected = new LakehouseConfig()
                .setTableType(HIVE);

        assertFullMapping(properties, expected);
    }
}
