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
package io.trino.plugin.openlineage.transport;

import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;
import io.trino.plugin.openlineage.OpenLineageTransport;

public class OpenLineageTransportConfig
{
    private OpenLineageTransport transport = OpenLineageTransport.CONSOLE;

    public OpenLineageTransport getTransport()
    {
        return transport;
    }

    @Config("openlineage-event-listener.transport.type")
    @ConfigDescription("Type of transport used to emit lineage information")
    public OpenLineageTransportConfig setTransport(OpenLineageTransport transport)
    {
        this.transport = transport;
        return this;
    }
}
