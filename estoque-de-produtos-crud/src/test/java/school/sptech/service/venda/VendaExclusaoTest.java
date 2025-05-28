package school.sptech.service.venda;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.venda.VendaRepository;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendaExclusaoTest {

    @InjectMocks
    private VendaService vendaService;

    @Mock
    private VendaRepository vendaRepository;

    @Test
    @DisplayName("Excluir venda existente deve funcionar corretamente")
    void excluirVendaExistenteDeveFuncionarCorretamente(){
        Venda vendaMock = new Venda();
        when(vendaRepository.findById(1)).thenReturn(Optional.of(vendaMock));

        assertDoesNotThrow(() -> vendaService.excluirVenda(1));
        verify(vendaRepository).delete(vendaMock);
    }


    @Test
    @DisplayName("Excluir venda inexistente deve lançar RuntimeException")
    void excluirVendaInexistenteDeveLancarRuntimeException() {
        when(vendaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vendaService.excluirVenda(99));
        verify(vendaRepository, never()).delete(any());
    }
}
