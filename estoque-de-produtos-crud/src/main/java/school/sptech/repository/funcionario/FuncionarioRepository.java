package school.sptech.repository.funcionario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

import java.util.List;
import java.util.Optional;


public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    //exemplo de optional e list
    // optional = retorna 0 ou 1 usuario da fkempresa informada
    // list = retorna lista fazia ou com funcionarios

    Optional<Funcionario> findByIdAndEmpresaId(int id, Integer idEmpresa);

//    Optional<Funcionario> findByCpf(String cpf);
//
    boolean existsByCpfIgnoreCaseAndEmpresa_Id(String cpf, Integer idEmpresa);

//    @Modifying // modificam o estado das entidades no banco
//    @Transactional
//    @Query("UPDATE Funcionario f SET f.ativo = false WHERE f.id = :id AND f.empresa = :empresa")
//    void softDeleteByIdAndEmpresa(@Param("id") int id, @Param("empresa") Empresa empresa);
//
    List<Funcionario> findByEmpresaIdAndAtivoTrue(Integer idEmpresa);
}
