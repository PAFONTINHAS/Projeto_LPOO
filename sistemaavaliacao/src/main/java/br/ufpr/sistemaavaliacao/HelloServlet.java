package br.ufpr.sistemaavaliacao;

import java.io.IOException;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write("<h1>Servlet funcionando! Juaum</h1>");
    }
}