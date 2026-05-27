package school.sptech.service.leitorNotaFiscal;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import school.sptech.controller.leitorNotaFiscal.ItemNotaDTO;
import school.sptech.controller.leitorNotaFiscal.NFCeResponseDTO;

import java.util.ArrayList;
import java.util.List;

@Service
public class NFCeService {

    public NFCeResponseDTO processarHtml(String html, String chNFe) {
        Document doc = Jsoup.parse(html);
        List<ItemNotaDTO> itens = new ArrayList<>();

        // 1. Captura todas as tabelas de dados básicos (visíveis) e de detalhes (ocultas)
        Elements tabelasAbertas = doc.select("table.toggle.box");
        Elements tabelasOcultas = doc.select("table.toggable.box, table.toggable");

        // 2. Garantimos que os tamanhos batem para iterar com precisão cirúrgica por índice
        int totalItens = Math.min(tabelasAbertas.size(), tabelasOcultas.size());

        for (int i = 0; i < totalItens; i++) {
            Element tabelaAberta = tabelasAbertas.get(i);
            Element tabelaOculta = tabelasOcultas.get(i);

            Elements spansDadosBasicos = tabelaAberta.select("span");
            if (spansDadosBasicos.size() < 5) continue;

            try {
                // Pega os dados básicos EXATOS do item 'i'
                String descricao = spansDadosBasicos.get(1).text();

                String qtdTexto = spansDadosBasicos.get(2).text()
                        .replace(".", "").replace(",", ".");

                String valorTexto = spansDadosBasicos.get(4).text()
                        .replace(".", "").replace(",", ".");

                double quantidade = Double.parseDouble(qtdTexto);
                double valor = Double.parseDouble(valorTexto);

                // Pega o código do produto EXATO do item 'i'
                String codigoProduto = "";
                Element labelCodigo = tabelaOculta.selectFirst("label:contains(Código do Produto)");

                if (labelCodigo != null) {
                    Element spanCodigo = labelCodigo.nextElementSibling();
                    if (spanCodigo != null && spanCodigo.tagName().equals("span")) {
                        codigoProduto = spanCodigo.text().trim();
                    }
                }

                // Monta o DTO emparelhado perfeitamente
                ItemNotaDTO item = new ItemNotaDTO(
                        descricao,
                        quantidade,
                        valor,
                        codigoProduto
                );

                itens.add(item);

            } catch (Exception e) {
                System.out.println("Erro ao processar item no índice: " + i);
                e.printStackTrace();
            }
        }

        return new NFCeResponseDTO(chNFe, itens);
    }
}