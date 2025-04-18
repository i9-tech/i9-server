package school.sptech.controller.itemCarrinho;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.itemCarrinho.dto.AdicionarPratoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.AdicionarProdutoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoListagemDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoMapper;
import school.sptech.service.itemCarrinho.ItemCarrinhoService;

import java.util.List;

@RestController
@RequestMapping("/itens-carrinho")
public class ItemCarrinhoController {

    private final ItemCarrinhoService itemCarrinhoService;

    public ItemCarrinhoController(ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoService = itemCarrinhoService;
    }

    @PostMapping("/prato")
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarPrato(
            @RequestBody AdicionarPratoItemCarrinhoDto request
            ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toPratoResponseDto(
                                itemCarrinhoService.adicionarPrato(
                                        ItemCarrinhoMapper.toEntity(request))));
    }

    @PostMapping("/produto")
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarProduto(
            @RequestBody AdicionarProdutoItemCarrinhoDto request
    ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toProdutoResponseDto(
                                itemCarrinhoService.adicionarProduto(
                                        ItemCarrinhoMapper.toEntity(request))));
    }

    @GetMapping
    public ResponseEntity<List<ItemCarrinhoListagemDto>> listarItens(){
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toResponseDtoList(
                                itemCarrinhoService.listarItens()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Integer id
    ) {
        itemCarrinhoService.removerItem(id);
        return ResponseEntity.ok().build();
    }
}
