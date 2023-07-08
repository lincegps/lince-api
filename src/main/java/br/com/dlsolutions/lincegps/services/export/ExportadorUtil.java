package br.com.dlsolutions.lincegps.services.export;


import static br.com.dlsolutions.lincegps.domain.InformacaoVendaEnum.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ExportadorUtil {
    private static final int TAMNHO_FONTE_PADRAO = 14;
    private ExportadorUtil() {
    }

    protected static CellStyle defineEstiloColunas(Workbook workbook) {

        Font fonte = workbook.createFont();
        fonte.setBold(true);
        fonte.setFontHeightInPoints((short) TAMNHO_FONTE_PADRAO);
        fonte.setColor(IndexedColors.BLACK.getIndex());

        CellStyle estilo = workbook.createCellStyle();
        estilo.setFont(fonte);

        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);

        return estilo;
    }
}
