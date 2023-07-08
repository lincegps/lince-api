package br.com.dlsolutions.lincegps.web.rest;


import br.com.dlsolutions.lincegps.domain.Produto;
import br.com.dlsolutions.lincegps.services.ProdutosService;
import br.com.dlsolutions.lincegps.services.dto.ProdutoDTO;
import br.com.dlsolutions.lincegps.services.mapper.ProdutosMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("produtos")
public class ProdutosResource {

    private final ProdutosService produtosService;


    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> obterTodos() {
        log.info("Obter todos os produtos");
        return ResponseEntity.ok(produtosService.findAll());
    }

    @GetMapping("/{idProduto}")
    public ResponseEntity<Produto> obter(@PathVariable("idProduto") final Integer idProduto) {
        log.info("Obtendo produto com id: " + idProduto);
        final Produto produto = produtosService.findById(idProduto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody final Produto produto) {
        log.info("Criando produto");
        final Produto produtoSalvo = produtosService.create(produto);
        return ResponseEntity.ok(produtoSalvo);
    }

    @PutMapping("/{idProduto}")
    public ResponseEntity<Void> alterar(@PathVariable("idProduto") final Integer idProduto, @RequestBody final Produto produtoUpdate) {
        log.info("Atualizando o produto de id: " + idProduto);
        produtosService.update(idProduto, produtoUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> deletar(@PathVariable("idProduto") final Integer idProduto) {
        produtosService.delete(idProduto);
        return ResponseEntity.noContent().build();
    }

}
