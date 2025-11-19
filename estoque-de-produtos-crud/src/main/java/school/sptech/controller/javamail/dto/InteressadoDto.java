package school.sptech.controller.javamail.dto;

public class InteressadoDto {

    private String email;
    private String plano;
    private String periodo;

    public InteressadoDto() {
    }

    public InteressadoDto(String email, String plano, String periodo) {
        this.email = email;
        this.plano = plano;
        this.periodo = periodo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
