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
import school.sptech.service.empresa.port.EmpresaPort;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaRemocaoTest {

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Remover empresa quando acionado com ID válido deve remover empresa")
    void removerEmpresaQuandoAcionadoComIDValidoDeveRemoverEmpresa() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresaMock));


        // THEN - RESPOSTA (Ação)
        empresaService.removerEmpresa(1);


        // ASSERT - VERIFICA RESPOSTA E INTERAÇÕES
        assertFalse(empresaMock.isAtivo());

        verify((EmpresaPort) empresaRepository, times(1)).save(empresaMock);
    }

    @Test
    @DisplayName("Remover empresa quando acionado com ID inválido deve lançar EntidadeNaoEncontradaException")
    void removerEmpresaQuandoAcionadoComIDInvalidoDeveLancarEntidadeNaoEncontradaException() {

        // WHEN - CONDIÇÃO
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.empty());

        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> empresaService.removerEmpresa(3));

        verify((EmpresaPort) empresaRepository, never()).save(any(Empresa.class));
    }
}