package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.services.MovimentacaoService;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoDTO;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoFiltroDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("movimentacoes")
public class MovimentacaoResource {

    private final MovimentacaoService movimentacaoService;

    @GetMapping
    public ResponseEntity<Page<MovimentacaoDTO>> search(MovimentacaoFiltroDTO filtro,  Pageable pageable) {
        log.info("Obter movimentações");
        final Page<MovimentacaoDTO> movimentacaoDTOS = movimentacaoService.pesquisar(filtro, pageable);
        return ResponseEntity.ok(movimentacaoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> find(@PathVariable Integer id) {
        log.info("Obter movimentação com id " + id);
        final MovimentacaoDTO movimentacaoDTO = movimentacaoService.findDto(id);
        return ResponseEntity.ok(movimentacaoDTO);
    }

    @PostMapping
    public ResponseEntity<MovimentacaoDTO> create(@RequestBody final MovimentacaoDTO dto) {
        log.info("Criando movimentaçao o tipo: " + dto.getTipoMovimentacao());
        return ResponseEntity.ok(movimentacaoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentacaoDTO> update(@PathVariable final Integer id, @RequestBody final MovimentacaoDTO dto) {
        log.info("Aterando movimentação com id " + id);
        final MovimentacaoDTO movimentacaoDTO = movimentacaoService.update(id, dto);
        return ResponseEntity.ok(movimentacaoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deletando movimentação com id " + id);
        movimentacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
