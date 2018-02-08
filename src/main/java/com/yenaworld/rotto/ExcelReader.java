package com.yenaworld.rotto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yenaworld.rotto.vo.NumberVo;

public class ExcelReader {

    @SuppressWarnings("resource")
    public List<NumberVo> xlsToNumberVoList(String filePath) {
        List<NumberVo> list = new ArrayList<NumberVo>();
        FileInputStream fis = null;
        HSSFWorkbook workbook = null;

        try {
            fis = new FileInputStream(filePath);
            workbook = new HSSFWorkbook(fis);

            HSSFSheet curSheet;
            HSSFRow curRow;
            HSSFCell curCell;
            NumberVo vo;

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                curSheet = workbook.getSheetAt(sheetIndex);
                for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
                    if (rowIndex != 0) {
                        curRow = curSheet.getRow(rowIndex);
                        vo = new NumberVo();

                        // row의 첫번째 cell값이 비어있지 않은 경우 만 cell탐색
                        if (!"".equals(curRow.getCell(0).getStringCellValue())) {

                            for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
                                curCell = curRow.getCell(cellIndex);
                                setVo(vo, cellIndex, getCellValue(curCell));
                            }
                            list.add(vo);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            try {
                // 사용한 자원은 finally에서 해제
                //                if (workbook != null)
                //                    workbook.close();
                if (fis != null)
                    fis.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * XLSX 파일을 분석하여 List<NumberVo> 객체로 반환
     * @param filePath
     * @return
     */
    public List<NumberVo> xlsxToNumberVoList(String filePath) {
        List<NumberVo> list = new ArrayList<NumberVo>();
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;

        try {
            fis = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fis);

            XSSFSheet curSheet;
            XSSFRow curRow;
            XSSFCell curCell;
            NumberVo vo;

            System.out.println("workbook.getNumberOfSheets(): " + workbook.getNumberOfSheets());
            for (int sheetIndex = 0; sheetIndex < 1; sheetIndex++) {
                curSheet = workbook.getSheetAt(sheetIndex);
                System.out.println("curSheet.getPhysicalNumberOfRows() : " + curSheet.getPhysicalNumberOfRows());
                for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
                    // row 0은 헤더정보이기 때문에 무시
                    if (rowIndex != 0) {
                        curRow = curSheet.getRow(rowIndex);
                        vo = new NumberVo();

                        for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
                            curCell = curRow.getCell(cellIndex);
                            if (curCell != null)
                                setVo(vo, cellIndex, getCellValue(curCell));
                        }
                        list.add(vo);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } finally {
            try {
                // 사용한 자원은 finally에서 해제
                if (workbook != null)
                    workbook.close();
                if (fis != null)
                    fis.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return list;
    }

    private String getCellValue(XSSFCell curCell) {
        String value;

        switch (curCell.getCellType()) {
            case XSSFCell.CELL_TYPE_FORMULA:
                value = curCell.getCellFormula();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(curCell)) {
                    Date date = curCell.getDateCellValue();
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    value = curCell.getNumericCellValue() + "";
                }
                break;
            case XSSFCell.CELL_TYPE_STRING:
                value = curCell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                value = curCell.getBooleanCellValue() + "";
                break;
            case XSSFCell.CELL_TYPE_ERROR:
                value = curCell.getErrorCellValue() + "";
                break;
            default:
                value = "";
                break;
        }

        return value;
    }
    
    private String getCellValue(HSSFCell curCell) {
        String value;

        switch (curCell.getCellType()) {
            case HSSFCell.CELL_TYPE_FORMULA:
                value = curCell.getCellFormula();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(curCell)) {
                    Date date = curCell.getDateCellValue();
                    value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                } else {
                    value = curCell.getNumericCellValue() + "";
                }
                break;
            case HSSFCell.CELL_TYPE_STRING:
                value = curCell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                value = curCell.getBooleanCellValue() + "";
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = curCell.getErrorCellValue() + "";
                break;
            default:
                value = "";
                break;
        }

        return value;
    }

    private void setVo(NumberVo vo, int cellIndex, String value) {
        int valueInt = 0;
        if (cellIndex < 8)
            valueInt = (int) Double.parseDouble(value);

        switch (cellIndex) {
            case 0:
                vo.setIndex(valueInt);
                break;
            case 1:
                vo.setOne(valueInt);
                break;
            case 2:
                vo.setTwo(valueInt);
                break;
            case 3:
                vo.setThree(valueInt);
                break;
            case 4:
                vo.setFour(valueInt);
                break;
            case 5:
                vo.setFive(valueInt);
                break;
            case 6:
                vo.setSix(valueInt);
                break;
            case 7:
                vo.setBonus(valueInt);
                break;
            case 8:
                vo.setDate(value);
                break;
            default:
                break;
        }
    }
}
