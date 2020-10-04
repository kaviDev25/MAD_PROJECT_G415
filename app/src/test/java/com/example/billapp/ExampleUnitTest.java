package com.example.billapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private EditBill editBill;

    @Before
    public void setUp(){
        editBill = new EditBill();
    }
    @Test
    public void USD_isCorrect() {
        float result = editBill.convertToRs(1);
        assertEquals(184.53,result, 0.1);
    }
    @Test
    public void rs_isCorrect() {
        float result = editBill.convertToUSD((float) 184.53);
        assertEquals(1,result, 0.1);
    }

}