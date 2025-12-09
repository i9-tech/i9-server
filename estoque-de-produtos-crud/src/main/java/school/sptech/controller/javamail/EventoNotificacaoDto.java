package school.sptech.controller.javamail;

import java.util.Map;

public record EventoNotificacaoDto(String tipoEvento, Map<String, Object> payload) {}
