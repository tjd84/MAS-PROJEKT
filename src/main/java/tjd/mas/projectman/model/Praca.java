package tjd.mas.projectman.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import tjd.mas.projectman.exceptions.BudgetExceedException;
import tjd.mas.projectman.exceptions.OverTimeException;
import tjd.mas.projectman.extent.Extent;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Praca implements Serializable {
    private static final long serialVersionUID = -4393068561568088262L;
    private String opisCzynnosci;
    private LocalDate dataWykonania;
    private Integer czasPracy;
    private Projekt.Zadanie zadanie;
    private Osoba.Pracownik pracownik;

    public Praca(Extent extent, String opisCzynnosci, LocalDate dataWykonania, Integer czasPracy, Osoba.Pracownik pracownik, Projekt.Zadanie zadanie) throws OverTimeException, BudgetExceedException {
        if(pracownik.getLimitDobowyCzasuPracy()<pracownik.getTotalHoursByDate(dataWykonania)+czasPracy){
            throw new OverTimeException("Przekroczono dobowy limit czasu pracy pracownika. Limit ustawiony jest na "+pracownik.getLimitDobowyCzasuPracy()+" godzin. Przed próbą zarejestrowania niniejszej pracy wykorzystano już "+pracownik.getTotalHoursByDate(dataWykonania)+" godzin. Oznacza to, że można zarejestrować maksymalnie "+(pracownik.getLimitDobowyCzasuPracy()-pracownik.getTotalHoursByDate(dataWykonania))+" godzin.");
        }

        if(zadanie.getProjekt() instanceof ProjektDlaKlienta){
            ProjektDlaKlienta pdk = (ProjektDlaKlienta) zadanie.getProjekt();
            if(pdk.getBudzet()<zadanie.getProjekt().getTotalCost()+(czasPracy*pracownik.getStawkaGodzinowa())){
                throw new BudgetExceedException("Przekroczono budżet projektu wynoszący "+pdk.getBudzet()+" zł. Przed próbą zarejestrowania niniejszej pracy wykorzystano już "+zadanie.getProjekt().getTotalCost()+" zł. Oznacza, to że pozostało "+(pdk.getBudzet()-zadanie.getProjekt().getTotalCost())+" zł. Stawka godzinowa wskazanego pracownika to "+pracownik.getStawkaGodzinowa()+" zł/h. Można więc wpisać maksymalnie "+((pdk.getBudzet()-zadanie.getProjekt().getTotalCost())/pracownik.getStawkaGodzinowa())+" godzin tego pracownika." );
            }
        }


        this.opisCzynnosci = opisCzynnosci;
        this.dataWykonania = dataWykonania;
        this.czasPracy = czasPracy;
        setZadanie(zadanie);
        setPracownik(pracownik);
        extent.addToExtent(this);
    }

    public String getOpisCzynnosci() {
        return opisCzynnosci;
    }

    public void setOpisCzynnosci(String opisCzynnosci) {
        this.opisCzynnosci = opisCzynnosci;
    }

    public LocalDate getDataWykonania() {
        return dataWykonania;
    }

    public void setDataWykonania(LocalDate dataWykonania) {
        this.dataWykonania = dataWykonania;
    }

    public Integer getCzasPracy() {
        return czasPracy;
    }

    public void setCzasPracy(Integer czasPracy) {
        this.czasPracy = czasPracy;
    }

    public void setPracownik(Osoba.Pracownik pracownik){
        if(this.pracownik!=null && this.pracownik!=pracownik){
            this.pracownik.removePraca(this);
        }
        this.pracownik=pracownik;
        pracownik.addPraca(this);
    }

    public void setZadanie(Projekt.Zadanie zadanie){
        if(this.zadanie!=null && this.zadanie!=zadanie){
            this.zadanie.removePraca(this);
        }
        this.zadanie=zadanie;
        zadanie.addPraca(this);
    }

    @JsonBackReference
    public Projekt.Zadanie getZadanie() {
        return zadanie;
    }

    public Osoba.Pracownik getPracownik() {
        return pracownik;
    }
}
