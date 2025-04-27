package school.sptech.controller.funcionario.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import school.sptech.entity.funcionario.Funcionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FuncionarioDetalhesDto implements UserDetails {

    private final String nome;
    private final String senha;
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
        return cpf;
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
