package jmol.jasper.Player;

import jmol.jasper.Player.Logic.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private final String NAME = "player";

    @Test
    void testPayMoney(){
        // GIVEN a standard player:
        Player player = new Player(NAME);
        int initialCapital = player.getAmountOfMoney();

        // WHEN he pays less than his capital:
        int payingAmount = player.getAmountOfMoney() % 2;
        player.payMoney(payingAmount);

        // THEN his capital should be equal to his original capital minus the amount payed:
        assertEquals(initialCapital - payingAmount, player.getAmountOfMoney());
    }

    @Test
    void testCanNotAffordPayment() {
        // GIVEN a standard player:
        Player player = new Player(NAME);
        int initialCapital = player.getAmountOfMoney();

        // WHEN he pays more than his capital:
        int payingAmount = player.getAmountOfMoney() + 1;
        player.payMoney(payingAmount);

        // THEN his capital should be zero:
        assertEquals(0, player.getAmountOfMoney());
    }

    @Test
    void testNegativeAmountMoneyPaid(){
        // GIVEN a standard player:
        Player player = new Player(NAME);
        int initialCapital = player.getAmountOfMoney();

        // WHEN he pays a minus sum:
        int payingAmount = -1;
        player.payMoney(payingAmount);

        // THEN his capital should the same as before:
        assertEquals(initialCapital, player.getAmountOfMoney());
    }

    @Test
    void testReceiveMoney(){
        // GIVEN a standard player:
        Player player = new Player(NAME);
        int initialCapital = player.getAmountOfMoney();

        // WHEN he receives a sum of money:
        int receivingAmount = player.getAmountOfMoney() + 1;
        player.receiveMoney(receivingAmount);

        // THEN his capital should be equal to his original capital plus the amount received:
        assertEquals(initialCapital + receivingAmount, player.getAmountOfMoney());
    }

    @Test
    void testNegativeAmountOfMoneyReceived() {
        // GIVEN a standard player:
        Player player = new Player(NAME);
        int initialCapital = player.getAmountOfMoney();

        // WHEN he receives a negative sum of money:
        int receivingAmount = -1;
        player.receiveMoney(receivingAmount);

        // THEN his capital should be the same as before:
        assertEquals(initialCapital, player.getAmountOfMoney());
    }
}
