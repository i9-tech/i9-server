package school.sptech.controller.images;

import java.io.Serializable;

public record EventoImagemProcessadaDto(
        String tipo,
        String identificador,
        String urlImagem
) implements Serializable {}
