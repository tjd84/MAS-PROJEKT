package tjd.mas.projectman.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.helpers.NextKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Osoba implements Serializable {
    private static final long serialVersionUID = -5179204983703568317L;
    private String imie;
    private String nazwisko;
    private String email;
    private String haslo;
    private Boolean blokada;
    private Organizacja.Dzial dzial;
    private Koordynator koordynator;
    private Pracownik pracownik;
    private Manager manager;

    public Osoba(Extent extent, String imie, String nazwisko, String email, String haslo) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.haslo = haslo;
        this.blokada = false;
        extent.addToExtent(this);
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleKoordynator(Integer dodatkoweWynagrodzenie, Integer maksymalnaLiczbaProjektow) {
        Koordynator tmp = new Koordynator(dodatkoweWynagrodzenie, maksymalnaLiczbaProjektow);
        this.koordynator = tmp;
    }
    public void removeRoleKoordynator(){
        this.koordynator=null;
    }
    @JsonBackReference
    public Koordynator getKoordynator(){
        return koordynator;
    }

    public void setRolePracownik(Integer stawkaGodzinowa) {
        Pracownik tmp = new Pracownik(stawkaGodzinowa);
        this.pracownik = tmp;
    }
    public void removeRolePracownik(){
        this.pracownik=null;
    }
    @JsonBackReference
    public Pracownik getPracownik(){
        return this.pracownik;
    }

    public void setRoleManager(Integer dodatkoweWynagrodzenie) {
        Manager tmp = new Manager(dodatkoweWynagrodzenie);
        this.manager = tmp;
    }
    public void removeRoleManager(){
        this.manager=null;
    }
    @JsonBackReference
    public Manager getManager(){
        return this.manager;
    }

    public void setDzial(Organizacja.Dzial dzial){
        if(this.dzial!=null && this.dzial!=dzial){
            this.dzial.removeOsoba(this);
        }
        this.dzial=dzial;
        dzial.addOsoba(this);
    }

    @Override
    public String toString() {
        return String.format("%-15s|%-15s|%-25s|%-15s|%-15s|%-15s|%-15s\n", imie, nazwisko, email, blokada?"ZABLOKOWANY":"AKTYWNY", pracownik!=null?"       X":"", koordynator!=null?"       X":"", manager!=null?"       X":"");
    }

    //klasy wewnętrzne - implementacja kompozycji
    public class Koordynator implements Serializable{
        private static final long serialVersionUID = -1349384429251301582L;
        private Integer dodatkoweWynagrodzenie;
        private Integer maksymalnaLiczbaProjektow;
        private Map<Integer, Projekt> projekty = new HashMap();

        private Koordynator(Integer dodatkoweWynagrodzenie, Integer maksymalnaLiczbaProjektow){
            this.dodatkoweWynagrodzenie=dodatkoweWynagrodzenie;
            this.maksymalnaLiczbaProjektow=maksymalnaLiczbaProjektow;
        }

        public Integer getDodatkoweWynagrodzenie() {
            return dodatkoweWynagrodzenie;
        }

        public void setDodatkoweWynagrodzenie(Integer dodatkoweWynagrodzenie) {
            this.dodatkoweWynagrodzenie = dodatkoweWynagrodzenie;
        }

        public Integer getMaksymalnaLiczbaProjektow() {
            return maksymalnaLiczbaProjektow;
        }

        public void setMaksymalnaLiczbaProjektow(Integer maksymalnaLiczbaProjektow) {
            this.maksymalnaLiczbaProjektow = maksymalnaLiczbaProjektow;
        }

        public Osoba getOsoba(){
            return Osoba.this;
        }

        public void addProjekt(Projekt projekt) {
            if(!projekty.containsValue(projekt)) {
                projekty.put(new NextKey<Projekt>().nextKey(projekty), projekt);
                projekt.setKoordynator(this);
            }
        }

        public void removeProjekt(Projekt projekt){
            for (Map.Entry<Integer, Projekt> entry : projekty.entrySet()) {
                if(entry.getValue()==projekt)
                    this.projekty.remove(entry.getKey());
            }
        }

    }
    public class Manager implements Serializable{
        private static final long serialVersionUID = -6668834540097859257L;
        private Integer dodatkoweWynagrodzenie;

        private Manager(Integer dodatkoweWynagrodzenie){
            this.dodatkoweWynagrodzenie=dodatkoweWynagrodzenie;
        }

        public Osoba getOsoba(){
            return Osoba.this;
        }

        public Integer getDodatkoweWynagrodzenie() {
            return dodatkoweWynagrodzenie;
        }
    }
    public class Pracownik implements Serializable{
        private static final long serialVersionUID = -5645895639291601484L;
        private Integer stawkaGodzinowa;
        private Integer limitDobowyCzasuPracy=12;
        private Map<Integer, Projekt.Zadanie> zadania = new HashMap<>();
        private Map<Integer, Praca> prace = new HashMap<>();

        private Pracownik(Integer stawkaGodzinowa){
            this.stawkaGodzinowa=stawkaGodzinowa;
        }

        public Osoba getOsoba(){
            return Osoba.this;
        }

        public Integer getStawkaGodzinowa() {
            return stawkaGodzinowa;
        }

        public void setStawkaGodzinowa(Integer stawkaGodzinowa) {
            this.stawkaGodzinowa = stawkaGodzinowa;
        }

        public Integer getLimitDobowyCzasuPracy() {
            return this.limitDobowyCzasuPracy;
        }

        public void addPraca(Praca praca) {
            if(!prace.containsValue(praca)) {
                prace.put(new NextKey<Praca>().nextKey(prace), praca);
                praca.setPracownik(this);
            }
        }

        public void removePraca(Praca praca){
            for (Map.Entry<Integer, Praca> entry : prace.entrySet()) {
                if(entry.getValue()==praca)
                    this.prace.remove(entry.getKey());
            }
        }


        public void addZadanie(Projekt.Zadanie zadanie) {
            if(!zadania.containsValue(zadanie)) {
                zadania.put(new NextKey<Projekt.Zadanie>().nextKey(zadania),zadanie);
                zadanie.setPracownik(this);
            }
        }

        public void removeZadanie(Projekt.Zadanie zadanie){
            for (Map.Entry<Integer, Projekt.Zadanie> entry : zadania.entrySet()) {
                if(entry.getValue()==zadanie)
                    this.zadania.remove(entry.getKey());
            }
        }

        public Integer getTotalHoursByDate(LocalDate data){
            Integer hours=0;
            for(Map.Entry<Integer, Praca> tmp : this.prace.entrySet()){
                if(tmp.getValue().getDataWykonania().equals(data)){
                    hours+=tmp.getValue().getCzasPracy();
                }
            }
            return hours;
        }


    }

}
