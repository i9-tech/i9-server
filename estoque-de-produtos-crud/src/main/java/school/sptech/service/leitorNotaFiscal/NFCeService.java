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

    public NFCeResponseDTO processarHtml(
            String html,
            String chNFe
    ) {

        Document doc = Jsoup.parse(html);

        List<ItemNotaDTO> itens =
                new ArrayList<>();

        // 🔥 pega tabela de produtos
        Elements produtos =
                doc.select("#Prod table.toggle.box");

        for (Element produto : produtos) {

            Elements spans =
                    produto.select("span");

            if (spans.size() >= 5) {

                try {

                    String descricao =
                            spans.get(1).text();

                    String qtdTexto =
                            spans.get(2)
                                    .text()
                                    .replace(".", "")
                                    .replace(",", ".");

                    String valorTexto =
                            spans.get(4)
                                    .text()
                                    .replace(".", "")
                                    .replace(",", ".");

                    double quantidade =
                            Double.parseDouble(
                                    qtdTexto
                            );

                    double valor =
                            Double.parseDouble(
                                    valorTexto
                            );

                    ItemNotaDTO item =
                            new ItemNotaDTO(
                                    descricao,
                                    quantidade,
                                    valor
                            );

                    itens.add(item);

                } catch (Exception e) {

                    System.out.println(
                            "Erro ao processar item"
                    );

                    e.printStackTrace();
                }
            }
        }

        return new NFCeResponseDTO(
                chNFe,
                itens
        );
    }
}