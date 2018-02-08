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

//        System.out.println("*****xls*****");
//        List<NumberVo> xlsList = excelReader.xlsToNumberVoList("C:\\Users\\NM11603074\\Desktop\\lotto.xlsx");
//        printList(xlsList);

        System.out.println("* start *");
        List<NumberVo> xlsxList = excelReader.xlsxToNumberVoList("C:\\Users\\NM11603074\\Desktop\\lotto.xlsx");
        System.out.println("* end *");
        printList(xlsxList);
    }
    

    public static void printList(List<NumberVo> list) {
        int[] numberArray = new int[46];
        
        
        for (NumberVo vo : list) {
            numberArray[vo.getOne()]++;
            numberArray[vo.getTwo()]++;
            numberArray[vo.getThree()]++;
            numberArray[vo.getFour()]++;
            numberArray[vo.getFive()]++;
            numberArray[vo.getSix()]++;
            numberArray[vo.getBonus()]++;
        }
        
        for(int i: numberArray){
            System.out.println(i);
        }
    }
}
