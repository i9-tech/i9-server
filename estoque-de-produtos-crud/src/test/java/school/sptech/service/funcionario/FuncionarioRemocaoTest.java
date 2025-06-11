package school.sptech.service.funcionario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.config.GerenciadorTokenJwt;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioRemocaoTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @Test
    @DisplayName("Deve remover funcionário com sucesso quando encontrado")
    void removerPorId_DeveRemoverComSucesso() {

        int idFuncionario = 1;
        int idEmpresa = 100;

        Funcionario funcionario = new Funcionario();
        funcionario.setId(idFuncionario);

        when(funcionarioRepository.findByIdAndEmpresaId(idFuncionario, idEmpresa))
                .thenReturn(Optional.of(funcionario));

        doNothing().when(funcionarioRepository).softDeleteByIdAndEmpresa(idFuncionario, idEmpresa);

        funcionarioService.removerPorId(idFuncionario, idEmpresa);

        verify(funcionarioRepository, times(1)).softDeleteByIdAndEmpresa(idFuncionario, idEmpresa);
    }

    @Test
    @DisplayName("Deve lançar exceção quando funcionário não encontrado para remoção")
    void removerPorId_DeveLancarExcecao_QuandoFuncionarioNaoEncontrado() {

        int idFuncionario = 999;
        int idEmpresa = 200;

        when(funcionarioRepository.findByIdAndEmpresaId(idFuncionario, idEmpresa))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> funcionarioService.removerPorId(idFuncionario, idEmpresa))
                .isInstanceOf(EntidadeNaoEncontradaException.class)
                .hasMessage("Funcionário não encontrado na empresa especificada.");

        verify(funcionarioRepository, never()).softDeleteByIdAndEmpresa(anyInt(), anyInt());
    }

    @Test
    @DisplayName("Deve garantir que softDelete não é chamado quando funcionário não existe")
    void removerPorId_NaoDeveChamarSoftDelete_QuandoFuncionarioNaoExiste() {

        int idFuncionario = 1234;
        int idEmpresa = 300;

        when(funcionarioRepository.findByIdAndEmpresaId(idFuncionario, idEmpresa))
                .thenReturn(Optional.empty());

        try {
            funcionarioService.removerPorId(idFuncionario, idEmpresa);
        } catch (EntidadeNaoEncontradaException ex) {

        }

        verify(funcionarioRepository, never()).softDeleteByIdAndEmpresa(anyInt(), anyInt());
    }
}
