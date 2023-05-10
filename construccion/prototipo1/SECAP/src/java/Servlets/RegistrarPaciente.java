/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import Logica.Expediente.Paciente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class RegistrarPaciente extends HttpServlet {

    private char priv = 'F';
    
    private String nombrePaciente = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno= "";
    private int sexo = 0;
    private String fechaNacimiento = "";
    private String ocupacion = "";
    private String domicilio = "";
    private Long telefono;
    private Long telefono2;
    private String email = "";
    private String facebook = "";
    
    
    private String tipoExpediente = "";
    private int idPaciente = 0;
    
    private boolean verifRegistro = false;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession sesion = request.getSession(false);
        request.setCharacterEncoding("UTF-8");
        if(sesion != null) {
            priv = (char)sesion.getAttribute("Privilegio");
            sesion.setAttribute("Privilegio", priv);
            if(priv == 'A' || priv == 'E' || priv == 'M') {
                nombrePaciente = request.getParameter("nombres");
                apellidoPaterno = request.getParameter("apellidoPaterno");
                apellidoMaterno = request.getParameter("apellidoMaterno");
                sexo = Integer.parseInt(request.getParameter("sexo"));
                fechaNacimiento = request.getParameter("fechaNacimiento");
                ocupacion = request.getParameter("ocupacion");
                domicilio = request.getParameter("domicilio");
                telefono = Long.parseLong(request.getParameter("telefono"));
                telefono2 = Long.parseLong(request.getParameter("telefono2"));
                email = request.getParameter("email");
                facebook = request.getParameter("facebook");
                ConexionDB cn  = new ConexionDB();
                ArrayList<Paciente>pacientes = new ArrayList<>();
                pacientes = cn.calcularEdad(fechaNacimiento);
                tipoExpediente = cn.obtenerTipoExpediente(pacientes);
                sesion.setAttribute("tipoExpedientee", tipoExpediente);
                // Realizamos el registro del paciente
                verifRegistro = cn.registrarPaciente(nombrePaciente, apellidoPaterno, apellidoMaterno, ocupacion, sexo, 
                        fechaNacimiento, domicilio, telefono, telefono2, email, facebook);
                if(verifRegistro != false) {
                    idPaciente = cn.obtenerUltimoIDregistrado();
                    sesion.setAttribute("idPaciente", idPaciente);
                }
            }
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Registro de paciente</title>");
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
                    // Verificamos si se realizó el registro del paciente
                    if(verifRegistro != false){
                        // Si es así, dependiendo el tipo de expediente mostramos los datos correspondientes
                        // Inicio de la búsqueda del tipo de expediente
                        if(tipoExpediente.equals("pediatrico")) {
                            out.println("<h2>Antecedentes médicos del paciente pediátrico: "+nombrePaciente+ " " +
                                                apellidoPaterno+" "+apellidoMaterno+"</h2><br/>");
                            out.println("<form method='POST' action='NvoExpediente'>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Tiempo de gestación *' id='tiempogestacion' name='tiempogestacion' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("<select style=\"width='50%';\" name='tipoparto' id='tipoparto'>");
                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                        out.println("<option value='2'>- Cesaria</option>");
                                        out.println("<option value='3'>- Agua</option>");
                                        out.println("<option value='4'>- Prematuro</option>");
                                        out.println("<option value='5'>- Inducido</option>");
                                    out.println("</select>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Incubadora *' id='incubadora' name='incubadora' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Complicación' id='complicacion' name='complicacion' />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='medicamento' id='medicamento' name='medicamento' />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='alergia' id='alergia' name='alergia' />");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("   <input type='submit' class='button fit special' value='Registrar'/>");
                                out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                            out.println("</form>");

                        } else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")){
                            if(tipoExpediente.equals("adulto")){
                                out.println("<h2>Antecedentes médicos del paciente adulto: "+nombrePaciente+ " " +
                                                apellidoPaterno+" "+apellidoMaterno+"</h2><br/>");
                            } else if(tipoExpediente.equals("geriatrico")){
                                out.println("<h2>Antecedentes médicos del paciente geriátrico: "+nombrePaciente+ " " +
                                                apellidoPaterno+" "+apellidoMaterno+"</h2><br/>");
                            }
                            out.println("<form method='POST' action='NvoExpediente'>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Medicamento' id='medicamento' name='medicamento' />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='Alergia' id='alergia' name='alergia' />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='T.A. *' id='ta' name='ta' required />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' placeholder='N.G. *' id='ng' name='ng' required />");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("   <input type='submit' class='button fit special' value='Registrar'/>");
                                out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                            out.println("</form>");
                        }
                        // Fin de la búsqueda del tipo de expediente
                    } 
                    // Si no se realizó el registro del paciente correctamente...
                    else {
                        out.println("<h2>Nuevo paciente</h2>");
                        out.println("<h4 style='color:red;'>El paciente no se pudo registrar, verifique que los datos sean correctos. Si persiste el problema contacte al desarrollador de la aplicación</h4><br/>");
                        out.println("<form method='POST' action='RegistrarPaciente'>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Nombres *' name='nombres' id='nombres' value='"+ nombrePaciente+"' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Apellido paterno *' value='"+ apellidoPaterno+"'  name='apellidoPaterno' id='apellidoPaterno' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Apellido materno *' value='"+ apellidoMaterno+"'  name='apellidoMaterno' id='apellidoMaterno' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                                out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                    out.println("<option value='1'>- Hombre</option>");
                                    out.println("<option value='2'>- Mujer</option>");
                                out.println("</select>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <h1 style='color:black;'>Fecha de nacimiento:</h1>");
                            out.println("   <input type='date' style='color:black;' min='1900-01-01' max='2016-06-01' value='"+ fechaNacimiento+"'  placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Ocupación o escolaridad *' id='ocupacion' value='"+ ocupacion+"'  name='ocupacion' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Domicilio *' id='domicilio' name='domicilio' value='"+ domicilio+"'  required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='tel' placeholder='Teléfono *' pattern='[0-9]{10}' value='"+ telefono+"'  name='telefono' id='telefono' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='tel' placeholder='Teléfono de recados *' pattern='[0-9]{10}' value='"+ telefono2+"'  name='telefono2' id='telefono2' required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='email' placeholder='Correo electrónico *' name='email' id='email' value='"+ email+"'  required/>");
                            out.println("</div>");
                            out.println("<div class='row uniform 50%'>");
                            out.println("   <input type='text' placeholder='Cuenta de facebook (opcional)' value='"+ facebook+"'  name='facebook' id='facebook'/>");
                            out.println("</div>");
                            out.println("<br/>");
                            out.println("   <input type='submit' class='button fit special' value='Volver a intentar el registro'/>");
                            out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                        out.println("</form>");
                    }
                } else {
                    out.println("<h2 style='color: red;'>Usted no tiene permiso de acceder a esta sección de la aplicación</h2>");
                }
                out.println("</div>");
                out.println("</section>");
                out.println("</article>");
                out.println("");
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
            Logger.getLogger(RegistrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RegistrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
