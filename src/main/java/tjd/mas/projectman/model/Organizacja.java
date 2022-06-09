package tjd.mas.projectman.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.helpers.NextKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Organizacja implements Serializable {
    private static final long serialVersionUID = -7196755162347456410L;

    private String nazwa;
    private String NIP;
    private List<Organizacja.Adres> adresy = new ArrayList<>();
    private List<Organizacja.Dzial> dzialy = new ArrayList<>();
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

        @JsonManagedReference
        private Map<Integer, ProjektWewnetrzny> projektyWewnetrzne = new HashMap<>();
        @JsonManagedReference
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

        public Organizacja getOrganizacja(){
            return Organizacja.this;
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
