package pl.sda.java.jsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "calculator", urlPatterns = "/kalkulator")
public class DynamicCalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String argParam = request.getParameter("arg");
        if (argParam == null) {
            argParam = "";
        }
        String ciągZnakówDoWyświetlenia = (String) request.getSession().getAttribute("ciągZnakówDoWyświetlenia");
        if (ciągZnakówDoWyświetlenia == null) {
            ciągZnakówDoWyświetlenia = "";
        }
        ciągZnakówDoWyświetlenia += argParam;
        request.getSession().setAttribute("ciągZnakówDoWyświetlenia", ciągZnakówDoWyświetlenia);
        String form = form(ciągZnakówDoWyświetlenia);
        response.getWriter().write(form);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ciągZnakówDoWyświetlenia = (String) request.getSession().getAttribute("ciągZnakówDoWyświetlenia");
        if (request.getParameter("plus") != null) {
            ciągZnakówDoWyświetlenia = saveToSession(request, ciągZnakówDoWyświetlenia);
            request.getSession().setAttribute("operacja", "+");
        }
        if (request.getParameter("minus") != null) {
            ciągZnakówDoWyświetlenia = saveToSession(request, ciągZnakówDoWyświetlenia);
            request.getSession().setAttribute("operacja", "-");
        }
        if (request.getParameter("multiply") != null) {
            ciągZnakówDoWyświetlenia = saveToSession(request, ciągZnakówDoWyświetlenia);
            request.getSession().setAttribute("operacja", "*");
        }
        if (request.getParameter("divide") != null) {
            ciągZnakówDoWyświetlenia = saveToSession(request, ciągZnakówDoWyświetlenia);
            request.getSession().setAttribute("operacja", "/");
        }
        if (request.getParameter("evaluate") != null) {
            String wynikCząstkowy = (String) request.getSession().getAttribute("wynikCząstkowy");
            int intWynikWynikCząstkowy = Integer.valueOf(wynikCząstkowy).intValue();
            int intCiągZnakówDoWyświetlenia = Integer.valueOf(ciągZnakówDoWyświetlenia).intValue();
            int wynik = 0;
            if ("+".equals(request.getSession().getAttribute("operacja"))) {
                wynik = intWynikWynikCząstkowy + intCiągZnakówDoWyświetlenia;
            }
            if ("-".equals(request.getSession().getAttribute("operacja"))) {
                wynik = intWynikWynikCząstkowy - intCiągZnakówDoWyświetlenia;
            }
            if ("*".equals(request.getSession().getAttribute("operacja"))) {
                wynik = intWynikWynikCząstkowy * intCiągZnakówDoWyświetlenia;
            }
            if ("/".equals(request.getSession().getAttribute("operacja"))) {
                wynik = intWynikWynikCząstkowy / intCiągZnakówDoWyświetlenia;
            }

            ciągZnakówDoWyświetlenia = wynik + "";
            request.getSession().setAttribute("wynikCząstkowy", ciągZnakówDoWyświetlenia);
            request.getSession().setAttribute("ciągZnakówDoWyświetlenia", ciągZnakówDoWyświetlenia);

        }
        String form = form(ciągZnakówDoWyświetlenia);
        response.getWriter().write(form);
    }

    private String saveToSession(HttpServletRequest request, String ciągZnakówDoWyświetlenia) {
        request.getSession().setAttribute("wynikCząstkowy", ciągZnakówDoWyświetlenia);
        ciągZnakówDoWyświetlenia = "";
        request.getSession().setAttribute("ciągZnakówDoWyświetlenia", ciągZnakówDoWyświetlenia);
        return ciągZnakówDoWyświetlenia;
    }

    private String form(String ciągZnakówDoWyświetlenia) {
        String form = " <html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    </head>\n" +
                "<body>\n" +
                "<table>\n" +
                "    <tr><td colspan=\"4\"><input type=\"text\" readonly value=\"" + ciągZnakówDoWyświetlenia + "\"></td></tr>\n" +
                "    <tr>\n" +
                "    <form action=\"/example/kalkulator\" method=\"GET\">\n" +
                "        <td><input type=\"submit\" name=\"arg\" value=\"7\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"8\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"9\"/></td>\n" +
                "        </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"plus\" value=\"+\"/></td>\n" +
                "        </form>\n" +
                "         </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"multiply\" value=\"*\"/></td>\n" +
                "        </form>\n" +
                "    <tr>\n" +
                "    <form action=\"/example/kalkulator\" method=\"GET\">\n" +
                "        <td><input type=\"submit\" name=\"arg\" value=\"4\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"5\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"6\"/></td>\n" +
                "        </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"minus\" value=\"-\"/></td>\n" +
                "        </form>\n" +
                "        </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"divide\" value=\"/\"/></td>\n" +
                "        </form>\n" +
                "        </tr>\n" +
                "    <tr>\n" +
                "    <form action=\"/example/kalkulator\" method=\"GET\">\n" +
                "        <td><input type=\"submit\" name=\"arg\" value=\"1\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"2\"/></td>\n" +
                "                <td><input type=\"submit\" name=\"arg\" value=\"3\"/></td>\n" +
                "        </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"evaluate\" value=\"=\"/></td>\n" +
                "        </form>\n" +
                "         </form>\n" +
                "    <form action=\"/example/kalkulator\" method=\"POST\">\n" +
                "                <td><input type=\"submit\" name=\"reset\" value=\"C\"/></td>\n" +
                "        </form>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "</body>\n" +
                "</html>";
        return form;
    }
}
