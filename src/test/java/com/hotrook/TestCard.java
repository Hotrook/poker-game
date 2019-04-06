package com.hotrook;

import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCard {


    @Test(expected = InvalidNumberOfRankException.class)
    public void testInvalidNumberOfRankException()
            throws InvalidNumberOfRankException,
            InvalidNumberOfSuitException {
        Card card = new Card(-1, 3);
    }


    @Test(expected = InvalidNumberOfSuitException.class)
    public void testInvalidNumberOfSuitException()
            throws InvalidNumberOfRankException,
            InvalidNumberOfSuitException {
        Card card = new Card(6, 13);
    }


    @Test
    public void testCompare() throws InvalidNumberOfRankException,
            InvalidNumberOfSuitException {

        Card card1 = new Card(8, 3);
        Card card2 = new Card(8, 2);
        Card card3 = new Card(9, 0);
        Card card4 = new Card(9, 0);

        assertEquals(1, card1.compare(card2));
        assertEquals(-1, card2.compare(card3));
        assertEquals(0, card3.compare(card4));
    }


}
