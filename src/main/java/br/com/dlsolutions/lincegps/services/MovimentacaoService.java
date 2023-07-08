package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.domain.PontoVenda;
import br.com.dlsolutions.lincegps.domain.enums.TipoMovimentacao;
import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import br.com.dlsolutions.lincegps.repository.MovimentacaoRepository;
import br.com.dlsolutions.lincegps.repository.specification.MovimentacaoEspecifications;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoDTO;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoFiltroDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.MovimentacaoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;

    private final MovimentacaoEspecifications movimentacaoEspecifications;

    private final ProdutosService produtosService;
    private final PontoVendaService pontoVendaService;
    private final UsuarioService usuarioService;

    private final MovimentacaoMapper movimentacaoMapper;

    @Transactional(readOnly = true)
    public Page<MovimentacaoDTO> pesquisar(MovimentacaoFiltroDTO filtro, Pageable pageable) {
        filtro.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
        Specification<Movimentacao> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        final Page<Movimentacao> movimentacoes = movimentacaoRepository.findAll(spec, pageable);
        return movimentacaoMapper.pageEntidadeParaPageDTO(movimentacoes);
    }

    @Transactional(readOnly = true)
    public Movimentacao findObj(Integer id) {
        return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movimentação não encontrada com id + " + id));
    }

    @Transactional(readOnly = true)
    public MovimentacaoDTO findDto(Integer id) {
        return movimentacaoMapper.entidadeParaDTO(findObj(id));
    }

    @Transactional
    public MovimentacaoDTO criar(MovimentacaoDTO dto) {
        if (dto.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA)
                && !possuiProdutosDisponiveisParaMovimentacao(dto.getIdProduto(), dto.getQuantidade())) {
            throw new BadRequestException("Não é possivel realizar movimentação, pois não há produtos em estoque");
        }

        final var movimentacoes = movimentacaoRepository
                .findAllByProdutoIdAndDataAndPontoVendaOrigemIdAndPontoVendaDestinoId(
                        dto.getIdProduto(),
                        dto.getData(),
                        dto.getPontoVendaDTOOrigem().getId(),
                        dto.getPontoVendaDTODestino().getId()
                );

        if(!movimentacoes.isEmpty()) {
            final var movimentacaoEntrada = movimentacoes
                    .stream()
                    .filter(movimentacao -> movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)).findFirst()
                    .orElse(null);
            final var movimentacaoSaida = movimentacoes
                    .stream()
                    .filter(movimentacao -> movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA)).findFirst()
                    .orElse(null);
            if(Objects.nonNull(movimentacaoEntrada) && Objects.nonNull(movimentacaoSaida)) {
                movimentacaoEntrada.setQuantidade(movimentacaoEntrada.getQuantidade() + dto.getQuantidade());
                movimentacaoSaida.setQuantidade(movimentacaoSaida.getQuantidade() + dto.getQuantidade());
                final var movimentacaoEntradaAlterada = movimentacaoRepository.saveAndFlush(movimentacaoEntrada);
                movimentacaoRepository.saveAndFlush(movimentacaoSaida);
                return movimentacaoMapper.entidadeParaDTO(movimentacaoEntradaAlterada);
            }
           throw new BadRequestException("Error ao alterar movimentações");
        }

        final Movimentacao movimentacao = Movimentacao.builder()
                .data(Objects.nonNull(dto.getData()) ? dto.getData() : LocalDate.now())
                .usuario(usuarioService.obterUsuarioLogado())
                .tipoMovimentacao(dto.getTipoMovimentacao())
                .quantidade(dto.getQuantidade())
                .produto(produtosService.findById(dto.getIdProduto()))
                .pontoVendaOrigem(pontoVendaService.getPontoVendaOrigem(dto.getPontoVendaDTOOrigem().getId()))
                .build();
        if (Objects.nonNull(dto.getPontoVendaDTODestino().getId())) {
            movimentacao.setPontoVendaDestino(pontoVendaService.findById((dto.getPontoVendaDTODestino().getId())));
        } else {
            movimentacao.setPontoVendaDestino(pontoVendaService.findByTipo(TipoPontoVenda.CENTRO_DISTRIBUICAO));
        }
        final Movimentacao movimentacaoSalva = movimentacaoRepository.saveAndFlush(movimentacao);
       //Movimentacao
       if(movimentacaoSalva.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)) {
           //Essa movimentação é entre fabricante e CD
           return movimentacaoMapper.entidadeParaDTO(movimentacaoSalva);
       } else {
           //Cria Movimetação de entrada
           Movimentacao movimentacaoEntrada = Movimentacao.builder()
                   .data(Objects.nonNull(dto.getData()) ? dto.getData() : LocalDate.now())
                   .usuario(usuarioService.obterUsuarioLogado())
                   .tipoMovimentacao(TipoMovimentacao.ENTRADA)
                   .quantidade(dto.getQuantidade())
                   .produto(produtosService.findById(dto.getIdProduto()))
                   .pontoVendaOrigem(pontoVendaService.getPontoVendaOrigem(dto.getPontoVendaDTOOrigem().getId()))
                   .pontoVendaDestino(pontoVendaService.findById((dto.getPontoVendaDTODestino().getId())))
                   .idMovimentacaoSaida(movimentacaoSalva.getId())
                   .build();
           final Movimentacao movimentacaoEntradaSalva = movimentacaoRepository.saveAndFlush(movimentacaoEntrada);
           return movimentacaoMapper.entidadeParaDTO(movimentacaoEntradaSalva);
       }
    }

    @Transactional
    public MovimentacaoDTO update(Integer id, MovimentacaoDTO dto) {
        final Movimentacao obj = findObj(id);
        obj.setQuantidade(dto.getQuantidade());
        obj.setData(dto.getData());
        if(obj.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)
                && !obj.getPontoVendaOrigem().getId().equals(PontoVenda.ID_FABRICANTE)) {
            final var movimentacaoSaida = findObj(obj.getIdMovimentacaoSaida());
            movimentacaoSaida.setQuantidade(dto.getQuantidade());
            movimentacaoSaida.setData(dto.getData());
            movimentacaoRepository.save(movimentacaoSaida);
        }
        return movimentacaoMapper.entidadeParaDTO(movimentacaoRepository.save(obj));
    }

    @Transactional
    public void delete(Integer id) {
        final Movimentacao obj = findObj(id);
        movimentacaoRepository.delete(obj);
    }

    public Integer getQtdProdutoEstoqueDisponivel(Integer idProduto) {
        final List<Movimentacao> movimentacoes = movimentacaoRepository.findAllByProdutoId(idProduto);
        final Integer quantidadeProdutosEntrada = movimentacoes.stream()
                .filter(this::isEntradaAndCentroDistribuicao)
                .map(Movimentacao::getQuantidade)
                .reduce(0, Integer::sum);

        final Integer quantidadeProdutosSaida = movimentacoes.stream()
                .filter(this::isSaidaAndCentroDistribuicao)
                .map(Movimentacao::getQuantidade)
                .reduce(0, Integer::sum);

        return quantidadeProdutosEntrada - quantidadeProdutosSaida;
    }

    @Transactional(readOnly = true)
    public List<Movimentacao> obterTodasSemPaginacao(MovimentacaoFiltroDTO filtro) {
        Specification<Movimentacao> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        return movimentacaoRepository.findAll(spec);
    }

    private Specification<Movimentacao> aplicarSpecificationBaseadoNoFiltro(MovimentacaoFiltroDTO filtro) {
        return movimentacaoEspecifications
                .filtroParaSpecifications(filtro)
                .stream()
                .reduce(Specification.where(null), Specification::and);
    }

    private boolean possuiProdutosDisponiveisParaMovimentacao(Integer idProduto, Integer quantidadeAMovimetar) {
        final Integer qtdProdutoEstoqueDisponivel = getQtdProdutoEstoqueDisponivel(idProduto);
        return qtdProdutoEstoqueDisponivel >= quantidadeAMovimetar;
    }

    private boolean isEntradaAndCentroDistribuicao(Movimentacao movimentacao) {
        return movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA)
                && movimentacao.getPontoVendaDestino().getTipo().equals(TipoPontoVenda.CENTRO_DISTRIBUICAO);
    }

    private boolean isSaidaAndCentroDistribuicao(Movimentacao movimentacao) {
        return movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.SAIDA)
                && movimentacao.getPontoVendaOrigem().getTipo().equals(TipoPontoVenda.CENTRO_DISTRIBUICAO);
    }



}
