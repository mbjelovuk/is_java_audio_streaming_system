/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.klijent;

import java.util.Scanner;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author Milica Bjelovuk
 */

public class Aplikacija {
    public static void main(String[] args) {
        Klijent k = new Klijent();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("ZAHTEVI");
            System.out.println("0. Kraj programa");
            System.out.println("1. Kreiranje grada");
            System.out.println("2. Kreiranje korisnika");
            System.out.println("3. Promena email adrese za korisnika");
            System.out.println("4. Promena mesta za korisnika");
            System.out.println("5. Kreiranje kategorije");
            System.out.println("6. Kreiranje audio snimka");
            System.out.println("7. Promena naziva audio snimka");
            System.out.println("8. Dodavanje kategorije audio snimku");
            System.out.println("9. Kreiranje paketa");
            System.out.println("10. Promena mesečne cene za paket");
            System.out.println("11. Kreiranje pretplate korisnika na paket");
            System.out.println("12. Kreiranje slušanja audio snimka od strane korisnika");
            System.out.println("13. Dodavanje audio snimka u omiljene od strane korisnika");
            System.out.println("14. Kreiranje ocene korisnika za audio snimak");
            System.out.println("15. Menjanje ocene korisnika za audio snimak");
            System.out.println("16. Brisanje ocene korisnika za audio snimak");
            System.out.println("17. Brisanje audio snimka od strane korisnika koji ga je kreirao");
            System.out.println("18. Dohvatanje svih mesta");
            System.out.println("19. Dohvatanje svih korisnika");
            System.out.println("20. Dohvatanje svih kategorija");
            System.out.println("21. Dohvatanje svih audio snimaka");
            System.out.println("22. Dohvatanje kategorija za određeni audio snimak");
            System.out.println("23. Dohvatanje svih paketa");
            System.out.println("24. Dohvatanje svih pretplata za korisnika");
            System.out.println("25. Dohvatanje svih slušanja za audio snimak");
            System.out.println("26. Dohvatanje svih ocena za audio snimak");
            System.out.println("27. Dohvatanje liste omiljenih audio snimaka za korisnika ");   
            System.out.println("Unesite broj zahteva: ");
            
            int zahtev = s.nextInt();
            s.nextLine();
            JsonObject odgovor = null;

            try {
                switch (zahtev) {
                    case 0:
                        System.out.println("baj baj");
                        return;
                    case 1:
                        System.out.println("Ukucajte naziv grada: ");
                        System.out.println("Nazic: ");
                        String naziv = s.nextLine();
                        JsonObject mesto = Json.createObjectBuilder()
                               .add("naziv", naziv)
                               .build();
                        odgovor = k.createMesto(mesto);
                        break;
                    case 2:
                        System.out.println("Ukucajte podatke za korisnika: ");
                        System.out.println("Ime: ");
                        String ime = s.nextLine();
                        System.out.println("Email: ");
                        String email = s.nextLine();
                        System.out.println("Godiste: ");
                        int gg = s.nextInt();
                        s.nextLine();
                        System.out.println("Pol: ");
                        String pol = s.nextLine();
                        System.out.println("ID mesta: ");
                        int idmes = s.nextInt();
                        s.nextLine();
                        JsonObject korisnik = Json.createObjectBuilder()
                                                  .add("ime", ime)
                                                  .add("email", email)
                                                  .add("godiste", gg)
                                                  .add("pol", pol)
                                                  .add("idMesto", idmes)
                                                  .build();
                        odgovor = k.createKorisnik(korisnik);
                        break;
                    case 3:
                        System.out.println("Ukucajte podatke za korisnika: ");
                        System.out.println("ID korisnika: ");
                        int idk = s.nextInt();
                        s.nextLine();
                        System.out.println("Novi email: ");
                        String novemail = s.nextLine();
                        JsonObject emaill = Json.createObjectBuilder()
                                                  .add("idKorisnik", idk)
                                                  .add("noviEmail", novemail)
                                                  .build();
                        odgovor = k.changeKorisnikEmail(idk, emaill);
                        break;
                    case 4:
                        System.out.println("Ukucajte podatke za korisnika: ");
                        System.out.println("ID korisnika: ");
                        int idkm = s.nextInt();
                        s.nextLine();
                        System.out.println("ID novog mesta: ");
                        int novomesto = s.nextInt();
                        s.nextLine();
                        JsonObject mestoo = Json.createObjectBuilder()
                                                  .add("idKorisnik", idkm)
                                                  .add("idMestoNovo", novomesto)
                                                  .build();
                        odgovor = k.changeKorisnikMesto(idkm, mestoo);
                        break;
                    case 5:
                        System.out.println("Ukucajte podatke za kategoriju: ");
                        System.out.println("Naziv kategorije: ");
                        String nn = s.nextLine();
                        JsonObject kategorija = Json.createObjectBuilder()
                                                    .add("naziv", nn)
                                                    .build();
                        odgovor = k.createKategorija(kategorija);
                        break;
                    case 6:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("Naziv audio snimka: ");
                        String nazivs = s.nextLine();
                        System.out.println("Trajanje: ");
                        int tr = s.nextInt();
                        s.nextLine();
                        System.out.println("ID korisnika koji dodaje: ");
                        int kid = s.nextInt();
                        s.nextLine();
                        System.out.println("Datum i vreme postavljanja snimka (yyyy-mm-dd hh:mm:ss): ");
                        String datv = s.nextLine();
                        JsonObject snimak = Json.createObjectBuilder()
                                                .add("naziv", nazivs)
                                                .add("trajanje", tr)
                                                .add("datetime postavljanja", datv)
                                                .add("vlasnikId", kid)
                                                .build();
                        odgovor = k.createAudiosnimak(snimak);
                        break;
                    case 7:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("ID snimka: ");
                        int ids = s.nextInt();
                        s.nextLine();
                        System.out.println("Novi naziv: ");
                        String novn = s.nextLine();
                        JsonObject novoimesnimka = Json.createObjectBuilder()
                                                  .add("idAudiosnimak", ids)
                                                  .add("noviNaziv", novn)
                                                  .build();
                        odgovor = k.changeNazivAudiosnimak(ids, novoimesnimka);
                        break;
                    case 8:
                        System.out.println("Ukucajte podatke za snimak i kategorijuu: ");
                        System.out.println("ID snimka: ");
                        int id1 = s.nextInt();
                        s.nextLine();
                        System.out.println("ID kategorije: ");
                        int id2 = s.nextInt();
                        s.nextLine();
                        JsonObject dodkat = Json.createObjectBuilder()
                                                .add("idAudiosnimak", id1)
                                                .add("idKategorija", id2)
                                                .build();
                        odgovor = k.addKategorija(id1, dodkat);
                        break;
                    case 9:
                        System.out.println("Ukucajte podatke za paket: ");
                        System.out.println("Naziv paketa: ");
                        String nazivp = s.nextLine();
                        System.out.println("Mesecna cena: ");
                        int cena = s.nextInt();
                        s.nextLine();
                        JsonObject paket = Json.createObjectBuilder()
                                               .add("naziv", nazivp)
                                               .add("cena", cena)
                                               .build();
                        odgovor = k.createPaket(paket);
                        break;
                    case 10:
                        System.out.println("Ukucajte podatke za paket: ");
                        System.out.println("ID paketa: ");
                        int idp = s.nextInt();
                        s.nextLine();
                        System.out.println("Nova cena: ");
                        int novacenaa = s.nextInt();
                        s.nextLine();
                        JsonObject cenaa = Json.createObjectBuilder()
                                                 .add("idPaket", idp)
                                                 .add("novaCena", novacenaa)
                                                 .build();
                        odgovor = k.changeCenaPaketa(idp, cenaa);
                        break;
                    case 11:
                        System.out.println("Ukucajte podatke za pretplatu: ");
                        System.out.println("ID korisnika: ");
                        int idkk = s.nextInt();
                        s.nextLine();
                        System.out.println("ID paketa: ");
                        int idpp = s.nextInt();
                        s.nextLine();
                        System.out.println("Datum i vreme pocetka pretplate (yyyy-mm-dd hh:mm:ss): ");
                        String datvr = s.nextLine();
                        JsonObject pretplata = Json.createObjectBuilder()
                                                   .add("idKorisnik", idkk)
                                                   .add("idPaket", idpp)
                                                   .add("datetimePocetka", datvr)
                                                   .build();
                        odgovor = k.createPretplata(pretplata);
                        break;
                    case 12:
                        System.out.println("Ukucajte podatke za slusanje: ");
                        System.out.println("ID korisnika: ");
                        int kidd = s.nextInt();
                        s.nextLine();
                        System.out.println("ID audio snimka: ");
                        int idas = s.nextInt();
                        s.nextLine();
                        System.out.println("Datum i vreme pocetka slusanja (yyyy-mm-dd hh:mm:ss): ");
                        String dv = s.nextLine();
                        System.out.println("Pocetni sekund: ");
                        int ps = s.nextInt();
                        s.nextLine();
                        System.out.println("Odslusano sekundi: ");
                        int os = s.nextInt();
                        s.nextLine();
                        JsonObject slusanje = Json.createObjectBuilder()
                                                  .add("idKorisnik", kidd)
                                                  .add("idAudiosnimak", idas)
                                                  .add("datetime pocslusanja", dv)
                                                  .add("pocetniSekund", ps)
                                                  .add("odslusanoSekundi", os)
                                                  .build();
                        odgovor = k.createSlusanje(slusanje);
                        break;
                    case 13:
                        System.out.println("Ukucajte podatke za korisnika i snimak: ");
                        System.out.println("ID korisnika: ");
                        int idk1 = s.nextInt();
                        s.nextLine();
                        System.out.println("ID audio snimka: ");
                        int ida = s.nextInt();
                        s.nextLine();
                        JsonObject omiljeni = Json.createObjectBuilder()
                                                  .add("idKorisnik", idk1)
                                                  .add("idAudiosnimak", ida)
                                                  .build();
                        odgovor = k.addOmiljeni(ida, omiljeni);
                        break;
                    case 14:
                        System.out.println("Ukucajte podatke za ocenu: ");
                        System.out.println("ID korisnika: ");
                        int idk3 = s.nextInt();
                        s.nextLine();
                        System.out.println("ID audio snimka: ");
                        int ida4 = s.nextInt();
                        s.nextLine();
                        System.out.println("Ocena (1-5): ");
                        int oc = s.nextInt();
                        s.nextLine();
                        System.out.println("Datum i vreme pocetka slusanja (yyyy-mm-dd hh:mm:ss): ");
                        String dvv = s.nextLine();
                        JsonObject ocena = Json.createObjectBuilder()
                                               .add("idKorisnik", idk3)
                                               .add("idAudiosnimak", ida4)
                                               .add("ocena", oc)
                                               .add("datetimeOcenjivanja", dvv)
                                               .build();
                        odgovor = k.createOcena(ocena);
                        break;
                    case 15:
                        System.out.println("Ukucajte podatke za ocenu: ");
                        System.out.println("ID korisnika: ");
                        int idko = s.nextInt();
                        s.nextLine();
                        System.out.println("ID snimka: ");
                        int idos = s.nextInt();
                        s.nextLine();
                        System.out.println("Nova ocena (1-5): ");
                        int novaoc = s.nextInt();
                        s.nextLine();
                        odgovor = k.changeOcena(idko, idos, novaoc);
                        break;
                    case 16:
                        System.out.println("Ukucajte podatke za ocenu: ");
                        System.out.println("ID korisnika: ");
                        int idkod = s.nextInt();
                        s.nextLine();
                        System.out.println("ID ocene: ");
                        int ido1 = s.nextInt();
                        s.nextLine();
                        odgovor = k.deleteOcena(idkod, ido1);
                        break;
                    case 17:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("ID audio snimka za brisanje: ");
                        int idad = s.nextInt();
                        s.nextLine();
                        System.out.println("ID korisnika koji brise: ");
                        int idkb = s.nextInt();
                        s.nextLine();
                        odgovor = k.deleteAudiosnimak(idad, idkb);
                        break;
                    case 18:
                        System.out.println("Sva mesta u bazi: ");
                        odgovor = k.getAllMesta();
                        break;
                    case 19:
                        System.out.println("Svi korisnici u bazi: ");
                        odgovor = k.getAllKorisnici();
                        break;
                    case 20:
                        System.out.println("Sve kategorije u bazi: ");
                        odgovor = k.getAllKategorije();
                        break;
                    case 21:
                        System.out.println("Svi audiosnimci u bazi: ");
                        odgovor = k.getAllAudiosnimci();
                        break;
                    case 22:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("ID audio snimka: ");
                        int ida2 = s.nextInt();
                        s.nextLine();
                        System.out.println("Sve kategorije u bazi za snimak " + ida2 + ": ");
                        odgovor = k.getKategorijeZaAudiosnimak(ida2);
                        break;
                    case 23:
                        System.out.println("Svi paketi u bazi: ");
                        odgovor = k.getAllPaketi();
                        break;
                    case 24:
                        System.out.println("Ukucajte podatke za korisnika: ");
                        System.out.println("ID korisnika: ");
                        int idk4 = s.nextInt();
                        s.nextLine();
                        System.out.println("Sve pretplate u bazi za korisnika " + idk4 + ": ");
                        odgovor = k.getPretplateZaKorisnika(idk4);
                        break;
                    case 25:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("ID audio snimka: ");
                        int ida3 = s.nextInt();
                        s.nextLine();
                        System.out.println("Sva slusanja u bazi za snimak " + ida3 + ": ");
                        odgovor = k.getSlusanjaZaSnimak(ida3);
                        break;
                    case 26:
                        System.out.println("Ukucajte podatke za snimak: ");
                        System.out.println("ID audio snimka: ");
                        int ida5 = s.nextInt();
                        s.nextLine();
                        System.out.println("Sva ocena u bazi za snimak " + ida5 + ": ");
                        odgovor = k.getOceneZaSnimak(ida5);
                        break;
                    case 27:
                        System.out.println("Ukucajte podatke za korisnika: ");
                        System.out.println("ID korisnika: ");
                        int idk5 = s.nextInt();
                        s.nextLine();
                        System.out.println("Svi omiljeni snimci u bazi za korisnika " + idk5 + ": ");
                        odgovor = k.getOmiljeni(idk5);
                        break;
                    default:
                        System.out.println("uh oh pokusajte ponovo");
                }
                if (odgovor != null) {
                    System.out.println(odgovor);
                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }
}
