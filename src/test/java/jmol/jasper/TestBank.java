package jmol.jasper;

import jmol.jasper.MonopolyBoard.BoardSpaces.Street;
import jmol.jasper.MonopolyBoard.Data.Bank;
import jmol.jasper.Player.Logic.Player;

import java.util.ArrayList;
import java.util.List;

public class TestBank extends Bank {
    private int nrOfTestHouses;
    private int nrOfTestHotels;

    private List<Street> ownedStreets = new ArrayList<>();

    public void addStreets(Street street1, Street street2, Street street3) {
        ownedStreets.add(street1);
        ownedStreets.add(street2);
        ownedStreets.add(street3);
    }


    @Override
    public void buyHouses(int amount) {
        if ((nrOfTestHouses - amount) < 0) {
            return;
        }
        nrOfTestHouses -= amount;
    }

    @Override
    public void buyHotel(int amount) {
        if ((nrOfTestHotels - amount) < 0) {
            return;
        }
        nrOfTestHotels -= amount;
    }

    @Override
    public List<Street> getOwnedStreetsOfCity(Player player) {
        return ownedStreets;
    }

    @Override
    public int getNrOfHouses() {return nrOfTestHouses;}

    public void setNrOfTestHouses(int nrOfTestHouses) {
        this.nrOfTestHouses = nrOfTestHouses;
    }

    @Override
    public int getNrOfHotels() {return nrOfTestHotels;}

    public void setNrOfTestHotels(int nrOfTestHotels) {
        this.nrOfTestHotels = nrOfTestHotels;
    }
}
