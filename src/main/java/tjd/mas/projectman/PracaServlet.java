package tjd.mas.projectman;

import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.Osoba;
import tjd.mas.projectman.model.Praca;
import tjd.mas.projectman.model.Projekt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

@WebServlet(name = "praca", value = "/praca")
public class PracaServlet extends HttpServlet {
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectIdStr = request.getParameter("projectId");
        String zadanieIdStr = request.getParameter("zadanieId");

        Extent extent = ExtentFile.readExtent();


        request.setAttribute("pracownicy", pracownicy(extent));
        request.setAttribute("projectIdStr", projectIdStr);
        request.setAttribute("zadanieIdStr", zadanieIdStr);
        request.getRequestDispatcher("/praca.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String projectIdStr = request.getParameter("projectId");
        Integer projectId = Integer.parseInt(projectIdStr);
        String zadanieIdStr = request.getParameter("zadanieId");
        Integer zadanieId = Integer.parseInt(zadanieIdStr);
        Extent extent = ExtentFile.readExtent();

        request.setAttribute("pracownicy", pracownicy(extent));
        request.setAttribute("projectIdStr", projectIdStr);
        request.setAttribute("zadanieIdStr", zadanieIdStr);

        String error="";
        //WALIDACJE
        //1. Limit 24 h czasu pracy + poprwaność wpisanego czasu - czy daje się parsować na Integer
        try {
            if (Integer.parseInt(request.getParameter("czasPracy")) <= 0)
                error += "Czas pracy powinien być dodatni. ";
        }
        catch (NumberFormatException ex){
            error += "Należy wprowadzić poprawny czas pracy w godzinach. ";
        }

        //2. Poprawność wprowadzonej daty - czy daje się parsować na LocalDate
        try {
            LocalDate.parse(request.getParameter("dataWykonania"));
        }
        catch (DateTimeParseException e) {
            error += "Należy wprowadzić datę w formacie rrrr-mm-dd. ";
        }

        //3. Czy pracownik został wskazany
        if(Integer.parseInt(request.getParameter("pracownik"))<0) error+="Należy wybrać pracownika. ";

        //4. Czy opis został wpisany
        if(request.getParameter("opisCzynnosci").length()==0) error+="Należy uzupełnić opis. ";


        if(error.length()==0) {
            try {
                Praca p = new Praca(extent,
                        request.getParameter("opisCzynnosci"),
                        LocalDate.parse(request.getParameter("dataWykonania")),
                        Integer.parseInt(request.getParameter("czasPracy")),
                        extent.getOsobaExtent().get(Integer.parseInt(request.getParameter("pracownik"))).getPracownik(),
                        extent.projektExtent.get(projectId).getZadania().get(zadanieId));
            } catch (Exception e) {
                error += e.getMessage();
            }
        }


        if(error.length()>0){
            request.setAttribute("oldOpisCzynnosci", request.getParameter("opisCzynnosci"));
            request.setAttribute("oldDataWykonania", request.getParameter("dataWykonania"));
            request.setAttribute("oldCzasPracy", request.getParameter("czasPracy"));
            request.setAttribute("oldPracownik", request.getParameter("pracownik"));
            request.setAttribute("error", error);
            request.getRequestDispatcher("/praca.jsp").forward(request, response);
        }
        else {
            ExtentFile.writeExtent(extent);
            response.sendRedirect("zadanie?projectId="+projectIdStr+"&zadanieId="+zadanieIdStr+"&dataStored=1");
        }

    }

    private Map<Integer, Osoba> pracownicy(Extent extent){
        Map<Integer, Osoba> osoby = extent.getOsobaExtent();
        Map<Integer, Osoba> pracownicy = new HashMap<>();
        for(Map.Entry<Integer, Osoba> tmp : osoby.entrySet()){
            if(tmp.getValue().getPracownik()!=null)
                pracownicy.put(tmp.getKey(), tmp.getValue());
        }
        return pracownicy;
    }

    public void destroy() {
    }
}
