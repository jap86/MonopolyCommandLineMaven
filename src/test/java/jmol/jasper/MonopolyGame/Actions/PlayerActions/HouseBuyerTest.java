package jmol.jasper.MonopolyGame.Actions.PlayerActions;

import jmol.jasper.MonopolyBoard.BoardSpaces.Street;
import jmol.jasper.Player.Logic.Player;
import jmol.jasper.TestBank;
import jmol.jasper.TestStreet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HouseBuyerTest {
    private TestStreet street1 = new TestStreet();
    private TestStreet street2 = new TestStreet();
    private TestStreet street3 = new TestStreet();
    private HouseBuyer houseBuyer;
    private TestBank testBank;
    private TransactionType transactionType;
    private Player player;

    @BeforeEach
    void init() {
        testBank = new TestBank();
        houseBuyer = new HouseBuyer(testBank);
        player = new Player("name");
    }

    @Test
    void testHowMayHousesCanBeBoughtNoHouses() {
        // GIVEN a city:
        List<Street> city = addAllStreets();

        // WHEN there are no houses:
        street1.setNumberOfTestHouses(0);
        street2.setNumberOfTestHouses(0);
        street3.setNumberOfTestHouses(0);

        // THEN it should not be possible to buy more than one street:
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street1, city));
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street2, city));
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street3, city));
        }

    @Test
    void testHowMayHousesCanBeBoughtOneHouse() {
        // GIVEN a city:
        List<Street> city = addAllStreets();

        // WHEN there is one house on one street1, but none on the others:
        street1.setNumberOfTestHouses(1);
        street2.setNumberOfTestHouses(0);
        street3.setNumberOfTestHouses(0);

        // THEN it should not be possible to buy a house for street1:
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street1, city));

        // AND it should be possible to buy 1 for the other streets.
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street2, city));
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street3, city));
    }

    @Test
    void testHowMayHousesCanBeBoughtTwoHouses() {
        // GIVEN a city:
        List<Street> city = addAllStreets();

        // WHEN there is one house on one street1, and two on the others:
        street1.setNumberOfTestHouses(1);
        street2.setNumberOfTestHouses(2);
        street3.setNumberOfTestHouses(2);

        // THEN it should not be possible to buy two houses for street1:
        assertEquals(2, houseBuyer.determineHowManyHousesCanBeBought(street1, city));

        // AND it should not be possible to buy houses for the other streets.
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street2, city));
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street3, city));
    }

    @Test
    void testHowMayHousesCanBeBoughtAllHotels() {
        // GIVEN a city:
        List<Street> city = addAllStreets();

        // WHEN there are 5 houses (hotels) on each:
        street1.setNumberOfTestHouses(5);
        street2.setNumberOfTestHouses(5);
        street3.setNumberOfTestHouses(5);

        // THEN it should not be possible to buy houses for each street:
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street1, city));
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street2, city));
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street3, city));
    }

    @Test
    void testHowMayHousesCanBeBoughtOneHotel() {
        // GIVEN a city:
        List<Street> city = addAllStreets();

        // WHEN there are five houses (hotel) on one street1, and four on the others:
        street1.setNumberOfTestHouses(5);
        street2.setNumberOfTestHouses(4);
        street3.setNumberOfTestHouses(4);

        // THEN it should not be possible to buy a house for street 1:
        assertEquals(0, houseBuyer.determineHowManyHousesCanBeBought(street1, city));

        // AND it should be possible to buy 1 house each for the other streets.
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street2, city));
        assertEquals(1, houseBuyer.determineHowManyHousesCanBeBought(street3, city));
    }

    @Test
    void testDetermineTransactionTypeOneHouse() {
        // GIVEN a street with no houses:
        street1.setNumberOfTestHouses(0);

        // THEN the transaction type should be one house if we buy one house:
        assertEquals(
                TransactionType.ONE_HOUSE,
                houseBuyer.determineTransactionType(street1, 1));
    }

    @Test
    void testDetermineTransactionTypeTwoHouses() {
        // GIVEN a street with two houses:
       street1.setNumberOfTestHouses(2);

        // THEN the transaction type should be two houses if we buy two houses:
        assertEquals(
                TransactionType.TWO_HOUSES,
                houseBuyer.determineTransactionType(street1, 2));
    }

    @Test
    void testDetermineTransactionTypeHotel() {
        // GIVEN a street with four houses:
        street1.setNumberOfTestHouses(4);

        // THEN the transaction type should be hotel if we buy one house:
        assertEquals(
                TransactionType.HOTEL,
                houseBuyer.determineTransactionType(street1, 1));
    }

    @Test
    void testDetermineTransactionTypeHouseHotel() {
        // GIVEN a street with three houses
        street1.setNumberOfTestHouses(3);

        // THEN the transaction type should house hotel if we buy two houses:
        assertEquals(
                TransactionType.HOUSE_AND_HOTEL,
                houseBuyer.determineTransactionType(street1, 2));
    }

    @Test
    void testDetermineTransactionTypeNoBuy() {
        // GIVEN a street with no houses:
        street1.setNumberOfTestHouses(0);

        // THEN the transaction type should be no transaction if we buy no houses:
        assertEquals(
                TransactionType.NO_TRANSACTION,
                houseBuyer.determineTransactionType(street1, 0)
        );
    }

    @Test
    void testDetermineTransactionTypeFullStreet() {
        // GIVEN a street with 5 houses:
        street1.setNumberOfTestHouses(5);

        // THEN the transaction type should be no transaction if we buy a house:
        assertEquals(
                TransactionType.NO_TRANSACTION,
                houseBuyer.determineTransactionType(street1, 1)
        );
    }

    @Test
    void testHasBankHousesOrHotelsOnlyHouses() {
        // GIVEN a bank with houses but no hotels:
        testBank.setNrOfTestHouses(2);
        testBank.setNrOfTestHotels(0);

        // THEN hasBankHousesOrHotels() should return true:
        assertTrue(houseBuyer.hasBankHousesOrHotels());
    }

    @Test
    void testHasBankHousesOrHotelsOnlyHotels() {
        // GIVEN a bank with hotels but no houses:
        testBank.setNrOfTestHouses(0);
        testBank.setNrOfTestHotels(2);

        // THEN hasBankHousesOrHotels() should return true:
        assertTrue(houseBuyer.hasBankHousesOrHotels());
    }

    @Test
    void testHasBankHousesOrHotelsNoHousesAndHotels() {
        // GIVEN a bank with no houses and hotels:
        testBank.setNrOfTestHotels(0);
        testBank.setNrOfTestHouses(0);

        // THEN hasBankHousesOrHotels() should return false:
        assertFalse(houseBuyer.hasBankHousesOrHotels());
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyOneHouseBankNotEnough() {
        // GIVEN a bank with no houses.
        testBank.setNrOfTestHouses(0);

        // WHEN the transaction type is one house:
        transactionType = TransactionType.ONE_HOUSE;

        // THEN the transaction type should not be allowed:
        assertFalse(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyOneHouseBankEnough() {
        // GIVEN a bank with no houses.
        testBank.setNrOfTestHouses(1);

        // WHEN the transaction type is one house:
        transactionType = TransactionType.ONE_HOUSE;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyTwoHousesBankNotEnough() {
        // GIVEN a bank with one house.
        testBank.setNrOfTestHouses(1);

        // WHEN the transaction type is two houses:
        transactionType = TransactionType.TWO_HOUSES;

        // THEN the transaction type should not be allowed:
        assertFalse(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyTwoHousesBankEnough() {
        // GIVEN a bank with two houses.
        testBank.setNrOfTestHouses(2);

        // WHEN the transaction type is two house:
        transactionType = TransactionType.TWO_HOUSES;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyHouseHotelNoHotel() {
        // GIVEN a bank with houses but no hotels.
        testBank.setNrOfTestHouses(1);
        testBank.setNrOfTestHotels(0);

        // WHEN the transaction type house and hotel:
        transactionType = TransactionType.HOUSE_AND_HOTEL;

        // THEN the transaction type should not be allowed:
        assertFalse(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyHouseHotelNoHouse() {
        // GIVEN a bank with a hotel but no houses.
        testBank.setNrOfTestHouses(0);
        testBank.setNrOfTestHotels(1);

        // WHEN the transaction type house and hotel:
        transactionType = TransactionType.HOUSE_AND_HOTEL;

        // THEN the transaction type should not be allowed:
        assertFalse(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyHouseHotelBankEnough() {
        // GIVEN a bank with houses and a hotel.
        testBank.setNrOfTestHouses(1);
        testBank.setNrOfTestHotels(1);

        // WHEN the transaction type house and hotel:
        transactionType = TransactionType.HOUSE_AND_HOTEL;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyTwoHouseBankNotEnough() {
        // GIVEN a bank with one house.
        testBank.setNrOfTestHouses(1);

        // WHEN the transaction type two houses:
        transactionType = TransactionType.TWO_HOUSES;

        // THEN the transaction type should not be allowed:
        assertFalse(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeBuyTwoHouseBankEnough() {
        // GIVEN a bank with two house.
        testBank.setNrOfTestHouses(2);

        // WHEN the transaction type two houses:
        transactionType = TransactionType.TWO_HOUSES;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeNoTransactionEmptyBank() {
        // GIVEN a bank with no houses and hotels.
        testBank.setNrOfTestHouses(0);
        testBank.setNrOfTestHotels(0);

        // WHEN the transaction type is no transaction:
        transactionType = TransactionType.NO_TRANSACTION;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testHasBankEnoughForTransactionTypeNoTransactionFullBank() {
        // GIVEN a bank with houses and hotels.
        testBank.setNrOfTestHouses(5);
        testBank.setNrOfTestHotels(5);

        // WHEN the transaction type is no transaction:
        transactionType = TransactionType.NO_TRANSACTION;

        // THEN the transaction type should be allowed:
        assertTrue(houseBuyer.hasBankEnoughHousesForTransaction(transactionType));
    }

    @Test
    void testBuyOneHouse() {
        // GIVEN a street with no houses and a bank with 1 house and a player:
        street1.setNumberOfTestHouses(0);
        testBank.setNrOfTestHouses(1);

        // WHEN we buy one house:
        houseBuyer.buyHouses(TransactionType.ONE_HOUSE, street1, player);

        // THEN the street should have one house:
        assertEquals(1, street1.getNumberOfHouses());

        // AND the bank should have no houses:
        assertEquals(0, testBank.getNrOfHouses());
    }

    @Test
    void testBuyTwoHouses() {
        // GIVEN a street with no houses and a bank with 2 houses:
        street1.setNumberOfTestHouses(0);
        testBank.setNrOfTestHouses(2);

        // WHEN we buy two houses:
        houseBuyer.buyHouses(TransactionType.TWO_HOUSES, street1, player);

        // THEN the street should have two houses:
        assertEquals(2, street1.getNumberOfHouses());

        // AND the bank should have no houses:
        assertEquals(0, testBank.getNrOfHouses());
    }

    @Test
    void testBuyHouseAndHotel() {
        // GIVEN a street with no houses and hotels and a bank with 1 house and 1 hotel:
        street1.setNumberOfTestHouses(0);
        testBank.setNrOfTestHouses(1);
        testBank.setNrOfTestHotels(1);

        // WHEN we buy one house:
        houseBuyer.buyHouses(TransactionType.HOUSE_AND_HOTEL, street1, player);

        // THEN the street should have two houses:
        assertEquals(2, street1.getNumberOfHouses());

        // AND the bank should have no houses AND no hotels:
        assertEquals(0, testBank.getNrOfHouses());
        assertEquals(0, testBank.getNrOfHotels());
    }

    @Test
    void testBuyHotel() {
        // GIVEN a street with no houses and hotels and a bank with 1 hotel:
        street1.setNumberOfTestHouses(0);
        testBank.setNrOfTestHotels(1);

        // WHEN we buy one house:
        houseBuyer.buyHouses(TransactionType.HOTEL, street1, player);

        // THEN the street should have one houses:
        assertEquals(1, street1.getNumberOfHouses());

        // AND the bank should have no hotels:
        assertEquals(0, testBank.getNrOfHouses());
    }

    private List<Street> addAllStreets() {
        List<Street> city = new ArrayList<>();
        city.add(street1);
        city.add(street2);
        city.add(street3);

        return city;
    }
}






