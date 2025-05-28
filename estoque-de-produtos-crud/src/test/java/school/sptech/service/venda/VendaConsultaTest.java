package school.sptech.service.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.venda.VendaRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendaConsultaTest {
    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    //CALCULANDO LUCRO TOTAL
    @Test
    @DisplayName("calcularLucroTotal quando acionado com dados válidos deve retornar resultado esperado")
    void calcularLucroTotalQuandoAcionadoComDadosValidosDeveRetornarResultadoEsperado(){
        Funcionario funcionario = new Funcionario();
        Empresa empresa = new Empresa();
        empresa.setId(1);
        funcionario.setEmpresa(empresa);

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));

        Venda venda1 = new Venda();
        venda1.setValorTotal(100.0);
        venda1.setFuncionario(funcionario);

        Venda venda2 = new Venda();
        venda2.setValorTotal(150.0);
        venda2.setFuncionario(funcionario);

        List<Venda> vendas = List.of(venda1, venda2);
        LocalDate data = LocalDate.now();

        when(vendaRepository.findAllByDataVenda(data)).thenReturn(vendas);

        Double resultado = vendaService.calcularLucroTotal(1, data);

        assertEquals(250.0, resultado);
    }

    // ERRO QUANDO FUNCIONÁRIO NÃO É ENCONTRADO
    @Test
    @DisplayName("calcularLucroTotal quando acionado com dados válidos deve lançar exceção se funcionário não encontrado")
    void calcularLucroTotalQuandoAcionadoComDadosValidosDeveLancarExcecao() {
        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> vendaService.calcularLucroTotal(1, LocalDate.now()));
    }

    // QUANTIDADE DE VENDAS IGUAL A ZERO
    @Test
    @DisplayName("quantidadeVendasPorEmpresaHoje quando acionado com produtos normais deve retornar lista vazia")
    void quantidadeVendasPorEmpresaHojeQuandoAcionadoComProdutosNormaisDeveRetornarListaVazia() {
        Integer empresaId = 1;
        LocalDate hoje = LocalDate.now();

        when(vendaRepository.contarVendasConcluidasPorEmpresaEData(empresaId, hoje)).thenReturn(0);

        Integer resultado = vendaService.quantidadeVendasPorEmpresaHoje(empresaId);

        assertEquals(0, resultado);
    }

    // CALCULANDO VALOR TOTAL POR CATEGORIA DO DIA ATUAL
    @Test
    @DisplayName("valorTotalPorCategoriaHoje quando acionado com produtos normais deve retornar resultado esperado")
    void valorTotalPorCategoriaHojeQuandoAcionadoComProdutosNormaisDeveRetornarResultadoEsperado() {
        Integer empresaId = 1;
        LocalDate hoje = LocalDate.now();

        List<Object[]> mockResultado = List.of(
                new Object[]{"Categoria A", 100.0},
                new Object[]{"Categoria B", 200.0}
        );

        when(vendaRepository.valorTotalDiarioPorCategoriaEmpresa(eq(empresaId), eq(hoje))).thenReturn(mockResultado);

        Map<String, Double> resultado = vendaService.valorTotalPorCategoriaHoje(empresaId);

        assertEquals(2, resultado.size());
        assertEquals(100.0, resultado.get("Categoria A"));
        assertEquals(200.0, resultado.get("Categoria B"));
    }

    // RETORNANDO VAZIO PARA CATEGORIAS
    @Test
    @DisplayName("valorTotalPorCategoriaHoje quando acionado com venda inexistente deve lançar exceção")
    void valorTotalPorCategoriaHojeQuandoAcionadoComVendaInexistenteDeveLancarExcecao() {
        Integer empresaId = 1;
        LocalDate hoje = LocalDate.now();

        when(vendaRepository.valorTotalDiarioPorCategoriaEmpresa(eq(empresaId), eq(hoje))).thenReturn(Collections.emptyList());

        Map<String, Double> resultado = vendaService.valorTotalPorCategoriaHoje(empresaId);

        assertTrue(resultado.isEmpty());
    }

    // CALCULANDO VALOR TOTAL POR SETOR DO DIA ATUAL
    @Test
    @DisplayName("valorTotalPorSetorHoje quando acionado com produtos normais deve retornar resultado esperado")
    void valorTotalPorSetorHojeQuandoAcionadoComProdutosNormaisDeveRetornarResultadoEsperado() {
        Integer empresaId = 1;
        LocalDate hoje = LocalDate.now();

        List<Object[]> mockResultado = List.of(
                new Object[]{"Setor A", 150.0},
                new Object[]{"Setor B", 250.0}
        );

        when(vendaRepository.valorTotalDiarioPorSetorEmpresa(eq(empresaId), eq(hoje))).thenReturn(mockResultado);

        Map<String, Double> resultado = vendaService.valorTotalPorSetorHoje(empresaId);

        assertEquals(2, resultado.size());
        assertEquals(150.0, resultado.get("Setor A"));
        assertEquals(250.0, resultado.get("Setor B"));
    }
}
