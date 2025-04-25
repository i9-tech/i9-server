package school.sptech.controller.funcionario.dto;


import jakarta.validation.constraints.NotNull;

public class FuncionarioTokenDto {

    @NotNull(message = "O Id não pode ser nulo")
    private Integer userId;

    @NotNull(message = " O Nome é obrigatório")
    private String nome;

    @NotNull(message = "O Token é obrigatório")
    private String token;

    public FuncionarioTokenDto() {}

    public FuncionarioTokenDto(Integer userId, String nome, String token) {
        this.userId = userId;
        this.nome = nome;
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "FuncionarioTokenDto{" +
                "userId=" + userId +
                ", nome='" + nome + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
