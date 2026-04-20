package school.sptech.controller.mensagemIA.dto;

import school.sptech.entity.mensagemIA.MensagemIA;

import java.util.List;

public class MensagemMapper {

    public static MensagemIA toEntity(MensagemIAEnvioDto dto) {
        if(dto == null) {
            return null;
        }
        MensagemIA entity = new MensagemIA();

        entity.setId(dto.getId());
        entity.setTipo(dto.getTipo());
        entity.setTexto(dto.getTexto());
        entity.setHora(dto.getHora());
        entity.setChat(dto.getChat());

        return entity;
    }

    public static MensagemIAEnvioDto toResponseDto(MensagemIA entity) {
        if(entity == null) {
            return null;
        }
        MensagemIAEnvioDto response = new MensagemIAEnvioDto();

        response.setId(entity.getId());
        response.setTipo(entity.getTipo());
        response.setTexto(entity.getTexto());
        response.setHora(entity.getHora());
        response.setChat(entity.getChat());

        return response;
    }

    public static List<MensagemIAEnvioDto> toRespondeDtoList(List<MensagemIA> entities) {
        if(entities == null) {
            return null;
        }

        return entities.stream()
                .map(MensagemMapper::toResponseDto)
                .toList();
    }
}
