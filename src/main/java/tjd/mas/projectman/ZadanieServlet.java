package tjd.mas.projectman;

import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.Projekt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "zadanie", value = "/zadanie")
public class ZadanieServlet extends HttpServlet {
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectIdStr = request.getParameter("projectId");
        Integer projectId = Integer.parseInt(projectIdStr);

        String zadanieIdStr = request.getParameter("zadanieId");
        Integer zadanieId = Integer.parseInt(zadanieIdStr);

        Extent extent = ExtentFile.readExtent();
        Projekt projekt = extent.projektExtent.get(projectId);
        Projekt.Zadanie zadanie = projekt.getZadania().get(zadanieId);

        request.setAttribute("projectIdStr", projectIdStr);
        request.setAttribute("projekt", projekt);
        request.setAttribute("zadanieIdStr", zadanieIdStr);
        request.setAttribute("zadanie", zadanie);
        request.setAttribute("dataStored", request.getParameter("dataStored"));
        request.getRequestDispatcher("/zadanie.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
