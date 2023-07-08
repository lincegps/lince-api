package br.com.dlsolutions.lincegps.web.rest;

import br.com.dlsolutions.lincegps.services.DashboasdService;
import br.com.dlsolutions.lincegps.services.dto.DashboardProdutosDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("dashboard")
public class DashboardResource {

    private final DashboasdService dashboasdService;

    @GetMapping("/produtos")
    public ResponseEntity<DashboardProdutosDTO> obterDadosEstoque() {
        final DashboardProdutosDTO dashboardProdutosDTO = dashboasdService.obterDadosEstoque();
        return ResponseEntity.ok(dashboardProdutosDTO);
    }



}
