package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Contato;
import br.com.dlsolutions.lincegps.domain.enums.FeedBackContatoEnum;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.services.dto.ContatoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContatoMapper implements AbstractMapper<Contato, ContatoDTO>{
    private final VendaMapper vendaMapper;


    @Override
    public ContatoDTO entidadeParaDTO(Contato entidade) {
        return ContatoDTO.builder()
                .id(entidade.getId())
                .data(entidade.getData())
                .tipo(Optional.ofNullable(entidade.getTipo()).map(Enum::toString).orElse(null))
                .venda(vendaMapper.entidadeParaDTO(entidade.getVenda()))
                .dataRetorno(entidade.getDataRetornoLigacao())
                .feedback(Optional.ofNullable(entidade.getFeedback()).map(FeedBackContatoEnum::getDescricao).orElse(null))
                .observacao(entidade.getObservacao())
                .retornarLigacao(entidade.isRetornarLigacao())
                .satisfeito(Optional.ofNullable(entidade.getIndSatisfeito()).map(Indicador::getValor).orElse(false))
                .build();
    }
}
