package school.sptech.controller.prato.dto;

import school.sptech.entity.prato.Prato;

import java.util.List;

public class PratoMapper {

    public static Prato toEntity(CadastroPratoDto dto) {
        if (dto == null) {
            return null;
        }
        Prato entity = new Prato();
        entity.setNome(dto.getNome());
        entity.setValorVenda(dto.getValorVenda());
        entity.setDescricao(dto.getDescricao());
        entity.setDisponivel(true);

        return entity;
    }

    public static RespostaPratoDto toResponseDto(Prato entity) {
        if (entity == null) {
            return null;
        }
        RespostaPratoDto response = new RespostaPratoDto();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setValorVenda(entity.getValorVenda());
        response.setDescricao(entity.getDescricao());
        response.setDisponivel(entity.getDisponivel());

        return response;
    }

    public static List<RespostaPratoDto> toResponseDtoList(List<Prato> entities) {
        if (entities == null) {
            return null;
        }

        return entities.stream()
                .map(PratoMapper::toResponseDto)
                .toList();
    }
}
