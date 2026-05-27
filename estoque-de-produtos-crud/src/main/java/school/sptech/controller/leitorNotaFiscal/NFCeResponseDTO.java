package school.sptech.controller.leitorNotaFiscal;

import java.util.List;

public class NFCeResponseDTO {
    private String chNFe;
    private List<ItemNotaDTO> itens;

    public NFCeResponseDTO(
            String chNFe,
            List<ItemNotaDTO> itens
    ) {
        this.chNFe = chNFe;
        this.itens = itens;
    }

    public String getChNFe() {
        return chNFe;
    }

    public void setChNFe(String chNFe) {
        this.chNFe = chNFe;
    }

    public List<ItemNotaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemNotaDTO> itens) {
        this.itens = itens;
    }
}