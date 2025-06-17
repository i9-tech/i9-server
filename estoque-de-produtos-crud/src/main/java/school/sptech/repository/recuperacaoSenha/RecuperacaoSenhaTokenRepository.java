package school.sptech.repository.recuperacaoSenha;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.sptech.entity.recuperacaoSenha.RecuperacaoSenhaToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecuperacaoSenhaTokenRepository extends JpaRepository<RecuperacaoSenhaToken, Integer> {

    // Método para encontrar um token pelo valor do token (String)
    Optional<RecuperacaoSenhaToken> findByToken(String token);

    // Opcional: Para encontrar tokens de um funcionário específico, se necessário
    Optional<RecuperacaoSenhaToken> findByFuncionarioId(Integer funcionarioId);

    // Método para buscar todos os tokens ativos (não usados e não expirados) de um funcionário
    List<RecuperacaoSenhaToken> findByFuncionarioIdAndTokenUsadoFalseAndExpiracaoAfter(Integer funcionarioId, LocalDateTime now);

    // Método para invalidar todos os tokens de recuperação de senha não usados e não expirados para um funcionário específico
    @Modifying
    @Query("UPDATE RecuperacaoSenhaToken t SET t.tokenUsado = true WHERE t.funcionario.id = :funcionarioId AND t.expiracao > :now")
    void invalidateActiveTokensByFuncionarioId(@Param("funcionarioId") Integer funcionarioId, @Param("now") LocalDateTime now);

}
