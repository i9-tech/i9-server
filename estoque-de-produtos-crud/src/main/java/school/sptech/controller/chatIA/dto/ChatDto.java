package school.sptech.controller.chatIA.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDateTime;

public class ChatDto {

    @NotBlank
    private Integer id;

    private String nomeChat;

    private String mensagemRecente;

    @NotBlank
    private LocalDateTime dtFixado;

    @NotBlank
    @ManyToOne
    private Funcionario funcionario;

    public @NotBlank Integer getId() {
        return id;
    }

    public void setId(@NotBlank Integer id) {
        this.id = id;
    }

    public String getNomeChat() {
        return nomeChat;
    }

    public void setNomeChat(String nomeChat) {
        this.nomeChat = nomeChat;
    }

    public @NotBlank LocalDateTime getDtFixado() {
        return dtFixado;
    }

    public void setDtFixado(@NotBlank LocalDateTime dtFixado) {
        this.dtFixado = dtFixado;
    }

    public @NotBlank Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(@NotBlank Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public String getMensagemRecente() {
        return mensagemRecente;
    }

    public void setMensagemRecente(String mensagemRecente) {
        this.mensagemRecente = mensagemRecente;
    }
}
