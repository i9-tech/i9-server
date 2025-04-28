package school.sptech.controller.setor.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.setor.Setor;

import java.util.ArrayList;
import java.util.List;

public class SetorMapper {

    @Operation(summary = "Transforma um DTO de cadastro de setor em entidade de setor.",
            description = "Converte os dados de um DTO de cadastro de empresa em uma entidade de setor para persistência no banco de dados.")
    public static Setor transformarEmEntidade(SetorCadastroDto setorEnviadoDto) {
        Setor entidadeSetor = new Setor();
        entidadeSetor.setNome(setorEnviadoDto.getNome());
        return entidadeSetor;
    }

    @Operation(summary = "Transforma um DTO de atualização de setor em entidade de empresa.",
            description = "Converte os dados de um DTO de atualização de setor em uma entidade de setor para atualizar os dados existentes no banco de dados.")
    public static Setor transformarEmEntidade(SetorAtualizarDto setorEnviadoDto) {
        Setor entidadeSetorParaAtualizar = new Setor();
        entidadeSetorParaAtualizar.setNome(setorEnviadoDto.getNome());
        return entidadeSetorParaAtualizar;
    }

    @Operation(summary = "Transforma uma entidade de setor em DTO de resposta.",
            description = "Converte os dados de uma entidade de setor em um DTO de resposta para ser enviado na resposta da API.")
    public static SetorListagemDto transformarEmRespostaDto(Setor entidadeSetorResposta) {
        SetorListagemDto dtoSetorParaResposta = new SetorListagemDto();
        dtoSetorParaResposta.setId(entidadeSetorResposta.getId());
        dtoSetorParaResposta.setNome(entidadeSetorResposta.getNome());
        dtoSetorParaResposta.setFuncionario(entidadeSetorResposta.getFuncionario());
        return dtoSetorParaResposta;
    }

    @Operation(summary = "Transforma uma lista de entidades de setor em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de setor em uma lista de DTOs de resposta para ser enviado na resposta da API.")
    public static List<SetorListagemDto> transformarEmListaRespostaDto(List<Setor> entidadesSetorResposta) {
        List<SetorListagemDto> respostasEmDto = new ArrayList<>();

        for (int i = 0; i < entidadesSetorResposta.size(); i++) {
        SetorListagemDto respostaDtoSetorParaResposta = new SetorListagemDto();
        respostaDtoSetorParaResposta.setId(entidadesSetorResposta.get(i).getId());
        respostaDtoSetorParaResposta.setNome(entidadesSetorResposta.get(i).getNome());
        respostaDtoSetorParaResposta.setFuncionario(entidadesSetorResposta.get(i).getFuncionario());

        respostasEmDto.add(respostaDtoSetorParaResposta);
        }

        return respostasEmDto;
    }
}
