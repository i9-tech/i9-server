package school.sptech.controller.funcionario.dto;

import lombok.AllArgsConstructor;
import school.sptech.entity.funcionario.Funcionario;

@AllArgsConstructor
public class FuncionarioMapper {
    //convertendo dto em entity
    public static Funcionario toEntity(FuncionarioRequestDto requestDto, int fkEmpresa){
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
        funcionario.setFkEmpresa(fkEmpresa);
        funcionario.setSenha(requestDto.getSenha());

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


}
