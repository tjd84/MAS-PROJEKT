package tjd.mas.projectman;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import tjd.mas.projectman.extent.Extent;
import tjd.mas.projectman.extent.ExtentFile;
import tjd.mas.projectman.model.Osoba;
import tjd.mas.projectman.model.Projekt;

import java.io.*;
import java.util.Map;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "extent", value = "/extent")
public class ExtentServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Extent extent = ExtentFile.readExtent();
        for(Map.Entry<Integer, Projekt> tmp : extent.getProjektExtent().entrySet()){
            tmp.getValue().setURL(tmp.getKey());
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String json = mapper.writeValueAsString(extent);

        out.println(json);
    }

    public void destroy() {
    }
}