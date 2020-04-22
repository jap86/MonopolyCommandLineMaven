package jmol.jasper;

import jmol.jasper.MonopolyBoard.BoardSpaces.Street;
import jmol.jasper.MonopolyBoard.Data.MonopolyBoardData;

public class TestStreet extends Street {
    public int numberOfTestHouses;


    public TestStreet() {
        super(
                "",
                0,
                MonopolyBoardData.BoardspaceType.STREET_ARNHEM,
                100,
                100,
                new int[]{1,1,1,1,1,1,1});
    }

    public TestStreet(MonopolyBoardData.BoardspaceType boardspaceType) {
        super(
                "",
                0,
                boardspaceType,
                100,
                100,
                new int[]{1,1,1,1,1,1,1});
    }

    @Override
    public void buyHouses(int amount) {
        this.numberOfTestHouses += amount;
    }

    @Override
    public void sellAllHouses() {
        sellHouses(this.numberOfTestHouses);
    }

    @Override
    public void sellHouses(int amount) {
        this.numberOfTestHouses -= amount;
    }

    @Override
    public int getNumberOfHouses() {
        return this.numberOfTestHouses;
    }

    public void setNumberOfTestHouses(int amount) {
        this.numberOfTestHouses = amount;
    }
}
