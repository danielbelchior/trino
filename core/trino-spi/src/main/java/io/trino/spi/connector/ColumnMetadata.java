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
package io.trino.spi.connector;

import io.trino.spi.type.Type;
import jakarta.annotation.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.trino.spi.connector.SchemaUtil.checkNotEmpty;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;

public class ColumnMetadata
{
    private final String name;
    private final Type type;
    private final Optional<String> defaultValue;
    private final boolean nullable;
    private final String comment;
    private final String extraInfo;
    private final boolean hidden;
    private final Map<String, Object> properties;

    public ColumnMetadata(String name, Type type)
    {
        this(name, type, Optional.empty(), true, null, null, false, emptyMap());
    }

    private ColumnMetadata(
            String name,
            Type type,
            Optional<String> defaultValue,
            boolean nullable,
            String comment,
            String extraInfo,
            boolean hidden,
            Map<String, Object> properties)
    {
        checkNotEmpty(name, "name");
        requireNonNull(type, "type is null");
        requireNonNull(defaultValue, "defaultValue is null");
        requireNonNull(properties, "properties is null");

        this.name = name.toLowerCase(ENGLISH);
        this.type = type;
        this.defaultValue = defaultValue;
        this.comment = comment;
        this.extraInfo = extraInfo;
        this.hidden = hidden;
        this.properties = properties.isEmpty() ? emptyMap() : unmodifiableMap(new LinkedHashMap<>(properties));
        this.nullable = nullable;
    }

    public String getName()
    {
        return name;
    }

    public Type getType()
    {
        return type;
    }

    public Optional<String> getDefaultValue()
    {
        return defaultValue;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    @Nullable // TODO make it Optional
    public String getComment()
    {
        return comment;
    }

    @Nullable // TODO make it Optional
    public String getExtraInfo()
    {
        return extraInfo;
    }

    public boolean isHidden()
    {
        return hidden;
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }

    public ColumnSchema getColumnSchema()
    {
        return ColumnSchema.builder(this)
                .build();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("ColumnMetadata{");
        sb.append("name='").append(name).append('\'');
        sb.append(", type=").append(type);
        sb.append(", ").append(nullable ? "nullable" : "nonnull");
        if (comment != null) {
            sb.append(", comment='").append(comment).append('\'');
        }
        if (extraInfo != null) {
            sb.append(", extraInfo='").append(extraInfo).append('\'');
        }
        if (hidden) {
            sb.append(", hidden");
        }
        if (!properties.isEmpty()) {
            sb.append(", properties=").append(properties);
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, type, nullable, comment, extraInfo, hidden);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColumnMetadata other = (ColumnMetadata) obj;
        return Objects.equals(this.name, other.name) &&
                Objects.equals(this.type, other.type) &&
                this.nullable == other.nullable &&
                Objects.equals(this.comment, other.comment) &&
                Objects.equals(this.extraInfo, other.extraInfo) &&
                this.hidden == other.hidden;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static Builder builderFrom(ColumnMetadata columnMetadata)
    {
        return new Builder(columnMetadata);
    }

    public static class Builder
    {
        private String name;
        private Type type;
        private Optional<String> defaultValue = Optional.empty();
        private boolean nullable = true;
        private Optional<String> comment = Optional.empty();
        private Optional<String> extraInfo = Optional.empty();
        private boolean hidden;
        private Map<String, Object> properties = emptyMap();

        private Builder() {}

        private Builder(ColumnMetadata columnMetadata)
        {
            this.name = columnMetadata.getName();
            this.type = columnMetadata.getType();
            this.nullable = columnMetadata.isNullable();
            this.comment = Optional.ofNullable(columnMetadata.getComment());
            this.extraInfo = Optional.ofNullable(columnMetadata.getExtraInfo());
            this.hidden = columnMetadata.isHidden();
            this.properties = columnMetadata.getProperties();
        }

        public Builder setName(String name)
        {
            this.name = requireNonNull(name, "name is null");
            return this;
        }

        public Builder setType(Type type)
        {
            this.type = requireNonNull(type, "type is null");
            return this;
        }

        public Builder setDefaultValue(Optional<String> defaultValue)
        {
            this.defaultValue = requireNonNull(defaultValue, "defaultValue is null");
            return this;
        }

        public Builder setNullable(boolean nullable)
        {
            this.nullable = nullable;
            return this;
        }

        public Builder setComment(Optional<String> comment)
        {
            this.comment = requireNonNull(comment, "comment is null");
            return this;
        }

        public Builder setExtraInfo(Optional<String> extraInfo)
        {
            this.extraInfo = requireNonNull(extraInfo, "extraInfo is null");
            return this;
        }

        public Builder setHidden(boolean hidden)
        {
            this.hidden = hidden;
            return this;
        }

        public Builder setProperties(Map<String, Object> properties)
        {
            this.properties = requireNonNull(properties, "properties is null");
            return this;
        }

        public ColumnMetadata build()
        {
            return new ColumnMetadata(
                    name,
                    type,
                    defaultValue,
                    nullable,
                    comment.orElse(null),
                    extraInfo.orElse(null),
                    hidden,
                    properties);
        }
    }
}
