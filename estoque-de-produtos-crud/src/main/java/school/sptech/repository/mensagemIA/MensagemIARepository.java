package school.sptech.repository.mensagemIA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.mensagemIA.MensagemIA;

import java.util.List;

public interface MensagemIARepository extends JpaRepository<MensagemIA, Integer> {

    @Query("SELECT m FROM MensagemIA m WHERE m.chat.id = :idChat AND m.funcionario.id = :idFuncionario")
    List<MensagemIA> buscarMensagensChat(@Param("idChat") Integer idChat, @Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("DELETE FROM MensagemIA m WHERE m.chat.id = :idChat AND m.funcionario.id = :idFuncionario")
    void deletarMensagensChat(@Param("idChat") Integer idChat, @Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("UPDATE MensagemIA m SET m.chat = NULL WHERE m.chat.id = :idChat AND m.funcionario.id = :idFuncionario")
    void removerChat(@Param("idChat") Integer idChat, @Param("idFuncionario") Integer idFuncionario);

}