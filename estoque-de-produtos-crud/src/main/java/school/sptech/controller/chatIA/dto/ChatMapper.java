package school.sptech.controller.chatIA.dto;

import school.sptech.entity.chatIA.ChatIA;
import java.util.List;

public class ChatMapper {

    public static ChatIA toEntity(ChatDto dto) {
        if(dto == null) {
            return null;
        }

        ChatIA entity = new ChatIA();
        entity.setNomeChat(dto.getNomeChat());
        entity.setDtFixado(dto.getDtFixado());
        entity.setFuncionario(dto.getFuncionario());
        entity.setMensagemRecente(dto.getMensagemRecente());

        return entity;
    }
    public static ChatIA toEntityAtualizar(ChatAtualizarDto dto) {
        if(dto == null) {
            return null;
        }

        ChatIA entity = new ChatIA();
        entity.setNomeChat(dto.getNomeChat());
        return entity;
    }

    public static ChatDto toResponseDto(ChatIA entity) {
        if(entity == null) {
            return null;
        }

        ChatDto response = new ChatDto();

        response.setId(entity.getId());
        response.setNomeChat(entity.getNomeChat());
        response.setDtFixado(entity.getDtFixado());
        response.setFuncionario(entity.getFuncionario());
        response.setMensagemRecente(entity.getMensagemRecente());


        return response;
    }

    public static ChatAtualizarDto toResponseDtoAtualizar(ChatIA entity) {
        if(entity == null) {
            return null;
        }

        ChatAtualizarDto response = new ChatAtualizarDto();

        response.setNomeChat(entity.getNomeChat());

        return response;
    }

    public static List<ChatDto> toRespondeDtoList(List<ChatIA> entities) {
        if(entities == null) {
            return null;
        }

        return entities.stream()
                .map(ChatMapper::toResponseDto)
                .toList();
    }

}
