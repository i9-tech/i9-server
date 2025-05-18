package school.sptech.service.empresa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.empresa.Empresa;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaAtualizacaoTest {


    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Atualizar empresa quando acionado com dados válidos deve retornar empresa atualizada")
    void atualizarEmpresaQuandoAcionadoComDadosValidosDeveRetornarEmpresaAtualizada() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaAntigaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");
        Empresa empresaNovaMock = new Empresa(1, "Tauá Lanches e Pastelaria", "12.345.678/0001-00", "Rua das Acácias, 157", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        when(empresaRepository.verificarEmpresaAtivaPorId(anyInt())).thenReturn(true);
        when(empresaRepository.existsById(anyInt())).thenReturn(true);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresaAntigaMock));
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresaNovaMock);


        // THEN - RESPOSTA
        Empresa empresaCadastradaMock = empresaService.atualizarEmpresa(1, empresaNovaMock);


        // ASSERT - VERIFICA RESPOSTA
        assertEquals(empresaCadastradaMock.getNome(), empresaNovaMock.getNome());
    }

    @Test
    @DisplayName("Atualizar empresa quando acionado com empresa inativa deve lançar EntidadeInativaException")
    void atualizarEmpresaQuandoAcionadoComEmpresaInativaDeveLancarEntidadeInativaException() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaAntigaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), false, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");
        Empresa empresaNovaMock = new Empresa(1, "Tauá Lanches e Pastelaria", "12.345.678/0001-00", "Rua das Acácias, 157", LocalDate.parse("2023-01-01"), false, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        when(empresaRepository.verificarEmpresaAtivaPorId(anyInt())).thenReturn(false);


        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeInativaException.class,
                () -> empresaService.atualizarEmpresa(1, empresaNovaMock));
    }

    @Test
    @DisplayName("Atualizar empresa quando acionado com dados inválidos deve lançar EntidadeNaoEncontradaException")
    void atualizarEmpresaQuandoAcionadoComDadosInvalidosDeveLancarEntidadeNaoEncontradaException() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaAntigaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");
        Empresa empresaNovaMock = new Empresa(3, "Tauá Lanches e Pastelaria", "12.345.678/0001-00", "Rua das Acácias, 157", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        when(empresaRepository.verificarEmpresaAtivaPorId(anyInt())).thenReturn(true);
        when(empresaRepository.existsById(anyInt())).thenReturn(false);


        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> empresaService.atualizarEmpresa(3, empresaNovaMock));
    }
}