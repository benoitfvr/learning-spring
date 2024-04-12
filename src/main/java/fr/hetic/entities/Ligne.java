package fr.hetic;

public class Ligne {
    private int id;
    private int param1;
    private int param2;
    private char operateur;
    private int index;
    private int fichierId;

    public int getId() {
        return id;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public char getOperateur() {
        return operateur;
    }

    public int getIndex() {
        return index;
    }

    public int getFichierId() {
        return fichierId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setParam1(int param1) {
        this.param1 = param1;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public void setOperateur(char operateur) {
        this.operateur = operateur;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFichierId(int fichierId) {
        this.fichierId = fichierId;
    }
}
