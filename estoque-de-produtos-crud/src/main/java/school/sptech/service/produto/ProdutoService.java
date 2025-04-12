package school.sptech.service.produto;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.controller.produto.dto.ProdutoMapper;
import school.sptech.controller.produto.dto.ProdutoRequestDto;
import school.sptech.controller.produto.dto.ProdutoResponseDto;
import school.sptech.entity.funcionario.Funcionario;
import school.sptech.entity.produto.Produto;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.repository.funcionario.FuncionarioRepository;
import school.sptech.repository.produto.ProdutoRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public ProdutoResponseDto cadastrarProduto(@Valid ProdutoRequestDto produtoRequestDto, Integer idFuncionario) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        Produto produto = ProdutoMapper.toEntity(produtoRequestDto);
        produto.setFuncionario(funcionario);

        Produto produtoCadastrado = repository.save(produto);

        return ProdutoMapper.toDto(produtoCadastrado);
    }

    public List<ProdutoResponseDto> listarProdutoPorEmpresa(Integer idFuncionario) {

        List<Produto> todosProdutosEmpresa = repository.buscarProdutosDaEmpresaDoFuncionario(idFuncionario);
        if (todosProdutosEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return todosProdutosEmpresa.stream()
                .map(ProdutoMapper::toDto)
                .collect(Collectors.toList());
    }


    public ProdutoResponseDto editarProduto(Integer id, Integer idFuncionario, @Valid ProdutoRequestDto produtoParaEditar) {
        Optional<Produto> produtoPorId = repository.findById(id);

        if (produtoPorId.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado na empresa especificada.");
        }

        Produto produtoExiste = produtoPorId.get();

        produtoExiste.setCodigo(produtoParaEditar.getCodigo());
        produtoExiste.setNome(produtoParaEditar.getNome());
        produtoExiste.setQuantidade(produtoParaEditar.getQuantidade());
        produtoExiste.setDataVencimento(produtoParaEditar.getDataVencimento());
        produtoExiste.setValorCompra(produtoParaEditar.getValorCompra());
        produtoExiste.setValorUnitario(produtoParaEditar.getValorUnitario());
        produtoExiste.setQuantidadeMin(produtoParaEditar.getQuantidadeMin());
        produtoExiste.setQuantidadeMax(produtoParaEditar.getQuantidadeMax());
        produtoExiste.setDescricao(produtoParaEditar.getDescricao());
        produtoExiste.setCategoria(produtoParaEditar.getCategoria());
        produtoExiste.setSetor(produtoParaEditar.getSetor());
        produtoExiste.setDataRegistro(produtoParaEditar.getDataRegistro());

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Funcionário não encontrado"));

        produtoExiste.setFuncionario(funcionario);

        return ProdutoMapper.toDto(repository.save(produtoExiste));
    }


    public void removerPorId(Integer id, Integer idFuncionario) {
        Optional<Produto> produto = repository.buscarProdutoPorIdComMesmaEmpresaDoFuncionarioInformadoParametro(id, idFuncionario);

        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado ou não pertence à empresa do funcionário informado.");
        }

        repository.delete(produto.get());
    }


}
