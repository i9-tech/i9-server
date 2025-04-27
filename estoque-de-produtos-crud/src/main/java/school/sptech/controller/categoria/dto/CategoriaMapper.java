package school.sptech.controller.categoria.dto;

import school.sptech.entity.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper {
    public static Categoria transformarEmEntidade(CategoriaCadastroDto categoriaEnviadaDto) {
        Categoria entidadeCategoria = new Categoria();
        entidadeCategoria.setNome(categoriaEnviadaDto.getNome());
        return entidadeCategoria;
    }

    public static Categoria transformarEmEntidade(CategoriaAtualizarDto categoriaEnviadaDto) {
        Categoria entidadeCategoriaParaAtualizar = new Categoria();
        entidadeCategoriaParaAtualizar.setNome(categoriaEnviadaDto.getNome());
        return entidadeCategoriaParaAtualizar;
    }

    public static CategoriaListagemDto transformarEmRespostaDto(Categoria entidadeCategoriaResposta) {
        CategoriaListagemDto entidadeCategoriaParaResposta = new CategoriaListagemDto();
        entidadeCategoriaParaResposta.setId(entidadeCategoriaResposta.getId());
        entidadeCategoriaParaResposta.setNome(entidadeCategoriaResposta.getNome());
        entidadeCategoriaParaResposta.setFuncionario(entidadeCategoriaResposta.getFuncionario());
        return entidadeCategoriaParaResposta;
    }

    public static List<CategoriaListagemDto> transformarEmRespostaListaDto(List<Categoria> entidadeCategoriaResposta) {

        List<CategoriaListagemDto> respostasDto = new ArrayList<>();

        for (int i = 0; i < entidadeCategoriaResposta.size(); i++) {
        CategoriaListagemDto dtoCategoriaParaResposta = new CategoriaListagemDto();
        dtoCategoriaParaResposta.setId(entidadeCategoriaResposta.get(i).getId());
        dtoCategoriaParaResposta.setNome(entidadeCategoriaResposta.get(i).getNome());
        dtoCategoriaParaResposta.setFuncionario(entidadeCategoriaResposta.get(i).getFuncionario());
        respostasDto.add(dtoCategoriaParaResposta);
        }

        return respostasDto;
    }
}
