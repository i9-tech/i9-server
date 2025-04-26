package school.sptech.controller.funcionario.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.funcionario.Funcionario;

public class FuncionarioMapper {

    public FuncionarioMapper() {
    }

    @Operation(summary = "Transforma um DTO de requisição de funcionário em entidade de funcionário",
            description = "Converte os dados de um DTO de requisição de funcionário em uma entidade de funcionário para persistência no banco de dados.")
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



    @Operation(summary = "Transforma uma entidade de funcionario em DTO de resposta",
            description = "Converte os dados de uma entidade de funcionario em um DTO de resposta para ser enviado na resposta da API.")
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
