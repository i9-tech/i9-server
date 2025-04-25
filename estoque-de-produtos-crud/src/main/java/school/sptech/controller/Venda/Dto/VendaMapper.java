package school.sptech.controller.Venda.Dto;

import school.sptech.controller.Venda.Dto.VendaRequestDto;
import school.sptech.controller.Venda.Dto.VendaResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;

import java.util.List;

public class VendaMapper {

    public VendaMapper() {
    }

    public static Venda toEntity(VendaRequestDto dto, Funcionario funcionario, List<ItemCarrinho> itens) {
        Venda venda = new Venda();
        venda.setDataVenda(dto.getDataVenda());
        venda.setValorTotal(dto.getValorTotal());
        venda.setMesa(dto.getMesa());
        venda.setFuncionario(funcionario);
        venda.setItensCarrinho(itens);
        venda.setVendaConcluida(dto.getVendaConcluida());
        return venda;
    }

    public static VendaResponseDto toDto(Venda venda) {
        if (venda == null) return null;

        return new VendaResponseDto(
                venda.getId(),
                venda.getDataVenda(),
                venda.getValorTotal(),
                venda.getMesa(),
                venda.getVendaConcluida(),
                venda.getFuncionario().getNome()
        );
    }
}
