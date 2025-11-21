package school.sptech.service.empresa.port;

import school.sptech.entity.empresa.Empresa;
import java.util.List;
import java.util.Optional;

public interface EmpresaPort {
    Empresa save(Empresa empresa);
    List<Empresa> findAll();
    Optional<Empresa> findById(Integer id);
    boolean existsById(Integer id);

    Boolean verificarEmpresaAtivaPorId(Integer idEmpresa);
    List<Empresa> findByAtivoTrue();
}


/*
Essa interface define o contrato da minha service sem mencionar
o JPA ou banco de dados.

 PORTA: o contrato do que o service precisa
*/
