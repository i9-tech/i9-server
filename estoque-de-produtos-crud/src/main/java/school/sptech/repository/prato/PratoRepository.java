package school.sptech.repository.prato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.entity.prato.Prato;

import java.util.List;
import java.util.Optional;

public interface PratoRepository extends JpaRepository<Prato, Integer> {
    @Query("select prato from Prato prato join prato.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    List<Prato> buscarTodosPratosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario);

    @Query("select prato from Prato prato join prato.funcionario funcionarioPrato where prato.id = :id and funcionarioPrato.empresa = (select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    Optional<Prato> buscarPratoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(@Param("id") Integer id, @Param("idFuncionario") Integer idFuncionario);

    @Query("select prato from Prato prato join prato.funcionario funcionario where funcionario.empresa = (select funcionario2.empresa from Funcionario funcionario2 where funcionario2.id = :idFuncionario)")
    Page<Prato> buscarPratosDaEmpresaDoFuncionario(@Param("idFuncionario") Integer idFuncionario, Pageable pageable);

    @Query("select prato from Prato prato join prato.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario) and (:termoBusca is null or lower(prato.nome) like lower(concat('%', :termoBusca, '%'))) and (:disponivel is null or prato.disponivel = :disponivel) and (:setorId is null or prato.setor.id = :setorId) and (:categoriaId is null or prato.categoria.id = :categoriaId) and (:areaId is null or prato.areaPreparo.id = :areaId)")
    Page<Prato> buscarPratosComFiltros(@Param("idFuncionario") Integer idFuncionario, @Param("termoBusca") String termoBusca, @Param("disponivel") Boolean disponivel, @Param("setorId") Integer setorId, @Param("categoriaId") Integer categoriaId, @Param("areaId") Integer areaId, Pageable pageable);

    @Query("select prato from Prato prato join prato.funcionario funcionarioPrato where prato.categoria.id = :categoriaId and funcionarioPrato.empresa = (select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    List<Prato> listarPratoPorCategoriaEmpresa(@Param("categoriaId") Integer categoriaId, @Param("idFuncionario") Integer idFuncionario);

    @Query("select prato from Prato prato join prato.funcionario funcionarioPrato where prato.setor.id = :setorId and funcionarioPrato.empresa = (select funcionarioParametro.empresa from Funcionario funcionarioParametro where funcionarioParametro.id = :idFuncionario)")
    List<Prato> listarPratoPorSetorEmpresa(@Param("setorId") Integer setorId, @Param("idFuncionario") Integer idFuncionario);

    @Query("SELECT prato FROM Prato prato JOIN prato.funcionario funcionarioPrato WHERE LOWER(prato.nome) LIKE LOWER(CONCAT('%', :nome, '%')) AND funcionarioPrato.empresa = (SELECT funcionarioParametro.empresa FROM Funcionario funcionarioParametro WHERE funcionarioParametro.id = :idFuncionario)")
    List<Prato> listarPratoPorNomeLikeEmpresa(@Param("nome") String nome, @Param("idFuncionario") Integer idFuncionario);

    @Modifying
    @Query("UPDATE ItemCarrinho ic SET ic.prato = NULL WHERE ic.prato.id = :pratoId")
    void desvincularPratoDosItens(@Param("pratoId") Integer pratoId);

    @Query("select sum(p.valorVenda) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario) and p.disponivel = true")
    Double valorTotalPratosEstoqueEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select sum(p.valorVenda) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Double valorTotalPratosEmpresaInativosAtivos(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(p) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Integer quantidadeTotalPratosPorEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(p) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario) and p.disponivel = true")
    Integer quantidadePratosAtivosPorEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(p) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario) and p.disponivel = false")
    Integer quantidadePratosInativosPorEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(distinct p.categoria.id) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Integer quantidadeCategoriasPratoPorEmpresa(@Param("idFuncionario") Integer idFuncionario);

    @Query("select count(distinct p.setor.id) from Prato p join p.funcionario f where f.empresa = (select f2.empresa from Funcionario f2 where f2.id = :idFuncionario)")
    Integer quantidadeSetoresPratoPorEmpresa(@Param("idFuncionario") Integer idFuncionario);
}
