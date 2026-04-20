package school.sptech.service.chatIA;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.entity.chatIA.ChatIA;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.mensagemIA.MensagemIA;
import school.sptech.exception.EntidadeConflictException;
import school.sptech.exception.EntidadeInativaException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.chatIA.ChatIARepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.mensagemIA.MensagemIARepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ChatIAService {

    private final ChatIARepository repository;
    private final FuncionarioRepository funcionarioRepository;
    private final MensagemIARepository mensagemIARepository;

    public ChatIAService(ChatIARepository repository, FuncionarioRepository funcionarioRepository, MensagemIARepository mensagemIARepository) {
        this.repository = repository;
        this.funcionarioRepository = funcionarioRepository;
        this.mensagemIARepository = mensagemIARepository;
    }

    public ChatIA criarNovoChat(Integer idFuncionario) {
        ChatIA novoChat = new ChatIA();

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        novoChat.setId(novoChat.getId());
        novoChat.setFuncionario(funcionario);
        repository.save(novoChat);

        novoChat.setNomeChat("Chat %s".formatted(novoChat.getId()));

        String mensagemRecente = gerarPrimeiraMensagem(novoChat, funcionario);
        novoChat.setMensagemRecente(mensagemRecente);
        return repository.save(novoChat);
    }

    public List<ChatIA> listarChats(Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        List<ChatIA> chatsIA = repository.listarChats(funcionario.getId());
        if(chatsIA.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Não foram encontrados chats para esse funcionário!");
        }

        return chatsIA;
    }

    public ChatIA atualizarNomeChat(Integer idChat, Integer idFuncionario, ChatIA chat) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        ChatIA chatEncontrado = repository.findById(idChat)
                .orElseThrow(() -> new EntidadeInativaException("Chat não encontrado!"));

        chatEncontrado.setNomeChat(chat.getNomeChat());
        chatEncontrado.setFuncionario(funcionario);

        return repository.save(chatEncontrado);
    }

    public ChatIA fixarChat(Integer idChat, Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        ChatIA chatEncontrado = repository.findById(idChat)
                .orElseThrow(() -> new EntidadeInativaException("Chat não encontrado!"));

        if(chatEncontrado.getDtFixado() != null) {
            chatEncontrado.setDtFixado(null);
            return repository.save(chatEncontrado);
        }

        if(repository.contarChatsFixados(idFuncionario) > 2)
            throw new EntidadeConflictException("Não é possível fixar mais de 3 chats!");

        chatEncontrado.setFuncionario(funcionario);

        LocalDateTime hojeNoBrasil = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        chatEncontrado.setDtFixado(hojeNoBrasil);

        return repository.save(chatEncontrado);
    }

    public ChatIA desafixarChat(Integer idChat, Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        ChatIA chatEncontrado = repository.findById(idChat)
                .orElseThrow(() -> new EntidadeInativaException("Chat não encontrado!"));

        chatEncontrado.setDtFixado(null);

        return repository.save(chatEncontrado);
    }

    @Transactional
    public void deletarChat(Integer idChat, Integer idFuncionario) {

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        ChatIA chatEncontrado = repository.findById(idChat)
                .orElseThrow(() -> new EntidadeInativaException("Chat não encontrado!"));

        mensagemIARepository.deletarMensagensChat(chatEncontrado.getId(), funcionario.getId());
        mensagemIARepository.removerChat(chatEncontrado.getId(), funcionario.getId());
        repository.delete(chatEncontrado);

    }

    public String gerarPrimeiraMensagem(ChatIA chat, Funcionario funcionario) {
        MensagemIA mensagemIA = new MensagemIA();
        LocalDateTime hojeNoBrasil = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

        mensagemIA.setId(mensagemIA.getId());
        mensagemIA.setChat(chat);
        mensagemIA.setTipo("bot");
        mensagemIA.setTexto("Olá! Posso ajudar você com informações sobre vendas de produtos");
        mensagemIA.setHora(hojeNoBrasil);
        mensagemIA.setFuncionario(funcionario);
        mensagemIARepository.save(mensagemIA);

        return mensagemIA.getTexto();
    }
}
