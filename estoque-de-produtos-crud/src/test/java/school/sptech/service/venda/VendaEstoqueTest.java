package school.sptech.service.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.produto.Produto;
import school.sptech.repository.venda.VendaRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendaEstoqueTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    // LISTANDO PRODUTOS ABAIXO DA QUANTIDADE MÍNIMA ESTABELECIDA
    @Test
    @DisplayName("Listar produtos abaixo da quantidade mínima deve retornar lista correta")
    void listarProdutosAbaixoQuantidadeMinimaDeveRetornarCorretamente() {
        // setando o ID da empresa que será utilizada
        Integer empresaId = 1;

        //criando produto 1
        Produto p1 = new Produto();
        p1.setId(1);
        p1.setNome("Arroz");
        p1.setQuantidade(1); // Supondo que o mínimo seja maior que 1

        //criando produto 2
        Produto p2 = new Produto();
        p2.setId(2);
        p2.setNome("Feijão");
        p2.setQuantidade(0); // Supondo que o mínimo seja maior que 1

        // criando lista simulada que será retornada
        List<Produto> produtosMock = List.of(p1, p2);

        // retornar lista com p1 e p2 quando buscado pelo ID estabelecido
        when(vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId)).thenReturn(produtosMock);

        // chamando o metodo da service
        List<Produto> resultado = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);

        // verificando se a quantidade = 2
        assertEquals(2, resultado.size());

        // verificando nome
        assertEquals("Arroz", resultado.get(0).getNome());
        assertEquals("Feijão", resultado.get(1).getNome());

        // Verifica se o metodo do repositório foi realmente chamado
        verify(vendaRepository).buscaProdutosAbaixoDeQuantidadeMinima(empresaId);
    }

    // RETORNANDO LISTA VAZIA QUANDO NÃO HOUVER ITENS ABAIXO DA QUANTIDADE MINIMA
    @Test
    @DisplayName("listarProdutosAbaixoDaQuantidadeMinima deve retornar lista vazia quando não houver produtos abaixo do mínimo")
    void listarProdutosAbaixoDaQuantidadeMinima_DeveRetornarListaVazia() {
        Integer empresaId = 1;

        when(vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId)).thenReturn(Collections.emptyList());

        List<Produto> resultado = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // RETORNANDO LISTA CORRETA QUANDO HOUVER ITENS ABAIXO DA QUANTIDADE MINIMA
    @Test
    @DisplayName("listarProdutosAbaixoDaQuantidadeMinima deve retornar produtos corretos abaixo do mínimo")
    void listarProdutosAbaixoDaQuantidadeMinima_DeveRetornarProdutosCorretos() {
        Integer empresaId = 1;

        Produto produto1 = new Produto();
        produto1.setId(1);
        produto1.setNome("Produto A");

        Produto produto2 = new Produto();
        produto2.setId(2);
        produto2.setNome("Produto B");

        List<Produto> produtos = List.of(produto1, produto2);

        when(vendaRepository.buscaProdutosAbaixoDeQuantidadeMinima(empresaId)).thenReturn(produtos);

        List<Produto> resultado = vendaService.listarProdutosAbaixoDaQuantidadeMinima(empresaId);

        assertEquals(2, resultado.size());
        assertEquals("Produto A", resultado.get(0).getNome());
        assertEquals("Produto B", resultado.get(1).getNome());
    }

}
