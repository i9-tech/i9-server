package school.sptech.controller.mensagemIA.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import school.sptech.entity.chatIA.ChatIA;
import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDateTime;

public class MensagemIAEnvioDto {

    @NotBlank
    private Integer id;

    @NotBlank
    private String tipo;

    @NotBlank
    private String texto;

    @NotBlank
    private LocalDateTime hora;

    @NotBlank
    @ManyToOne
    private ChatIA chat;

    @NotBlank
    @ManyToOne
    private Funcionario funcionario;

    public @NotBlank Integer getId() {
        return id;
    }

    public void setId(@NotBlank Integer id) {
        this.id = id;
    }

    public @NotBlank String getTipo() {
        return tipo;
    }

    public void setTipo(@NotBlank String tipo) {
        this.tipo = tipo;
    }

    public @NotBlank String getTexto() {
        return texto;
    }

    public void setTexto(@NotBlank String texto) {
        this.texto = texto;
    }

    public @NotBlank LocalDateTime getHora() {
        return hora;
    }

    public void setHora(@NotBlank LocalDateTime hora) {
        this.hora = hora;
    }

    public @NotBlank ChatIA getChat() {
        return chat;
    }

    public void setChat(@NotBlank ChatIA chat) {
        this.chat = chat;
    }

    public @NotBlank Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(@NotBlank Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
