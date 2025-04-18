package school.sptech.controller.categoria.dto;

import school.sptech.entity.categoria.Categoria;

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

    public static Categoria transformarEmRespostaDto(CategoriaListagemDto entidadeCategoriaResposta) {
        Categoria entidadeCategoriaParaResposta = new Categoria();
        entidadeCategoriaParaResposta.setId(entidadeCategoriaResposta.getId());
        entidadeCategoriaParaResposta.setNome(entidadeCategoriaResposta.getNome());
        return entidadeCategoriaParaResposta;
    }
}
