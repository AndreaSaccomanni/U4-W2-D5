package org.example;

public class Rivista extends Articolo {
    Periodicita periodicita;

    public enum Periodicita {SETTIMANALE, MENSILE, SEMESTRALE}


    public Rivista(int ISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(ISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
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
