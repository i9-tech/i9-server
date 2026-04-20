package school.sptech.service.mensagemIA;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.entity.chatIA.ChatIA;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.mensagemIA.MensagemIA;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.chatIA.ChatIARepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.mensagemIA.MensagemIARepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class MensagemIAService {

    private final MensagemIARepository repository;
    private final ChatIARepository chatIARepository;
    private final FuncionarioRepository funcionarioRepository;

    public MensagemIAService(MensagemIARepository repository, ChatIARepository chatIARepository, FuncionarioRepository funcionarioRepository) {
        this.repository = repository;
        this.chatIARepository = chatIARepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public MensagemIA enviarMensagem(MensagemIA mensagem, Integer idChat, Integer idFuncionario){

        ChatIA chatIA = chatIARepository.findById(idChat)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi possível encontrar o chat associado a mensagem!"));

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));


        mensagem.setId(mensagem.getId());
        mensagem.setChat(chatIA);
        mensagem.setFuncionario(funcionario);
        chatIA.setMensagemRecente(mensagem.getTexto());

        return repository.save(mensagem);
    }

    @Transactional
    public void apagarMensagens(Integer idChat, Integer idFuncionario) {

        if(!chatIARepository.existsById(idChat))
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar o chat associado a mensagem!");

        repository.deletarMensagensChat(idChat, idFuncionario);
        gerarPrimeiraMensagem(idChat, idFuncionario);

    }

    public void gerarPrimeiraMensagem(Integer idChat, Integer idFuncionario) {

        ChatIA chat = chatIARepository.findById(idChat)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi possível encontrar o chat associado a mensagem!"));

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));


        MensagemIA mensagemIA = new MensagemIA();
        LocalDateTime hojeNoBrasil = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        mensagemIA.setId(mensagemIA.getId());
        mensagemIA.setChat(chat);
        mensagemIA.setTipo("bot");
        mensagemIA.setTexto("Olá! Posso ajudar você com informações sobre vendas de produtos");
        mensagemIA.setHora(hojeNoBrasil);
        mensagemIA.setFuncionario(funcionario);
        chat.setMensagemRecente(mensagemIA.getTexto());
        chatIARepository.save(chat);
        repository.save(mensagemIA);
    }


    @Transactional
    public void apagarMensagemUsuario(Integer idMensagem) {

        if(!repository.existsById(idMensagem))
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar a mensagem!");

        repository.deleteById(idMensagem);

    }



    public List<MensagemIA> listarMensagens(Integer idChat, Integer idFuncionario) {

        if(!chatIARepository.existsById(idChat))
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar o chat associado as mensagens!");

        if(!funcionarioRepository.existsById(idFuncionario))
            throw new EntidadeNaoEncontradaException("Não foi possível encontrar o funcionario associado!");

        List<MensagemIA> mensagensEncontradas = repository.buscarMensagensChat(idChat, idFuncionario);

        if(mensagensEncontradas.isEmpty())
            throw new EntidadeNaoEncontradaException("Chat não possui mensagens!");

        return mensagensEncontradas;
    }


}
