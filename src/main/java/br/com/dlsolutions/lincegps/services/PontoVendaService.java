package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Endereco;
import br.com.dlsolutions.lincegps.domain.PontoVenda;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import br.com.dlsolutions.lincegps.repository.PontoVendaRepository;
import br.com.dlsolutions.lincegps.services.dto.PontoVendaDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.PontoVendaMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PontoVendaService {

    private final PontoVendaRepository pontoVendaRepository;

    private final PontoVendaMapper pontoVendaMapper;

    @Transactional(readOnly = true)
    public PontoVendaDTO find(Integer idPontoVenda) {
        return pontoVendaMapper.entidadeParaDTO(findById(idPontoVenda));
    }

    @Transactional(readOnly = true)
    public PontoVenda findById(Integer idPontoVenda) {
        return pontoVendaRepository.findById(idPontoVenda)
                .orElseThrow(() -> new ResourceNotFoundException("PontoVenda não encontrado com id: " + idPontoVenda));
    }

    @Transactional(readOnly = true)
    public PontoVenda findByTipo(TipoPontoVenda tipo) {
        return pontoVendaRepository.findByTipo(tipo)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de venda com tipo, " + tipo.getCodigo() + " não encontrado."));
    }

    @Transactional(readOnly = true)
    public PontoVenda getPontoVendaOrigem(Integer idPontoVendaOrigem) {
        return Optional.ofNullable(idPontoVendaOrigem)
                .map(this::findById)
                .orElse(findByTipo(TipoPontoVenda.CENTRO_DISTRIBUICAO));
    }

    @Transactional(readOnly = true)
    public List<PontoVendaDTO> findAll() {
        List<PontoVenda> pontoVendas = pontoVendaRepository.findAllByIndAtivo(Indicador.S);
        return pontoVendaMapper.entidadesParaDTOs(pontoVendas);
    }

    @Transactional
    public PontoVendaDTO create(PontoVendaDTO dto) {
        naoExistePontoVendaPeloNomeCidadeEBairro(dto);
        dto.setId(null);
        final PontoVenda obj = PontoVenda.builder()
                .nome(dto.getNome())
                .telefones(dto.getTelefones())
                .responsavel(dto.getResponsavel())
                .emails(dto.getEmails())
                .endereco(Endereco.builder()
                        .cidade(dto.getCidade())
                        .bairro(dto.getBairro())
                        .build())
                .build();
        final PontoVenda pontoVenda = pontoVendaRepository.saveAndFlush(obj);
        return pontoVendaMapper.entidadeParaDTO(pontoVenda);
    }

    @Transactional
    public PontoVendaDTO update(Integer idPontoVenda, PontoVendaDTO dto) {
        final PontoVenda pontoVenda = findById(idPontoVenda);

        if (!dto.getNome().equals(pontoVenda.getNome())
                || !dto.getCidade().equals(pontoVenda.getEndereco().getCidade())
                || !dto.getBairro().equals(pontoVenda.getEndereco().getBairro())
        ) {
            naoExistePontoVendaPeloNomeCidadeEBairro(dto);
        }
        pontoVenda.setNome(dto.getNome());
        pontoVenda.setTelefones(dto.getTelefones());
        pontoVenda.setResponsavel(dto.getResponsavel());
        pontoVenda.setEmails(dto.getEmails());
        pontoVenda.setEndereco(Endereco.builder()
                .cidade(dto.getCidade())
                .bairro(dto.getBairro())
                .build());
        final PontoVenda produtoAtualizado = pontoVendaRepository.saveAndFlush(pontoVenda);
        return pontoVendaMapper.entidadeParaDTO(produtoAtualizado);
    }

    @Transactional
    public void delete(Integer idPontoVenda) {
        final PontoVenda pontoVenda = findById(idPontoVenda);
        pontoVenda.setIndAtivo(Indicador.N);
        pontoVendaRepository.saveAndFlush(pontoVenda);
    }

    private void naoExistePontoVendaPeloNomeCidadeEBairro(PontoVendaDTO dto) {
        final boolean pontoVendaExistente = pontoVendaRepository
                .existsByNomeAndEnderecoCidadeAndEnderecoBairro(dto.getNome(), dto.getCidade(), dto.getBairro());
        if (pontoVendaExistente) {
            throw new BadRequestException("Ponto de venda existe com esse nome para essa localidade");
        }
    }

}
