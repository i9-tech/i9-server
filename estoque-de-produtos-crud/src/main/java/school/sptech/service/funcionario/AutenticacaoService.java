package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import school.sptech.controller.funcionario.dto.FuncionarioDetalhesDto;
import school.sptech.entity.funcionario.Funcionario;

import school.sptech.repository.funcionario.FuncionarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    private final FuncionarioRepository repository;

    public AutenticacaoService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Funcionario> funcionario = repository.findByCpf(username);

        if (funcionario.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new FuncionarioDetalhesDto(funcionario.get());
    }

}
