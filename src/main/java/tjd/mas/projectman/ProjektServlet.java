package tjd.mas.projectman;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.Projekt;

import javax.naming.InsufficientResourcesException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "projekt", value = "/projekt")
public class ProjektServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectIdStr = request.getParameter("projectId");
        Integer projectId = Integer.parseInt(projectIdStr);

        Extent extent = ExtentFile.readExtent();
        Projekt projekt = extent.projektExtent.get(projectId);

        request.setAttribute("projectIdStr", projectIdStr);
        request.setAttribute("projekt", projekt);
        request.getRequestDispatcher("/projekt.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
