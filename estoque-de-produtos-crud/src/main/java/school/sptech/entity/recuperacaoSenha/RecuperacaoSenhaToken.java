package school.sptech.entity.recuperacaoSenha;

import jakarta.persistence.*;
import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class RecuperacaoSenhaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    @ManyToOne
    private Funcionario funcionario;

    private LocalDateTime expiracao;

    private boolean tokenUsado;

    public RecuperacaoSenhaToken() {}

    public RecuperacaoSenhaToken(Integer id, String token, Funcionario funcionario, LocalDateTime expiracao, boolean tokenUsado) {
        this.id = id;
        this.token = token;
        this.funcionario = funcionario;
        this.expiracao = expiracao;
        this.tokenUsado = tokenUsado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDateTime getExpiracao() {
        return expiracao;
    }

    public void setExpiracao(LocalDateTime expiracao) {
        this.expiracao = expiracao;
    }

    public boolean isTokenUsado() {
        return tokenUsado;
    }

    public void setTokenUsado(boolean tokenUsado) {
        this.tokenUsado = tokenUsado;
    }
}
