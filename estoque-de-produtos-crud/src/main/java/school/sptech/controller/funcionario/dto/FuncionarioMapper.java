package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

public class FuncionarioMapper {

    public FuncionarioMapper() {
    }

    @Operation(summary = "Transforma um DTO de requisição de funcionário em uma entidade de funcionário.",
            description = "Converte os dados de um DTO de requisição de funcionário em uma entidade de funcionário, para persistência no banco de dados.")
    public static Funcionario toEntity(FuncionarioRequestDto requestDto, Empresa empresa){
        if (requestDto == null){
            return null;
        }

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(requestDto.getNome());
        funcionario.setCpf(requestDto.getCpf());
        funcionario.setCargo(requestDto.getCargo());
        funcionario.setDataAdmissao(requestDto.getDataAdmissao());
        funcionario.setAcessoSetorCozinha(requestDto.isAcessoSetorCozinha());
        funcionario.setAcessoSetorEstoque(requestDto.isAcessoSetorEstoque());
        funcionario.setAcessoSetorAtendimento(requestDto.isAcessoSetorAtendimento());
        funcionario.setProprietario(requestDto.isProprietario());
        funcionario.setSenha(requestDto.getSenha());

//        // Agora setamos a empresa
//        funcionario.setEmpresa(idEmpresa);

        return funcionario;
    }



    @Operation(summary = "Transforma uma entidade de funcionário em um DTO de resposta.",
            description = "Converte os dados de uma entidade de funcionário em um DTO de resposta, para ser enviado na resposta da API.")
    //convertendo entity em dto
    public static FuncionarioResponseDto toDto(Funcionario funcionario){
        if (funcionario == null){
            return null;
        }

        return new FuncionarioResponseDto(
                funcionario.getId(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getCargo(),
                funcionario.getDataAdmissao(),
                funcionario.isAcessoSetorCozinha(),
                funcionario.isAcessoSetorEstoque(),
                funcionario.isAcessoSetorAtendimento(),
                funcionario.isProprietario()
        );
    }

    public static Funcionario of(FuncionarioLoginDto funcionarioLoginDto){
        Funcionario funcionario = new Funcionario();

        funcionario.setCpf(funcionarioLoginDto.getCpf());
        funcionario.setSenha(funcionarioLoginDto.getSenha());

        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token){
        FuncionarioTokenDto funcionarioTokenDto = new FuncionarioTokenDto();

        funcionarioTokenDto.setUserId(funcionario.getId());
        funcionarioTokenDto.setEmpresaId(funcionario.getEmpresa().getId());
        funcionarioTokenDto.setNome(funcionario.getNome());
        funcionarioTokenDto.setToken(token);

        return funcionarioTokenDto;
    }



}
