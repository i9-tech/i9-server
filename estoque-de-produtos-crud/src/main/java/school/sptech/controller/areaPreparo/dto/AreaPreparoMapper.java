package school.sptech.controller.areaPreparo.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.areaPreparo.AreaPreparo;

import java.util.ArrayList;
import java.util.List;

public class AreaPreparoMapper {

    @Operation(summary = "Transforma um DTO de cadastro de área de preparo em uma entidade de área de preparo.",
            description = "Converte os dados de um DTO de cadastro de área de preparo em uma entidade de área de preparo, para persistência no banco de dados.")
    public static AreaPreparo transformarEmEntidade(AreaPreparoCadastroDto areaPreparoEnviadaDto) {
        AreaPreparo entidadeAreaPreparo = new AreaPreparo();
        entidadeAreaPreparo.setNome(areaPreparoEnviadaDto.getNome());
        return entidadeAreaPreparo;
    }

    @Operation(summary = "Transforma um DTO de atualização de área de preparo em uma entidade de área de preparo.",
            description = "Converte os dados de um DTO de atualização de área de preparo em uma entidade de área de preparo, para atualizar os dados existentes no banco de dados.")
    public static AreaPreparo transformarEmEntidade(AreaPreparoAtualizarDto areaPreparoEnviadaDto) {
        AreaPreparo entidadeAreaPreparoParaAtualizar = new AreaPreparo();
        entidadeAreaPreparoParaAtualizar.setNome(areaPreparoEnviadaDto.getNome());
        return entidadeAreaPreparoParaAtualizar;
    }

    @Operation(summary = "Transforma uma entidade de área de preparo em um DTO de resposta.",
            description = "Converte os dados de uma entidade de área de preparo em um DTO de resposta, para ser enviado na resposta da API.")
    public static AreaPreparoListagemDto transformarEmRespostaDto(AreaPreparo entidadeAreaPreparoResposta) {
        AreaPreparoListagemDto dtoAreaPreparoParaResposta = new AreaPreparoListagemDto();
        dtoAreaPreparoParaResposta.setId(entidadeAreaPreparoResposta.getId());
        dtoAreaPreparoParaResposta.setNome(entidadeAreaPreparoResposta.getNome());
        dtoAreaPreparoParaResposta.setFuncionario(entidadeAreaPreparoResposta.getFuncionario());
        return dtoAreaPreparoParaResposta;
    }

    @Operation(summary = "Transforma uma lista de entidades de área de preparo em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de área de preparo em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<AreaPreparoListagemDto> transformarEmRespostaListaDto(List<AreaPreparo> entidadeAreaPreparoResposta) {

        List<AreaPreparoListagemDto> respostasDto = new ArrayList<>();

        for (int i = 0; i < entidadeAreaPreparoResposta.size(); i++) {
            AreaPreparoListagemDto dtoAreaPreparoParaResposta = new AreaPreparoListagemDto();
            dtoAreaPreparoParaResposta.setId(entidadeAreaPreparoResposta.get(i).getId());
            dtoAreaPreparoParaResposta.setNome(entidadeAreaPreparoResposta.get(i).getNome());
            dtoAreaPreparoParaResposta.setFuncionario(entidadeAreaPreparoResposta.get(i).getFuncionario());
            respostasDto.add(dtoAreaPreparoParaResposta);
        }
        return respostasDto;
    }
}
