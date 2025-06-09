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
    @DisplayName("Criar venda com dados válidos deve salvar venda com valorTotal correto")
    void criarVendaComDadosValidos_DeveSalvarVendaComValorTotal() {
        Venda venda = new Venda();
        venda.setFuncionario(funcionarioMock);
        venda.setItensCarrinho(Arrays.asList(item1, item2));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setFuncionario(funcionarioMock);
        vendaSalva.setItensCarrinho(Arrays.asList(item1, item2));
        vendaSalva.setValorTotal(15.0);

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        Venda result = vendaService.criarVenda(venda);

        assertNotNull(result);
        assertEquals(15.0, result.getValorTotal());
    }

    @Test
    @DisplayName("Criar venda com funcionário null deve lançar exceção")
    void criarVendaComFuncionarioNull_DeveLancarExcecao() {
        Venda venda = new Venda();
        venda.setFuncionario(null); // simulando funcionário não encontrado
        venda.setItensCarrinho(Arrays.asList(item1, item2));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(venda));

        assertEquals("Funcionário não encontrado.", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda com lista de itens vazia deve lançar exceção")
    void criarVendaComListaItensVazia_DeveLancarExcecao() {
        Venda venda = new Venda();
        venda.setFuncionario(funcionarioMock);
        venda.setItensCarrinho(Collections.emptyList());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(venda));

        assertEquals("Itens da venda não encontrados.", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda com itens repetidos deve calcular valor corretamente")
    void criarVendaComItensRepetidos_DeveCalcularValorCorretamente() {
        Venda venda = new Venda();
        venda.setFuncionario(funcionarioMock);
        venda.setItensCarrinho(Arrays.asList(item1, item1, item2));

        Venda vendaSalva = new Venda();
        vendaSalva.setId(1);
        vendaSalva.setFuncionario(funcionarioMock);
        vendaSalva.setItensCarrinho(Arrays.asList(item1, item1, item2));
        vendaSalva.setValorTotal(10.0 * 2 + 5.0); // 25.0

        when(vendaRepository.save(any(Venda.class))).thenReturn(vendaSalva);

        Venda result = vendaService.criarVenda(venda);

        assertNotNull(result);
        assertEquals(25.0, result.getValorTotal());
    }

}
