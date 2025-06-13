package school.sptech.repository.recuperacaoSenha;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.entity.recuperacaoSenha.RecuperacaoSenhaToken;

import java.util.Optional;

@Repository
public interface RecuperacaoSenhaTokenRepository extends JpaRepository<RecuperacaoSenhaToken, Integer> {

    // Método para encontrar um token pelo valor do token (String)
    Optional<RecuperacaoSenhaToken> findByToken(String token);

    // Opcional: Para encontrar tokens de um funcionário específico, se necessário
    Optional<RecuperacaoSenhaToken> findByFuncionarioId(Integer funcionarioId);
}
