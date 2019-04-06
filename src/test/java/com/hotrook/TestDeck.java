package com.hotrook;

import com.hotrook.exceptions.InvalidNumberOfRankException;
import com.hotrook.exceptions.InvalidNumberOfSuitException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestDeck {


    @Test
    public void testShuffle() throws InvalidNumberOfRankException, InvalidNumberOfSuitException {

        List<Card> cards1 = Arrays.asList(new Card[52]);
        List<Card> cards2 = Arrays.asList(new Card[52]);

        Collections.copy(cards1, Deck.getInstance().getCards());

        Deck.getInstance().shuffle();

        Collections.copy(cards2, Deck.getInstance().getCards());

        Assert.assertNotEquals(cards1, cards2);

    }

}
