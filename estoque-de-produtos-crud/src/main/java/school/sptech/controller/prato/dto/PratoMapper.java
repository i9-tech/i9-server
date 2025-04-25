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
        entity.setImagem(dto.getImagem());
        entity.setValorVenda(dto.getValorVenda());
        entity.setDescricao(dto.getDescricao());
        entity.setDisponivel(true);
        entity.setFuncionario(dto.getFuncionario());
        entity.setSetor(dto.getSetor());
        entity.setCategoria(dto.getCategoria());

        return entity;
    }

    public static Prato toEntity(AtualizarPratoDto dto, Integer id) {
        if (dto == null) {
            return null;
        }
        Prato entity = new Prato();
        entity.setId(id);
        entity.setNome(dto.getNome());
        entity.setImagem(dto.getImagem());
        entity.setValorVenda(dto.getValorVenda());
        entity.setDescricao(dto.getDescricao());
        entity.setDisponivel(dto.isDisponivel());
        entity.setFuncionario(dto.getFuncionario());
        entity.setSetor(dto.getSetor());
        entity.setCategoria(dto.getCategoria());

        return entity;
    }

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
