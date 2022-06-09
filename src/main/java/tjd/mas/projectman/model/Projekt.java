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

public abstract class Projekt implements Serializable {
    private static final long serialVersionUID = 4157717825230077241L;
    private String nazwa;
    private LocalDate dataRozpoczecia;
    private LocalDate przewidywanaDataZakonczenia;
    private LocalDate dataZakonczenia;
    private String URL; //smieciowy atrybut - do jsona ;)

    @JsonManagedReference
    private Map<Integer, Zadanie> zadania = new HashMap<>();

    //@JsonManagedReference
    private Osoba.Koordynator koordynator;

    public Projekt(Extent extent, String nazwa, LocalDate dataRozpoczecia){
        this.nazwa=nazwa;
        this.dataRozpoczecia=dataRozpoczecia;
        extent.addToExtent(this);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public LocalDate getDataRozpoczecia() {
        return dataRozpoczecia;
    }

    public void setDataRozpoczecia(LocalDate dataRozpoczecia) {
        this.dataRozpoczecia = dataRozpoczecia;
    }

    public LocalDate getPrzewidywanaDataZakonczenia() {
        return przewidywanaDataZakonczenia;
    }

    public void setPrzewidywanaDataZakonczenia(LocalDate przewidywanaDataZakonczenia) {
        this.przewidywanaDataZakonczenia = przewidywanaDataZakonczenia;
    }

    public LocalDate getDataZakonczenia() {
        return dataZakonczenia;
    }

    public void setDataZakonczenia(LocalDate dataZakonczenia) {
        this.dataZakonczenia = dataZakonczenia;
    }

    public void setKoordynator(Osoba.Koordynator koordynator){
        if(this.koordynator !=null && this.koordynator!=koordynator){
            this.koordynator.removeProjekt(this);
        }
        this.koordynator=koordynator;
        koordynator.addProjekt(this);
    }

    @JsonManagedReference
    public Osoba.Koordynator getKoordynator() {
        return koordynator;
    }

    public Zadanie addZadanie(String nazwa, LocalDate planowanaDataWykonaia) {
        Zadanie tmp = new Zadanie(nazwa, planowanaDataWykonaia);
        zadania.put(new NextKey<Zadanie>().nextKey(zadania), tmp);
        return tmp;
    }
    public void removeZadanie(Zadanie zadanie){
        zadania.remove(zadanie);
    }
    public Map<Integer, Zadanie> getZadania(){
        return zadania;
    }

    public Integer getTotalCost(){
        Integer cost=0;
        for(Map.Entry<Integer, Zadanie> tmp : zadania.entrySet()){
            cost+=tmp.getValue().getTotalCost();
        }
        return cost;
    }

    //śmieciowe metody zwracajace url w srodowisku dev
    public String getURL(){
        return URL;
    }
    public void setURL(Integer idInMap){
        this.URL="http://localhost:8080/projectMan-1.0-SNAPSHOT/projekt?projectId="+idInMap;
    }

    //kompozycja poprzez klasę wewnętrzną
    public class Zadanie implements Serializable {
        private static final long serialVersionUID = -3915180820339392116L;
        private String nazwa;
        private LocalDate planowanaDataWykonaia;
        private ZadanieStatusEnum status;
        private Osoba.Pracownik pracownik;
        private Map<Integer, Praca> prace = new HashMap<>();

        public Zadanie(String nazwa, LocalDate planowanaDataWykonaia){
            this.nazwa=nazwa;
            this.planowanaDataWykonaia=planowanaDataWykonaia;
            this.status=ZadanieStatusEnum.oczekujace;
        }

        public String getNazwa() {
            return nazwa;
        }

        public void setNazwa(String nazwa) {
            this.nazwa = nazwa;
        }

        public LocalDate getPlanowanaDataWykonaia() {
            return planowanaDataWykonaia;
        }

        public void setPlanowanaDataWykonaia(LocalDate planowanaDataWykonaia) {
            this.planowanaDataWykonaia = planowanaDataWykonaia;
        }

        public ZadanieStatusEnum getStatus() {
            return status;
        }

        public void setStatus(ZadanieStatusEnum status) {
            this.status = status;
        }

        @JsonBackReference
        public Projekt getProjekt(){
            return Projekt.this;
        }

        public void setPracownik(Osoba.Pracownik pracownik){
            if(this.pracownik!=null && this.pracownik!=pracownik){
                this.pracownik.removeZadanie(this);
            }
            this.pracownik=pracownik;
            pracownik.addZadanie(this);
        }

        public Osoba.Pracownik getPracownik(){
            return pracownik;
        }

        public void addPraca(Praca praca){
            if(!prace.containsValue(praca)) {
                prace.put(new NextKey<Praca>().nextKey(prace), praca);
                praca.setZadanie(this);
            }
        }

        public void removePraca(Praca praca){
            for (Map.Entry<Integer, Praca> entry : prace.entrySet()) {
                if(entry.getValue()==praca)
                    this.prace.remove(entry.getKey());
            }
        }

        @JsonManagedReference
        public Map<Integer, Praca> getPrace() {
            return prace;
        }

        public Integer getTotalTime(){
            Integer time=0;
            for(Map.Entry<Integer, Praca> tmp : getPrace().entrySet()){
                time+=tmp.getValue().getCzasPracy();
            }
            return time;
        }

        public Integer getTotalCost(){
            Integer cost=0;
            for(Map.Entry<Integer, Praca> tmp : getPrace().entrySet()){
                cost+=tmp.getValue().getCzasPracy()*tmp.getValue().getPracownik().getStawkaGodzinowa();
            }
            return cost;
        }
    }

}
