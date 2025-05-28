package school.sptech.service.funcionario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.repository.funcionario.FuncionarioRepository;

@ExtendWith(MockitoExtension.class)
public class FuncionarioCadastroTest {

    @InjectMocks
    FuncionarioService funcionarioService;

    @Mock
    FuncionarioRepository funcionarioRepository;


    // CADASTRANDO FUNCIONÁRIO COM DADOS VÁLIDOS
    @Test
    @DisplayName("Cadastrar funcionário quando acionado com dados válidos deve retornar funcionário cadastrado")
    void cadastrarFuncionarioQuandoAcionadoComDadosValidosDeveRetornarFuncionarioCadastrado(){



    }

    // RETORNANDO EXCEÇÃO QUANDO FUNCIONÁRIO COM DADOS INVÁLIDOS FOR CADASTRADO
    @Test
    @DisplayName("Cadastrar funcionário quando acionado com dados inválidos deve retornar exceção")
    void cadastrarFuncionarioQuandoAcionadoComDadosInvalidosDeveRetornarExcecao(){


    }

    // ERRO AO CADASTRAR FUNCIONÁRIO COM EMPRESA INEXISTENTE
    @Test
    @DisplayName("Cadastrar funcionário quando acionado com empresa inválida deve retornar EntidadeNaoEncontradaException/")
    void cadastrarFuncionarioQuandoAcionadoComEmpresaInexistenteDeveRetornarExcecao(){


    }


}
