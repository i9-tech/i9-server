package school.sptech.controller.images;

import java.io.Serializable;

public record EventoProcessamentoImagemDto(
        byte[] imagem,
        String nomeArquivoOriginal,
        String tipoConteudo,
        String identificador
) implements Serializable {}