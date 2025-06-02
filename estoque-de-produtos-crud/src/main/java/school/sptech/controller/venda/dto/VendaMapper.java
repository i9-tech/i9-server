package school.sptech.controller.venda.dto;

import school.sptech.controller.categoria.dto.CategoriaMapper;
import school.sptech.controller.categoria.dto.RespostaCategoriaDashDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class VendaMapper {

    public VendaMapper() {
    }

    public static Venda toEntity(VendaRequestDto dto, Funcionario funcionario, List<ItemCarrinho> itens) {
        Venda venda = new Venda();
        venda.setDataVenda(dto.getDataVenda());
        venda.setValorTotal(dto.getValorTotal());
        venda.setMesa(dto.getMesa());
        venda.setCliente(dto.getCliente());
        venda.setFormaPagamento(dto.getFormaPagamento());
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
                venda.getCliente(),
                venda.getFormaPagamento(),
                venda.getFuncionario().getNome(),
                venda.getItensCarrinho(),
                venda.getVendaConcluida()
        );
    }

    public static VendaKpisRespostaDto toDto(Object[] row) {
        if (row == null || row.length < 6) return null;

        Double lucroDiario = row[0] != null ? ((Number) row[0]).doubleValue() : 0.0;
        Double lucroDiarioOntem = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
        Integer vendasDiaria = row[2] != null ? ((Number) row[2]).intValue() : 0;
        Integer vendasDiariaOntem = row[3] != null ? ((Number) row[3]).intValue() : 0;
        Double lucroLiquidoDiario = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;
        Double totalMercadoriaDiario = row[5] != null ? ((Number) row[5]).doubleValue() : 0.0;

        return new VendaKpisRespostaDto(
                lucroDiario,
                lucroDiarioOntem,
                vendasDiaria,
                vendasDiariaOntem,
                lucroLiquidoDiario,
                totalMercadoriaDiario
        );

    }


    public static List<VendaKpisRespostaDto> toDtoListObject(List<Object[]> rows) {
        if (rows == null || rows.isEmpty()) return Collections.emptyList();

        return rows.stream()
                .map(VendaMapper::toDto)
                .filter(Objects::nonNull)
                .toList();
    }

}
