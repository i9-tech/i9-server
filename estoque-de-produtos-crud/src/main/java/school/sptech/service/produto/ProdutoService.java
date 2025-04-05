package school.sptech.service.produto;

import org.springframework.beans.factory.annotation.Autowired;
import school.sptech.entity.produto.Produto;
import school.sptech.exception.EntidadeConflictException;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.exception.ValidacaoException;
import school.sptech.repository.produto.ProdutoRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;


    public Produto cadastrarProduto(Produto produto, int fkEmpresa){

        if (produto.getFkEmpresa() != fkEmpresa) {
            throw new EntidadeConflictException("Esse usuário já está cadastrado!");
        }

        return repository.save(produto);
    }












    

    public List<Produto> listarPorEmpresa(int fkEmpresa) {
        List<Produto> todosProdutosEmpresa = repository.findByFkEmpresa(fkEmpresa);

        if (todosProdutosEmpresa.isEmpty()) {
            return Collections.emptyList();
        }

        return todosProdutosEmpresa;
    }

    public List<Produto> listarPorNome(String nomeProduto, int fkEmpresa) {

        List<Produto> todosProdutosPorNome = repository.findByNomeProdutoContainingIgnoreCaseAndFkEmpresa(nomeProduto, fkEmpresa);

        if (todosProdutosPorNome.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Nenhum produto pelo nome %s foi encontrado para a empresa ID: %d", nomeProduto, fkEmpresa));
        }

        return todosProdutosPorNome;
    }

    public List<Produto> listarPorCategoria(String categoriaProduto, int fkEmpresa) {

        List<Produto> todosProdutosPorCategoria = repository.findByCategoriaContainingIgnoreCaseAndFkEmpresa(categoriaProduto, fkEmpresa);

        if (todosProdutosPorCategoria.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Nenhum produto pela categoria %s foi encontrado para a empresa ID: %d", categoriaProduto, fkEmpresa));
        }

        return todosProdutosPorCategoria;
    }

    public List<Produto> listarPorSetor(String setorAlimenticio, int fkEmpresa) {

        List<Produto> todosProdutosPorSetor = repository.findBySetorAlimenticioContainingIgnoreCaseAndFkEmpresa(setorAlimenticio, fkEmpresa);

        if (todosProdutosPorSetor.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Nenhum produto do setor %s foi encontrado para a empresa ID: %d", setorAlimenticio, fkEmpresa));
        }

        return todosProdutosPorSetor;
    }

    public List<Produto> listarEstoqueBaixo(int fkEmpresa) {

        List<Produto> produtos = repository.findByFkEmpresa(fkEmpresa);
        List<Produto> produtosEstoqueBaixo = new ArrayList<>();

        for (Produto produtoDaVez : produtos) {
            if (produtoDaVez.getQuantidade() < produtoDaVez.getQuantidadeMin()) {
                produtosEstoqueBaixo.add(produtoDaVez);
            }
        }

        if (produtosEstoqueBaixo.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Nenhum produto de estoque baixo foi encontrado para a empresa ID: %d", fkEmpresa));
        }

        return produtosEstoqueBaixo;
    }

    public List<Produto> listarEstoqueAlto(int fkEmpresa) {

        List<Produto> produtos = repository.findByFkEmpresa(fkEmpresa);
        List<Produto> produtosEstoqueAlto = new ArrayList<>();

        for (Produto produtoDaVez : produtos) {
            if (produtoDaVez.getQuantidade() > produtoDaVez.getQuantidadeMax()) {
                produtosEstoqueAlto.add(produtoDaVez);
            }
        }

        if (produtosEstoqueAlto.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Nenhum produto de estoque alto foi encontrado para a empresa ID: %d", fkEmpresa));
        }

        return produtosEstoqueAlto;
    }

    public void removerPorId(int id, int fkEmpresa) {

        Optional<Produto> produto = repository.findByIdAndFkEmpresa(id, fkEmpresa);

        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado na empresa especificada.");
        }

        repository.deleteById(produto.get());

    }

    public Produto editarProduto(int id, Produto produtoParaEditar, int fkEmpresa){
        Optional<Produto> produto = repository.findByIdAndFkEmpresa(id, fkEmpresa);


        if (produto.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Produto não encontrado na empresa especificada.");
        }

        Produto produtoExiste = produto.get();

        validarProduto(produtoParaEditar);

        produtoExiste.setId(id);
        produtoExiste.setNomeProduto(produtoParaEditar.getNomeProduto());
        produtoExiste.setDataVencimento(produtoParaEditar.getDataVencimento());
        produtoExiste.setValorUnitario(produtoParaEditar.getValorUnitario());
        produtoExiste.setDescricao(produtoParaEditar.getDescricao());
        produtoExiste.setCategoria(produtoParaEditar.getCategoria());
        produtoExiste.setSetorAlimenticio(produtoParaEditar.getSetorAlimenticio());


        return repository.save(produtoExiste);
    }

    private void validarProduto(Produto produto){
        if (produto.getNomeProduto() == null || produto.getNomeProduto().trim().isEmpty()){
            throw new ValidacaoException("O nome do produto é obrigatório");
        }

        if (produto.getCategoria() == null || produto.getCategoria().trim().isEmpty()){
            throw new ValidacaoException("A categoria do produto é obrigatório");
        }

        if (produto.getSetorAlimenticio() == null || produto.getSetorAlimenticio().trim().isEmpty()){
            throw new ValidacaoException("O setor alimentício do produto é obrigatório");
        }

        if (produto.getValorUnitario() <= 0) {
            throw new ValidacaoException("O valor unitário deve ser maior que zero");
        }

        if (produto.getDataVencimento() == null){
            throw new ValidacaoException("A data de vencimento do produto é obrigatório");
        }

    }
}
