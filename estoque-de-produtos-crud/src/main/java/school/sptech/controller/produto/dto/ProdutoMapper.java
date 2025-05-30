package school.sptech.controller.produto.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.controller.prato.dto.PratoMapper;
import school.sptech.controller.prato.dto.RespostaPratoDashDto;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;

import java.util.List;

public class ProdutoMapper {

    public ProdutoMapper(){
    }

    @Operation(summary = "Transforma um DTO de cadastro de produto em uma entidade de produto.",
            description = "Converte os dados de um DTO de cadastro de produto em uma entidade de produto, para persistência no banco de dados.")
    //convertendo dto em entity
    public static Produto toEntity(ProdutoCadastroDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(requestDto.getNome());
        produto.setCodigo(requestDto.getCodigo());
        produto.setImagem(requestDto.getImagem());
        produto.setQuantidade(requestDto.getQuantidade());
        produto.setValorCompra(requestDto.getValorCompra());
        produto.setValorUnitario(requestDto.getValorUnitario());
        produto.setQuantidadeMin(requestDto.getQuantidadeMin());
        produto.setQuantidadeMax(requestDto.getQuantidadeMax());
        produto.setDescricao(requestDto.getDescricao());
        produto.setCategoria(requestDto.getCategoria());
        produto.setSetor(requestDto.getSetor());
        produto.setDataRegistro(requestDto.getDataRegistro());
        return produto;
    }


    @Operation(summary = "Transforma um DTO de atualização de produto em uma entidade de produto.",
            description = "Converte os dados de um DTO de atualização de produto em uma entidade de produto, para atualizar os dados existentes no banco de dados.")
    //convertendo dto em entity
    public static void atualizarProdutoComDto(Produto produto, ProdutoEdicaoDto dto) {
        if (produto == null || dto == null) return;

        produto.setNome(dto.getNome());
        produto.setImagem(dto.getImagem());
        produto.setQuantidade(dto.getQuantidade());
        produto.setValorCompra(dto.getValorCompra());
        produto.setValorUnitario(dto.getValorUnitario());
        produto.setQuantidadeMin(dto.getQuantidadeMin());
        produto.setQuantidadeMax(dto.getQuantidadeMax());
        produto.setDescricao(dto.getDescricao());
        produto.setCategoria(dto.getCategoria());
        produto.setSetor(dto.getSetor());
        produto.setDataRegistro(dto.getDataRegistro());
    }

    @Operation(summary = "Transforma uma entidade de produto em um DTO de resposta.",
            description = "Converte os dados de uma entidade de produto em um DTO de resposta, para ser enviado na resposta da API.")
    //convertendo entity em dto
    public static ProdutoListagemDto toDto(Produto produto) {
        if (produto == null) {
            return null;
        }

        return new ProdutoListagemDto(
                produto.getId(),
                produto.getCodigo(),
                produto.getImagem(),
                produto.getNome(),
                produto.getQuantidade(),
                produto.getValorCompra(),
                produto.getValorUnitario(),
                produto.getQuantidadeMin(),
                produto.getQuantidadeMax(),
                produto.getDescricao(),
                produto.getDataRegistro(),
                produto.getSetor(),
                produto.getCategoria(),
                produto.getFuncionario()
        );
    }

    @Operation(summary = "Transforma uma lista de entidades de produtos em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de produtos em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<ProdutoListagemDto> toResponseDtoList(List<Produto> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(ProdutoMapper::toDto)
                .toList();
    }

    public static RespostaProdutoDashDto toResponseDto(Object[] row) {
        if (row == null || row.length < 3) return null;

        String nome = (String) row[0];
        Long quantidadeLong = (Long) row[1];
        Double total = (Double) row[2];

        Integer quantidade = quantidadeLong != null ? quantidadeLong.intValue() : 0;

        return new RespostaProdutoDashDto(nome, quantidade, total != null ? total : 0.0);
    }

    public static List<RespostaProdutoDashDto> toResponseDtoListObject(List<Object[]> rows) {
        if (rows == null) return null;

        return rows.stream()
                .map(ProdutoMapper::toResponseDto)
                .toList();
    }
}
