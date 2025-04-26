package school.sptech.controller.setor.dto;

import school.sptech.entity.setor.Setor;

import java.util.ArrayList;
import java.util.List;

public class SetorMapper {
    public static Setor transformarEmEntidade(SetorCadastroDto setorEnviadoDto) {
        Setor entidadeSetor = new Setor();
        entidadeSetor.setNome(setorEnviadoDto.getNome());
        return entidadeSetor;
    }

    public static Setor transformarEmEntidade(SetorAtualizarDto setorEnviadoDto) {
        Setor entidadeSetorParaAtualizar = new Setor();
        entidadeSetorParaAtualizar.setNome(setorEnviadoDto.getNome());
        return entidadeSetorParaAtualizar;
    }

    public static SetorListagemDto transformarEmRespostaDto(Setor entidadeSetorResposta) {
        SetorListagemDto dtoSetorParaResposta = new SetorListagemDto();
        dtoSetorParaResposta.setId(entidadeSetorResposta.getId());
        dtoSetorParaResposta.setNome(entidadeSetorResposta.getNome());
        dtoSetorParaResposta.setFuncionario(entidadeSetorResposta.getFuncionario());
        return dtoSetorParaResposta;
    }

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
