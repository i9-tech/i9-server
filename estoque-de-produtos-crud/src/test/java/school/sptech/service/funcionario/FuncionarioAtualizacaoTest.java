package school.sptech.service.funcionario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FuncionarioAtualizacaoTest {

    // ATUALIZANDO FUNCIONÁRIO COM DADOS VÁLIDOS
    @Test
    @DisplayName("Atualizar funcionário quando acionado com dados válidos deve retornar funcionário atualizada")
    void atualizarFuncionarioQuandoAcionadoComDadosValidosDeveRetornarFuncionarioAtualizado(){

    }

    // LANÇANDO EXCEÇÃO QUANDO FUNCIONÁRIO COM DADOS INVÁLIDOS FOR ACIONADO
    @Test
    @DisplayName("Atualizar funcionário quando acionado com dados inválidos deve lançar Exceção")
    void atualizarFuncionarioQuandoAcionadoComDadosInvalidosDeveRetornarExcecao(){

    }

    // ERRO AO TENTAR ATUALIZAR CPF E DATA DE ADMISSÃO

}
