package school.sptech.service.funcionario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FuncionarioAtualizacaoTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private EmpresaRepository empresaRepository; // não usado nesse método, mas presente no Service

    @InjectMocks
    private FuncionarioService funcionarioService;

    private Funcionario funcionarioExistente;

    @BeforeEach
    void setUp() {
        funcionarioExistente = new Funcionario();
        funcionarioExistente.setId(1);
        funcionarioExistente.setCpf("111.222.333-44");
        funcionarioExistente.setNome("Funcionario Antigo");
        funcionarioExistente.setCargo("Cargo Antigo");
        funcionarioExistente.setSenha("senha-antiga");
        funcionarioExistente.setAcessoSetorCozinha(false);
        funcionarioExistente.setAcessoSetorAtendimento(false);
        funcionarioExistente.setAcessoSetorEstoque(false);
    }

    @Test
    @DisplayName("Deve atualizar funcionário com sucesso quando encontrado")
    void editarFuncionario_DeveAtualizarComSucesso() {
        when(funcionarioRepository.findByIdAndEmpresaId(1, 10))
                .thenReturn(Optional.of(funcionarioExistente));

        Funcionario dadosAtualizados = new Funcionario();
        dadosAtualizados.setCpf("555.666.777-88");
        dadosAtualizados.setNome("Funcionario Novo");
        dadosAtualizados.setCargo("Novo Cargo");
        dadosAtualizados.setSenha("nova-senha");
        dadosAtualizados.setAcessoSetorCozinha(true);
        dadosAtualizados.setAcessoSetorAtendimento(true);
        dadosAtualizados.setAcessoSetorEstoque(true);

        when(funcionarioRepository.save(any(Funcionario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario resultado = funcionarioService.editarFuncionario(1, 10, dadosAtualizados);

        assertThat(resultado.getNome()).isEqualTo("Funcionario Novo");
        assertThat(resultado.getCpf()).isEqualTo("555.666.777-88");
        assertThat(resultado.getCargo()).isEqualTo("Novo Cargo");
        assertThat(resultado.getSenha()).isEqualTo("nova-senha");
        assertThat(resultado.isAcessoSetorCozinha()).isTrue();
        assertThat(resultado.isAcessoSetorAtendimento()).isTrue();
        assertThat(resultado.isAcessoSetorEstoque()).isTrue();

        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando funcionário não for encontrado")
    void editarFuncionario_DeveLancarExcecao_QuandoFuncionarioNaoEncontrado() {
        when(funcionarioRepository.findByIdAndEmpresaId(999, 10))
                .thenReturn(Optional.empty());

        Funcionario dadosAtualizados = new Funcionario();

        assertThatThrownBy(() -> funcionarioService.editarFuncionario(999, 10, dadosAtualizados))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessage("Funcionário não encontrado na empresa especificada.");

        verify(funcionarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar somente nome e cargo, mantendo outros campos inalterados")
    void editarFuncionario_DeveManterCamposNaoAlterados() {
        when(funcionarioRepository.findByIdAndEmpresaId(1, 10))
                .thenReturn(Optional.of(funcionarioExistente));

        Funcionario dadosAtualizados = new Funcionario();
        dadosAtualizados.setCpf(funcionarioExistente.getCpf()); // mantém igual
        dadosAtualizados.setNome("Novo Nome");
        dadosAtualizados.setCargo("Novo Cargo");
        dadosAtualizados.setSenha(funcionarioExistente.getSenha()); // mantém igual
        dadosAtualizados.setAcessoSetorCozinha(funcionarioExistente.isAcessoSetorCozinha());
        dadosAtualizados.setAcessoSetorAtendimento(funcionarioExistente.isAcessoSetorAtendimento());
        dadosAtualizados.setAcessoSetorEstoque(funcionarioExistente.isAcessoSetorEstoque());

        when(funcionarioRepository.save(any(Funcionario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario resultado = funcionarioService.editarFuncionario(1, 10, dadosAtualizados);

        assertThat(resultado.getNome()).isEqualTo("Novo Nome");
        assertThat(resultado.getCargo()).isEqualTo("Novo Cargo");

        assertThat(resultado.getSenha()).isEqualTo("senha-antiga");
        assertThat(resultado.getCpf()).isEqualTo("111.222.333-44");
        assertThat(resultado.isAcessoSetorCozinha()).isFalse();
        assertThat(resultado.isAcessoSetorAtendimento()).isFalse();
        assertThat(resultado.isAcessoSetorEstoque()).isFalse();

        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve atualizar senha corretamente")
    void editarFuncionario_DeveAtualizarSenha() {
        when(funcionarioRepository.findByIdAndEmpresaId(1, 10))
                .thenReturn(Optional.of(funcionarioExistente));

        Funcionario dadosAtualizados = new Funcionario();
        dadosAtualizados.setCpf(funcionarioExistente.getCpf());
        dadosAtualizados.setNome(funcionarioExistente.getNome());
        dadosAtualizados.setCargo(funcionarioExistente.getCargo());
        dadosAtualizados.setSenha("senha-nova");
        dadosAtualizados.setAcessoSetorCozinha(funcionarioExistente.isAcessoSetorCozinha());
        dadosAtualizados.setAcessoSetorAtendimento(funcionarioExistente.isAcessoSetorAtendimento());
        dadosAtualizados.setAcessoSetorEstoque(funcionarioExistente.isAcessoSetorEstoque());

        when(funcionarioRepository.save(any(Funcionario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario resultado = funcionarioService.editarFuncionario(1, 10, dadosAtualizados);

        assertThat(resultado.getSenha()).isEqualTo("senha-nova");

        verify(funcionarioRepository).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Deve atualizar acessos corretamente")
    void editarFuncionario_DeveAtualizarAcessos() {
        when(funcionarioRepository.findByIdAndEmpresaId(1, 10))
                .thenReturn(Optional.of(funcionarioExistente));

        Funcionario dadosAtualizados = new Funcionario();
        dadosAtualizados.setCpf(funcionarioExistente.getCpf());
        dadosAtualizados.setNome(funcionarioExistente.getNome());
        dadosAtualizados.setCargo(funcionarioExistente.getCargo());
        dadosAtualizados.setSenha(funcionarioExistente.getSenha());
        dadosAtualizados.setAcessoSetorCozinha(true);
        dadosAtualizados.setAcessoSetorAtendimento(true);
        dadosAtualizados.setAcessoSetorEstoque(true);

        when(funcionarioRepository.save(any(Funcionario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Funcionario resultado = funcionarioService.editarFuncionario(1, 10, dadosAtualizados);

        assertThat(resultado.isAcessoSetorCozinha()).isTrue();
        assertThat(resultado.isAcessoSetorAtendimento()).isTrue();
        assertThat(resultado.isAcessoSetorEstoque()).isTrue();

        verify(funcionarioRepository).save(any(Funcionario.class));
    }

}
