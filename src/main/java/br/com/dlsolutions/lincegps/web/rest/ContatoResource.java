package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.domain.Contato;
import br.com.dlsolutions.lincegps.services.ContatoService;
import br.com.dlsolutions.lincegps.services.dto.ContatoDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("contatos")
public class ContatoResource {

    private final ContatoService contatoService;

    @GetMapping
    public ResponseEntity<List<ContatoDTO>> findAll() {
        log.info("Buscando todos os contatos");
        final var contatos = contatoService.findAll();
        return ResponseEntity.ok(contatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContatoDTO> find(@PathVariable Integer id) {
        log.info("Buscando contato com id: " + id);
        final var contato = contatoService.findDto(id);
        return ResponseEntity.ok(contato);
    }

    @PostMapping
    public ResponseEntity<ContatoDTO> create(@RequestBody ContatoDTO dto) {
        log.info("Criando contato!");
        final var contato = contatoService.create(dto);
        return ResponseEntity.ok(contato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> update(@PathVariable Integer id, @RequestBody ContatoDTO dto) {
        log.info("Editando contato com id: " + id);
        return ResponseEntity.ok(contatoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info("Deletando contato com id: " + id);
        contatoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
