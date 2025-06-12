package school.sptech.repository.funcionario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

import java.util.List;
import java.util.Optional;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Optional<Funcionario> findByIdAndEmpresaId(int id, Integer idEmpresa);

    boolean existsByCpfAndEmpresa_Id(String cpf, Integer idEmpresa);

    @Modifying
    @Transactional
    @Query("UPDATE Funcionario f SET f.ativo = false WHERE f.id = :id AND f.empresa.id = :idEmpresa")
    void softDeleteByIdAndEmpresa(@Param("id") int id, @Param("idEmpresa") Integer idEmpresa);

    List<Funcionario> findByEmpresaIdAndAtivoTrue(Integer idEmpresa);

    Optional<Funcionario> findByCpf(String cpf);


}
