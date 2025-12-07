package school.sptech.service.areaPreparo;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.entity.areaPreparo.AreaPreparo;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.prato.Prato;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.areaPreparo.AreaPreparoRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.prato.PratoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AreaPreparoService {

        private final AreaPreparoRepository areaPreparoRepository;
        private final FuncionarioRepository funcionarioRepository;
        private PratoRepository pratoRepository;

    public AreaPreparoService(AreaPreparoRepository areaPreparoRepository, FuncionarioRepository funcionarioRepository) {
        this.areaPreparoRepository = areaPreparoRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public AreaPreparo cadastrarAreaPreparo(AreaPreparo areaParaCadastrar, Integer idFuncionario) {

            if (!areaPreparoRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
                throw new EntidadeInativaException();
            }

            Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

            areaParaCadastrar.setFuncionario(funcionario);
            areaParaCadastrar.setId(areaParaCadastrar.getId());
            return areaPreparoRepository.save(areaParaCadastrar);
        }

        public List<AreaPreparo> listarTodasAreasPreparo(Integer idFuncionario) {
            if (!areaPreparoRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
                throw new EntidadeInativaException();
            }
            return areaPreparoRepository.buscarCategoriasDaEmpresaDoFuncionario(idFuncionario);
        }

            public Optional<AreaPreparo> buscarAreasPorId(Integer idAreaPreparo, Integer idFuncionario) {

            if (!areaPreparoRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
                throw new EntidadeInativaException();
            }

            Optional<AreaPreparo> areaEncontrada = areaPreparoRepository.buscarAreaPreparoPorIdDoFuncionarioDaEmpresa(idAreaPreparo, idFuncionario);

            if (areaEncontrada.isEmpty()) {
                throw new EntidadeNaoEncontradaException();
            }
            return areaEncontrada;
        }

        public AreaPreparo atualizarArea(Integer id, AreaPreparo areaParaAtualizar, Integer idFuncionario) {

            if (!areaPreparoRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
                throw new EntidadeInativaException();
            }

            Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                    .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

            Optional<AreaPreparo> areaPreparoEncontrada = areaPreparoRepository.findById(id);

            if (areaPreparoEncontrada.isEmpty()) {
                throw new EntidadeNaoEncontradaException("A área de preparo não foi encontrada");
            }

            areaParaAtualizar.setId(id);
            areaParaAtualizar.setFuncionario(funcionario);
            return areaPreparoRepository.save(areaParaAtualizar);
        }

    @Transactional
    public void removerAreaPreparo(Integer areaId, Integer idFuncionario) {

        if (!areaPreparoRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        AreaPreparo areaPreparo = areaPreparoRepository.findById(areaId)
                .orElseThrow(EntidadeNaoEncontradaException::new);


        List<Prato> pratos = pratoRepository.findByAreaPreparoId(areaId);

        for (Prato prato : pratos) {
            prato.setAreaPreparo(null);
        }

        pratoRepository.saveAll(pratos);
        areaPreparoRepository.delete(areaPreparo);
    }
}
