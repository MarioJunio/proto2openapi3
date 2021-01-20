/*
 * Copyright 2018 Nordstrom, Inc.
 *
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

package br.com.mj.lib;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.squareup.wire.schema.Schema;
import com.squareup.wire.schema.SchemaLoader;
import io.swagger.v3.oas.models.OpenAPI;
import lombok.val;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * This converts a set of proto files in source directories representing a protobuf 3 schema to
 * either a OpenAPI object model or to OpenAPI yaml writen to an OutputStream or Writer.
 */
public class SchemaConverter {

    public enum EXPORT_TYPE {
        YAML, JSON
    }

    private final SchemaLoader loader = new SchemaLoader();

    private ObjectMapper mapper;
    private final FileSystem fs;

    private EXPORT_TYPE exportType = EXPORT_TYPE.YAML;

    public SchemaConverter(final EXPORT_TYPE exportType) {
        this(FileSystems.getDefault());

        this.exportType = exportType;

        mapper = createMapper();
    }

    public SchemaConverter(FileSystem fs) {
        this.fs = fs;
    }

    public SchemaConverter addSource(File file) {
        loader.addSource(file);
        return this;
    }

    public SchemaConverter addSource(Path path) {
        loader.addSource(path);
        return this;
    }

    public SchemaConverter addSource(String path) {
        return addSource(fs.getPath(path));
    }

    public SchemaConverter addProto(String proto) {
        loader.addProto(proto);
        return this;
    }

    public SchemaConverter convert(OutputStream output) throws IOException {
        return convert(new OutputStreamWriter(output));
    }

    public SchemaConverter convert(Writer writer) throws IOException {
        mapper.writeValue(writer, convert());

        return this;
    }

    public OpenAPI convert() throws IOException {
        Schema protoSchema = loader.load();
        return ModelConverter.convert(protoSchema);
    }

    private ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper(isJson() ? new JsonFactory() : new YAMLFactory());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return mapper;
    }

    private boolean isJson() {
        return exportType == EXPORT_TYPE.JSON;
    }
}
