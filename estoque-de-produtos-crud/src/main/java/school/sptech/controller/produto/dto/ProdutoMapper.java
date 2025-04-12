package school.sptech.controller.produto.dto;

import school.sptech.entity.produto.Produto;

public class ProdutoMapper {

    public ProdutoMapper(){
    }
    //convertendo dto em entity
    public static Produto toEntity(ProdutoRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(requestDto.getNome());
        produto.setCodigo(requestDto.getCodigo());
        produto.setQuantidade(requestDto.getQuantidade());
        produto.setDataVencimento(requestDto.getDataVencimento());
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


    //convertendo entity em dto
    public static ProdutoResponseDto toDto(Produto produto) {
        if (produto == null) {
            return null;
        }

        return new ProdutoResponseDto(
                produto.getId(),
                produto.getNome(),
                produto.getCodigo(),
                produto.getQuantidade(),
                produto.getDataVencimento(),
                produto.getValorCompra(),
                produto.getValorUnitario(),
                produto.getQuantidadeMin(),
                produto.getQuantidadeMax(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getSetor(),
                produto.getDataRegistro(),
                produto.getFuncionario()
        );
    }
}
