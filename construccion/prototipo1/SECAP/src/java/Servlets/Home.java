/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Job
 */
public class Home extends HttpServlet {

    private char priv = 'F';
    private String username = "";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        priv = (char)request.getAttribute("Privilegio");
        HttpSession sesion = request.getSession(false);
        if(sesion != null) {
            sesion.setAttribute("Privilegio", priv);
            username = (String)sesion.getAttribute("username");
            try (PrintWriter out = response.getWriter()) {
                String privilegio = null;
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Bienvenid@ a SECAP</title>"); 
                out.println("<meta charset=\"utf-8\" />");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />");
                out.println("<!--[if lte IE 8]><script src=\"assets/js/ie/html5shiv.js\"></script><![endif]-->");
                out.println("<link rel=\"stylesheet\" href=\"assets/css/main.css\" />");
                out.println("<!--[if lte IE 8]><link rel=\"stylesheet\" href=\"assets/css/ie8.css\" /><![endif]-->");
                out.println("<!--[if lte IE 9]><link rel=\"stylesheet\" href=\"assets/css/ie9.css\" /><![endif]-->");           
                out.println("</head>");
                out.println("<body class=\"landing\">");
                    out.println("<!-- Page Wrapper -->");
                    out.println("<div id=\"page-wrapper\">");
                        out.println("<!-- Header -->");
                        out.println("<header id=\"header\" class=\"alt\">");
                            out.println("<h1><a href=\"index.html\">Ópticas Monarca</a></h1>");
                            out.println("<nav id=\"nav\">");
                            out.println("<ul>");
                            out.println("<li class=\"special\">");
                            out.println("<a href=\"#menu\" class=\"menuToggle\"><span>Menu</span></a>");
                            out.println("<div id=\"menu\">");
                            out.println("<ul>");
                            if(priv == 'A' || priv == 'E' || priv == 'M'){
                                out.println("<li><a href='NvoPaciente'>Nuevo paciente</a></li>");
                                out.println("<li><a href='BuscarPaciente'>- Buscar</a></li>");
                                if(priv == 'A'){
                                    out.println("<li><a href='NvoUsuario'>Nuevo usuario</a></li>");
                                }
                            }
                            out.println("<li><a href=\"Logout\"><b>Cerrar sesión</b></a></li>");
                            out.println("</ul>");
                            out.println("</div>");
                            out.println("</li>");
                            out.println("</ul>");
                            out.println("</nav>");
                        out.println("</header>");
                        out.println("<!-- Banner -->");
                        out.println("<section id=\"banner\">");
                            out.println("<div class=\"inner\">");
                            
                            switch (priv) {
                                case 'M':
                                    out.println("<h2>Bienvenid@ personal de mostrador " + username + " a SECAP</h2>");
                                    break;
                                case 'A':
                                    out.println("<h2>Bienvenid@ Sr. Administrador " + username + " a SECAP</h2>");
                                    break;
                                case 'E':
                                    out.println("<h2>Bienvenid@ especialista " + username + " a SECAP</h2>");
                                    break;
                                default:
                                    break;
                            }
                            out.println("<p>Sistema de Expedientes Clínicos y Agenda de Pacientes de Ópticas Monarca</p>");
                            out.println("</div>");
                            out.println("<a href=\"#footer\" class=\"more scrolly\">Más información</a>");
                        out.println("</section>");
                        out.println("<!-- Footer -->");
                        out.println("	<footer id=\"footer\">");
                        out.println("		<ul class=\"copyright\">");
                        out.println("			<li>Los datos que aquí se muestran son de carácter confidencial, queda estrictamente prohibido "+
                                        "un uso ajeno al indicado por la norma oficial mexicana NOM-168-SSA1-1998 del expediente clínico</li>");
                        out.println("		</ul>");
                        out.println("	</footer>");
                    out.println("</div>");
                    out.println("");
                    out.println("<!-- Scripts -->");
                    out.println("	<script src=\"assets/js/jquery.min.js\"></script>");
                    out.println("	<script src=\"assets/js/jquery.scrollex.min.js\"></script>");
                    out.println("	<script src=\"assets/js/jquery.scrolly.min.js\"></script>");
                    out.println("	<script src=\"assets/js/skel.min.js\"></script>");
                    out.println("	<script src=\"assets/js/util.js\"></script>");
                    out.println("	<!--[if lte IE 8]><script src=\"assets/js/ie/respond.min.js\"></script><![endif]-->");
                    out.println("	<script src=\"assets/js/main.js\"></script>");
                out.println("</body>");
                out.println("</html>");
            }
        }
        else{
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
