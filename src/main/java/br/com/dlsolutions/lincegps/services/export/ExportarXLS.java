package br.com.dlsolutions.lincegps.services.export;

import br.com.dlsolutions.lincegps.domain.*;
import br.com.dlsolutions.lincegps.services.exception.ExportException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
public class ExportarXLS {

    private List<Venda> vendas;

    public ExportarXLS(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public byte[] exportar(){
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet abaPlanilha = workbook.createSheet("vendas");
            int rowCount = 0;
            createColuns(abaPlanilha, rowCount, workbook);
            createLineVenda(abaPlanilha, rowCount);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch (IOException e) {
            throw new ExportException("Erro ao exportar XLS");
        }
    }

    private void createLineVenda(Sheet abaPlanilha, int rowCount) {
        for (Venda venda : this.vendas) {
            Row row = abaPlanilha.createRow(++rowCount);
            writeVenda(venda, row);
        }
    }

    private void createColuns(Sheet abaPlanilha, int rowCount, Workbook workbook) {
        int infocount = 0;
        final var colunas = Arrays
                .stream(InformacaoVendaEnum.values())
                .map(InformacaoVendaEnum::getDescricao)
                .collect(Collectors.toList());
        Row row = abaPlanilha.createRow(rowCount);
        for(String coluna: colunas) {
            writeColunm(coluna, row, infocount++, workbook);
        }
    }

    private void writeColunm(String info, Row row, int positionCell, Workbook workbook) {
        Cell cell = row.createCell(positionCell);
        cell.setCellValue(info);
        cell.setCellStyle(ExportadorUtil.defineEstiloColunas(workbook));
    }

    private void writeVenda(Venda venda, Row row ) {

        Cell cell = row.createCell(0);
        cell.setCellValue(Optional.ofNullable(venda.getPontoVenda()).map(PontoVenda::getNome).orElse("-"));

        cell = row.createCell(1);
        cell.setCellValue(Optional.ofNullable(venda.getCliente()).map(Cliente::getNome).orElse("-"));

        cell = row.createCell(2);
        cell.setCellValue(Optional.ofNullable(venda.getCliente()).map(Cliente::getCpfOuCnpj).orElse("-"));

        cell = row.createCell(3);
        cell.setCellValue(Optional.ofNullable(venda.getCliente()).map(Cliente::getEmails)
                        .stream()
                        .flatMap(Collection::stream)
                        .findFirst()
                        .orElse("-"));

        cell = row.createCell(4);
        cell.setCellValue(Optional.ofNullable(venda.getCliente()).map(Cliente::getTelefones)
                .stream()
                .flatMap(Collection::stream)
                .findFirst()
                .orElse("-"));

        cell = row.createCell(5);
        cell.setCellValue(Optional.ofNullable(venda.getProduto()).map(Produto::getNome).orElse("-"));

        cell = row.createCell(6);
        cell.setCellValue(Optional.ofNullable(venda.getNumeroSerie()).map(Objects::toString).orElse("-"));

        cell = row.createCell(7);
        cell.setCellValue(Optional.ofNullable(venda.getDataCriacao()).map(Objects::toString).orElse("-"));

        cell = row.createCell(8);
        cell.setCellValue(Optional.ofNullable(venda.getDataVencimento()).map(Objects::toString).orElse("-"));

        cell = row.createCell(9);
        cell.setCellValue(Optional.ofNullable(venda.getFormaPagamento()).map(Objects::toString).orElse("-"));

        cell = row.createCell(10);
        cell.setCellValue(Optional.ofNullable(venda.getCodigoCartao()).map(Objects::toString).orElse("-"));

        cell = row.createCell(11);
        cell.setCellValue(Optional.ofNullable(venda.getBandeira()).map(Objects::toString).orElse("-"));

        cell = row.createCell(12);
        cell.setCellValue(Optional.ofNullable(venda.getStatusVenda()).map(Objects::toString).orElse("-"));

        cell = row.createCell(13);
        cell.setCellValue(Optional.ofNullable(venda.getIndNotaFiscal()).map(Objects::toString).orElse("-"));

        cell = row.createCell(14);
        cell.setCellValue(Optional.ofNullable(venda.getPontoVendaEntrega()).map(PontoVenda::getNome).orElse("-"));

        cell = row.createCell(15);
        cell.setCellValue(venda.getValorTotal().toEngineeringString());

        cell = row.createCell(16);
        cell.setCellValue(Optional.ofNullable(venda.getObservacao()).map(Objects::toString).orElse("-"));

    }
}
