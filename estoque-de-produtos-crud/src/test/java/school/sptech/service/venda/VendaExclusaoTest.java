package school.sptech.service.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.venda.VendaRepository;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendaExclusaoTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    //EXCLUINDO VENDAS EXISTENTES
    @Test
    @DisplayName("Excluir venda existente deve funcionar corretamente")
    void excluirVendaExistenteDeveFuncionarCorretamente(){

        // criando um objeto de venda simulado
        Venda vendaMock = new Venda();

        // retornando venda quando encontrado pelo ID 1
        when(vendaRepository.findById(1)).thenReturn(Optional.of(vendaMock));

        // verificando se não possui exceções
        assertDoesNotThrow(() -> vendaService.excluirVenda(1));

        // verificando delete
        verify(vendaRepository).delete(vendaMock);
    }

    // LANÇANDO EXCEÇÃO QUANDO A VENDA FOR INEXISTENTE
    @Test
    @DisplayName("Excluir venda inexistente deve lançar RuntimeException")
    void excluirVendaInexistenteDeveLancarRuntimeException() {
        // retornar vazio quando a venda de ID 99 for buscada
        when(vendaRepository.findById(99)).thenReturn(Optional.empty());

        //verificando se possui RuntimeException
        assertThrows(RuntimeException.class, () -> vendaService.excluirVenda(99));

        // verificando se o delete não foi chamado
        verify(vendaRepository, never()).delete(any());
    }

    // GARANTINDO QUE O METODO DELETE SEJA CHAMADO APENAS UMA ÚNICA VEZ
    @Test
    @DisplayName("excluirVenda deve chamar delete exatamente uma vez")
    void excluirVenda_DeveChamarDeleteUmaVez() {
        Integer vendaId = 2;
        Venda venda = new Venda();
        venda.setId(vendaId);

        when(vendaRepository.findById(vendaId)).thenReturn(Optional.of(venda));

        vendaService.excluirVenda(vendaId);

        verify(vendaRepository, times(1)).delete(venda);
    }

    // GARANTINDO QUE DELETE NAO SEJA CHAMADO COM OBJETO NULO
    @Test
    @DisplayName("excluirVenda não deve chamar delete com null")
    void excluirVenda_NaoDeveChamarDeleteComNull() {
        Integer vendaId = 3;

        when(vendaRepository.findById(vendaId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vendaService.excluirVenda(vendaId));

        verify(vendaRepository, never()).delete(isNull());
    }


}
