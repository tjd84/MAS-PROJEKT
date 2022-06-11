package tjd.mas.projectman.mains;

import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        //Extent extent = ExtentFile.readExtent();
        Extent extent = new Extent();

        Organizacja org = new Organizacja(extent, "Firma Testowa Sp. z o.o.", "5213268226");
        org.addAdres("Siedziba", "Jara", "12", "2", "02-988", "Warszawa");
        org.addAdres("Magazyn", "Wilanowska", "5A", "2", "02-762", "Warszawa");
        Organizacja.Dzial d1 = org.addDzial("Bardzo ważny dział");
        Organizacja.Dzial d2 =  org.addDzial("Jeszcze ważniejszy dział");

        Osoba o1 = new Osoba(extent, "Tomasz", "Dzioch", "tomasz.dzioch@gmail.com", "P@ssw0rd");
        o1.setRoleKoordynator(6000, 5);
        o1.setRoleManager(5000);
        o1.setDzial(d1);

        Osoba o2 = new Osoba(extent, "Krzysztof", "Drogi", "k.nowak@gmail.com", "P@ssw0rd");
        o2.setRoleKoordynator(6000, 5);
        o2.setRolePracownik(215);
        o2.setDzial(d2);

        Osoba o3 = new Osoba(extent, "Michał", "Wolontariusz", "wolontatiat@michal.pl", "P@ssw0rd");
        o3.setRolePracownik(1);
        o3.setDzial(d2);

        Osoba o4 = new Osoba(extent, "Jan", "Kowalski", "jkowal@gmail.com", "P@ssw0rd");
        o4.setRolePracownik(60);
        o4.setDzial(d2);

        Projekt p = new ProjektDlaKlienta(extent, "Projekt pokazowy - buda dla psa", LocalDate.now(), 5000, 3000);
        p.setPrzewidywanaDataZakonczenia(LocalDate.of(2023,01,01));
        p.setKoordynator(o1.getKoordynator());

        Projekt.Zadanie z1 = p.addZadanie("Zaprojektować budę", LocalDate.of(2022,7,11));
        z1.setPracownik(o2.getPracownik());
        Projekt.Zadanie z2 = p.addZadanie("Wybudować budę", LocalDate.of(2022,8,20));
        z2.setPracownik(o4.getPracownik());
        Projekt.Zadanie z3 = p.addZadanie("Sprzedać budę", LocalDate.of(2022,12,1));
        z3.setPracownik(o3.getPracownik());

        try{
            new Praca(extent, "Koncepcja naszkicowana", LocalDate.now(), 5, o2.getPracownik(), z1);
            new Praca(extent, "Wykaz materiałów przygotowany", LocalDate.now(), 3, o2.getPracownik(), z1);

            new Praca(extent, "Zakup desek", LocalDate.now(), 1, o4.getPracownik(), z2);
            new Praca(extent, "Transport desek", LocalDate.now(), 3, o4.getPracownik(), z2);
            new Praca(extent, "Rozładunek desek", LocalDate.now(), 2, o4.getPracownik(), z2);
        }
        catch (Exception ex) {}


        ExtentFile.writeExtent(extent);

    }
}
