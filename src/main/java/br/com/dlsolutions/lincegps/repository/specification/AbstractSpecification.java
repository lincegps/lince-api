package br.com.dlsolutions.lincegps.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AbstractSpecification<T, F> {

   List<Specification<T>> filtroParaSpecifications(F filtro);

}
