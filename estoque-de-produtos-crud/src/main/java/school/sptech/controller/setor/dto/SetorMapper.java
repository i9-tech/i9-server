package school.sptech.controller.setor.dto;

import school.sptech.entity.setor.Setor;

public class SetorMapper {
    public static Setor transformarEmRespostaDto(SetorCadastroDto setorEnviadoDto) {
        Setor entidadeSetor = new Setor();
        entidadeSetor.setNome(setorEnviadoDto.getNome());
        return entidadeSetor;
    }

    public static Setor transformarEmEntidade(SetorAtualizarDto setorEnviadoDto) {
        Setor entidadeSetorParaAtualizar = new Setor();
        entidadeSetorParaAtualizar.setNome(setorEnviadoDto.getNome());
        return entidadeSetorParaAtualizar;
    }

    public static Setor transformarEmRespostaDto(SetorListagemDto entidadeSetorResposta) {
        Setor entidadeSetorParaResposta = new Setor();
        entidadeSetorParaResposta.setId(entidadeSetorResposta.getId());
        entidadeSetorParaResposta.setNome(entidadeSetorResposta.getNome());
        return entidadeSetorParaResposta;
    }
}
