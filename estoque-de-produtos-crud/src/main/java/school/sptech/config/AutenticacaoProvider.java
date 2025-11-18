package school.sptech.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import school.sptech.exception.UsuarioBloqueadoException;
import school.sptech.service.funcionario.AutenticacaoService;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AutenticacaoProvider implements AuthenticationProvider {

    private final AutenticacaoService usuarioAutorizacaoService;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;

    private static final int MAX_TENTATIVAS = 5;
    private static final long TEMPO_BLOQUEIO_MINUTOS = 30;
    private static final String PREFIXO_TENTATIVAS = "login:tentativas:";
    private static final String PREFIXO_BLOQUEIO = "login:bloqueio:";

    public AutenticacaoProvider(AutenticacaoService usuarioAutorizacaoService, PasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate) {
        this.usuarioAutorizacaoService = usuarioAutorizacaoService;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        final String username = authentication.getName();
        final String password = authentication.getCredentials().toString();

        String chaveBloqueio = PREFIXO_BLOQUEIO + username;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(chaveBloqueio))) {
            // BLOQUEIO DE 30 MINUTOS
            // Long tempoRestante = redisTemplate.getExpire(chaveBloqueio, TimeUnit.MINUTES);
            // BLOQUEIO DE 30 SEGUNDOS
            Long tempoRestante = redisTemplate.getExpire(chaveBloqueio, TimeUnit.SECONDS);

            long minutosParaExibir = (tempoRestante != null && tempoRestante > 0) ? tempoRestante : 1;

            throw new UsuarioBloqueadoException(
                    String.format("Usuário bloqueado. Tente novamente em %d minuto(s).", minutosParaExibir)
            );
        }

        UserDetails userDetails = this.usuarioAutorizacaoService.loadUserByUsername(username);

        if (this.passwordEncoder.matches(password, userDetails.getPassword())) {
            redisTemplate.delete(PREFIXO_TENTATIVAS + username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            String chaveTentativas = PREFIXO_TENTATIVAS + username;
            Long tentativas = redisTemplate.opsForValue().increment(chaveTentativas);

            if (tentativas != null && tentativas == 1) {
                // BLOQUEIO DE 30 MINUTOS
                // redisTemplate.expire(chaveTentativas, 30, TimeUnit.MINUTES);
                // BLOQUEIO DE 30 SEGUNDOS
                redisTemplate.expire(chaveTentativas, 30, TimeUnit.SECONDS);
            }

            if (tentativas != null && tentativas >= MAX_TENTATIVAS) {
                // BLOQUEIO DE 30 MINUTOS
                // redisTemplate.opsForValue().set(chaveBloqueio, "BLOQUEADO", Duration.ofMinutes(TEMPO_BLOQUEIO_MINUTOS));
                // BLOQUEIO DE 30 SEGUNDOS
                redisTemplate.opsForValue().set(chaveBloqueio, "BLOQUEADO", Duration.ofSeconds(TEMPO_BLOQUEIO_MINUTOS));
                redisTemplate.delete(chaveTentativas);

                throw new UsuarioBloqueadoException(
                        String.format("Muitas tentativas falhas. Usuário bloqueado por %d minutos.", TEMPO_BLOQUEIO_MINUTOS)
                );
            }

            throw new BadCredentialsException("Usuário ou Senha inválidos");
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}