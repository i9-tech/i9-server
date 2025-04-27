package school.sptech.controller.itemCarrinho;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.itemCarrinho.dto.AdicionarPratoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.AdicionarProdutoItemCarrinhoDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoListagemDto;
import school.sptech.controller.itemCarrinho.dto.ItemCarrinhoMapper;
import school.sptech.entity.prato.Prato;
import school.sptech.service.itemCarrinho.ItemCarrinhoService;

import java.util.List;

@RestController
@RequestMapping("/itens-carrinho")
public class ItemCarrinhoController {

    private final ItemCarrinhoService itemCarrinhoService;

    public ItemCarrinhoController(ItemCarrinhoService itemCarrinhoService) {
        this.itemCarrinhoService = itemCarrinhoService;
    }

    @PostMapping("/prato/{idFuncionario}")
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarPrato(
            @RequestBody AdicionarPratoItemCarrinhoDto request,
            @PathVariable Integer idFuncionario
            ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toPratoResponseDto(
                                itemCarrinhoService.adicionarPrato(ItemCarrinhoMapper.toEntity(request), idFuncionario)));
    }

    @PostMapping("/produto/{idFuncionario}")
    public ResponseEntity<ItemCarrinhoListagemDto> adicionarProduto(
            @RequestBody AdicionarProdutoItemCarrinhoDto request,
            @PathVariable Integer idFuncionario
    ) {
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toProdutoResponseDto(
                                itemCarrinhoService.adicionarProduto(
                                        ItemCarrinhoMapper.toEntity(request), idFuncionario)));
    }

    @GetMapping("/{venda}/{idFuncionario}")
    public ResponseEntity<List<ItemCarrinhoListagemDto>> listarItens(
            @PathVariable String venda,
            @PathVariable Integer idFuncionario
    ){
        return ResponseEntity
                .ok(ItemCarrinhoMapper.
                        toResponseDtoList(
                                itemCarrinhoService.listarItens(venda, idFuncionario)));
    }

    @DeleteMapping("/{id}/{venda}")
    public ResponseEntity<Void> removerItem(
            @PathVariable Integer id,
            @PathVariable String venda
    ) {
        itemCarrinhoService.removerItem(id, venda);
        return ResponseEntity.ok().build();
    }
}
