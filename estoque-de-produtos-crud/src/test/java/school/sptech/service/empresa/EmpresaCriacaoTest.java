package school.sptech.service.empresa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.empresa.Empresa;
import school.sptech.repository.empresa.EmpresaRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmpresaCriacaoTest {

    @InjectMocks
    private EmpresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepository;

    @Test
    @DisplayName("Cadastrar empresa quando acionado com dados válidos deve retornar empresa cadastrada")
    void cadastrarEmpresaQuandoAcionadoComDadosValidosDeveRetornarEmpresaCadastrada() {

        // GIVEN - VALOR MOCKADO
        Empresa empresaMock = new Empresa(1, "Tauá Lanches", "12.345.678/0001-00", "Rua das Acácias, 123", LocalDate.parse("2023-01-01"), true, "+5511942780654", "yasmim.silva510811@gmail.com", "taua");


        // WHEN - CONDIÇÃO
        when(empresaRepository.save(empresaMock))
                .thenReturn(empresaMock);


        // THEN - RESPOSTA
        Empresa empresaCadastradaMock = empresaService.cadastrarEmpresa(empresaMock);


        // ASSERT - VERIFICA RESPOSTA
        assertEquals(empresaCadastradaMock, empresaMock);
    }


}

