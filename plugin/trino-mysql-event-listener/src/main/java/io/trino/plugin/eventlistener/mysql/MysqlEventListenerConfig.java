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
package io.trino.plugin.eventlistener.mysql;

import com.mysql.cj.jdbc.Driver;
import io.airlift.configuration.Config;
import io.airlift.configuration.ConfigDescription;
import io.airlift.configuration.ConfigSecuritySensitive;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.sql.SQLException;
import java.util.List;

public class MysqlEventListenerConfig
{
    private String url;
    private List<String> pruneColumns = List.of();

    @NotNull
    public String getUrl()
    {
        return url;
    }

    @ConfigSecuritySensitive
    @Config("mysql-event-listener.db.url")
    public MysqlEventListenerConfig setUrl(String url)
    {
        this.url = url;
        return this;
    }

    public List<String> getPruneColumns()
    {
        return pruneColumns;
    }

    @ConfigDescription("List of columns to prune from the event data. Comma-separated list of column names.")
    @Config("mysql-event-listener.db.prune-columns")
    public MysqlEventListenerConfig setPruneColumns(List<String> pruneColumns)
    {

        this.pruneColumns = pruneColumns;
        return this;
    }

    @AssertTrue(message = "Invalid JDBC URL for MySQL event listener")
    public boolean isValidUrl()
    {
        try {
            return new Driver().acceptsURL(getUrl());
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
