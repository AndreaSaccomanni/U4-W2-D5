package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Archivio mioArchivio = new Archivio();
        int i = -1;
        while (i != 0) {
            if (mioArchivio.archivioVuoto()) {

                System.out.println("Digita 1 per aggiungere un libro");
                System.out.println("Digita 2 per aggiungere una rivista ");
                System.out.println("Digita 0 per chiudere il catalogo");

                if (scanner.hasNextInt()) {
                    i = scanner.nextInt();
                } else {
                    System.out.println("Errore: Devi inserire un numero valido.");
                    scanner.next();
                    continue;
                }
                switch (i) {
                    case 1:
                        mioArchivio.aggiungiElemento(mioArchivio.creaLibro());
                        break;
                    case 2:
                        mioArchivio.aggiungiElemento(mioArchivio.creaRivista());
                        break;
                    case 0:
                        System.out.println("Catalogo chiuso. A presto!");
                        break;
                    default:
                        System.out.println("Seleziona 1 o 2 oppure 0 per chiudere il catalogo");
                }
            } else {
                System.out.println("Digita 1 per aggiungere un libro");
                System.out.println("Digita 2 per aggiungere una rivista ");
                System.out.println("Digita 3 per cercare un articolo");
                System.out.println("Digita 4 per rimuovere un articolo tramite il codice ISBN");
                System.out.println("Digita 5 per modificare un articolo");
                System.out.println("Digita 6 per visualizzare gli articoli nel catalogo");
                System.out.println("Digita 0 per chiudere il catalogo");

                if (scanner.hasNextInt()) {
                    i = scanner.nextInt();
                } else {
                    System.out.println("Errore: Devi inserire un numero valido.");
                    scanner.next();
                    continue;
                }

                switch (i) {
                    case 1:
                        mioArchivio.aggiungiElemento(mioArchivio.creaLibro());
                        mioArchivio.mostraArchivio();
                        break;
                    case 2:
                        mioArchivio.aggiungiElemento(mioArchivio.creaRivista());
                        mioArchivio.mostraArchivio();

                        break;
                    case 3:
                        mioArchivio.ricercaArticolo();
                        break;
                    case 4:
                        mioArchivio.rimuoviArticolo();
                        mioArchivio.mostraArchivio();

                        break;
                    case 5:
                        mioArchivio.modificaArticolo();
                        mioArchivio.mostraArchivio();

                        break;
                    case 6:
                        mioArchivio.mostraArchivio();
                        break;
                    case 0:
                        System.out.println("Catalogo chiuso. A presto!");
                        break;
                    default:
                        System.out.println("Seleziona un numero da 1 a 6 oppure 0 per chiudere il lettore multimediale");
                }
            }
        }
    }
}
