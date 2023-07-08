package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.services.PontoVendaService;
import br.com.dlsolutions.lincegps.services.dto.PontoVendaDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("ponto-vendas")
public class PontoVendasResouce {

    private final PontoVendaService pontoVendaService;

    @GetMapping
    public ResponseEntity<List<PontoVendaDTO>> obterTodos (){
        log.info("Obter todos os Pontos de Vendas");
        final List<PontoVendaDTO> pontoVendas = pontoVendaService.findAll();
        return ResponseEntity.ok(pontoVendas);
    }

    @GetMapping("/{idPontoVenda}")
    public ResponseEntity<PontoVendaDTO> obter (@PathVariable ("idPontoVenda") final Integer idPontoVenda){
        log.info("Obtendo Ponto de Venda com id: "+ idPontoVenda);
        final PontoVendaDTO pontoVendaDTO = pontoVendaService.find(idPontoVenda);
        return ResponseEntity.ok(pontoVendaDTO);
    }

    @PostMapping
    public ResponseEntity<PontoVendaDTO> criar (@RequestBody final PontoVendaDTO dto){
        log.info("Criando Ponto de Venda");
        final PontoVendaDTO pontoVendaDTO = pontoVendaService.create(dto);
        return ResponseEntity.ok(pontoVendaDTO);
    }

    @PutMapping("/{idPontoVenda}")
    public ResponseEntity<PontoVendaDTO> alterar (@PathVariable("idPontoVenda") final Integer idPontoVenda, @RequestBody final PontoVendaDTO dto){
        log.info("Atualizando o Ponto de Venda de id: "+ idPontoVenda);
        final PontoVendaDTO pontoVendaDTO = pontoVendaService.update(idPontoVenda, dto);
        return ResponseEntity.ok(pontoVendaDTO);
    }

    @DeleteMapping("/{idPontoVenda}")
    public ResponseEntity<Void> deletar(@PathVariable("idPontoVenda") final Integer idPontoVenda){
        pontoVendaService.delete(idPontoVenda);
        return ResponseEntity.noContent().build();
    }

}
