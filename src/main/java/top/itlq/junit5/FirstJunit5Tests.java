package top.itlq.junit5;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstJunit5Tests {
    @Test
    void test(){
        assertEquals(2,1+1);
    }
}
