package school.sptech.controller.empresa.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import school.sptech.entity.empresa.Empresa;

import java.util.ArrayList;
import java.util.List;

public class EmpresaMapper {

    @Operation(summary = "Transforma um DTO de cadastro de empresa em uma entidade de empresa.",
            description = "Converte os dados de um DTO de cadastro de empresa em uma entidade de empresa, para persistência no banco de dados.")
    public static Empresa transformarEmEntidade(EmpresaCadastroDto empresaEnviadaDto) {
        Empresa entidadeEmpresa = new Empresa();

        entidadeEmpresa.setNome(empresaEnviadaDto.getNome());
        entidadeEmpresa.setCnpj(empresaEnviadaDto.getCnpj());
        entidadeEmpresa.setEndereco(empresaEnviadaDto.getEndereco());
        entidadeEmpresa.setDataCadastro(empresaEnviadaDto.getDataCadastro());
        entidadeEmpresa.setAtivo(true);
        entidadeEmpresa.setWhatsapp(empresaEnviadaDto.getWhatsapp());
        return entidadeEmpresa;
    }

    @Operation(summary = "Transforma um DTO de atualização de empresa em uma entidade de empresa.",
            description = "Converte os dados de um DTO de atualização de empresa em uma entidade de empresa, para atualizar os dados existentes no banco de dados.")
    public static Empresa transformarEmEntidade(EmpresaAtualizarDto empresaEnviadaDto) {
        Empresa entidadeEmpresaParaAtualizar = new Empresa();

        entidadeEmpresaParaAtualizar.setNome(empresaEnviadaDto.getNome());
        entidadeEmpresaParaAtualizar.setEndereco(empresaEnviadaDto.getEndereco());
        entidadeEmpresaParaAtualizar.setWhatsapp(empresaEnviadaDto.getWhatsapp());

        return entidadeEmpresaParaAtualizar;
    }

    @Operation(summary = "Transforma uma entidade de empresa em um DTO de resposta.",
            description = "Converte os dados de uma entidade de empresa em um DTO de resposta, para ser enviado na resposta da API.")
    public static EmpresaListagemDto transformarEmRespostaDto(Empresa entidadeEmpresaResposta) {
        EmpresaListagemDto entidadeEmpresaParaResposta = new EmpresaListagemDto();

        entidadeEmpresaParaResposta.setId(entidadeEmpresaResposta.getId());
        entidadeEmpresaParaResposta.setNome(entidadeEmpresaResposta.getNome());
        entidadeEmpresaParaResposta.setCnpj(entidadeEmpresaResposta.getCnpj());
        entidadeEmpresaParaResposta.setEndereco(entidadeEmpresaResposta.getEndereco());
        entidadeEmpresaParaResposta.setDataCadastro(entidadeEmpresaResposta.getDataCadastro());
        entidadeEmpresaParaResposta.setAtivo(entidadeEmpresaResposta.isAtivo());
        entidadeEmpresaParaResposta.setWhatsapp(entidadeEmpresaResposta.getWhatsapp());

        return entidadeEmpresaParaResposta;
    }

    @Operation(summary = "Transforma uma lista de entidades de empresa em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de empresa em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<EmpresaListagemDto> transformarEmRespostaDtoList(List<Empresa> empresaList) {

        List<EmpresaListagemDto> respostasEmDto = new ArrayList<>();

        for (int i = 0; i < empresaList.size(); i++) {
            EmpresaListagemDto entidadeEmpresaParaResposta = new EmpresaListagemDto();

            entidadeEmpresaParaResposta.setId(empresaList.get(i).getId());
            entidadeEmpresaParaResposta.setNome(empresaList.get(i).getNome());
            entidadeEmpresaParaResposta.setCnpj(empresaList.get(i).getCnpj());
            entidadeEmpresaParaResposta.setEndereco(empresaList.get(i).getEndereco());
            entidadeEmpresaParaResposta.setDataCadastro(empresaList.get(i).getDataCadastro());
            entidadeEmpresaParaResposta.setAtivo(empresaList.get(i).isAtivo());
            entidadeEmpresaParaResposta.setWhatsapp(empresaList.get(i).getWhatsapp());

            respostasEmDto.add(entidadeEmpresaParaResposta);
        }
        return respostasEmDto;
    }
}
