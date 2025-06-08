package school.sptech.controller.setor.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.setor.Setor;

import java.util.ArrayList;
import java.util.List;

public class SetorMapper {

    @Operation(summary = "Transforma um DTO de cadastro de setor em uma entidade de setor.",
            description = "Converte os dados de um DTO de cadastro de empresa em uma entidade de setor, para persistência no banco de dados.")
    public static Setor transformarEmEntidade(SetorCadastroDto setorEnviadoDto) {
        Setor entidadeSetor = new Setor();
        entidadeSetor.setNome(setorEnviadoDto.getNome());
        entidadeSetor.setImagem(setorEnviadoDto.getImagem());
        return entidadeSetor;
    }

    @Operation(summary = "Transforma um DTO de atualização de setor em uma entidade de empresa.",
            description = "Converte os dados de um DTO de atualização de setor em uma entidade de setor, para atualizar os dados existentes no banco de dados.")
    public static Setor transformarEmEntidade(SetorAtualizarDto setorEnviadoDto) {
        Setor entidadeSetorParaAtualizar = new Setor();
        entidadeSetorParaAtualizar.setNome(setorEnviadoDto.getNome());
        entidadeSetorParaAtualizar.setImagem(setorEnviadoDto.getImagem());
        return entidadeSetorParaAtualizar;
    }

    @Operation(summary = "Transforma uma entidade de setor em um DTO de resposta.",
            description = "Converte os dados de uma entidade de setor em um DTO de resposta, para ser enviado na resposta da API.")
    public static SetorListagemDto transformarEmRespostaDto(Setor entidadeSetorResposta) {
        SetorListagemDto dtoSetorParaResposta = new SetorListagemDto();
        dtoSetorParaResposta.setId(entidadeSetorResposta.getId());
        dtoSetorParaResposta.setNome(entidadeSetorResposta.getNome());
        dtoSetorParaResposta.setFuncionario(entidadeSetorResposta.getFuncionario());
        dtoSetorParaResposta.setImagem(entidadeSetorResposta.getImagem());
        return dtoSetorParaResposta;
    }

    @Operation(summary = "Transforma uma lista de entidades de setor em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de setor em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<SetorListagemDto> transformarEmListaRespostaDto(List<Setor> entidadesSetorResposta) {
        List<SetorListagemDto> respostasEmDto = new ArrayList<>();

        for (int i = 0; i < entidadesSetorResposta.size(); i++) {
        SetorListagemDto respostaDtoSetorParaResposta = new SetorListagemDto();
        respostaDtoSetorParaResposta.setId(entidadesSetorResposta.get(i).getId());
        respostaDtoSetorParaResposta.setNome(entidadesSetorResposta.get(i).getNome());
        respostaDtoSetorParaResposta.setFuncionario(entidadesSetorResposta.get(i).getFuncionario());
        respostaDtoSetorParaResposta.setImagem(entidadesSetorResposta.get(i).getImagem());

        respostasEmDto.add(respostaDtoSetorParaResposta);
        }

        return respostasEmDto;
    }

    public static RespostaSetorDashDto transformarEmRespostaDto(Object[] row) {
        if (row == null || row.length < 3) return null;

        String nome = (String) row[0];
        Long quantidade = row[1] != null ? ((Number) row[1]).longValue() : 0L;
        Double total = row[2] != null ? (Double) row[2] : 0.0;

        return new RespostaSetorDashDto(nome, quantidade, total);
    }

    public static List<RespostaSetorDashDto> transformarEmRespostaListaObjetoDto(List<Object[]> rows) {
        if (rows == null) return null;

        return rows.stream()
                .map(SetorMapper::transformarEmRespostaDto)
                .toList();
    }
}
