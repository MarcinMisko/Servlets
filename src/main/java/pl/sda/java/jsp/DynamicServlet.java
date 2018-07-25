package pl.sda.java.jsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "dynamic", urlPatterns = "/dynamic")
public class DynamicServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Content-Type", "text/html");
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            resp.getWriter().print(name + " " + req.getParameter(name) + "<br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String form = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "    <head>\n" +
                "        <title>Strona statyczna</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        <form action=\"./dynamic\" method=\"POST\">\n" +
                "            Imie i nazwisko: <input name=\"imie\" value=\"${imie}\"/><br/>\n" +
                "            Opis: <textarea name=\"opis\" >${opis}</textarea><br/>\n" +
                "            Plec: <select name=\"plec\"><option name=\"K\" ${optionK}>K</option><option name=\"M\"${optionM} >M</option></select><br/>\n" +
                "            Zgoda na przetwarzanie danych: <input type=\"checkbox\" name=\"zgoda\" value=\"Tak\" ${checked}/><br/>\n" +
                "            <input type=\"hidden\" name=\"ukryte\" />\n" +
                "            <input type=\"submit\" value=\"wyslij\"/>\n" +
                "        </form>\n" +
                "    </body>\n" +
                "</html>";
        form = form.replace("${imie}", req.getParameter("imie"));
        form = form.replace("${opis}", req.getParameter("opis"));
        form = form.replace("${checked}", req.getParameter("zgoda") != null ? "checked" : "");
        if ("M".equals(req.getParameter("plec"))) {
            form.replace("${optionK}", "");
            form.replace("${optionM}", "selected");
        } else if ("K".equals(req.getParameter("plec"))) {
            form.replace("${optionM}", "");
            form.replace("${optionK}", "selected");
        }

        resp.getWriter().print(form);
    }
}
