package tjd.mas.projectman.model;

import tjd.mas.projectman.extent.Extent;

import java.io.Serializable;
import java.time.LocalDate;

public class ProjektDlaKlienta extends Projekt implements Serializable {
    private static final long serialVersionUID = -2854080630121306201L;
    private Integer oczekiwanyPrzychod;
    private Integer budzet;

    public ProjektDlaKlienta(Extent extent, String nazwa, LocalDate dataRozpoczecia, Integer oczekiwanyPrzychod, Integer budzet) {
        super(extent, nazwa, dataRozpoczecia);
        this.oczekiwanyPrzychod=oczekiwanyPrzychod;
        this.budzet=budzet;
    }

    public Integer getOczekiwanyPrzychod() {
        return oczekiwanyPrzychod;
    }

    public void setOczekiwanyPrzychod(Integer oczekiwanyPrzychod) {
        this.oczekiwanyPrzychod = oczekiwanyPrzychod;
    }

    public Integer getBudzet() {
        return budzet;
    }

    public void setBudzet(Integer budzet) {
        this.budzet = budzet;
    }
}
