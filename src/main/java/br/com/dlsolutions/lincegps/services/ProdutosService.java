package br.com.dlsolutions.lincegps.services;


import br.com.dlsolutions.lincegps.domain.Produto;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.repository.ProdutoRepository;
import br.com.dlsolutions.lincegps.services.dto.ProdutoDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.ProdutosMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProdutosService {

    private final ProdutoRepository produtoRepository;
    private final ProdutosMapper produtosMapper;

    @Transactional(readOnly = true)
    public List<ProdutoDTO> findAll() {
        final List<Produto> produtos = produtoRepository.findAllByIndAtivo();
        return produtosMapper.entidadesParaDTOs(produtos);
    }

    @Transactional(readOnly = true)
    public Produto findById(Integer idProduto) {
        return produtoRepository.findById(idProduto)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com id: " + idProduto));
    }

    @Transactional
    public Produto create(Produto produto) {
        produto.setId(null);
        exisitsByName(produto.getNome());
        return produtoRepository.save(produto);
    }

    @Transactional
    public void update(Integer idProduto, Produto produtoUpdate) {
        Produto produto = findById(idProduto);

        exisitsByName(produtoUpdate.getNome());

        produto.setNome(produtoUpdate.getNome());
//        produto.setQuantidade(produtoUpdate.getQuantidade());
//        produto.setDataValidade(produtoUpdate.getDataValidade());

        produtoRepository.save(produto);

    }

    @Transactional
    public void delete(Integer idProduto) {
        final Produto produto = findById(idProduto);
        produto.setIndAtivo(Indicador.N);
        produtoRepository.save(produto);
    }

    private  void exisitsByName(String nomeProduto) {
        final Optional<Produto> produtoOptional = produtoRepository.findByNome(nomeProduto);
        if(produtoOptional.isPresent()) {
            throw new BadRequestException("Já existe produto com esse nome cadastrado, por favor digitar outro!");
        }
    }

}
