package com.api.csv.impl;

import com.api.csv.auxiliar.GetFile;
import com.api.csv.repository.CsvRepository;
import com.api.csv.repository.model.CSV;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class CsvOutputFileXSSFWorkbook {

    @Autowired
    private GetFile getFile;

    @Autowired
    private CsvRepository csvRepository;

    @PostConstruct
    public void processCsvFileAndSave() throws Exception {
        File file = null;
        try {
            file = getFile.getFile(); // Obter o arquivo
            if (file == null || !file.exists()) {
                throw new IOException("File not found or is null: " + (file != null ? file.getAbsolutePath() : "null"));
            }

            // Usar try-with-resources para garantir que o Workbook seja fechado
            try (FileInputStream fis = new FileInputStream(file);
                 Workbook wb = new XSSFWorkbook(fis)) { // Usar XSSFWorkbook para .xlsx

                XSSFSheet ws = (XSSFSheet) wb.getSheetAt(0);
                log.info("Iniciando leitura do arquivo Excel: {}", file.getName());

                List<CSV> csvList = new ArrayList<>();
                Iterator<Row> rowIterator = ws.iterator();

                if (!rowIterator.hasNext()) { // Verifica se a planilha está vazia
                    log.warn("A planilha está vazia. Nenhum dado para processar.");
                    return;
                }

                // Leitura dos cabeçalhos para mapear colunas (linha 0)
                Row headerRow = rowIterator.next();
                Map<String, Integer> columnMap = new HashMap<>();
                for (Cell headerCell : headerRow) {
                    columnMap.put(headerCell.getStringCellValue().toLowerCase(), headerCell.getColumnIndex());
                }

                // Verificar se as colunas essenciais existem
                if (!columnMap.containsKey("year") ||
                        !columnMap.containsKey("title") ||
                        !columnMap.containsKey("studios") ||
                        !columnMap.containsKey("producers") ||
                        !columnMap.containsKey("winner")) {
                    log.error("Cabeçalhos essenciais (year, title, studios, producers, winner) não encontrados na planilha.");
                    throw new IllegalArgumentException("Planilha com formato inválido: cabeçalhos ausentes.");
                }

                // Processar as demais linhas de dados
                while (rowIterator.hasNext()) {
                    Row dataRow = rowIterator.next();
                    CSV csvEntry = new CSV();
                    List<String> currentListValues = new ArrayList<>();

                    // Extrair o 'year'
                    Cell yearCell = dataRow.getCell(columnMap.get("year"));
                    if (yearCell != null && yearCell.getCellType() == CellType.NUMERIC) {
                        csvEntry.setYear((long) yearCell.getNumericCellValue()); // Atribui Double diretamente
                    } else {
                        log.warn("Linha {}: Célula 'year' inválida ou vazia. Pulando linha ou tratando como nulo.", dataRow.getRowNum());
                        // Decide como tratar: pular a linha, atribuir null, etc.
                        continue; // Pular a linha se o 'year' for inválido
                    }

                    // Extrair os 'listValues' de colunas específicas
                    // A ordem pode ser definida aqui, ou ser flexível
                    List<String> valueColumns = Arrays.asList("title", "studios", "producers", "winner");
                    for (String colName : valueColumns) {
                        Integer colIndex = columnMap.get(colName);
                        if (colIndex != null) {
                            Cell valueCell = dataRow.getCell(colIndex);
                            if (valueCell != null && valueCell.getCellType() == CellType.STRING) {
                                currentListValues.add(valueCell.getStringCellValue());
                            } else {
                                // Se uma célula de string está vazia ou não é string, adicione uma string vazia ou ignore
                                currentListValues.add(""); // Ou não adicione, dependendo da regra
                                log.warn("Linha {}: Célula '{}' inválida ou vazia. Adicionando string vazia.", dataRow.getRowNum(), colName);
                            }
                        }
                    }
                    csvEntry.setListValues(currentListValues);

                    csvList.add(csvEntry);
                }

                log.info("Total de {} registros CSV lidos do arquivo.", csvList.size());

                log.info("Salvando registros no repositório...");
                this.csvRepository.saveAll(csvList);
                log.info("Registros salvos com sucesso.");

            } catch (IOException e) {
                log.error("Erro de I/O ao processar o arquivo Excel {}: {}", file.getName(), e.getMessage());
                throw e; // Relançar, pode ser envolvido por uma exceção customizada
            } catch (Exception e) { // Captura outras exceções durante o processamento do Excel
                log.error("Erro inesperado ao processar o arquivo Excel {}: {}", file.getName(), e.getMessage(), e);
                throw e;
            }
        } catch (IOException e) {
            log.error("Não foi possível acessar o arquivo: {}", e.getMessage());
            throw new Exception("Erro ao obter ou acessar o arquivo", e); // Exceção mais genérica para o PostConstruct
        }
    }
}