package school.sptech.controller.empresa.dto;

import school.sptech.controller.empresa.dto.EmpresaAtualizarDto;
import school.sptech.controller.empresa.dto.EmpresaCadastroDto;
import school.sptech.controller.empresa.dto.EmpresaListagemDto;
import school.sptech.entity.empresa.Empresa;

import java.util.ArrayList;
import java.util.List;

public class EmpresaMapper {
    public static Empresa transformarEmEntidade(EmpresaCadastroDto empresaEnviadaDto) {
        Empresa entidadeEmpresa = new Empresa();

        entidadeEmpresa.setNome(empresaEnviadaDto.getNome());
        entidadeEmpresa.setCnpj(empresaEnviadaDto.getCnpj());
        entidadeEmpresa.setEndereco(empresaEnviadaDto.getEndereco());
        entidadeEmpresa.setDataCadastro(empresaEnviadaDto.getDataCadastro());
        entidadeEmpresa.setAtivo(true);
        return entidadeEmpresa;
    }

    public static Empresa transformarEmEntidade(EmpresaAtualizarDto empresaEnviadaDto) {
        Empresa entidadeEmpresaParaAtualizar = new Empresa();

        entidadeEmpresaParaAtualizar.setNome(empresaEnviadaDto.getNome());
        entidadeEmpresaParaAtualizar.setEndereco(empresaEnviadaDto.getEndereco());

        return entidadeEmpresaParaAtualizar;
    }

    public static EmpresaListagemDto transformarEmRespostaDto(Empresa entidadeEmpresaResposta) {
        EmpresaListagemDto entidadeEmpresaParaResposta = new EmpresaListagemDto();

        entidadeEmpresaParaResposta.setId(entidadeEmpresaResposta.getId());
        entidadeEmpresaParaResposta.setNome(entidadeEmpresaResposta.getNome());
        entidadeEmpresaParaResposta.setCnpj(entidadeEmpresaResposta.getCnpj());
        entidadeEmpresaParaResposta.setEndereco(entidadeEmpresaResposta.getEndereco());
        entidadeEmpresaParaResposta.setDataCadastro(entidadeEmpresaResposta.getDataCadastro());
        entidadeEmpresaParaResposta.setAtivo(entidadeEmpresaResposta.isAtivo());

        return entidadeEmpresaParaResposta;
    }

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

            respostasEmDto.add(entidadeEmpresaParaResposta);
        }
        return respostasEmDto;
    }
}
