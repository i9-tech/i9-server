package school.sptech.controller.images;

import java.io.Serializable;

public record EventoProcessamentoImagemDto(
        byte[] imagem,
        String tipo,
        String nomeArquivoOriginal,
        String tipoConteudo,
        String identificador
) implements Serializable {}