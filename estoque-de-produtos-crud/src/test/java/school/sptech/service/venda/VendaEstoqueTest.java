package school.sptech.service.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.venda.VendaRepository;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendaEstoqueTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Listar produtos abaixo da quantidade mínima deve retornar lista correta")
    void listarProdutosAbaixoQuantidadeMinimaDeveRetornarCorretamente() {
        Integer empresaId = 1;

        Produto p1 = new Produto();
        p1.setId(1);
        p1.setNome("Arroz");
        p1.setQuantidade(1);

        Produto p2 = new Produto();
        p2.setId(2);
        p2.setNome("Feijão");
        p2.setQuantidade(0);

        List<Produto> produtosMock = List.of(p1, p2);

        when(vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId)).thenReturn(produtosMock);

        List<Produto> resultado = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);

        assertEquals(2, resultado.size());
        assertEquals("Arroz", resultado.get(0).getNome());
        assertEquals("Feijão", resultado.get(1).getNome());

        verify(vendaRepository).buscaProdutosAbaixoDeQuantidadeMinima(empresaId);
    }
}
