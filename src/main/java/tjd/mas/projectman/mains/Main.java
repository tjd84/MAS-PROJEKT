package tjd.mas.projectman.mains;

import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.Osoba;
import tjd.mas.projectman.model.Praca;
import tjd.mas.projectman.model.Projekt;
import tjd.mas.projectman.model.ProjektDlaKlienta;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        //Extent extent = ExtentFile.readExtent();
        Extent extent = new Extent();

        Osoba o1 = new Osoba(extent, "Jan", "Kowalski", "jkowal@gmail.com", "P@ssw0rd");
        o1.setRoleKoordynator(6000, 5);

        Osoba o2 = new Osoba(extent, "Krzysztof", "Nowak", "tomasz.dzioch@gmail.com", "P@ssw0rd");
        o2.setRoleKoordynator(6000, 5);
        o2.setRolePracownik(115);

        Projekt p = new ProjektDlaKlienta(extent, "Projekt pokazowy", LocalDate.now(), 35000, 25000);
        p.setKoordynator(o1.getKoordynator());
        p.setKoordynator(o2.getKoordynator());

        Projekt.Zadanie z1 = p.addZadanie("Wybudować dom", LocalDate.of(2022,8,12));
        z1.setPracownik(o2.getPracownik());
        Projekt.Zadanie z2 = p.addZadanie("Sprzedać dom", LocalDate.of(2023,1,20));
        z2.setPracownik(o2.getPracownik());

        try{
            Praca w1 = new Praca(extent, "Zakup cegieł", LocalDate.now(), 1, o2.getPracownik(), z1);
            Praca w2 = new Praca(extent, "Transport cegieł", LocalDate.now(), 3, o2.getPracownik(), z1);
            Praca w3 = new Praca(extent, "Rozładunek cegiał", LocalDate.now(), 1, o2.getPracownik(), z1);
        }
        catch (Exception ex) {}



        //System.out.println(extent);

        ExtentFile.writeExtent(extent);

    }
}
