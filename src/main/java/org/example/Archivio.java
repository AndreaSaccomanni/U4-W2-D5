package org.example;

import java.time.Year;
import java.util.*;
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
    public String controlloPeriodicita(String messaggio) {
        String periodicita = "";
        boolean periodicitaValida = false;

        while (!periodicitaValida) {
            System.out.println(messaggio);
            int scelta = controlloInputValidoInt("1)Settimanale\n2)Mensile\n3)Semestrale");
            switch (scelta) {
                case 1:
                    periodicita = "Settimanale";
                    periodicitaValida = true;
                    break;
                case 2:
                    periodicita = "Mensile";
                    periodicitaValida = true;
                    break;
                case 3:
                    periodicita = "Semestrale";
                    periodicitaValida = true;
                    break;
                default:
                    System.out.println("Errore: scegli 1, 2 o 3.");

            }
        }
        return periodicita;
    }

    // Metodo per creare un libro
    public Libro creaLibro() {
        Scanner scanner = new Scanner(System.in);


        int ISBN = controlloInputValidoInt("Inserisci codice ISBN: ");
        scanner.nextLine();

        System.out.print("Inserisci il titolo: ");
        String titolo = scanner.nextLine();

        int annoPubblicazione = controlloAnno("Inserisci l'anno di pubblicazione: ");
        scanner.nextLine();

        int numeroPagine = controlloInputValidoInt("Inserisci il numero di pagine: ");
        scanner.nextLine();

        System.out.print("Inserisci l'autore: ");
        String autore = scanner.nextLine();

        System.out.print("Inserisci il genere: ");
        String genere = scanner.nextLine();

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


        String periodicita = controlloPeriodicita("Inserisci la periodicità di uscita della rivista: ");


        return new Rivista(ISBN, titolo, annoPubblicazione, numeroPagine, periodicita);
    }

    // Metodo per cercare un articolo
    public void ricercaArticolo() {
        Scanner scanner = new Scanner(System.in);
        int i = -1;
        System.out.println("Digita 1 per eseguire la ricerca tramite il codice ISBN");
        System.out.println("Digita 2 per eseguire la ricerca tramite l'anno di pubblicazione");
        System.out.println("Digita 3 per eseguire la ricerca tramite autore");
        i = controlloInputValidoInt("1)Ricerca tramite codice ISBN\n2)Ricerca tramite anno di pubblicazione\n3)Ricerca tramite autore");

        switch (i) {
            case 1:
                int ISBN = controlloInputValidoInt("Inserisci il codice ISBN dell'articolo che vuoi cercare: ");
                try {
                    // Utilizzo degli Stream per cercare l'articolo tramite ISBN
                    Articolo articolo = items.values().stream()
                            .filter(a -> a.getISBN() == ISBN)  // Filtra per ISBN
                            .findFirst()
                            .orElseThrow(() -> new ArticoloNonTrovato("Articolo con ISBN " + ISBN + " non trovato."));
                    if (articolo instanceof Libro) {
                        System.out.println("Libro trovato: " + (Libro)articolo);
                    } else if (articolo instanceof Rivista) {
                        System.out.println("Rivista trovata: " + (Rivista)articolo);
                    }
                } catch (ArticoloNonTrovato e) {
                    System.out.println(e.getMessage());
                }
                break;


            case 2:
                int annoProduzione = controlloInputValidoInt("Inserisci l'anno di produzione dell'articolo che vuoi cercare: ");
                try {
                    // Utilizzo degli Stream per cercare tramite anno di produzione
                    Articolo articolo = items.values().stream()
                            .filter(a -> a.getAnnoPubblicazione() == annoProduzione)  // Filtra per anno di pubblicazione
                            .findFirst()  // Trova il primo risultato
                            .orElseThrow(() -> new ArticoloNonTrovato("Articolo con anno di produzione " + annoProduzione + " non trovato."));  // Se non trovato, lancia l'eccezione
                    if (articolo instanceof Libro) {
                        System.out.println("Libro trovato: " + (Libro)articolo);
                    } else if (articolo instanceof Rivista) {
                        System.out.println("Rivista trovata: " + (Rivista)articolo);
                    }
                } catch (ArticoloNonTrovato e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                System.out.println("Inserisci l'autore del libro che vuoi cercare: ");
                String autore = scanner.nextLine();
                try {

                    List<Libro> libri = items.values().stream()
                            .filter(a -> a instanceof Libro && ((Libro) a).getAutore().equalsIgnoreCase(autore))
                            .map(a -> (Libro) a)  // Cast a Libro
                            .collect(Collectors.toList());  // Raccogli i risultati in una lista

                    if (libri.isEmpty()) {
                        throw new ArticoloNonTrovato("Nessun libro trovato per l'autore: " + autore);
                    }

                    // Stampa i libri trovati
                    libri.forEach(libro -> System.out.println("Articolo trovato: " + libro));

                } catch (ArticoloNonTrovato e) {
                    System.out.println(e.getMessage());
                }
                break;


            default:
                System.out.println("Errore: scegli 1, 2 o 3.");
        }
    }


    // Metodo per rimuovere un articolo tramite ISBN
    public void rimuoviArticolo() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci l'ISBN dell'articolo da rimuovere: ");
        int ISBN = scanner.nextInt();

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

        System.out.print("Inserisci l'ISBN dell'articolo da modificare: ");
        Long ISBN = scanner.nextLong();
        scanner.nextLine();

        Articolo articolo = items.get(ISBN);

        if (articolo != null) {
            System.out.println("Articolo trovato: " + articolo);

            // Modifica titolo
            System.out.print("Nuovo titolo (lascia vuoto per non modificare): ");
            String nuovoTitolo = scanner.nextLine();
            if (!nuovoTitolo.isEmpty()) {
                articolo.titolo = nuovoTitolo;
            }

            // Modifica anno di pubblicazione
            System.out.print("Nuovo anno di pubblicazione (lascia vuoto per non modificare): ");
            String nuovoAnno = scanner.nextLine();
            if (!nuovoAnno.isEmpty()) {
                articolo.annoPubblicazione = Integer.parseInt(nuovoAnno);
            }

            // Modifica numero di pagine
            System.out.print("Nuovo numero di pagine (lascia vuoto per non modificare): ");
            String nuovePagine = scanner.nextLine();
            if (!nuovePagine.isEmpty()) {
                articolo.numeroPagine = Integer.parseInt(nuovePagine);
            }

            System.out.println("Articolo modificato: " + articolo);
        } else {
            System.out.println("Articolo con ISBN " + ISBN + " non trovato.");
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
}
