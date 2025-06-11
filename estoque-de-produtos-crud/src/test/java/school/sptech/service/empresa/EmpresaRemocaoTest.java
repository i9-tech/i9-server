package school.sptech.service.empresa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.empresa.Empresa;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpresaRemocaoTest {

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Remover empresa quando acionado com ID válido deve remover empresa")
    void removerEmpresaQuandoAcionadoComIDValidoDeveRemoverEmpresa() {

        // EM REMOÇÃO, NÃO TEMOS GIVEN!!!
        // MAS COMO ISSO É UM SOFT DELETE, TEMOS UM VALOR
        // GIVEN - VALOR MOCKADO
        Empresa empresaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
       when(empresaRepository.existsById(anyInt())).thenReturn(true);
       when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresaMock));
       // SE FOSSE UM DELETE DE VERDADE, UTILIZARIAMOS ESSE WHEN:
       // doNothing().when(empresaRepository).deleteById(anyInt());

        // THEN - RESPOSTA
        empresaService.removerEmpresa(1);


        // ASSERT - VERIFICA RESPOSTA
        assertFalse(empresaMock.isAtivo());
        // SE FOSSE UM DELETE DE VERDADE, UTILIZARIAMOS ESSE VERIFY NO LUGAR DO ASSERT:
        // verify(empresaRepository, times(1)).existsById(anyInt());
        // verify(empresaRepository, times(1)).deleteById(anyInt());

    }

    @Test
    @DisplayName("Remover empresa quando acionado com ID inválido deve lançar EntidadeNaoEncontradaException")
    void removerEmpresaQuandoAcionadoComIDInvalidoDeveLancarEntidadeNaoEncontradaException() {

        // EM REMOÇÃO, NÃO TEMOS GIVEN!!!

        // WHEN - CONDIÇÃO
        when(empresaRepository.existsById(anyInt())).thenReturn(false);

        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> empresaService.removerEmpresa(3));
    }
}