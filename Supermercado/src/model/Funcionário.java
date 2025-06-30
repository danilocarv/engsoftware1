package model;

public class Funcionário extends Pessoa{
    private String Função;
    private double Salário;

    public String getFunção() {
        return this.Função;
    }

    public void setFunção(String Função) {
        this.Função = Função;
    }

    public double getSalário() {
        return this.Salário;
    }

    public void setSalário(double Salário) {
        this.Salário = Salário;
    }
}