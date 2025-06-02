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
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpresaListagemTest {


    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Listar empresas quando acionado com empresas cadastradas retorna uma lista de empresas")
    void listarEmpresasQuandoAcionadoComEmpresasCadastradasDeveRetornarEmpresas() {

        // GIVEN - VALOR MOCKADO
        List<Empresa> empresasMock = List.of(
                new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua"),
                new Empresa(2, "Doces da Tai", "98.765.432/0001-01", "Avenida Central, 456", LocalDate.parse("2022-06-15"), true, "+5511970533898", "tai@gmail.com", "tai"),
                new Empresa(3, "Ygonna Bolos", "11.222.333/0001-02", "Rua Secundária, 789", LocalDate.parse("2023-03-10"), false, "+5511967232879", "maria@gmail.com", "maria")
        );


        // WHEN - CONDIÇÃO
        when(empresaRepository.findAll()).thenReturn(empresasMock);


        // THEN - RESPOSTA
        List<Empresa> listagemEmpresasMock = empresaService.listarEmpresa();


        // ASSERT - VERIFICA RESPOSTA
        assertEquals(empresasMock, listagemEmpresasMock);
    }

    @Test
    @DisplayName("Listar empresas quando acionado sem empresas cadastradas lança EntidadeNaoEncontradaException")
    void listarEmpresasQuandoAcionadoSemEmpresasCadastradasDeveLancarEntidadeNaoEncontradaException() {

        // GIVEN - VALOR MOCKADO
        List<Empresa> empresasMock = List.of();


        // WHEN - CONDIÇÃO
        when(empresaRepository.findAll()).thenReturn(empresasMock);


        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> empresaService.listarEmpresa());
    }

    @Test
    @DisplayName("Listar empresas por ID quando acionado com empresa cadastrada retorna empresa Tauá Lanches")
    void listarEmpresasPorIdQuandoAcionadoComEmpresaCadastradaDeveRetornarEmpresa() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        // NESSE CENÁRIO, O RETORNO É UM OPTIONAL
        // E PRECISAMOS ADICIONAR OUTRO WHEN PARA QUE O TESTE NÃO CAIA NA EXCEPTION
        when(empresaRepository.existsById(anyInt())).thenReturn(true);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresaMock));

        // THEN - RESPOSTA
        Empresa empresaPorId = empresaService.listarEmpresaPorId(1);


        // ASSERT - VERIFICA RESPOSTA
        assertEquals(empresaMock, empresaPorId);
    }

    @Test
    @DisplayName("Listar empresas por ID quando acionado sem empresa cadastrada lança EntidadeNaoEncontradaException")
    void listarEmpresasPorIdQuandoAcionadoSemEmpresaCadastradaDeveLancarEntidadeNaoEncontradaException() {

        // GIVEN - VALOR MOCKADO
        // QUANDO A BUSCA NÃO RETORNA UM OBJETO, NÃO PRECISAMOS DECLARAR ELE

        // WHEN - CONDIÇÃO
        // NESSE CENÁRIO NÃO TEMOS RETORNO, SENÃO O TESTE DÁ ERRO
        // E PRECISAMOS ADICIONAR OUTRO WHEN PARA QUE O TESTE NÃO CAIA DIRETO NA EXCEPTION
        when(empresaRepository.existsById(anyInt())).thenReturn(false);

        // THEN | ASSERT - POR JOGAR EXCEPTION, SÃO OS DOIS PASSOS EM UM
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> empresaService.listarEmpresaPorId(2));
    }


}