package org.example;

public class Rivista extends Articolo {
    private String periodicita;

    public Rivista(int ISBN, String titolo, int annoPubblicazione, int numeroPagine, String periodicita) {
        super(ISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public String getString() {
        return periodicita;
    }

    public void setPeriodicita(String periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                ", ISBN=" + ISBN +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}
