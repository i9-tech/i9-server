package school.sptech.service.setor;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.sptech.controller.images.EventoProcessamentoImagemDto;
import school.sptech.controller.setor.dto.SetorAtualizarDto;
import school.sptech.controller.setor.dto.SetorCadastroDto;
import school.sptech.controller.setor.dto.SetorListagemDto;
import school.sptech.controller.setor.dto.SetorMapper;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.entity.setor.Setor;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.setor.SetorRepository;
import school.sptech.service.imagesService.ImagemProducer;

import java.io.IOException;
import java.util.List;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final FuncionarioRepository funcionarioRepository;

    private final ImagemProducer rabbitMQProducerService;

    public SetorService(SetorRepository setorRepository, FuncionarioRepository funcionarioRepository, ImagemProducer rabbitMQProducerService) {
        this.setorRepository = setorRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.rabbitMQProducerService = rabbitMQProducerService;
    }

    public SetorListagemDto cadastrarSetor(SetorCadastroDto setorDto, MultipartFile imagem, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Setor setor = SetorMapper.transformarEmEntidade(setorDto);
        setor.setFuncionario(funcionario);

        Setor salvo = setorRepository.save(setor);

        if (imagem != null && !imagem.isEmpty()) {
            try {
                EventoProcessamentoImagemDto evento = new EventoProcessamentoImagemDto(
                        imagem.getBytes(),
                        "SETOR",
                        imagem.getOriginalFilename(),
                        imagem.getContentType(),
                        String.valueOf(salvo.getId())
                );
                rabbitMQProducerService.enviarEventoProcessamentoImagem(evento);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao ler os bytes da imagem.", e);
            }
        }

        return SetorMapper.transformarEmRespostaDto(salvo);
    }

    @Transactional
    public void atualizarUrlImagem(Integer idSetor, String urlImagem) {
        Setor setor = setorRepository.findById(idSetor)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Setor não encontrado para atualização da imagem"));

        setor.setImagem(urlImagem);
        setorRepository.save(setor);
        System.out.println("URL da imagem do setor " + idSetor + " atualizada para: " + urlImagem);
    }

    public List<SetorListagemDto> listarTodosSetores(Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        List<Setor> setores = setorRepository.buscarSetorsDaEmpresaDoFuncionario(idFuncionario);
        return SetorMapper.transformarEmListaRespostaDto(setores);
    }

    public SetorListagemDto buscarSetorPorId(Integer id, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Setor setor = setorRepository.buscarSetorPorIdDoFuncionarioDaEmpresa(id, idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Setor não encontrado"));

        return SetorMapper.transformarEmRespostaDto(setor);
    }

    public SetorListagemDto atualizarSetor(Integer id, SetorAtualizarDto setorDto, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        setorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("O setor não foi encontrado"));

        Setor setor = SetorMapper.transformarEmEntidade(setorDto);
        setor.setId(id);
        setor.setFuncionario(funcionario);

        Setor atualizado = setorRepository.save(setor);
        return SetorMapper.transformarEmRespostaDto(atualizado);
    }

    @Transactional
    public void removerSetor(Integer setorId, Integer idFuncionario) {
        if (!setorRepository.verificarEmpresaAtivaPorFuncionarioId(idFuncionario)) {
            throw new EntidadeInativaException();
        }

        setorRepository.findById(setorId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException());

        setorRepository.desvincularPratosDoSetor(setorId);
        setorRepository.desvincularProdutosDoSetor(setorId);
        setorRepository.deleteSetorById(setorId);
    }
}
