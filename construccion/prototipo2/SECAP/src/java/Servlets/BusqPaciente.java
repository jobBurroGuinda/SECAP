/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import Logica.Expediente.Paciente;
import Logica.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BusqPaciente extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        String nombrePaciente = request.getParameter("nombrePaciente");
        HttpSession sesion = request.getSession(false);
        if(sesion != null){
            priv = (char)sesion.getAttribute("Privilegio");
            sesion.setAttribute("Privilegio", priv);
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Búsqueda del paciente</title>"); 
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
                                out.println("<li><a href='ResumenesClinicos'>Resúmenes clínicos</a></li>");
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
                    if(priv == 'A' || priv == 'E' || priv == 'M'){
                        ConexionDB cn = new ConexionDB();
                        Persona p = new Persona();
                        List<Paciente> pacientes = new ArrayList<>();
                        pacientes = cn.buscarPaciente(nombrePaciente);
                        int tPacientes = pacientes.size();
                        out.println("<h2>Buscar paciente</h2>");
                        out.println("<form method='POST' action='BusqPaciente'>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Introducir nombre' value='" + nombrePaciente + "' id='nombrePaciente' name='nombrePaciente' required/>");
                            out.println("</div>");
                            out.println("<br/><input type='submit' class='button fit special' value='Buscar'/>");
                        out.println("</form>");
                        out.println("<br/>");
                        if(tPacientes > 0) {
                            out.println("<center>");
                            if(tPacientes == 1) {
                                out.println("<h5>Sólo se encontró un resultado coincidente con su búsqueda</h5>");
                            } else if(tPacientes > 1) {
                                out.println("<h5>Se encontraron " + tPacientes + " resultados coincidentes con su búsqueda</h5>");
                            }
                            out.println("</center>");
                            out.println("<table>");
                                out.println("<thead>");
                                    out.println("<th>Nombre</th>");
                                out.println("</thead>");
                                for(int i=0 ; i<tPacientes ; i++) {
                                out.println("<tr>");
                                    out.println("   <td>" + pacientes.get(i).getNombre() + "</td>");
                                    out.println("   <td><a class='button special small' href='DetallesPaciente?idPaciente=" + pacientes.get(i).getIdPaciente() + "'>Detalles</a></td>");
                                    out.println("   <td><a class='button special small' href='EditarPaciente?idPaciente=" + pacientes.get(i).getIdPaciente() + "'>Editar</a></td>");
                                if(priv == 'E' || priv == 'A'){
                                    out.println("   <td><a class='button special small' href='BajaPaciente?idPaciente=" + pacientes.get(i).getIdPaciente() + "'>Baja</a></td>");
                                }
                                out.println("</tr>");
                                }
                            out.println("</table>");
                        } else {
                            out.println("<h2>El paciente \"" + nombrePaciente + "\", no se encuentra registrado</h2>");
                            out.println("<h3><a class='button fit' href='NvoPaciente?nombrePaciente="+ nombrePaciente +"'>Clic aquí para registrar</a></h3>");
                        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BusqPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(BusqPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
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
