package school.sptech.service.funcionario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.controller.funcionario.dto.FuncionarioRequestDto;
import school.sptech.exception.ValidacaoException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class FuncionarioCadastroTest {

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Deve validar com sucesso quando todos os campos obrigatórios estão preenchidos")
    void validarFuncionario_DevePassar_QuandoCamposValidos() {

        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setNome("João Silva");
        dto.setCpf("123.456.789-00");
        dto.setCargo("Atendente");

        assertThatCode(() -> funcionarioService.validarFuncionario(dto))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for nulo")
    void validarFuncionario_DeveLancarExcecao_QuandoNomeNulo() {

        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setNome(null);
        dto.setCpf("123.456.789-00");
        dto.setCargo("Atendente");

        assertThatThrownBy(() -> funcionarioService.validarFuncionario(dto))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O nome do funcionário é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o nome for vazio ou em branco")
    void validarFuncionario_DeveLancarExcecao_QuandoNomeVazioOuEmBranco() {

        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setNome("   "); // Nome em branco
        dto.setCpf("123.456.789-00");
        dto.setCargo("Atendente");


        assertThatThrownBy(() -> funcionarioService.validarFuncionario(dto))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O nome do funcionário é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o CPF for nulo")
    void validarFuncionario_DeveLancarExcecao_QuandoCpfNulo() {

        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setNome("Maria Souza");
        dto.setCpf(null);
        dto.setCargo("Gerente");


        assertThatThrownBy(() -> funcionarioService.validarFuncionario(dto))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O CPF do funcionário é obrigatório");
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cargo for nulo ou vazio")
    void validarFuncionario_DeveLancarExcecao_QuandoCargoNuloOuVazio() {
        // Cenário com cargo nulo
        FuncionarioRequestDto dtoComCargoNulo = new FuncionarioRequestDto();
        dtoComCargoNulo.setNome("Carlos Mendes");
        dtoComCargoNulo.setCpf("987.654.321-00");
        dtoComCargoNulo.setCargo(null);

        // Assert para cargo nulo
        assertThatThrownBy(() -> funcionarioService.validarFuncionario(dtoComCargoNulo))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O cargo do funcionário é obrigatório");

        // Cenário com cargo vazio
        FuncionarioRequestDto dtoComCargoVazio = new FuncionarioRequestDto();
        dtoComCargoVazio.setNome("Carlos Mendes");
        dtoComCargoVazio.setCpf("987.654.321-00");
        dtoComCargoVazio.setCargo("   "); // Cargo em branco

        // Assert para cargo vazio
        assertThatThrownBy(() -> funcionarioService.validarFuncionario(dtoComCargoVazio))
                .isInstanceOf(ValidacaoException.class)
                .hasMessage("O cargo do funcionário é obrigatório");
    }

}
