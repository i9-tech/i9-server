package school.sptech.service.venda;

import org.springframework.stereotype.Service;
import school.sptech.controller.venda.dto.VendaMapper;
import school.sptech.controller.venda.dto.VendaRequestDto;
import school.sptech.controller.venda.dto.VendaResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.itemCarrinho.ItemCarrinho;
import school.sptech.entity.venda.Venda;
import school.sptech.repository.venda.VendaRepository;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.itemCarrinho.ItemCarrinhoRepository;
import school.sptech.repository.produto.ProdutoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {
    private final FuncionarioRepository funcionarioRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final VendaRepository vendaRepository;

    public VendaService(FuncionarioRepository funcionarioRepository, ItemCarrinhoRepository itemCarrinhoRepository, VendaRepository vendaRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
        this.vendaRepository = vendaRepository;
    }

    public Venda criarVenda(VendaRequestDto vendaRequest) {
        Funcionario funcionario = funcionarioRepository.findById(vendaRequest.getFuncionarioId())
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        List<ItemCarrinho> itens = itemCarrinhoRepository.findAllById(vendaRequest.getItens());
        if (itens.isEmpty()) {
            throw new RuntimeException("Itens não encontrados");
        }

        Venda venda = VendaMapper.toEntity(vendaRequest, funcionario, itens);
        venda.setValorTotal(calcularValorTotal(itens));

        return vendaRepository.save(venda);
    }

    public Double calcularValorTotal(List<ItemCarrinho> itens) {
        return itens.stream()
                .collect(Collectors.groupingBy(item -> item))  // Agrupar os itens pelo próprio objeto
                .entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getValorUnitario() * entry.getValue().size())  // Multiplicando valorUnitario pela quantidade (tamanho do grupo)
                .sum();  // Somando o valor total
    }

    public VendaResponseDto buscarVendaPorId(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
        return VendaMapper.toDto(venda);
    }


    public void excluirVenda(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        vendaRepository.delete(venda);
    }

    public Double calcularLucroTotal(Integer idFuncionario, LocalDate dataVenda) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario).orElseThrow();
        Integer idEmpresa = funcionario.getEmpresa().getId();

        List<Venda> vendas = vendaRepository.findAllByDataVenda(dataVenda);
        return vendas.stream()
                .filter(venda -> venda.getFuncionario().getEmpresa().getId().equals(idEmpresa))
                .mapToDouble(Venda::getValorTotal)
                .sum();
    }

}
