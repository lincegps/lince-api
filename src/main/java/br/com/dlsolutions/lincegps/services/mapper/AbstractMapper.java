package br.com.dlsolutions.lincegps.services.mapper;

import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface AbstractMapper <CLASSE, DTO> {
    default List<DTO> entidadesParaDTOs(Collection<CLASSE> entidades) {
        return entidades == null ? null :
                entidades.stream()
                        .filter(Objects::nonNull)
                        .map(this::entidadeParaDTO)
                        .collect(Collectors.toList());
    }

    default List<CLASSE> dtosParaEntidades(Collection<DTO> dtos) {
        return dtos == null ? null :
                dtos.stream()
                        .filter(Objects::nonNull)
                        .map(this::dtoParaEntidade)
                        .collect(Collectors.toList());
    }

    DTO entidadeParaDTO(CLASSE entidade);

    default CLASSE dtoParaEntidade(DTO dto) {
        return null;
    }

    default Page<DTO> pageEntidadeParaPageDTO(Page<CLASSE> entidade) {
        return entidade.map(this::entidadeParaDTO);
    }

}
