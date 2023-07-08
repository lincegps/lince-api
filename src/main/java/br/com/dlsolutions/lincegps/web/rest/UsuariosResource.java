package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.domain.enums.StatusUsuario;
import br.com.dlsolutions.lincegps.services.UsuarioService;
import br.com.dlsolutions.lincegps.services.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("usuarios")
public class UsuariosResource {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obterTodos() {
        log.info("Obter todos os usuarios");
        final List<UsuarioDTO> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO dto) {
        log.info("Criando um novo usuário");
        final UsuarioDTO usuarioDTO = usuarioService.create(dto);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{idUsuario}")
    public ResponseEntity<Void> alterarStatus(
            @PathVariable Integer idUsuario,
            @RequestParam StatusUsuario statusUsuario
    ) {
        usuarioService.alterarStatus(idUsuario, statusUsuario);
        return ResponseEntity.noContent().build();
    }
}
