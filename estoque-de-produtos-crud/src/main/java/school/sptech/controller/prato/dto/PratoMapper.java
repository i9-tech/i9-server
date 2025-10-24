package school.sptech.controller.prato.dto;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import school.sptech.entity.prato.Prato;

import java.util.List;

public class PratoMapper {

    @Operation(summary = "Transforma um DTO de cadastro de prato em entidade de prato.",
            description = "Converte os dados de um DTO de cadastro de prato em uma entidade de prato, para persistência no banco de dados.")
    public static Prato toEntity(CadastroPratoDto dto) {
        if (dto == null) {
            return null;
        }
        Prato entity = new Prato();

        entity.setNome(dto.getNome());
        entity.setImagem(dto.getImagem());
        entity.setValorVenda(dto.getValorVenda());
        entity.setDescricao(dto.getDescricao());
        entity.setDisponivel(dto.isDisponivel());
        entity.setFuncionario(dto.getFuncionario());
        entity.setSetor(dto.getSetor());
        entity.setCategoria(dto.getCategoria());
        entity.setAreaPreparo(dto.getAreaPreparo());

        return entity;
    }

    @Operation(summary = "Transforma um DTO de atualização de prato em entidade de prato.",
            description = "Converte os dados de um DTO de atualização de prato em uma entidade de prato, para atualizar os dados existentes no banco de dados.")
    public static Prato toEntity(AtualizarPratoDto dto) {
        if (dto == null) {
            return null;
        }
        Prato entity = new Prato();

        entity.setNome(dto.getNome());
        entity.setImagem(dto.getImagem());
        entity.setValorVenda(dto.getValorVenda());
        entity.setDescricao(dto.getDescricao());
        entity.setDisponivel(dto.isDisponivel());
        entity.setFuncionario(dto.getFuncionario());
        entity.setSetor(dto.getSetor());
        entity.setCategoria(dto.getCategoria());
        entity.setAreaPreparo(dto.getAreaPreparo());

        return entity;
    }

    @Operation(summary = "Transforma uma entidade de prato em DTO de resposta.",
            description = "Converte os dados de uma entidade de prato em um DTO de resposta, para ser enviado na resposta da API.")
    public static RespostaPratoDto toResponseDto(Prato entity) {
        if (entity == null) {
            return null;
        }
        RespostaPratoDto response = new RespostaPratoDto();

        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setImagem(entity.getImagem());
        response.setValorVenda(entity.getValorVenda());
        response.setDescricao(entity.getDescricao());
        response.setDisponivel(entity.getDisponivel());
        response.setFuncionario(entity.getFuncionario());
        response.setSetor(entity.getSetor());
        response.setCategoria(entity.getCategoria());
        response.setAreaPreparo(entity.getAreaPreparo());


        return response;
    }

    @Operation(summary = "Transforma uma lista de entidades de pratos em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de pratos em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<RespostaPratoDto> toResponseDtoList(List<Prato> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(PratoMapper::toResponseDto)
                .toList();
    }

    @Operation(summary = "Transforma uma lista de entidades de pratos em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de pratos em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static Page<RespostaPratoDto> toResponseDtoPage(Page<Prato> entities) {
        if (entities == null) {
            return null;
        }

        return entities.map(PratoMapper::toResponseDto);
    }

    public static RespostaPratoDashDto toResponseDto(Object[] row) {
        if (row == null || row.length < 3) return null;

        String nome = (String) row[0];
        Long quantidadeLong = (Long) row[1];
        Double total = (Double) row[2];

        Integer quantidade = quantidadeLong != null ? quantidadeLong.intValue() : 0;

        return new RespostaPratoDashDto(nome, quantidade, total != null ? total : 0.0);
    }

    public static List<RespostaPratoDashDto> toResponseDtoListObject(List<Object[]> rows) {
        if (rows == null) return null;

        return rows.stream()
                .map(PratoMapper::toResponseDto)
                .toList();
    }

}
