import br.com.mj.lib.SchemaConverter;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class SchemaConverterTest {

    @Test
    public void generateJsonTest() throws IOException {
        FileOutputStream output = new FileOutputStream("calculator-openapi.json");

        SchemaConverter converter = new SchemaConverter(SchemaConverter.EXPORT_TYPE.JSON);
        converter
                .addSource("/Users/mariomartins/serverless-examples/grpc-first-example/src/main/proto")
                .addProto("calculator.proto")
                .convert(output);
    }

    @Test
    public void generateYamlTest() throws IOException {
        FileOutputStream output = new FileOutputStream("calculator-openapi.yml");

        SchemaConverter converter = new SchemaConverter(SchemaConverter.EXPORT_TYPE.YAML);
        converter
                .addSource("/Users/mariomartins/serverless-examples/grpc-first-example/src/main/proto")
                .addProto("calculator.proto")
                .convert(output);
    }
}
