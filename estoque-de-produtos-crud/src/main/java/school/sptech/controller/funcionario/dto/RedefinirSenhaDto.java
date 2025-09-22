package school.sptech.controller.funcionario.dto;

public class RedefinirSenhaDto {
    private String senha;
    private boolean primeiroAcesso;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(boolean primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }
}
