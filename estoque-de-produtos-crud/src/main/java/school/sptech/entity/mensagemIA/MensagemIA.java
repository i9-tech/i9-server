package school.sptech.entity.mensagemIA;

import jakarta.persistence.*;
import school.sptech.entity.chatIA.ChatIA;
import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagem_ia")
public class MensagemIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;
    private String texto;
    private LocalDateTime hora;

    @ManyToOne
    private ChatIA chat;

    @ManyToOne
    private Funcionario funcionario;

    public MensagemIA(Integer id, String tipo, String texto, LocalDateTime hora, ChatIA chat, Funcionario funcionario) {
        this.id = id;
        this.tipo = tipo;
        this.texto = texto;
        this.hora = hora;
        this.chat = chat;
        this.funcionario = funcionario;
    }

    public MensagemIA() {
    }

    public ChatIA getChat() {
        return chat;
    }

    public void setChat(ChatIA chat) {
        this.chat = chat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getHora() {
        return hora;
    }

    public void setHora(LocalDateTime hora) {
        this.hora = hora;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
