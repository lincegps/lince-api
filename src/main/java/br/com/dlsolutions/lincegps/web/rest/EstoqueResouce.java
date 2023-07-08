package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.services.EstoqueService;
import br.com.dlsolutions.lincegps.services.dto.EstoqueDetalhadoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("estoque")
public class EstoqueResouce {

    private final EstoqueService estoqueService;

    @GetMapping("ponto-venda/{idPontoVenda}/produto/{idProduto}")
    public ResponseEntity<Integer> obterQtdEstoqueProdutoPontoVenda(@PathVariable Integer idPontoVenda, @PathVariable Integer idProduto) {
        log.info("Obtendo quantidade disponivel do ponto de venda " + idPontoVenda + " para o produto " + idProduto);
        final Integer qtdProdutoEstoqueDisponivel = estoqueService.obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(idPontoVenda, idProduto);
        return ResponseEntity.ok(qtdProdutoEstoqueDisponivel);
    }

    @GetMapping("produto/{idProduto}/detalhado-ponto-venda")
    public ResponseEntity<List<EstoqueDetalhadoDTO>> obterEstoqueDetalhadoPorPontoDeVenda(
            @PathVariable("idProduto") Integer idProduto
    ) {
        final List<EstoqueDetalhadoDTO> estoqueDetalhadoDTOS = estoqueService
                .obterEstoqueDetalhadoPorPontoDeVenda(idProduto);
        return ResponseEntity.ok(estoqueDetalhadoDTOS);
    }
}
