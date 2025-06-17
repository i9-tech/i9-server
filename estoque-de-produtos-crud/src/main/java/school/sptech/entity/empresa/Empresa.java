package school.sptech.entity.empresa;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "empresa")
@Schema(
        name = "Empresa",
        description = "Entidade que representa uma empresa cadastrada no sistema. É a base para execução de operações relacionadas à gestão empresarial.")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único da empresa.",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome fantasia ou razão social da empresa.",
            example = "Restaurante Tauá",
            type = "string"
    )
    private String nome;

    @Schema(
            description = "CNPJ da empresa. Pode ser informado com ou sem formatação (ex: 12345678000195 ou 12.345.678/0001-95).",
            example = "12.345.678/0001-95",
            minLength = 14,
            maxLength = 18,
            type = "string"
    )
    private String cnpj;

    @Schema(
            description = "Endereço físico ou comercial da empresa.",
            example = "Rua Haddock Lobo, 595 - Consolação, São Paulo - SP",
            type = "string"
    )
    private String endereco;

    @Schema(
            description = "Data em que a empresa foi cadastrada no sistema.",
            example = "2025-04-25",
            format = "date",
            type = "string"
    )
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dataCadastro;


    @Schema(
            description = "Indica se a empresa está ativa no sistema. 'true' para ativa, 'false' para inativa.",
            example = "true",
            type = "boolean"
    )
    private boolean ativo;

    private String whatsapp;

    private String email;

    private String nomeSenha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeSenha() {
        return nomeSenha;
    }

    public void setNomeSenha(String nomeSenha) {
        this.nomeSenha = nomeSenha;
    }

    public String getWhatsapp() { return whatsapp; }

    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }

    public Empresa() {}

    public Empresa(Integer id, String nome, String cnpj, String endereco, LocalDate dataCadastro, boolean ativo, String whatsapp, String email, String nomeSenha) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
        this.ativo = ativo;
        this.whatsapp = whatsapp;
        this.email = email;
        this.nomeSenha = nomeSenha;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", endereco='" + endereco + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", ativo=" + ativo +
                ", whatsapp='" + whatsapp + '\'' +
                ", email='" + email + '\'' +
                ", nomeSenha='" + nomeSenha + '\'' +
                '}';
    }


}
