package school.sptech.service.venda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.venda.VendaRepository;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaCriacaoTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private ItemCarrinhoRepository itemCarrinhoRepository;

    @Mock
    private VendaRepository vendaRepository;

    private Funcionario funcionarioMock;
    private ItemCarrinho item1;
    private ItemCarrinho item2;

    @BeforeEach
    void setup() {
        funcionarioMock = new Funcionario();
        funcionarioMock.setId(1);

        item1 = new ItemCarrinho();
        item1.setId(1);
        item1.setValorUnitario(10.0);

        item2 = new ItemCarrinho();
        item2.setId(2);
        item2.setValorUnitario(5.0);
    }

    @Test
    @DisplayName("Criar venda com dados válidos deve retornar DTO correto")
    void criarVendaComDadosValidos_DeveRetornarDto() {
        VendaRequestDto request = new VendaRequestDto();
        request.setFuncionarioId(1);
        request.setItens(Arrays.asList(1, 2));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
        when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 2))).thenReturn(Arrays.asList(item1, item2));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setFuncionario(funcionarioMock);
        vendaSalva.setItensCarrinho(Arrays.asList(item1, item2));
        vendaSalva.setValorTotal(15.0);

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        VendaResponseDto response = vendaService.criarVendaRetornandoDto(request);

        assertNotNull(response);
        assertEquals(15.0, response.getValorTotal());
    }

    @Test
    @DisplayName("Criar venda com funcionário inexistente deve lançar exceção")
    void criarVendaComFuncionarioInexistente_DeveLancarExcecao() {
        VendaRequestDto request = new VendaRequestDto();
        request.setFuncionarioId(1);
        request.setItens(Arrays.asList(1, 2));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVendaRetornandoDto(request));

        assertEquals("Funcionário não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda com itens inexistentes deve lançar exceção")
    void criarVendaComItensInexistentes_DeveLancarExcecao() {
        VendaRequestDto request = new VendaRequestDto();
        request.setFuncionarioId(1);
        request.setItens(Arrays.asList(1, 2));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
        when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 2))).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVendaRetornandoDto(request));

        assertEquals("Itens não encontrados", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda com lista de itens vazia deve lançar exceção")
    void criarVendaComListaItensVazia_DeveLancarExcecao() {
        VendaRequestDto request = new VendaRequestDto();
        request.setFuncionarioId(1);
        request.setItens(Collections.emptyList());

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
        when(itemCarrinhoRepository.findAllById(Collections.emptyList())).thenReturn(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVendaRetornandoDto(request));

        assertEquals("Itens não encontrados", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda com itens repetidos deve calcular valor corretamente")
    void criarVendaComItensRepetidos_DeveCalcularValorCorretamente() {
        VendaRequestDto request = new VendaRequestDto();
        request.setFuncionarioId(1);
        request.setItens(Arrays.asList(1, 1, 2));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
        when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 1, 2))).thenReturn(Arrays.asList(item1, item1, item2));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setFuncionario(funcionarioMock);
        vendaSalva.setItensCarrinho(Arrays.asList(item1, item1, item2));
        vendaSalva.setValorTotal(10.0 * 2 + 5.0); // 25.0

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        VendaResponseDto response = vendaService.criarVendaRetornandoDto(request);

        assertNotNull(response);
        assertEquals(25.0, response.getValorTotal());
    }
}
