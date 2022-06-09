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
import java.util.HashMap;
import java.util.Map;

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
        try {
            if (Integer.parseInt(request.getParameter("czasPracy")) > 24)
                error += "Czas pracy nie może przekroczyć 24h. ";
        }
        catch (NumberFormatException ex){
            error += "Należy wprowadzić poprawny czas pracy w godzinach. ";
        }
        if(Integer.parseInt(request.getParameter("pracownik"))<0) error+="Należy wybrać pracownika. ";

        if(error.length()>0){
            request.setAttribute("oldOpisCzynnosci", request.getParameter("opisCzynnosci"));
            request.setAttribute("oldDataWykonania", request.getParameter("dataWykonania"));
            request.setAttribute("oldCzasPracy", request.getParameter("czasPracy"));
            request.setAttribute("oldPracownik", request.getParameter("pracownik"));
            request.setAttribute("error", error);
            request.getRequestDispatcher("/praca.jsp").forward(request, response);
        }
        else {
            Praca p = new Praca(extent,
                                request.getParameter("opisCzynnosci"),
                                LocalDate.parse(request.getParameter("dataWykonania")),
                                Integer.parseInt(request.getParameter("czasPracy")),
                                extent.getOsobaExtent().get(Integer.parseInt(request.getParameter("pracownik"))).getPracownik(),
                                extent.projektExtent.get(projectId).getZadania().get(zadanieId));

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
