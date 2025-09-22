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
import school.sptech.controller.venda.dto.VendaKpisRespostaDto;
import school.sptech.controller.venda.dto.VendaMapper;
import school.sptech.controller.setor.dto.RespostaSetorDashDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
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

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar venda por ID", description = "Busca uma venda pelo seu ID e retorna os dados da venda.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda encontrada e retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Venda não encontrada para o ID informado.")
    })
    public ResponseEntity<VendaResponseDto> buscarVendaPorId(@PathVariable Integer id) {
        Venda venda = vendaService.buscarVendaPorId(id);
        VendaResponseDto dto = VendaMapper.toDto(venda);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar nova venda", description = "Cadastra uma nova venda na base de dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venda cadastrada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou dados mal formatados.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
        {
          "mensagem": "Dados inválidos. Verifique os campos obrigatórios."
        }
        """))
            )
    }) public ResponseEntity<VendaResponseDto> criarVenda(
            @Parameter(description = "Dados da venda para cadastro.", required = true)
            @RequestBody @Valid VendaRequestDto dto) {

        Venda venda = VendaMapper.toEntity(dto);

        Venda vendaSalva = vendaService.criarVenda(venda);

        return ResponseEntity.status(HttpStatus.CREATED).body(VendaMapper.toDto(vendaSalva));
    }

    @PostMapping("/concluir-prato")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Concluir venda de prato",
            description = "Finaliza uma venda de prato existente com base no ID da venda fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venda concluída com sucesso. Retorna true se a venda foi finalizada corretamente."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou ID da venda não encontrado.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Venda não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Boolean> finalizarPratoVenda(
            @RequestParam Integer idVenda
    ) {
        return ResponseEntity.ok(vendaService.finalizarVendaPrato(idVenda));
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
    public ResponseEntity<Double> lucroTotal(
            @Parameter(description = "ID do funcionário para busca.", required = true)
            @RequestParam Integer idFuncionario) {

        LocalDate dataAtual = LocalDate.now();
        Double lucroTotal = vendaService.calcularLucroTotal(idFuncionario, dataAtual);

        return ResponseEntity.ok(lucroTotal);
    }

    @GetMapping("/valor-total-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Valor total diário por empresa",
            description = "Retorna o valor total das vendas realizadas em um determinado período para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valor total diário retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Double> valorTotalPorEmpresa(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        Double valorTotal = vendaService.valorTotalPorEmpresaNoPeriodo(empresaId, inicio, fim);
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/valor-liquido-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Lucro líquido diário por empresa",
            description = "Retorna o lucro líquido das vendas realizadas em um determinado período para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lucro líquido diário retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Double> lucroLiquidoPorEmpresa(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {
        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        Double lucro = vendaService.lucroLiquidoPorEmpresaNoPeriodo(empresaId, inicio, fim);
        return ResponseEntity.ok(lucro);
    }

    @GetMapping("/valor-total-setor-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Valor total diário por setor",
            description = "Retorna o valor total das vendas realizadas hoje agrupadas por setor para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores totais por setor retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Map<String, Double>> valorTotalPorSetorHoje(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(vendaService.valorTotalPorSetorHoje(empresaId));
    }

    @GetMapping("/valor-total-categoria-diario/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Valor total diário por categoria",
            description = "Retorna o valor total das vendas realizadas hoje agrupadas por categoria para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Valores totais por categoria retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Map<String, Double>> valorTotalPorCategoriaHoje(@PathVariable Integer empresaId) {
        return ResponseEntity.ok(vendaService.valorTotalPorCategoriaHoje(empresaId));
    }

    @GetMapping("/quantidade-vendas/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Quantidade de vendas hoje por empresa",
            description = "Retorna a quantidade de vendas realizadas em um determinado período para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de vendas retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<Integer> quantidadeVendas(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        Integer quantidade = vendaService.quantidadeVendasPorEmpresaNoPeriodo(empresaId, inicio, fim);
        return ResponseEntity.ok(quantidade);
    }

    @GetMapping("/quantidade-minima/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar produtos abaixo da quantidade mínima",
            description = "Retorna uma lista de produtos da empresa especificada cujo estoque está abaixo da quantidade mínima."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado abaixo da quantidade mínima."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<List<Produto>> listarAbaixoMinimo(@PathVariable Integer empresaId) {
        List<Produto> produtos = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);
        return produtos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(produtos);
    }

    @GetMapping("/itens-vendidos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar resumo de itens vendidos hoje",
            description = "Retorna uma lista resumida dos nomes dos itens vendidos hoje para a empresa especificada pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resumo dos itens vendidos retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = """
            {
              "mensagem": "Empresa não encontrada para o ID informado."
            }
            """))
            )
    })
    public ResponseEntity<List<String>> listarResumoItensVendidosHoje(@PathVariable Integer empresaId) {
        List<String> resumoItens = vendaService.listarResumoItensVendidosPorEmpresaEData(empresaId);
        return ResponseEntity.ok(resumoItens);
    }

    @GetMapping("/pratos-vendidos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Listar pratos vendidos hoje",
            description = "Retorna uma lista de vendas dos pratos vendidos hoje para a empresa informada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pratos vendidos retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<VendaResponseDto>> listarPratosVendidos(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        List<Venda> vendas = vendaService.listarPratosVendidosPorEmpresaNoPeriodo(empresaId, inicio, fim);

        List<VendaResponseDto> dtos = vendas.stream()
                .map(VendaMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/top-pratos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Top 7 pratos mais vendidos",
            description = "Retorna os 7 pratos mais vendidos da empresa informada em determinado período."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista dos top 7 pratos retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<RespostaPratoDashDto>> top7Pratos(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        return ResponseEntity.ok(PratoMapper.toResponseDtoListObject(vendaService.top7Pratos(empresaId, inicio, fim)));
    }

    @GetMapping("/top-produtos/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Top 7 produtos mais vendidos",
            description = "Retorna os 7 produtos mais vendidos da empresa informada em determinado período."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista dos top 7 produtos retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<RespostaProdutoDashDto>> top7Produtos(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        return ResponseEntity.ok(ProdutoMapper.toResponseDtoListObject(vendaService.top7Produtos(empresaId, inicio, fim)));
    }

    @GetMapping("/top-categorias/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Top 5 categorias mais vendidas",
            description = "Retorna as 5 categorias mais vendidas da empresa informada em determinado período."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista das top 5 categorias retornada com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<RespostaCategoriaDashDto>> top5Categorias(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        return ResponseEntity.ok(CategoriaMapper.transformarEmRespostaListaObjetoDto(vendaService.top5Categorias(empresaId, inicio, fim)));
    }
                         
    @GetMapping("/ranking-setores/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "Ranking dos setores mais vendidos",
            description = "Retorna o ranking dos setores com maiores vendas para a empresa informada em determinado período."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking dos setores retornado com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<RespostaSetorDashDto>> rankingSetoresMaisVendidos(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        return ResponseEntity.ok(SetorMapper.transformarEmRespostaListaObjetoDto(vendaService.obterRankingSetoresMaisVendidos(empresaId, inicio, fim)));
    }

    @GetMapping("/kpis/{empresaId}")
    @SecurityRequirement(name = "Bearer")
    @Operation(
            summary = "KPIs de vendas",
            description = "Retorna os principais indicadores de desempenho (KPIs) das vendas da empresa informada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "KPIs retornados com sucesso."),
            @ApiResponse(responseCode = "401", description = "Usuário não autorizado."),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada.")
    })
    public ResponseEntity<List<VendaKpisRespostaDto>> kpis(@PathVariable Integer empresaId, @RequestParam(required = false) LocalDate dataInicio, @RequestParam(required = false) LocalDate dataFim) {

        LocalDate inicio = dataInicio != null ? dataInicio : LocalDate.now();
        LocalDate fim = dataFim != null ? dataFim : inicio;

        return ResponseEntity.ok(VendaMapper.toDtoListObject(vendaService.calculosKpi(empresaId, inicio, fim)));
    }

}

