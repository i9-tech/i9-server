package school.sptech.controller.funcionario.dto;

import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

public class FuncionarioMapper {

    public FuncionarioMapper() {
    }

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

        // Agora setamos a empresa
        funcionario.setEmpresa(empresa);

        return funcionario;
    }


    //convertendo entity em dto
    public static FuncionarioResponseDto toDto(Funcionario funcionario){
        if (funcionario == null){
            return null;
        }

        return new FuncionarioResponseDto(
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

        funcionario.setSenha(funcionarioLoginDto.getSenha());
        funcionario.setCpf(funcionarioLoginDto.getCpf());

        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token){
        FuncionarioTokenDto funcionarioTokenDto = new FuncionarioTokenDto();

        funcionarioTokenDto.setToken(token);
        funcionarioTokenDto.setNome(funcionario.getNome());
        funcionarioTokenDto.setUserId(funcionario.getId());

        return funcionarioTokenDto;
    }



}
