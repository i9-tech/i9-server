package school.sptech.controller.empresa.dto;

import io.swagger.v3.oas.annotations.Operation;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.plano.GerenciamentoPlano;
import school.sptech.entity.plano.PlanoTemplate;
import school.sptech.controller.plano.dto.GerenciamentoPlanoResponse;
import school.sptech.controller.plano.dto.PlanoTemplateResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        EmpresaListagemDto dto = new EmpresaListagemDto();

        dto.setId(entidadeEmpresaResposta.getId());
        dto.setNome(entidadeEmpresaResposta.getNome());
        dto.setCnpj(entidadeEmpresaResposta.getCnpj());
        dto.setEndereco(entidadeEmpresaResposta.getEndereco());
        dto.setDataCadastro(entidadeEmpresaResposta.getDataCadastro());
        dto.setAtivo(entidadeEmpresaResposta.isAtivo());
        dto.setWhatsapp(entidadeEmpresaResposta.getWhatsapp());

        // nomePlano (se tiver gerenciamento/planoTemplate)
        if (entidadeEmpresaResposta.getGerenciamentoPlano() != null &&
                entidadeEmpresaResposta.getGerenciamentoPlano().getPlanoTemplate() != null &&
                entidadeEmpresaResposta.getGerenciamentoPlano().getPlanoTemplate().getTipo() != null) {

            dto.setNomePlano(entidadeEmpresaResposta.getGerenciamentoPlano().getPlanoTemplate().getTipo().toString());

            // Mapear GerenciamentoPlano -> GerenciamentoPlanoResponse (evita passar entidade)
            GerenciamentoPlano gp = entidadeEmpresaResposta.getGerenciamentoPlano();
            GerenciamentoPlanoResponse gpResp = new GerenciamentoPlanoResponse();

            gpResp.setId(gp.getId());
            gpResp.setPeriodo(gp.getPeriodo());
            gpResp.setDataAdesao(gp.getDataAdesao());
            gpResp.setDataInicio(gp.getDataInicio());
            gpResp.setDataFim(gp.getDataFim());
            gpResp.setTesteGratis(gp.isTesteGratis());
            gpResp.setDiasTeste(gp.getDiasTeste());
            gpResp.setAtivo(gp.isAtivo());
            gpResp.setValorCobrado(gp.getValorCobrado());

            if (gp.getEmpresa() != null) {
                gpResp.setEmpresaId(gp.getEmpresa().getId());
                gpResp.setEmpresaNome(gp.getEmpresa().getNome());
            }

            PlanoTemplate pt = gp.getPlanoTemplate();
            if (pt != null) {
                PlanoTemplateResponse ptr = new PlanoTemplateResponse();
                ptr.setId(pt.getId());
                ptr.setTipo(pt.getTipo() != null ? pt.getTipo().toString() : null);
                ptr.setDescricao(pt.getDescricao());
                ptr.setPrecoMensal(pt.getPrecoMensal());
                ptr.setPrecoMensalComDescontoAnual(pt.getPrecoMensalComDescontoAnual());
                ptr.setPrecoAnual(pt.getPrecoAnual());
                ptr.setQtdUsuarios(pt.getQtdUsuarios());
                ptr.setQtdSuperUsuarios(pt.getQtdSuperUsuarios());
                ptr.setAcessoRelatorioWhatsApp(Boolean.valueOf(pt.isAcessoRelatorioWhatsApp()));
                ptr.setAcessoDashboard(Boolean.valueOf(pt.isAcessoDashboard()));
                gpResp.setPlanoTemplate(ptr);
            }

            dto.setGerenciamentoPlano(gpResp);
        } else {
            dto.setNomePlano("SEM PLANO");
            dto.setGerenciamentoPlano(null);
        }

        return dto;
    }

    @Operation(summary = "Transforma uma lista de entidades de empresa em uma lista de DTOs de resposta.",
            description = "Converte uma lista de entidades de empresa em uma lista de DTOs de resposta, para ser enviada na resposta da API.")
    public static List<EmpresaListagemDto> transformarEmRespostaDtoList(List<Empresa> empresaList) {
        if (empresaList == null) return new ArrayList<>();
        return empresaList.stream()
                .map(EmpresaMapper::transformarEmRespostaDto)
                .collect(Collectors.toList());
    }
}
