package br.com.mj.lib;

import io.swagger.v3.oas.models.info.Info;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class InfoPlus {

    private Info info;

    private List<String> serversUrl;

    private List<String> tagsName;

}
