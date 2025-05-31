package school.sptech.controller.venda;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.controller.categoria.dto.CategoriaListagemDto;
import school.sptech.controller.categoria.dto.CategoriaMapper;
import school.sptech.controller.categoria.dto.RespostaCategoriaDashDto;
import school.sptech.controller.prato.dto.PratoMapper;
import school.sptech.controller.prato.dto.RespostaPratoDashDto;
import school.sptech.controller.prato.dto.RespostaPratoDto;
import school.sptech.controller.produto.dto.ProdutoListagemDto;
import school.sptech.controller.produto.dto.ProdutoMapper;
import school.sptech.controller.produto.dto.RespostaProdutoDashDto;
import school.sptech.controller.setor.dto.RespostaSetorDashDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.venda.Venda;
import school.sptech.service.venda.VendaService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar nova venda", description = "Cadastra uma nova venda na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "venda cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
            }
            """))
            )
    })
    public ResponseEntity<VendaResponseDto> criarVenda(
            @Parameter(description = "Dados da venda para cadastro.", required = true)
            @RequestBody @Valid VendaRequestDto dto) {
        Venda venda = vendaService.criarVenda(dto);
        VendaResponseDto response = vendaService.buscarVendaPorId(venda.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Remover venda existente em uma empresa específica", description = "Remove uma venda de determinada empresa da base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venda removida com sucesso.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "A venda não foi encontrado."
            }
            """)))
    })
    public ResponseEntity<Void> excluirVenda(
            @Parameter(description = "ID da para busca.", required = true)
            @PathVariable Integer id
    ) {
        vendaService.excluirVenda(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lucro-total")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar Lucro total", description = "Retorna lucro total presente na base de dados a partir de seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lucro total calculado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Lucro total não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "O Lucro total não foi encontrado."
            }
            """))
            )
    })
    public Double lucroTotal(
            @Parameter(description = "ID do funcionário para busca.", required = true)
            @RequestParam Integer idFuncionario) {
        LocalDate dataAtual = LocalDate.now();
        return vendaService.calcularLucroTotal(idFuncionario, dataAtual);
    }


    @GetMapping("/valor-total-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Double> valorTotalPorEmpresaHoje(@PathVariable Integer empresaId) {
        Double valorTotal = vendaService.valorTotalPorEmpresaHoje(empresaId);
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/valor-liquido-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Double> lucroLiquidoHojePorEmpresa(@PathVariable Integer empresaId) {
        Double lucro = vendaService.lucroLiquidoPorEmpresaHoje(empresaId);
        return ResponseEntity.ok(lucro);
    }

    @GetMapping("/valor-total-setor-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Map<String, Double>> valorTotalPorSetorHoje(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(vendaService.valorTotalPorSetorHoje(empresaId));
    }

    @GetMapping("/valor-total-categoria-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Map<String, Double>> valorTotalPorCategoriaHoje(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(vendaService.valorTotalPorCategoriaHoje(empresaId));
    }

    @GetMapping("/quantidade-vendas/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Integer> quantidadeVendasHoje(@PathVariable Integer empresaId) {
        Integer quantidade = vendaService.quantidadeVendasPorEmpresaHoje(empresaId);
        return ResponseEntity.ok(quantidade);
    }

    @GetMapping("/quantidade-minima/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<Produto>> listarAbaixoMinimo(@PathVariable Integer empresaId) {
        List<Produto> produtos = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);
        return produtos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(produtos);
    }

    @GetMapping("/itens-vendidos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public List<String> listarResumoItensVendidosHoje(@PathVariable Integer empresaId) {
        return vendaService.listarResumoItensVendidosPorEmpresaEData(empresaId);
    }

    @GetMapping("/top-pratos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<RespostaPratoDashDto>> top7Pratos(@PathVariable Integer empresaId){
        return ResponseEntity.ok(PratoMapper.toResponseDtoListObject(vendaService.top7Pratos(empresaId)));
    }

    @GetMapping("/top-produtos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<RespostaProdutoDashDto>> top7Produtos(@PathVariable Integer empresaId){
        return ResponseEntity.ok(ProdutoMapper.toResponseDtoListObject(vendaService.top7Produtos(empresaId)));
    }

    @GetMapping("/top-categorias/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<RespostaCategoriaDashDto>> top5Categorias(@PathVariable Integer empresaId){
        return ResponseEntity.ok(CategoriaMapper.transformarEmRespostaListaObjetoDto(vendaService.top5Categorias(empresaId)));
    }

    @GetMapping("/ranking-setores/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<RespostaSetorDashDto>> rankingSetoresMaisVendidos(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(SetorMapper.transformarEmRespostaListaObjetoDto(vendaService.obterRankingSetoresMaisVendidos(empresaId)));
    }

}

