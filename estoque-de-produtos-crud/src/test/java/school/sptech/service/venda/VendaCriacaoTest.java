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

    private Funcionario funcionarioMock;
    private List<ItemCarrinho> itensMock;
    private VendaRequestDto vendaRequestDto;
    private Venda vendaMock;

    @BeforeEach
    void setup(){
        funcionarioMock = new Funcionario();
        funcionarioMock.setId(1);

        ItemCarrinho item = new ItemCarrinho();
        item.setId(1);
        item.setValorUnitario(10.0);

        itensMock = Arrays.asList(item, item); //adicionando dois itens IGUAIS!!!

        vendaRequestDto = new VendaRequestDto();
        vendaRequestDto.setFuncionarioId(1);
        vendaRequestDto.setItens(Arrays.asList(1, 1));

        vendaMock = new Venda();
        vendaMock.setId(1);
        vendaMock.setFuncionario(funcionarioMock);
        vendaMock.setItensCarrinho(itensMock);
        vendaMock.setDataVenda(LocalDate.now());
        vendaMock.setValorTotal(20.0);
        }

        @Test
        @DisplayName("Criar venda quando acionado com dados válidos deve retornar venda persistida")
        void criarVendaQuandoAcionadoComDadosValidosDeveRetornarResumoCorreto() {
            when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
            when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 1))).thenReturn(itensMock);
            when(vendaRepository.save(any(Venda.class))).thenReturn(vendaMock); //USADO PARA VALIDAR O TIPO

            Venda venda = vendaService.criarVenda(vendaRequestDto);

            assertNotNull(venda);
            assertEquals(20.0, venda.getValorTotal());
            assertEquals(2, venda.getItensCarrinho().size());
            assertEquals(funcionarioMock, venda.getFuncionario());
        }


    @Test
    @DisplayName("Criar venda quando acionado com funcionário inexistente deve lançar exceção")
    void criarVendaQuandoAcionadoComProdutosNormaisDeveRetornarResumoCorreto() {
        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(vendaRequestDto));

        assertEquals("Funcionário não encontrado", ex.getMessage());
    }

    @Test
    @DisplayName("Criar venda quando acionado com itens inexistentes deve lançar exceção")
    void criarVendaQuandoAcionadoComProdutosVendidosDeveRetornarResumoCorreto() {
        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionarioMock));
        when(itemCarrinhoRepository.findAllById(Arrays.asList(1, 1))).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vendaService.criarVenda(vendaRequestDto));

        assertEquals("Itens não encontrados", ex.getMessage());
    }

    }

