package com.yenaworld.rotto;

import java.util.List;

import com.yenaworld.rotto.vo.NumberVo;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {

        ExcelReader excelReader = new ExcelReader();

        System.out.println("*****xls*****");
        List<NumberVo> xlsList = excelReader.xlsToNumberVoList("C:\\excel\\test.xls");
        printList(xlsList);

        System.out.println("*****xlsx*****");
        List<NumberVo> xlsxList = excelReader.xlsToNumberVoList("C:\\excel\\test.xlsx");
        printList(xlsxList);
    }

    public static void printList(List<NumberVo> list) {
        NumberVo vo;
        
        for (int i = 0; i < list.size(); i++) {
            vo = list.get(i);
            System.out.println(vo.toString());
        }
    }
}
