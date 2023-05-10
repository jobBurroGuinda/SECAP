/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Job
 */
public class RegistrarUsuario extends HttpServlet {

    private char priv = 'F';
    
    private String username = "";
    private String password = "";
    private int idPrivilegio = 0;
    private String nombres = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    
    private boolean verifRegistro = false;
    
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
        if(sesion != null) {
            priv = (char)sesion.getAttribute("Privilegio");
            sesion.setAttribute("Privilegio", priv);
            ConexionDB cn = new ConexionDB();
            if(priv == 'A'){
                username = request.getParameter("username");
                password = request.getParameter("password");
                idPrivilegio = Integer.parseInt(request.getParameter("privilegio"));
                nombres = request.getParameter("nombres");
                apellidoPaterno = request.getParameter("apellidoPaterno");
                apellidoMaterno = request.getParameter("apellidoMaterno");
                
                switch (idPrivilegio) {
                    case 1:
                        verifRegistro = cn.registrarUsuarioMostrador(username, password, nombres,
                                apellidoPaterno, apellidoMaterno);
                        break;
                    case 2:
                        verifRegistro = cn.registrarUsuarioAdministrador(username, password, nombres, apellidoPaterno, apellidoMaterno);
                        break;
                    case 3:
                        verifRegistro = cn.registrarUsuarioEspecialista(username, password, nombres, apellidoPaterno, apellidoMaterno);
                        break;
                    default:
                        break;
                }
            }
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Registrar usuario</title>");   
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
                if(priv == 'A') {
                    if(verifRegistro != false){
                        out.println("<h2 style='color:green;'>¡El usuario '" + username + "' se registró exitosamente!</h2>");
                        out.println("<a href=\"NvoUsuario\" class=\"button fit\">Registrar un nuevo usuario</a>");
                        out.println("<br/>");
                        out.println("<a href=\"BuscarPaciente\" class=\"button fit\">Regresar a pacientes</a>");
                    } else{
                        out.println("<h4 style='color:red;'>El usuario no se pudo dar de alta, puede que ya esté registrado el nombre de usuario seleccionado, vuelva a intentar con uno diferente</h4>");
                        out.println("<form method='POST' action='RegistrarUsuario'>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+username+"' placeholder='Nombre de usuario *' id='username' name='username' required />");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='password' placeholder='Contraseña *' id='password' name='password' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='password' placeholder='Repita la contraseña *' id='passw2' name='passw2' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("<select style=\"width='50%';\" name='privilegio' id='privilegio'>");
                                        switch (idPrivilegio) {
                                            case 1:
                                                out.println("<option selected='selected' value='1'>- Permisos de mostrador</option>");
                                                out.println("<option value='3'>- Especialista</option>");
                                                out.println("<option value='2'>- Administrador</option>");
                                                break;
                                            case 2:
                                                out.println("<option value='1'>- Permisos de mostrador</option>");
                                                out.println("<option value='3'>- Especialista</option>");
                                                out.println("<option selected='selected' value='2'>- Administrador</option>");
                                                break;
                                            case 3:
                                                out.println("<option value='1'>- Permisos de mostrador</option>");
                                                out.println("<option selected='selected' value='3'>- Especialista</option>");
                                                out.println("<option value='2'>- Administrador</option>");
                                                break;
                                            default:
                                                break;
                                        }
                                    out.println("</select>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+nombres+"' placeholder='Nombre de la persona *' id='nombres' name='nombres' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+apellidoPaterno+"' placeholder='Apellido paterno *' id='apellidoPaterno' name='apellidoPaterno' required/>");
                                out.println("</div>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+apellidoMaterno+"' placeholder='Apellido materno *' id='apellidoMaterno' name='apellidoMaterno' required/>");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("   <input type='submit' class='button fit special' value='Registrar'/>");
                                out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                            out.println("</form>");
                    }
                    
                } else {
                    out.println("<h2 style='color: red;'>Usted no tiene permiso de acceder a esta sección de la aplicación</h2>");
                    if(priv == 'E' || priv == 'M'){
                        out.println("<h4>Sólo el administrador puede crear nuevos usuarios</h4>");
                    }
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
