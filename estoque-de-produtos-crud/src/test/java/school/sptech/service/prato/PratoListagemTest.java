package school.sptech.service.prato;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.categoria.Categoria;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.entity.setor.Setor;
import school.sptech.repository.prato.PratoRepository;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PratoListagemTest {

    @InjectMocks
    private PratoService pratoService;

    @Mock
    private PratoRepository pratoRepository;

    @Test
    @DisplayName("Buscar pratos por nome quando acionado com pratos cadastrados deve retornar lista de pratos com nome 'Lasanha'")
    void buscarPratosPorNomeQuandoAcionadoComPratosCadastradosDeveRetornarPratosComNome() {

        // GIVEN - VALOR MOCKADO - PARAMETROS MOCKADOS!!!!
        String nomeBusca = "lasanha";

        // CRIANDO FUNCIONARIO - PARAMETROS MOCKADOS!!!!
        Funcionario funcionarioMock = new Funcionario();
        funcionarioMock.setId(1);

        // CRIANDO SETOR - PARAMETROS MOCKADOS!!!!
        Setor setorMock = new Setor();
        setorMock.setId(2);
        setorMock.setNome("Cozinha");

        // CRIANDO CATEGORIA - PARAMETROS MOCKADOS!!!!
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(5);
        categoriaMock.setNome("Massas");


        List<Prato> pratosMockados = List.of(
                new Prato(1, "Lasanha Bolonhesa", "", 35.0, "prato", true, funcionarioMock, setorMock, categoriaMock),
                new Prato(2, "Lasanha de Queijo", "", 32.0, "prato", true, funcionarioMock, setorMock, categoriaMock)
        );

        // WHEN - CONDIÇÃO
        when(pratoRepository.listarPratoPorNomeLikeEmpresa
                (nomeBusca, funcionarioMock.getId()))
                .thenReturn(pratosMockados);

        // THEN - RESPOSTA
        List<Prato> resultadoEsperado = pratoService.buscarPratosPorNome(nomeBusca, funcionarioMock.getId());

        // ASSERT - VERIFICA RESPOSTA
        assertEquals(pratosMockados, resultadoEsperado);
    }

}