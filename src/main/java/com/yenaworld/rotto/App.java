package com.yenaworld.rotto;

import java.io.IOException;
import java.util.List;

import com.yenaworld.rotto.vo.NumberVo;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        ExcelReader excelReader = new ExcelReader();

//        System.out.println("*****xls*****");
//        List<NumberVo> xlsList = excelReader.xlsToNumberVoList("C:\\Users\\NM11603074\\Desktop\\lotto.xlsx");
//        printList(xlsList);

        System.out.println("* start *");
        List<NumberVo> xlsxList = excelReader.xlsxToNumberVoList("C:\\Users\\NM11603074\\Desktop\\lotto.xlsx");
        sendInflux(xlsxList);
        
        System.out.println("* end *");
    }
    
    public static void sendInflux(List<NumberVo> xlsxList){
        try {
            InfluxdbController.setUp("http://10.107.95.39:8086", "admin", "admin");
            
            for (NumberVo vo : xlsxList) {
                InfluxdbController.sendInfluxTagByNumber("test_2s_tag_number_3", vo);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
