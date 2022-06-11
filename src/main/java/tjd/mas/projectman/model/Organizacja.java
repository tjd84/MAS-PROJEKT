package tjd.mas.projectman.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.helpers.NextKey;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Organizacja implements Serializable {
    private static final long serialVersionUID = -7196755162347456410L;

    private String nazwa;
    private String NIP;
    private Map<Integer, Adres> adresy = new HashMap<>();
    private Map<Integer, Dzial> dzialy = new HashMap<>();
    private List<ProjektWewnetrzny> projektyWewnetrzne = new ArrayList<>();

    public Organizacja(Extent extent, String nazwa, String NIP) {
        this.nazwa = nazwa;
        this.NIP = NIP;
        extent.addToExtent(this);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public Dzial addDzial(String nazwa) {
        Dzial tmp = new Dzial(nazwa);
        dzialy.put(new NextKey<Organizacja.Dzial>().nextKey(dzialy), tmp);
        return tmp;
    }
    public void removeDzial(Dzial dzial){
        dzialy.remove(dzialy);
    }
    public Map<Integer, Dzial> getDzialy(){
        return dzialy;
    }


    public Adres addAdres(String nazwa, String ulica, String numerBudynku, String numerLokalu, String kod, String miasto) {
        Adres tmp = new Adres(nazwa, ulica, numerBudynku, numerLokalu, kod, miasto);
        adresy.put(new NextKey<Organizacja.Adres>().nextKey(adresy), tmp);
        return tmp;
    }
    public void removeDzial(Adres adres){
        adresy.remove(adres);
    }
    public Map<Integer, Adres> getAdresy(){
        return adresy;
    }


    public class Adres implements Serializable {
        private static final long serialVersionUID = -7854232163294913302L;
        private String nazwa;
        private String ulica;
        private String numerBudynku;
        private String numerLokalu;
        private String kod;
        private String miasto;

        public Adres(String nazwa, String ulica, String numerBudynku, String numerLokalu, String kod, String miasto) {
            this.nazwa = nazwa;
            this.ulica = ulica;
            this.numerBudynku = numerBudynku;
            this.numerLokalu = numerLokalu;
            this.kod = kod;
            this.miasto = miasto;
        }

        @JsonBackReference
        public Organizacja getOrganizacja(){
            return Organizacja.this;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public String getUlica() {
            return ulica;
        }

        public void setUlica(String ulica) {
            this.ulica = ulica;
        }

        public String getNumerBudynku() {
            return numerBudynku;
        }

        public void setNumerBudynku(String numerBudynku) {
            this.numerBudynku = numerBudynku;
        }

        public String getNumerLokalu() {
            return numerLokalu;
        }

        public void setNumerLokalu(String numerLokalu) {
            this.numerLokalu = numerLokalu;
        }

        public String getKod() {
            return kod;
        }

        public void setKod(String kod) {
            this.kod = kod;
        }

        public String getMiasto() {
            return miasto;
        }

        public void setMiasto(String miasto) {
            this.miasto = miasto;
        }
    }

    public class Dzial implements Serializable {
        private static final long serialVersionUID = 3733178013949540214L;

        private String nazwa;
        private Map<Integer, ProjektWewnetrzny> projektyWewnetrzne = new HashMap<>();
        private Map<Integer, Osoba> osoby = new HashMap<>();

        public Dzial(String nazwa) {
            this.nazwa = nazwa;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        @JsonBackReference
        public Organizacja getOrganizacja(){
            return Organizacja.this;
        }

        public Map<Integer, Osoba> getOsoba(){
            return osoby;
        }

        public void addProjektWewnetrzny(ProjektWewnetrzny projektWewnetrzny){
            if(!projektyWewnetrzne.containsValue(projektWewnetrzny)) {
                projektyWewnetrzne.put(new NextKey<ProjektWewnetrzny>().nextKey(projektyWewnetrzne), projektWewnetrzny);
                projektWewnetrzny.setDzial(this);
            }
        }

        public void removeProjektWewnetrzny(ProjektWewnetrzny projektWewnetrzny){
            for (Map.Entry<Integer, ProjektWewnetrzny> entry : projektyWewnetrzne.entrySet()) {
                if(entry.getValue()==projektWewnetrzny)
                    this.projektyWewnetrzne.remove(entry.getKey());
            }
        }

        public void addOsoba(Osoba osoba){
            if(!osoby.containsValue(osoba)) {
                osoby.put(new NextKey<Osoba>().nextKey(osoby), osoba);
                osoba.setDzial(this);
            }
        }

        public void removeOsoba(Osoba osoba){
            for (Map.Entry<Integer, Osoba> entry : osoby.entrySet()) {
                if(entry.getValue()==osoba)
                    this.osoby.remove(entry.getKey());
            }
        }
    }
}
