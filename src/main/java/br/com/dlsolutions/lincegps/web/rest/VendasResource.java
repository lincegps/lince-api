package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import br.com.dlsolutions.lincegps.services.VendasService;
import br.com.dlsolutions.lincegps.services.dto.VendaDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaPorPeriodoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("vendas")
public class VendasResource {

    private final VendasService vendasService;

    @GetMapping
    public ResponseEntity<Page<VendaDTO>> pesquisar(VendaFiltroDTO filtro, Pageable pageable) {
        log.info("Obter todas as vendas");
        final Page<VendaDTO> vendas = vendasService.pesquisar(filtro, pageable);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> find(@PathVariable Integer id) {
        final var vendaDTO = vendasService.findDto(id);
        return ResponseEntity.ok(vendaDTO);
    }


    @GetMapping("{numeroSerie}/numero-serie")
    public ResponseEntity<VendaDTO> findPor(@PathVariable String numeroSerie) {
        final var vendaDTO = vendasService.findPorNumeroDeSerie(numeroSerie);
        return ResponseEntity.ok(vendaDTO);
    }


    @GetMapping("/sem-paginacao")
    public ResponseEntity<List<VendaDTO>> pesquisar(VendaFiltroDTO filtro) {
        log.info("Obter todas as vendas");
        final List<VendaDTO> vendas = vendasService.pesquisarSemPaginacao(filtro);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/qtd-por-periodo")
    public ResponseEntity<VendaPorPeriodoDTO> obterTotalDeVendasPorPeriodo(VendaFiltroDTO filtro) {
        log.info("Obter total de vendas por periodo");
        final VendaPorPeriodoDTO vendaPorPeriodoDTO = vendasService.obterTotalDeVendasPorPeriodo(filtro);
        return ResponseEntity.ok(vendaPorPeriodoDTO);
    }

    @GetMapping("/data-vencimento")
    public LocalDate obterDataVencimento(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataBase,
            @RequestParam TipoProduto tipoProduto,
            @RequestParam Integer clienteId,
            @RequestParam Integer pontoVendaId
    ) {
        final var localDate = vendasService.obterDataVencimento(dataBase, tipoProduto, clienteId, pontoVendaId);
        return localDate;
    }

    @PostMapping
    public ResponseEntity<VendaDTO> criarVenda(@RequestBody VendaDTO dto) {
        log.info("Criando venda para o ponto de venda: " + dto.getPontoVenda().getNome());
        final VendaDTO vendaDTO = vendasService.criarVenda(dto);
        return ResponseEntity.ok(vendaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> update(@PathVariable Integer id, @RequestBody VendaDTO dto) {
        log.info("Atualizando venda com id: " + id);
        final VendaDTO vendaDTO = vendasService.update(id, dto);
        return ResponseEntity.ok(vendaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id) {
        log.info("Cancelar venda");
        vendasService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exrpotar(VendaFiltroDTO filtro) {
        return ResponseEntity.ok().headers(excelHeaders())
                .body(vendasService.exportar(filtro));
    }

    private HttpHeaders excelHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/vnd.ms-excel");
        headers.setContentDispositionFormData("Documento Excel", "itens.xlsx");
        return headers;
    }


}
