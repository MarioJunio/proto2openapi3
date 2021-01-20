import br.com.mj.lib.InfoPlus;
import br.com.mj.lib.SchemaConverter;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class SchemaConverterTest {

    private final InfoPlus infoPlus = InfoPlus.builder()
            .info(new Info().title("Teste gRPC").description("Somente para testes").version("1.44.0"))
            .serversUrl(Arrays.asList("https://api.itau.com.br/demo_nova_esteira/v1"))
            .tagsName(Arrays.asList("microsservicos"))
            .build();

    @Test
    public void generateJsonTest() throws IOException {
        FileOutputStream output = new FileOutputStream("calculator-openapi.json");

        SchemaConverter converter = new SchemaConverter(SchemaConverter.EXPORT_TYPE.JSON);
        converter
                .addSource("/Users/mariomartins/serverless-examples/grpc-first-example/src/main/proto")
                .addProto("calculator.proto")
                .convert(output, infoPlus);
    }

    @Test
    public void generateYamlTest() throws IOException {
        FileOutputStream output = new FileOutputStream("calculator-openapi.yml");

        SchemaConverter converter = new SchemaConverter(SchemaConverter.EXPORT_TYPE.YAML);
        converter
                .addSource("/Users/mariomartins/serverless-examples/grpc-first-example/src/main/proto")
                .addProto("calculator.proto")
                .convert(output, infoPlus);
    }
}
