/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import Logica.Expediente.AntecedentesMedicos.AntecedenteAG;
import Logica.Expediente.AntecedentesMedicos.AntecedentePediatrico;
import Logica.Expediente.Paciente;
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
public class DetallesPaciente extends HttpServlet {

    private char priv = 'F';
    private int idPaciente = 0;
    private String tipoExpediente = "";
    private int tPacientes = 0;
    
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
        if(sesion != null) {
            priv = (char)sesion.getAttribute("Privilegio");
            ConexionDB cn = new ConexionDB();
            ArrayList<Paciente>edadesPacientes = new ArrayList<>();
            List<AntecedentePediatrico> expedientePediatrico = new ArrayList<>();
            List<AntecedenteAG> expedientesAG = new ArrayList<>();
            if(priv == 'A' || priv == 'E' || priv == 'M') {
                if(request.getParameter("idPaciente") != null) {
                    idPaciente = Integer.parseInt(request.getParameter("idPaciente"));
                } else {
                    idPaciente = 0;
                }
                edadesPacientes = cn.calcularEdad(idPaciente);
                tipoExpediente = cn.obtenerTipoExpediente(edadesPacientes);
                sesion.setAttribute("tipoExpediente", tipoExpediente);
                if(tipoExpediente.equals("pediatrico")) {
                    expedientePediatrico = cn.buscarPacientePediatricoID(idPaciente);
                    sesion.setAttribute("expedientePediatrico", expedientePediatrico);
                }
                else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")){
                    expedientesAG = cn.buscarPacienteAG_ID(idPaciente);
                    if(expedientesAG.isEmpty()){ // Si el buscador no encuentra al paciente adulto o geriátrico
                        // Debe percatarse si está registrado o no, si lo está, es pediátrico y cumplió años
                        // Y ya es mayor de 14, entonces podremos buscar sus datos en la "agenda" de pacientes pediátricos
                        // para actualizar sus datos y que se maneje en lo susecivo un expediente adulto
                        boolean cumple = false;
                        for(int i=0 ; i<edadesPacientes.size() ; i++){
                            if(edadesPacientes.get(i).getEdad() >= 15){
                                cumple = true;
                                break;
                            }
                        }
                        if(cumple != false){ // Si el paciente existe y ya es mayor de 14 años...
                            expedientePediatrico = cn.buscarPacientePediatricoID(idPaciente);
                            if(!expedientePediatrico.isEmpty()){ // Para mayor garantìa, comprobamos que de verdad estè registrado como pediàtrico
                                boolean actCumple = false;
                                for(int i=0 ; i<expedientePediatrico.size() ; i++){
                                    // Actualizamos el expediente a adulto
                                    actCumple = cn.actualizarCumpleanero(expedientePediatrico.get(i).getIdAntecedenteMedico(), expedientePediatrico.get(i).getIdFechasExpediente());
                                }
                                if(actCumple != false) { // Si el paciente se actualiza correctamente...
                                    // Entonces ya lo podemos buscar entre los pacientes adultos y geriátricos
                                    expedientesAG = cn.buscarPacienteAG_ID(idPaciente);
                                }
                            }
                        }
                    }
                    sesion.setAttribute("expedienteAG", expedientesAG);
                }
                
            }
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Detalles del paciente</title>");  
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
                        out.println("<h2>Buscar paciente</h2>");
                        out.println("<form method='POST' action='BusqPaciente'>");
                            out.println("<div class='row uniform 50%'>");
                            if(tipoExpediente.equals("pediatrico")) {
                                for(int i=0 ; i<expedientePediatrico.size() ; i++){
                                    String nombre = expedientePediatrico.get(0).getNombre();
                                    String apellidoPaterno = expedientePediatrico.get(0).getApellidoPaterno();
                                    String apellidoMaterno = expedientePediatrico.get(0).getApellidoMaterno();
                                    out.println("   <input type='text' value='" + nombre + " " + apellidoPaterno + " " + apellidoMaterno + "' placeholder='Introducir nombre' id='nombrePaciente' name='nombrePaciente' required/>");
                                }
                            } else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")) {
                                for(int i=0 ; i<expedientesAG.size() ; i++){
                                    String nombre = expedientesAG.get(0).getNombre();
                                    String apellidoPaterno = expedientesAG.get(0).getApellidoPaterno();
                                    String apellidoMaterno = expedientesAG.get(0).getApellidoMaterno();
                                    out.println("   <input type='text' value='" + nombre + " " + apellidoPaterno + " " + apellidoMaterno + "' placeholder='Introducir nombre' id='nombrePaciente' name='nombrePaciente' required/>");
                                }
                            }
                            out.println("</div>");
                            out.println("<br/><input type='submit' class='button fit special' value='Buscar'/>");
                        out.println("</form>");
                        out.println("<br/>");
                        if(tipoExpediente.equals("pediatrico")) {
                            if(!expedientePediatrico.isEmpty()){
                                tPacientes = expedientePediatrico.size();
                                out.println("<center>");
                                    out.println("<h3>Expediente pediátrico</h3>");
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++) {
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Folio:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getFolio() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>El expediente se creó en el día y hora:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getFechaExpedicion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Su última actualización fue del día y hora:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getFechaActualizacion() + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<a class='button fit special' href='EditarPaciente?idPaciente=" + idPaciente + "'>Editar</a>");
                                if(priv == 'A' || priv == 'E') {
                                    out.println("<br/>");
                                    out.println("<a class='button fit special' href='BajaPaciente?idPaciente=" + idPaciente + "'>Dar de baja</a>");
                                }
                                out.println("<br/>");
                                out.println("<center>");
                                    out.println("<h4>Datos personales</h4>");
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++){
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Nombres:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getNombre() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Apellido paterno:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getApellidoPaterno() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Apellido materno:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getApellidoMaterno() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Sexo:</td>");
                                            String sexo = null;
                                            if(expedientePediatrico.get(0).getIdSexo() == 1){
                                                sexo = "Masculino";
                                            } else if(expedientePediatrico.get(0).getIdSexo() == 2){
                                                sexo = "Femenino";
                                            }
                                            out.println("<td>" + sexo + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Ocupación:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getOcupacion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Fecha de nacimiento:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getFechaNacimeinto() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Edad:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getEdad() + " años" + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Teléfono del tutor:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getTelefono() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Teléfono de recados:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getTelefono2() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Correo electrónico del tutor:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getEmail() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Facebook:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getFacebook() + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<br/>");
                                out.println("<center>");
                                    out.println("<h4>Antecedentes médicos</h4>");
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++) {
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Enfermedad:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getEnfermedad() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Medicamento:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getMedicamento() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Alergia:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getAlergia() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Tiempo de gestación:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getTiempoGestacion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Complicación:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getComplicacion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Incubadora:</td>");
                                            out.println("<td>" + expedientePediatrico.get(0).getIncubadora() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Tipo de parto:</td>");
                                            String tipoParto = null;
                                        switch (expedientePediatrico.get(0).getIdTipoParto()) {
                                            case 1:
                                                tipoParto = "Normal";
                                                break;
                                            case 2:
                                                tipoParto = "Cesaria";
                                                break;
                                            case 3:
                                                tipoParto = "Agua";
                                                break;
                                            case 4:
                                                tipoParto = "Prematuro";
                                                break;
                                            case 5:
                                                tipoParto = "Inducido";
                                                break;
                                            default:
                                                break;
                                        }
                                            out.println("<td>" + tipoParto + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<br/>");
                                out.println("<a class='button fit special' href='EditarPaciente?idPaciente=" + idPaciente + "'>Editar</a>");
                                if(priv == 'A' || priv == 'E') {
                                    out.println("<br/>");
                                    out.println("<a class='button fit special' href='BajaPaciente?idPaciente=" + idPaciente + "'>Dar de baja</a>");
                                }
                            }/////
                            else {
                                out.println("<br/>");
                                out.println("<h1 style='color: red;'>El paciente con ID '"+idPaciente+"', no existe</h1>");
                            }
                        } 
                        // Si el expediente es de adulto o geriátrico
                        else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")){
                            if(!expedientesAG.isEmpty()) {
                                tPacientes = expedientesAG.size();
                                out.println("<center>");
                                if(tipoExpediente.equals("adulto")){
                                    out.println("<h3>Expediente adulto</h3>");
                                } else if(tipoExpediente.equals("geriatrico")){
                                    out.println("<h3>Expediente geriátrico</h3>");
                                }
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++) {
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Folio:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getFolio() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>El expediente se creó en el día y hora:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getFechaExpedicion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Su última actualización fue del día y hora:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getFechaActualizacion() + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<a class='button fit special' href='EditarPaciente?idPaciente=" + idPaciente + "'>Editar</a>");
                                if(priv == 'A' || priv == 'E') {
                                    out.println("<br/>");
                                    out.println("<a class='button fit special' href='BajaPaciente?idPaciente=" + idPaciente + "'>Dar de baja</a>");
                                }
                                
                                out.println("<br/>");
                                out.println("<center>");
                                    out.println("<h4>Datos personales</h4>");
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++){
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Nombres:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getNombre() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Apellido paterno:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getApellidoPaterno() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Apellido materno:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getApellidoMaterno() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Sexo:</td>");
                                            String sexo = null;
                                            if(expedientesAG.get(0).getIdSexo() == 1){
                                                sexo = "Masculino";
                                            } else if(expedientesAG.get(0).getIdSexo() == 2){
                                                sexo = "Femenino";
                                            }
                                            out.println("<td>" + sexo + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Ocupación:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getOcupacion() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Fecha de nacimiento:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getFechaNacimeinto() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Edad:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getEdad() + " años" + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Teléfono:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getTelefono() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Teléfono de recados:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getTelefono2() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Correo electrónico:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getEmail() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Facebook:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getFacebook() + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<br/>");
                                out.println("<center>");
                                    out.println("<h4>Antecedentes médicos</h4>");
                                out.println("</center>");
                                out.println("<table>");
                                for(int i=0 ; i<tPacientes ; i++) {
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Enfermedad:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getEnfermedad() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Medicamento:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getMedicamento() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>Alergia:</td>");
                                            out.println("<td>" + expedientesAG.get(0).getAlergia() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>T.A.</td>");
                                            out.println("<td>" + expedientesAG.get(0).getTa() + "</td>");
                                        out.println("</tr>");
                                        out.println("<tr>");
                                            out.println("<td style='font-weight: bold;'>N.G.</td>");
                                            out.println("<td>" + expedientesAG.get(0).getNg() + "</td>");
                                        out.println("</tr>");
                                }
                                out.println("</table>");
                                out.println("<br/>");
                                out.println("<a class='button fit special' href='EditarPaciente?idPaciente=" + idPaciente + "'>Editar</a>");
                                if(priv == 'A' || priv == 'E') {
                                    out.println("<br/>");
                                    out.println("<a class='button fit special' href='BajaPaciente?idPaciente=" + idPaciente + "'>Dar de baja</a>");
                                }
                            } else {
                                out.println("<br/>");
                                out.println("<h1 style='color: red;'>El paciente con ID '"+idPaciente+"', no existe</h1>");
                            }
                        }//////////////////////////
                } 
                else {
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
            Logger.getLogger(DetallesPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DetallesPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
