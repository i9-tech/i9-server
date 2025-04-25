package school.sptech.service.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
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
        Optional<Funcionario> funcionarioOptional = repository.findByCpf(cpf);

        if (funcionarioOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Funcionário: %s não encontrado", cpf));
        }

        // Obtendo nome e senha do Funcionario
        Funcionario funcionario = funcionarioOptional.get();
        String nome = funcionario.getNome(); // Assumindo que existe o método getNome()
        String senha = funcionario.getSenha(); // Assumindo que existe o método getSenha()

        // Retornando o UserDetails com a classe FuncionarioDetalhesDto
        return new FuncionarioDetalhesDto(funcionario, nome, senha);
    }

}
