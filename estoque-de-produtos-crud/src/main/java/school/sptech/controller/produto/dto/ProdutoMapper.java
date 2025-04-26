package school.sptech.controller.produto.dto;

import school.sptech.entity.produto.Produto;

public class ProdutoMapper {

    public ProdutoMapper(){
    }
    //convertendo dto em entity
    public static Produto toEntity(ProdutoCadastroDto requestDto) {
        if (requestDto == null) {
            return null;
        }

        Produto produto = new Produto();
        produto.setNome(requestDto.getNome());
        produto.setCodigo(requestDto.getCodigo());
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

    //convertendo dto em entity
    public static void atualizarProdutoComDto(Produto produto, ProdutoEdicaoDto dto) {
        if (produto == null || dto == null) return;

        produto.setNome(dto.getNome());
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


    //convertendo entity em dto
    public static ProdutoListagemDto toDto(Produto produto) {
        if (produto == null) {
            return null;
        }

        return new ProdutoListagemDto(
                produto.getId(),
                produto.getNome(),
                produto.getCodigo(),
                produto.getQuantidade(),
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
