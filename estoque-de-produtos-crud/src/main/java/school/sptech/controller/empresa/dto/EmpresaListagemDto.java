package school.sptech.controller.empresa.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "EmpresaListagemDTO",
        description = "DTO para transferência de dados da empresa para listagem.")
public class EmpresaListagemDto {

    @Schema(
            description = "Identificador único da empresa",
            example = "1",
            type = "integer"
    )
    private Integer id;

    @Schema(
            description = "Nome fantasia ou razão social da empresa",
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
            description = "Endereço físico ou comercial da empresa",
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
}
