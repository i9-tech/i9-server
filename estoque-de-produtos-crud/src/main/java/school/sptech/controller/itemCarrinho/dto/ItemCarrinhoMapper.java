package school.sptech.controller.itemCarrinho.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.prato.Prato;

import java.util.List;

public class ItemCarrinhoMapper {

    @Operation(summary = "Transforma um DTO de prato em entidade de prato no carrinho.",
            description = "Converte os dados de um DTO de prato em uma entidade de prato, para adição do item ao carrinho e persistência no banco de dados.")
    public static ItemCarrinho toEntity(AdicionarPratoItemCarrinhoDto dto) {
        ItemCarrinho entity = new ItemCarrinho();

        entity.setId(entity.getId());
        entity.setVenda(dto.getVenda());
        entity.setPrato(dto.getPrato());
        entity.setProduto(null);
        entity.setObservacao(dto.getObservacao());
        entity.setFuncionario(dto.getFuncionario());
        entity.setValorUnitario(entity.getPrato().getValorVenda());

        return entity;
    }

    @Operation(summary = "Transforma um DTO de produto em entidade de produto no carrinho.",
            description = "Converte os dados de um DTO de produto em uma entidade de produto, para a adição do item ao carrinho e a persistência no banco de dados.")
    public static ItemCarrinho toEntity(AdicionarProdutoItemCarrinhoDto dto) {
        ItemCarrinho entity = new ItemCarrinho();

        entity.setId(entity.getId());
        entity.setVenda(dto.getVenda());
        entity.setProduto(dto.getProduto());
        entity.setPrato(null);
        entity.setObservacao(null);
        entity.setValorUnitario(entity.getProduto().getValorUnitario());

        return entity;
    }

    @Operation(summary = "Transforma uma entidade de prato no carrinho em um DTO de resposta.",
            description = "Converte os dados de uma entidade de prato no carrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static ItemCarrinhoListagemDto toPratoResponseDto(ItemCarrinho entity) {
        ItemCarrinhoListagemDto response = new ItemCarrinhoListagemDto();

        response.setId(entity.getId());
        response.setPrato(entity.getPrato());
        response.setObservacao(entity.getObservacao());
        response.setValorUnitario(entity.getPrato().getValorVenda());
        response.setFuncionario(entity.getFuncionario());

        return response;
    }

    @Operation(summary = "Transforma uma entidade de produto no carrinho em um DTO de resposta.",
            description = "Converte os dados de uma entidade de produto no carrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static ItemCarrinhoListagemDto toProdutoResponseDto(ItemCarrinho entity) {
        ItemCarrinhoListagemDto response = new ItemCarrinhoListagemDto();

        response.setId(entity.getId());
        response.setProduto(entity.getProduto());
        response.setValorUnitario(entity.getProduto().getValorUnitario());
        response.setFuncionario(entity.getFuncionario());

        return response;
    }

    @Operation(summary = "Transforma uma entidade de itemCarrinho em DTO de resposta.",
            description = "Converte os dados de uma entidade de itemCarrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static ItemCarrinhoListagemDto toResponseDto(ItemCarrinho entity) {
        ItemCarrinhoListagemDto response = new ItemCarrinhoListagemDto();

        response.setId(entity.getId());
        response.setPrato(entity.getPrato());
        response.setProduto(entity.getProduto());
        response.setObservacao(entity.getObservacao());
        response.setValorUnitario(entity.getValorUnitario());
        response.setFuncionario(entity.getFuncionario());

        return response;
    }

    @Operation(summary = "Transforma uma entidade de lista de pratos no carrinho em um DTO de resposta.",
            description = "Converte os dados de uma entidade de pratos no carrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static List<ItemCarrinhoListagemDto> toPratoResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toPratoResponseDto).toList();
    }

    @Operation(summary = "Transforma uma entidade de lista de produtos no carrinho em um DTO de resposta.",
            description = "Converte os dados de uma entidade de produtos no carrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static List<ItemCarrinhoListagemDto> toProdutoResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toProdutoResponseDto).toList();
    }

    @Operation(summary = "Transforma uma entidade de lista de itens no carrinho em um DTO de resposta.",
            description = "Converte os dados de uma entidade de itens no carrinho em um DTO de resposta, para ser enviado na resposta da API.")
    public static List<ItemCarrinhoListagemDto> toResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toResponseDto).toList();
    }

}
