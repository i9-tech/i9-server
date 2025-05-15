package school.sptech.controller.venda;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.venda.Venda;
import school.sptech.service.venda.VendaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    public ResponseEntity<VendaResponseDto> criarVenda(@RequestBody @Valid VendaRequestDto dto) {
        Venda venda = vendaService.criarVenda(dto);
        VendaResponseDto response = vendaService.buscarVendaPorId(venda.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVenda(@PathVariable Integer id) {
        vendaService.excluirVenda(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/lucro-total")
    public Double lucroTotal(@RequestParam Integer idFuncionario) {
        LocalDate dataAtual = LocalDate.now();
        return vendaService.calcularLucroTotal(idFuncionario, dataAtual);
    }

}
