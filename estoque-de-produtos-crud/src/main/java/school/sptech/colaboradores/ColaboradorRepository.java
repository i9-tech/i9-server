package school.sptech.colaboradores;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.estoque_de_produtos.Produto;

import java.util.List;


public interface ColaboradorRepository extends JpaRepository<Colaborador, Integer> {

    List<Colaborador> findByFkEmpresa(int fkEmpresa);

    List<Colaborador> findByCargoContainingIgnoreCase(String cargo);
}
