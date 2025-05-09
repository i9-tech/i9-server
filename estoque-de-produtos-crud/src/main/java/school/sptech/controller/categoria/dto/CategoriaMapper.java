package school.sptech.controller.categoria.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.categoria.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaMapper {

    @Operation(summary = "Transforma um DTO de cadastro de categoria em uma entidade de categoria.",
            description = "Converte os dados de um DTO de cadastro de categoria em uma entidade de categoria, para persistência no banco de dados.")
    public static Categoria transformarEmEntidade(CategoriaCadastroDto categoriaEnviadaDto) {
        Categoria entidadeCategoria = new Categoria();
        entidadeCategoria.setNome(categoriaEnviadaDto.getNome());
        return entidadeCategoria;
    }

    @Operation(summary = "Transforma um DTO de atualização de categoria em uma entidade de categoria.",
            description = "Converte os dados de um DTO de atualização de categoria em uma entidade de categoria, para atualizar os dados existentes no banco de dados.")
    public static Categoria transformarEmEntidade(CategoriaAtualizarDto categoriaEnviadaDto) {
        Categoria entidadeCategoriaParaAtualizar = new Categoria();
        entidadeCategoriaParaAtualizar.setNome(categoriaEnviadaDto.getNome());
        return entidadeCategoriaParaAtualizar;
    }

    @Operation(summary = "Transforma uma entidade de categoria em um DTO de resposta.",
            description = "Converte os dados de uma entidade de categoria em um DTO de resposta, para ser enviado na resposta da API.")
    public static CategoriaListagemDto transformarEmRespostaDto(Categoria entidadeCategoriaResposta) {
        CategoriaListagemDto entidadeCategoriaParaResposta = new CategoriaListagemDto();
        entidadeCategoriaParaResposta.setId(entidadeCategoriaResposta.getId());
        entidadeCategoriaParaResposta.setNome(entidadeCategoriaResposta.getNome());
        entidadeCategoriaParaResposta.setFuncionario(entidadeCategoriaResposta.getFuncionario());
        return entidadeCategoriaParaResposta;
    }

    @Operation(summary = "Transforma uma lista de entidades de categoria em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de categoria em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
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
