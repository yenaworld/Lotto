package com.yenaworld.rotto;

import com.yenaworld.rotto.vo.NumberVo;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
        NumberVo num = new NumberVo();
        testNumberVo(num);
        
        System.out.println("Hello World!" + num.getBonus());
    }
    
    private static void testNumberVo(NumberVo num){
        num.setBonus(12);
    }
}
