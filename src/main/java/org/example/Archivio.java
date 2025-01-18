package org.example;

import java.time.Year;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Archivio {

    private Map<Integer, Articolo> items = new HashMap<>();


    public boolean archivioVuoto() {
        if (items.size() <= 0) {
            System.out.println("L'archivio non contiene ancora nessun elemento");
            return true;
        } else {
            return false;
        }
    }

    // Metodo per aggiungere un elemento
    public void aggiungiElemento(Articolo articolo) {
        if (items.containsKey(articolo.ISBN)) {
            System.out.println("Impossibile aggiungere l'articolo perchè è gia presente nel catalogo");
        } else {
            items.put(articolo.ISBN, articolo);
            System.out.println("Articolo aggiunto: " + articolo);
        }
    }

    // Metodo per controllo dell'input
    public int controlloInputValidoInt(String messaggio) {
        Scanner scanner = new Scanner(System.in);
        int valore = 0;
        boolean inputValido = false;

        while (!inputValido) {
            System.out.println(messaggio);
            if (scanner.hasNextInt()) {
                valore = scanner.nextInt();
                inputValido = true;
            } else {
                System.out.println("Errore: devi inserire un numero intero!");
                scanner.next();
            }
        }
        return valore;
    }

    // Metodo controllo autore
    public String controlloStringAutore(String messaggio) {
        Scanner scanner = new Scanner(System.in);
        String autore = "";
        boolean inputValido = false;
        while (!inputValido) {
            System.out.println(messaggio);
            if (scanner.hasNextInt()) {
                System.out.println("Errore: non puoi inserire un numero come nome!");
                scanner.next();
            } else {
                autore = scanner.nextLine();
                inputValido = true;
            }
        }
        return autore;
    }


    // Metodo controllo anno
    public int controlloAnno(String messaggio) {
        Scanner scanner = new Scanner(System.in);
        int valore = 0;
        boolean inputValido = false;
        int annoCorrente = Year.now().getValue();

        while (!inputValido) {
            System.out.println(messaggio);
            if (scanner.hasNextInt()) {
                valore = scanner.nextInt();
                if (valore <= annoCorrente) {
                    inputValido = true;
                } else {
                    System.out.println("Errore: l'anno non può essere superiore a " + annoCorrente + "!");
                }
            } else {
                System.out.println("Errore: devi inserire un numero intero!");
                scanner.next();
            }
        }
        return valore;
    }

    // Metodo per controllo periodicità
    public Rivista.Periodicita controlloPeriodicita(String messaggio) {

        Rivista.Periodicita periodicita = null;

        while (periodicita == null) {
            System.out.println(messaggio);
            int scelta = controlloInputValidoInt("1)Settimanale\n2)Mensile\n3)Semestrale");
            switch (scelta) {
                case 1:
                    periodicita = Rivista.Periodicita.SETTIMANALE;
                    break;
                case 2:
                    periodicita = Rivista.Periodicita.MENSILE;
                    break;
                case 3:
                    periodicita = Rivista.Periodicita.SEMESTRALE;
                    break;
                default:
                    System.out.println("Errore! Devi scegliere un numero tra 1 e 3");
                    break;
            }

        }
        return periodicita;
    }


    // Metodo per controllo genere
    public String controlloGenere(String messaggio) {
        String genere = "";
        boolean genereValido = false;

        while (!genereValido) {
            System.out.println(messaggio);
            int scelta = controlloInputValidoInt("1)Romantico\n2)Horror\n3)Thriller\n4)Fantasy\n5)Commedia");
            switch (scelta) {
                case 1:
                    genere = "Romantico";
                    genereValido = true;
                    break;
                case 2:
                    genere = "Horror";
                    genereValido = true;
                    break;
                case 3:
                    genere = "Thriller";
                    genereValido = true;
                    break;
                case 4:
                    genere = "Fantasy";
                    genereValido = true;
                    break;
                case 5:
                    genere = "Commedia";
                    genereValido = true;
                    break;
                default:
                    System.out.println("Errore: scegli un numero da 1 a 5.");
            }
        }
        return genere;
    }

    // Metodo per creare un libro
    public Libro creaLibro() {
        Scanner scanner = new Scanner(System.in);

        int ISBN = controlloInputValidoInt("Inserisci codice ISBN: ");

        System.out.print("Inserisci il titolo: ");
        String titolo = scanner.nextLine();

        int annoPubblicazione = controlloAnno("Inserisci l'anno di pubblicazione: ");

        int numeroPagine = controlloInputValidoInt("Inserisci il numero di pagine: ");

        String autore = controlloStringAutore("Inserisci l'autore: ");

        String genere = controlloGenere("Inserisci il genere: ");

        return new Libro(ISBN, titolo, annoPubblicazione, numeroPagine, autore, genere);
    }

    // Metodo per creare una rivista
    public Rivista creaRivista() {
        Scanner scanner = new Scanner(System.in);

        int ISBN = controlloInputValidoInt("Inserisci ISBN: ");

        System.out.print("Inserisci il titolo: ");
        String titolo = scanner.nextLine();

        int annoPubblicazione = controlloAnno("Inserisci l'anno di pubblicazione: ");

        int numeroPagine = controlloInputValidoInt("Inserisci il numero di pagine: ");

        Rivista.Periodicita periodicita = controlloPeriodicita("Inserisci la periodicità di uscita della rivista: ");

        return new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine, periodicita);
    }

    // Metodo per cercare un articolo
    public void  ricercaArticolo() {
        Scanner scanner = new Scanner(System.in);
        //Articolo articolo;
        int i = -1;
        i = controlloInputValidoInt("1)Ricerca tramite codice ISBN\n2)Ricerca tramite anno di pubblicazione\n3)Ricerca tramite autore");
        while (i != 0) {
            switch (i) {
                case 1:
                    int ISBN = controlloInputValidoInt("Inserisci il codice ISBN dell'articolo che vuoi cercare: ");
                    try {
                        Articolo articolo = items.values().stream()
                                .filter(a -> a.getISBN() == ISBN)
                                .findFirst()
                                .orElseThrow(() -> new ArticoloNonTrovato("Articolo con ISBN " + ISBN + " non trovato."));
                        if (articolo instanceof Libro) {
                            System.out.println("Libro trovato: " + (Libro) articolo);
                        } else if (articolo instanceof Rivista) {
                            System.out.println("Rivista trovata: " + (Rivista) articolo);
                        }

                        i=0;
                        break;
                    } catch (ArticoloNonTrovato e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 2:
                    int annoProduzione = controlloInputValidoInt("Inserisci l'anno di produzione dell'articolo che vuoi cercare: ");
                    try {
                        List<Articolo> articoli = items.values().stream()
                                .filter(articolo -> articolo.getAnnoPubblicazione() == annoProduzione)
                                .toList();
                        if (articoli.isEmpty()) {
                            throw new ArticoloNonTrovato("Articolo con anno di produzione " + annoProduzione + " non trovato.")
                                    ;
                        }
                        articoli.forEach(articolo -> System.out.println("Articolo trovato: " + articolo));
                        i=0;
                        break;
                    } catch (ArticoloNonTrovato e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Inserisci l'autore del libro che vuoi cercare: ");
                    String autore = scanner.nextLine();
                    try {

                        List<Libro> libri = items.values().stream()
                                .filter(libro -> libro instanceof Libro && ((Libro) libro).getAutore().equalsIgnoreCase(autore))
                                .map(libro -> (Libro) libro)  // Cast a Libro
                                .collect(Collectors.toList());

                        if (libri.isEmpty()) {
                            throw new ArticoloNonTrovato("Nessun libro trovato per l'autore: " + autore);
                        }

                        libri.forEach(libro -> System.out.println("Articolo trovato: " + libro));
                        i=0;
                        break;
                    } catch (ArticoloNonTrovato e) {
                        System.out.println(e.getMessage());
                    }
                    i=0;
                    break;
                default:
                    System.out.println("Errore: scegli 1, 2 o 3.");
            }
            i=0;
            break;
        }

    }


    // Metodo per rimuovere un articolo tramite ISBN
    public void rimuoviArticolo() {
        Scanner scanner = new Scanner(System.in);

        int ISBN = controlloInputValidoInt("Inserisci il codice ISBN dell'articolo che vuoi rimuovere: ");

        Articolo articolo = items.remove(ISBN);

        if (articolo != null) {
            System.out.println("Articolo rimosso: " + articolo);
        } else {
            System.out.println("Articolo con ISBN " + ISBN + " non trovato.");
        }
    }

    // Metodo per modificare un articolo
    public void modificaArticolo() {
        Scanner scanner = new Scanner(System.in);
        int ISBN = controlloInputValidoInt("Inserisci il codice ISBN dell'articolo che vuoi modificare: ");
        Articolo articolo = items.get(ISBN);
        if (articolo != null) {
            System.out.println("Articolo trovato: " + articolo);
            if (articolo instanceof Libro) {
                int scelta = controlloInputValidoInt("1)Modifica il titolo del libro\n2)Modifica l'anno di pubblicazione del libro\n3)Modifica il numero di pagine del libro\n4)Modifica l'autore del libro\n5)Modifica il genere del libro");
                switch (scelta) {
                    case 1:
                        System.out.println("Inserisci il nuovo titolo:");
                        String nuovoTitolo = scanner.nextLine();
                        articolo.titolo = nuovoTitolo;
                        break;
                    case 2:
                        int nuovoAnno = controlloInputValidoInt("Inserisci il nuovo anno di pubblicazione: ");
                        articolo.annoPubblicazione = nuovoAnno;
                        break;
                    case 3:
                        int nuovePagine = controlloInputValidoInt("Inserisci il nuovo numero di pagine: ");
                        ;
                        articolo.numeroPagine = nuovePagine;
                        break;
                    case 4:
                        String nuovoAutore = controlloStringAutore("Inserisci il nuovo autore: ");
                        articolo.titolo = nuovoAutore;
                        break;
                    case 5:
                        String nuovoGenere = controlloGenere("Inserisci il nuovo genere:");
                        ((Libro) articolo).setGenere(nuovoGenere);
                        break;
                    default:
                        System.out.println("Errore! Scegli un numero da 1 a 5");
                }
                System.out.println("Libro modificato con successo: " + articolo);

            } else if (articolo instanceof Rivista) {
                int scelta = controlloInputValidoInt("1)Modifica il titolo della rivista\n2)Modifica l'anno di pubblicazione della rivista\3Modifica il numero di pagine della rivista\n4)Modifica la periodicità della rivista");
                switch (scelta) {
                    case 1:
                        System.out.println("Inserisci il nuovo titolo:");
                        String nuovoTitolo = scanner.nextLine();
                        articolo.titolo = nuovoTitolo;
                        break;
                    case 2:
                        int nuovoAnno = controlloInputValidoInt("Inserisci il nuovo anno di pubblicazione: ");
                        articolo.annoPubblicazione = nuovoAnno;
                        break;
                    case 3:
                        int nuovePagine = controlloInputValidoInt("Inserisci il nuovo numero di pagine: ");
                        ;
                        articolo.numeroPagine = nuovePagine;
                        break;
                    case 4:
                        Rivista.Periodicita nuovaPeriodicita = controlloPeriodicita("Inserisci la nuova periodicità: ");
                        ((Rivista) articolo).periodicita = nuovaPeriodicita;
                        break;
                    default:
                        System.out.println("Errore! Scegli un numero da 1 a 4");
                }
                System.out.println("Rivista modificata con successo: " + articolo);
            } else {
                System.out.println("Articolo con ISBN " + ISBN + " non trovato.");
            }
        }
    }

    // Metodo per mostrare tutti gli articoli
    public void mostraArchivio() {
        if (items.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            System.out.println("Ora gli articoli presenti nel catalogo sono:");
            int posizione = 1;
            for (Articolo articolo : items.values()) {
                System.out.println(posizione + ") " + articolo);
                posizione++;
            }
        }
    }

    public void statistiche() {
        if (items.isEmpty()) {
            System.out.println("Il catalogo è vuoto.");
        } else {
            long numeroLibri = items.values().stream().filter(libro -> libro instanceof Libro).count();
            long numeroRiviste = items.values().stream().filter(rivista -> rivista instanceof Rivista).count();
            Optional<Articolo> maxPagine = items.values().stream().max(Comparator.comparingInt(Articolo::getNumeroPagine));
            Articolo articoloMaxPagine = maxPagine.get();
            OptionalDouble mediaNumeroPagine = items.values().stream().mapToInt(Articolo::getNumeroPagine).average();
            double media = mediaNumeroPagine.getAsDouble();

            System.out.println("-- Statistiche del catalogo --");
            System.out.println("Numero totale di libri nel catalogo: " + numeroLibri);
            System.out.println("Numero totale di riviste nel catalogo: " + numeroRiviste);
            System.out.println("L'articolo con il maggior numero di pagine è: " + articoloMaxPagine);
            System.out.println("La media del numero di pagine presenti in tutto il catalogo è: " + media);
        }
    }
}
