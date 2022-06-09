package tjd.mas.projectman.extent;

import tjd.mas.projectman.helpers.NextKey;
import tjd.mas.projectman.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extent implements Serializable {
    private static final long serialVersionUID = -3716323185420030657L;

    public Map<Integer, Organizacja> organizacjaExtent = new HashMap<>();
    public Map<Integer, Osoba> osobaExtent = new HashMap<>();
    public Map<Integer, Praca> pracaExtent = new HashMap<>();
    public Map<Integer, Projekt> projektExtent = new HashMap<>();
    public Map<Integer, ProjektDlaKlienta> projektDlaDklientaExtent = new HashMap<>();
    public Map<Integer, ProjektWewnetrzny> projektWewnetrznyExtent = new HashMap<>();


    public void addToExtent(Organizacja object){
        organizacjaExtent.put(new NextKey<Organizacja>().nextKey(organizacjaExtent), object);
    }
    public void addToExtent(Osoba object){
        osobaExtent.put(new NextKey<Osoba>().nextKey(osobaExtent), object);
    }
    public void addToExtent(Praca object){
        pracaExtent.put(new NextKey<Praca>().nextKey(pracaExtent), object);
    }
    public void addToExtent(Projekt object){
        projektExtent.put(new NextKey<Projekt>().nextKey(projektExtent), object);
    }

    public Map<Integer, Organizacja> getOrganizacjaExtent() {
        return organizacjaExtent;
    }

    public void setOrganizacjaExtent(Map<Integer, Organizacja> organizacjaExtent) {
        this.organizacjaExtent = organizacjaExtent;
    }

    public Map<Integer, Osoba> getOsobaExtent() {
        return osobaExtent;
    }

    public void setOsobaExtent(Map<Integer, Osoba> osobaExtent) {
        this.osobaExtent = osobaExtent;
    }

    public Map<Integer, Praca> getPracaExtent() {
        return pracaExtent;
    }

    public void setPracaExtent(Map<Integer, Praca> pracaExtent) {
        this.pracaExtent = pracaExtent;
    }

    public Map<Integer, Projekt> getProjektExtent() {
        return projektExtent;
    }

    public void setProjektExtent(Map<Integer, Projekt> projektExtent) {
        this.projektExtent = projektExtent;
    }

    public Map<Integer, ProjektDlaKlienta> getProjektDlaDklientaExtent() {
        return projektDlaDklientaExtent;
    }

    public void setProjektDlaDklientaExtent(Map<Integer, ProjektDlaKlienta> projektDlaDklientaExtent) {
        this.projektDlaDklientaExtent = projektDlaDklientaExtent;
    }

    public Map<Integer, ProjektWewnetrzny> getProjektWewnetrznyExtent() {
        return projektWewnetrznyExtent;
    }

    public void setProjektWewnetrznyExtent(Map<Integer, ProjektWewnetrzny> projektWewnetrznyExtent) {
        this.projektWewnetrznyExtent = projektWewnetrznyExtent;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Stan ekstensji\n\n");
        sb.append("Osoby\n");
        sb.append(String.format("%-15s|%-15s|%-25s|%-15s|%-15s|%-15s|%-15s\n", "Imie", "Nazwisko", "E-mail", "Blokada", "Pracownik", "Koordynator", "Manager"));
        sb.append(String.format("%-15s|%-15s|%-25s|%-15s|%-15s|%-15s|%-15s\n", "---------------", "---------------", "-------------------------", "---------------", "---------------", "---------------", "---------------"));
        for (Map.Entry<Integer, Osoba> tmp : osobaExtent.entrySet()){
            sb.append(tmp.getValue());
        }

        return sb.toString();
    }
}
