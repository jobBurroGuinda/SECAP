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
public class NvoPaciente extends HttpServlet {

    private char priv = 'F';
    
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
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = request.getSession(false);
        if(sesion != null){
            String nombreCliente = "";
                if(request.getParameter("nombreCliente") != null) {
                    nombreCliente = request.getParameter("nombreCliente");
                }
            priv = (char)sesion.getAttribute("Privilegio");
            sesion.setAttribute("Privilegio", priv);
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Nuevo paciente</title>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1' />");
                out.println("<meta charset='utf-8' />");
                out.println("<link rel='stylesheet' href='assets/css/main.css' />");           
                out.println("</head>");
                out.println("<body class='landing'>");
                out.println("<!-- Page Wrapper -->");
                out.println("<div id='page-wrapper'>");
                // Inicio del encabezado
                        out.println("<!-- Header -->");
                        out.println("<header id=\"header\"");
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
                // Fin del encabezado
                
                out.println("<article id=\"main\">");
                out.println("<section class=\"wrapper style5\">");
                out.println("<div class=\"inner\">");
                if(priv == 'A' || priv == 'E' || priv == 'M') {
                    out.println("<h2>Nuevo paciente</h2><br/>");
                    out.println("<form method='POST' action='RegistrarPaciente'>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Nombres *' name='nombres' id='nombres' value='"+ nombreCliente+"' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Apellido paterno *' name='apellidoPaterno' id='apellidoPaterno' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Apellido materno *' name='apellidoMaterno' id='apellidoMaterno' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                            out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                out.println("<option value='1'>- Hombre</option>");
                                out.println("<option value='2'>- Mujer</option>");
                            out.println("</select>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <h1 style='color:black;'>Fecha de nacimiento:</h1>");
                        out.println("   <input type='date' style='color:black;' min='1900-01-01' max='2016-06-01' placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Ocupación o escolaridad *' id='ocupacion' name='ocupacion' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Domicilio *' id='domicilio' name='domicilio' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='tel' placeholder='Teléfono *' pattern='[0-9]{10}' name='telefono' id='telefono' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='tel' placeholder='Teléfono de recados *' pattern='[0-9]{10}' name='telefono2' id='telefono2' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='email' placeholder='Correo electrónico *' name='email' id='email' required/>");
                        out.println("</div>");
                        out.println("<div class='row uniform 50%'>");
                        out.println("   <input type='text' placeholder='Cuenta de facebook (opcional)' name='facebook' id='facebook'/>");
                        out.println("</div>");
                        out.println("<br/>");
                        out.println("   <input type='submit' class='button fit special' value='Siguiente'/>");
                        out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                    out.println("</form>");
                } else {
                    out.println("<h2 style='color: red;'>Usted no tiene permiso de acceder a esta sección de la aplicación</h2>");
                }
                out.println("</div>");
                out.println("</section>");
                out.println("</article>");
                out.println("</div>");
                out.println("");
                out.println("");
                out.println("<!-- Scripts -->");
                out.println("	<script src=\"assets/js/jquery.min.js\"></script>");
                out.println("	<script src=\"assets/js/jquery.scrollex.min.js\"></script>");
                out.println("	<script src=\"assets/js/jquery.scrolly.min.js\"></script>");
                out.println("	<script src=\"assets/js/skel.min.js\"></script>");
                out.println("	<script src=\"assets/js/util.js\"></script>");
                out.println("	<!--[if lte IE 8]><script src=\"assets/js/ie/respond.min.js\"></script> -->");
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
