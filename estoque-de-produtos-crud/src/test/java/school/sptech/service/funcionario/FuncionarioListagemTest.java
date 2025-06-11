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
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FuncionarioListagemTest {

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
    @DisplayName("Deve retornar lista não vazia de funcionários ativos")
    void listarPorEmpresa_DeveRetornarListaNaoVazia() {

        Integer idEmpresa = 1;

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("João");

        when(funcionarioRepository.findByEmpresaIdAndAtivoTrue(idEmpresa))
                .thenReturn(List.of(funcionario));

        List<Funcionario> resultado = funcionarioService.listarPorEmpresa(idEmpresa);

        assertThat(resultado).isNotEmpty();
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo("João");
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando empresa não tem funcionários ativos")
    void listarPorEmpresa_DeveRetornarListaVazia_QuandoNaoExistemFuncionarios() {

        Integer idEmpresa = 2;

        when(funcionarioRepository.findByEmpresaIdAndAtivoTrue(idEmpresa))
                .thenReturn(Collections.emptyList());

        List<Funcionario> resultado = funcionarioService.listarPorEmpresa(idEmpresa);

        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando empresa não existe (simulado como lista vazia)")
    void listarPorEmpresa_DeveRetornarListaVazia_QuandoEmpresaNaoExiste() {
        Integer idEmpresaInvalida = 999;

        when(funcionarioRepository.findByEmpresaIdAndAtivoTrue(idEmpresaInvalida))
                .thenReturn(Collections.emptyList());

        List<Funcionario> resultado = funcionarioService.listarPorEmpresa(idEmpresaInvalida);

        assertThat(resultado).isEmpty();
    }
}
