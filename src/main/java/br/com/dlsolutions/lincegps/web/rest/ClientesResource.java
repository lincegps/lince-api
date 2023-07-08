package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.domain.Cliente;
import br.com.dlsolutions.lincegps.services.ClienteService;
import br.com.dlsolutions.lincegps.services.dto.ClienteDTO;
import br.com.dlsolutions.lincegps.services.dto.ClienteFiltro;
import br.com.dlsolutions.lincegps.services.dto.VendaDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("clientes")
public class ClientesResource {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> pesquisar(ClienteFiltro filtro, Pageable pageable) {
        log.info("Obter todos os clientes");
        final Page<ClienteDTO> clienteDTOS = clienteService.pesquisar(filtro, pageable);
        return ResponseEntity.ok(clienteDTOS);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Cliente>> obterTodos() {
        log.info("Obter todos os clientes");
        final List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> obter(@PathVariable("idCliente") final Integer idCliente) {
        log.info("Obtendo cliente com id: " + idCliente);
        final Cliente cliente = clienteService.findById(idCliente);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody final ClienteDTO dto) {
        log.info("Criando cliente");
        final Cliente cliente = clienteService.create(dto);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<Void> alterar(@PathVariable("idCliente") final Integer idCliente, @RequestBody final ClienteDTO dto) {
        log.info("Atualizando o Cliente de id: " + idCliente);
        clienteService.update(idCliente, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deletar(@PathVariable("idCliente") final Integer idCliente) {
        clienteService.delete(idCliente);
        return ResponseEntity.noContent().build();
    }

}
