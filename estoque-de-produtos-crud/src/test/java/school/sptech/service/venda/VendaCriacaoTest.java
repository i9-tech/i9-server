package school.sptech.service.venda;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.venda.VendaRepository;

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
    void deveCriarVendaComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        ItemCarrinho item1 = new ItemCarrinho();
        item1.setId(10);
        item1.setValorUnitario(50.0);

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setId(20);
        item2.setValorUnitario(30.0);

        Venda venda = new Venda();
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(List.of(item1, item2));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        when(itemCarrinhoRepository.findAllById(List.of(10, 20))).thenReturn(List.of(item1, item2));
        when(vendaRepository.save(any(Venda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venda vendaCriada = vendaService.criarVenda(venda);

        assertNotNull(vendaCriada);
        assertEquals(funcionario, vendaCriada.getFuncionario());
        assertEquals(2, vendaCriada.getItensCarrinho().size());
        assertEquals(80.0, vendaCriada.getValorTotal());
        verify(vendaRepository, times(1)).save(vendaCriada);
    }

    @Test
    void deveLancarExcecaoQuandoFuncionarioNaoEncontrado() {
        Venda venda = new Venda();
        Funcionario funcionario = new Funcionario();
        funcionario.setId(999);
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(List.of(new ItemCarrinho()));

        when(funcionarioRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> vendaService.criarVenda(venda));
        assertEquals("Funcionário não encontrado.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoItensCarrinhoNulos() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        Venda venda = new Venda();
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(null);  // itens nulos

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));  // mock do funcionário válido

        RuntimeException exception = assertThrows(RuntimeException.class, () -> vendaService.criarVenda(venda));
        assertEquals("Itens da venda não encontrados.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoItensCarrinhoVazios() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        Venda venda = new Venda();
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(List.of());  // carrinho vazio

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));  // retorna funcionário válido

        RuntimeException exception = assertThrows(RuntimeException.class, () -> vendaService.criarVenda(venda));
        assertEquals("Itens da venda não encontrados.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNenhumItemValidoEncontrado() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        ItemCarrinho item = new ItemCarrinho();
        item.setId(5);

        Venda venda = new Venda();
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(List.of(item));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        when(itemCarrinhoRepository.findAllById(List.of(5))).thenReturn(List.of()); // nenhum item encontrado

        RuntimeException exception = assertThrows(RuntimeException.class, () -> vendaService.criarVenda(venda));
        assertEquals("Nenhum item válido encontrado para os IDs informados.", exception.getMessage());
    }

    @Test
    void deveCalcularValorTotalCorreto() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        ItemCarrinho item1 = new ItemCarrinho();
        item1.setId(10);
        item1.setValorUnitario(15.0);

        ItemCarrinho item2 = new ItemCarrinho();
        item2.setId(20);
        item2.setValorUnitario(25.0);

        ItemCarrinho item3 = new ItemCarrinho();
        item3.setId(30);
        item3.setValorUnitario(10.0);

        Venda venda = new Venda();
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(List.of(item1, item2, item3));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        when(itemCarrinhoRepository.findAllById(List.of(10, 20, 30))).thenReturn(List.of(item1, item2, item3));
        when(vendaRepository.save(any(Venda.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Venda vendaCriada = vendaService.criarVenda(venda);

        assertEquals(50.0, vendaCriada.getValorTotal());
    }
}


