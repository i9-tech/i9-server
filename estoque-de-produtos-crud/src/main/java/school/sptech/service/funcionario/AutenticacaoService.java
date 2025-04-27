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

    @Autowired
    private FuncionarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {

        Optional<Funcionario> funcionario = repository.findByCpf(cpf);

        if (funcionario.isEmpty()) {

            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", cpf));
        }

        return new FuncionarioDetalhesDto(funcionario.get());
    }
}
