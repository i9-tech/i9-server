package school.sptech.repository.chatIA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.chatIA.ChatIA;

import java.util.List;

public interface ChatIARepository extends JpaRepository<ChatIA, Integer> {

    @Query("SELECT COUNT(c) FROM ChatIA c WHERE c.dtFixado IS NOT NULL AND c.funcionario.id = :idFuncionario")
    Long contarChatsFixados(@Param("idFuncionario") Integer idFuncionario);

    @Query("SELECT c FROM ChatIA c WHERE c.funcionario.id = :idFuncionario")
    List<ChatIA> listarChats(@Param("idFuncionario") Integer idFuncionario);

}