package school.sptech.entity.chatIA;


import jakarta.persistence.*;
import school.sptech.entity.funcionario.Funcionario;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_ia")
public class ChatIA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nomeChat;

    private LocalDateTime dtFixado;

    private String mensagemRecente;

    @ManyToOne
    private Funcionario funcionario;

    public ChatIA(Integer id, String nomeChat, LocalDateTime dtFixado, String mensagemRecente, Funcionario funcionario) {
        this.id = id;
        this.nomeChat = nomeChat;
        this.dtFixado = dtFixado;
        this.mensagemRecente = mensagemRecente;
        this.funcionario = funcionario;
    }

    public ChatIA() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeChat() {
        return nomeChat;
    }

    public void setNomeChat(String nomeChat) {
        this.nomeChat = nomeChat;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDateTime getDtFixado() {
        return dtFixado;
    }

    public void setDtFixado(LocalDateTime dtFixado) {
        this.dtFixado = dtFixado;
    }

    public String getMensagemRecente() {
        return mensagemRecente;
    }

    public void setMensagemRecente(String mensagemRecente) {
        this.mensagemRecente = mensagemRecente;
    }
}
