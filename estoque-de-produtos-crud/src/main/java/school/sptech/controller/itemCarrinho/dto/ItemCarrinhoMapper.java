package school.sptech.controller.itemCarrinho.dto;

import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.prato.Prato;

import java.util.List;

public class ItemCarrinhoMapper {

    public static ItemCarrinho toEntity(AdicionarPratoItemCarrinhoDto dto) {
        ItemCarrinho entity = new ItemCarrinho();

        entity.setId(entity.getId());
        entity.setPrato(dto.getPrato());
        entity.setProduto(null);
        entity.setObservacao(dto.getObservacao());
        entity.setValorUnitario(dto.getValorUnitario());
        entity.setFuncionario(dto.getFuncionario());

        return entity;
    }

    public static ItemCarrinho toEntity(AdicionarProdutoItemCarrinhoDto dto) {
        ItemCarrinho entity = new ItemCarrinho();

        entity.setId(entity.getId());
        entity.setProduto(dto.getProduto());
        entity.setPrato(null);
        entity.setObservacao(null);
        entity.setValorUnitario(dto.getValorUnitario());
        entity.setFuncionario(dto.getFuncionario());

        return entity;
    }

    public static ItemCarrinhoListagemDto toPratoResponseDto(ItemCarrinho entity) {
        ItemCarrinhoListagemDto response = new ItemCarrinhoListagemDto();

        response.setId(entity.getId());
        response.setPrato(entity.getPrato());
        response.setObservacao(entity.getObservacao());
        response.setValorUnitario(entity.getValorUnitario());
        response.setFuncionario(entity.getFuncionario());

        return response;
    }

    public static ItemCarrinhoListagemDto toProdutoResponseDto(ItemCarrinho entity) {
        ItemCarrinhoListagemDto response = new ItemCarrinhoListagemDto();

        response.setId(entity.getId());
        response.setProduto(entity.getProduto());
        response.setValorUnitario(entity.getValorUnitario());
        response.setFuncionario(entity.getFuncionario());

        return response;
    }

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

    public static List<ItemCarrinhoListagemDto> toPratoResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toPratoResponseDto).toList();
    }

    public static List<ItemCarrinhoListagemDto> toProdutoResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toProdutoResponseDto).toList();
    }

    public static List<ItemCarrinhoListagemDto> toResponseDtoList(List<ItemCarrinho> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream().map(ItemCarrinhoMapper::toResponseDto).toList();
    }

}
