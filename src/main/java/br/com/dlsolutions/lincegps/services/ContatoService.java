package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Contato;
import br.com.dlsolutions.lincegps.domain.enums.FeedBackContatoEnum;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoContatoEnum;
import br.com.dlsolutions.lincegps.repository.ContatoRepository;
import br.com.dlsolutions.lincegps.services.dto.ContatoDTO;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.ContatoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final VendasService vendasService;
    private final UsuarioService usuarioService;

    private final ContatoMapper contatoMapper;


    @Transactional(readOnly = true)
    public ContatoDTO findDto(Integer id){
        return contatoRepository.findById(id)
                .map(contatoMapper::entidadeParaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public Contato find(Integer id){
        return contatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ContatoDTO> findAll() {
        return contatoMapper.entidadesParaDTOs(contatoRepository.findAll());
    }

    @Transactional
    public ContatoDTO create(ContatoDTO dto) {
        final var venda = vendasService.find(dto.getVenda().getId());
        final var usuario = usuarioService.obterUsuarioLogado();
        final var obj = Contato.builder()
                .data(dto.getData())
                .tipo(TipoContatoEnum.valueOf(dto.getTipo()))
                .feedback(FeedBackContatoEnum.valueOf(dto.getFeedback()))
                .usuario(usuario)
                .venda(venda)
                .dataRetornoLigacao(dto.getDataRetorno())
                .retornarLigacao(dto.isRetornarLigacao())
                .indSatisfeito(Optional.of(dto.isSatisfeito()).map(Indicador::getIndicador).orElse(null))
                .observacao(dto.getObservacao())
                .build();
        return contatoMapper.entidadeParaDTO(contatoRepository.save(obj));

    }

    @Transactional
    public ContatoDTO update(Integer id, ContatoDTO dto) {
        final var contato = find(id);
        final var venda = vendasService.find(dto.getVenda().getId());
        contato.setData(dto.getData());
        contato.setTipo(TipoContatoEnum.valueOf(dto.getTipo()));
        contato.setVenda(venda);
        contato.setObservacao(dto.getObservacao());
        contato.setIndSatisfeito(Optional.of(dto.isSatisfeito()).map(Indicador::getIndicador).orElse(null));
        contato.setRetornarLigacao(dto.isRetornarLigacao());
        dto.setDataRetorno(dto.getDataRetorno());
        return contatoMapper.entidadeParaDTO(contatoRepository.save(contato));
    }

    @Transactional
    public void delete(Integer id) {
        final var contato = find(id);
        contatoRepository.delete(contato);
    }


}
