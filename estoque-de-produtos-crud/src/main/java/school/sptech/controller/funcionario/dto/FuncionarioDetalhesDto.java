package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.entity.funcionario.Funcionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FuncionarioDetalhesDto implements UserDetails {

    @Schema(
            description = "Nome de registro ou social de pessoas que empregam uma determinada empresa.",
            example = "Agatha Nunes",
            type = "string"
    )
    private final String nome;

    @Schema(
            description = "Senha que dará acesso ao sistema para a pessoa contratada.",
            type = "string"
    )
    private final String senha;

    @Schema(
            description = "CPF da pessoa empregada. Precisa ser informado com formatação.",
            example = "999.999.999-99",
            maxLength = 14,
            type = "string"
    )
    private final String cpf;

    public FuncionarioDetalhesDto(Funcionario funcionario) {
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.senha = funcionario.getSenha();
        this.funcionario = funcionario;
    }

    private final Funcionario funcionario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // Adiciona as permissões com base nos campos do funcionário
        if (funcionario.isAcessoSetorCozinha()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_COZINHA"));
        }
        if (funcionario.isAcessoSetorEstoque()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ESTOQUE"));
        }
        if (funcionario.isAcessoSetorAtendimento()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ATENDIMENTO"));
        }
        if (funcionario.isProprietario()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PROPRIETARIO"));
        }

        return authorities;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return funcionario.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
