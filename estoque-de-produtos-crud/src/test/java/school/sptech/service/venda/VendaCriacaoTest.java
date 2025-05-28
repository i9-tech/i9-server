package school.sptech.service.venda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.venda.VendaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    // Objetos usados nos testes
    private Funcionario funcionarioMock;
    private List<ItemCarrinho> itensMock;
    private VendaRequestDto vendaRequestDto;
    private Venda vendaMock;

    // será executado antes dos testes para preparar os objetos
    @BeforeEach
    void setup(){
        // funcionário com ID 1
        funcionarioMock = new Funcionario();
        funcionarioMock.setId(1);

        // item de carrinho com valor unitário
        ItemCarrinho item = new ItemCarrinho();
        item.setId(1);
        item.setValorUnitario(10.0);

        itensMock = Arrays.asList(item, item); //adicionando dois itens IGUAIS!!!

        vendaRequestDto = new VendaRequestDto();
        vendaRequestDto.setFuncionarioId(1);
        vendaRequestDto.setItens(Arrays.asList(1, 1));

        // venda retornada após salvar
        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setFuncionario(funcionarioMock);
        vendaMock.setItensCarrinho(itensMock);
        vendaMock.setDataVenda(LocalDate.now());
        vendaMock.setValorTotal(20.0);
        }

        // CRIANDO VENDA COM DADOS VÁLIDOS
        @Test
        @DisplayName("Criar venda quando acionado com dados válidos deve retornar venda persistida")
        void criarVendaQuandoAcionadoComDadosValidosDeveRetornarResumoCorreto() {
            when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
            when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 1))).thenReturn(itensMock);
            when(vendaRepository.save(any(Venda.class))).thenReturn(vendaMock); //USADO PARA VALIDAR O TIPO

            Venda venda = vendaService.criarVenda(vendaRequestDto);

            // Verifica se a venda retornada não é nula
            assertNotNull(venda);

            // Verifica se o valor total está correto
            assertEquals(20.0, venda.getValorTotal());

            // Verifica se foram incluídos os 2 itens no carrinho
            assertEquals(2, venda.getItensCarrinho().size());

            // Verifica se o funcionário associado está correto
            assertEquals(funcionarioMock, venda.getFuncionario());
        }

    //ERRO AO CRIAR A VENDA COM FUNCIONÁRIO INEXISTENTE
    @Test
    @DisplayName("Criar venda quando acionado com funcionário inexistente deve lançar exceção")
    void criarVendaQuandoAcionadoComProdutosNormaisDeveRetornarResumoCorreto() {
        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(vendaRequestDto));

        assertEquals("Funcionário não encontrado", ex.getMessage());
    }

    // ERRO AO CRIAR VENDA COM ITENS INEXISTENTES
    @Test
    @DisplayName("Criar venda quando acionado com itens inexistentes deve lançar exceção")
    void criarVendaQuandoAcionadoComProdutosVendidosDeveRetornarResumoCorreto() {
        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));

        // Simula que nenhum item de carrinho foi encontrado
        when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 1))).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(vendaRequestDto));

        assertEquals("Itens não encontrados", ex.getMessage());
    }
}

