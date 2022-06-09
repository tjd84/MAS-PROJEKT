package tjd.mas.projectman.model;

import tjd.mas.projectman.extent.Extent;

import java.io.Serializable;
import java.time.LocalDate;

public class ProjektWewnetrzny extends Projekt implements Serializable {
    private static final long serialVersionUID = -5981442792280355387L;
    private Organizacja.Dzial dzial;

    public ProjektWewnetrzny(Extent extent, String nazwa, LocalDate dataRozpoczecia) {
        super(extent, nazwa, dataRozpoczecia);
    }

    public void setDzial(Organizacja.Dzial dzial){
        if(this.dzial!=null && this.dzial!=dzial){
            this.dzial.removeProjektWewnetrzny(this);
        }
        this.dzial=dzial;
        dzial.addProjektWewnetrzny(this);
    }
}
