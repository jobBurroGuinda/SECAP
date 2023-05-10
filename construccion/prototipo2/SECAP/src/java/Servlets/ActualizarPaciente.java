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
public class ActualizarPaciente extends HttpServlet {

    private char priv = 'F';
    private String tipoExpediente;
    
    private int idPaciente =0;
    private String nombrePaciente = "";
    private String apellidoPaterno = "";
    private String apellidoMaterno = "";
    private int idSexo = 0;
    private String fechaNacimiento = "";
    private String ocupacion = "";
    private int idContacto = 0;
    private String domicilio = "";
    private Long telefono1;
    private Long telefono2;
    private String email = "";
    private String facebook = "";
    private int idFechaExpediente = 0;
    private int idAntecedenteMed = 0;
    private int idAntecedentePediatrico = 0;
    private String tiempoGestacion = "";
    private int idTipoParto =0;
    private String incubadora = "";
    private String complicacion = "";
    private String enfermedad = "";
    private String medicamento = "";
    private String alergia = "";
    private int idAntecedenteAG;
    private String ta = "";
    private String ng = "";
    
    private int tPacientes = 0;
    private boolean verifActualizacion = false;
            
    
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
            List<AntecedentePediatrico> expedientePediatrico = new ArrayList<>();
            List<AntecedenteAG> expedientesAG = new ArrayList<>();
            if(priv == 'A' || priv == 'E' || priv == 'M') {
                tipoExpediente = (String)sesion.getAttribute("tipoExpediente");
                nombrePaciente = request.getParameter("nombres");
                apellidoPaterno = request.getParameter("apellidoPaterno");
                apellidoMaterno = request.getParameter("apellidoMaterno");
                idSexo = Integer.parseInt(request.getParameter("sexo"));
                fechaNacimiento = request.getParameter("fechaNacimiento");
                ocupacion = request.getParameter("ocupacion");
                domicilio = request.getParameter("domicilio");
                telefono1 = Long.parseLong(request.getParameter("telefono"));
                telefono2 = Long.parseLong(request.getParameter("telefono2"));
                email = request.getParameter("email");
                facebook = request.getParameter("facebook");
                enfermedad = request.getParameter("enfermedad");
                medicamento = request.getParameter("medicamento");
                alergia = request.getParameter("alergia");
                
                // Si el expediente a actualizar es pediátrico...
                if(tipoExpediente.equals("pediatrico")) {
                    tiempoGestacion = request.getParameter("tiempogestacion");
                    idTipoParto = Integer.parseInt(request.getParameter("tipoparto"));
                    incubadora = request.getParameter("incubadora");
                    complicacion = request.getParameter("complicacion");
                    expedientePediatrico = (List<AntecedentePediatrico>)sesion.getAttribute("expedientePediatrico");
                    for(int i=0 ; i<expedientePediatrico.size() ; i++){
                        idPaciente = expedientePediatrico.get(i).getIdPaciente();
                        idContacto = expedientePediatrico.get(i).getIdContacto();
                        idFechaExpediente = expedientePediatrico.get(i).getIdFechasExpediente();
                        idAntecedenteMed = expedientePediatrico.get(i).getIdAntecedenteMedico();
                        idAntecedentePediatrico = expedientePediatrico.get(i).getIdAntecedentePediatrico();
                    }
                    // Realizar la actualización
                    verifActualizacion = cn.actualizarPacientePediatrico(idPaciente, nombrePaciente, apellidoPaterno,
                            apellidoMaterno, ocupacion, fechaNacimiento, idContacto, domicilio, telefono1, telefono2,
                            email, facebook, idSexo, idFechaExpediente, idAntecedenteMed, enfermedad, medicamento, alergia,
                                                idAntecedentePediatrico, tiempoGestacion, complicacion, incubadora, idTipoParto);
                }
                // Si elexpediente a actualizar es adulto o geriátrico...
                else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")) {
                    ta = request.getParameter("ta");
                    ng = request.getParameter("ng");
                    expedientesAG = (List<AntecedenteAG>)sesion.getAttribute("expedienteAG");
                    for(int i=0 ; i<expedientesAG.size() ; i++){
                        idPaciente = expedientesAG.get(i).getIdPaciente();
                        idContacto = expedientesAG.get(i).getIdContacto();
                        idSexo = expedientesAG.get(i).getIdSexo();
                        idFechaExpediente = expedientesAG.get(i).getIdFechasExpediente();
                        idAntecedenteMed = expedientesAG.get(i).getIdAntecedenteMedico();
                        idAntecedenteAG = expedientesAG.get(i).getIdAntecedenteAG();
                    }
                    // Realizar la actualización
                    verifActualizacion = cn.actualizarPacienteAG(idPaciente, nombrePaciente, apellidoPaterno, apellidoMaterno,
                                ocupacion, fechaNacimiento, idContacto, domicilio, telefono1, telefono2, email, facebook, idSexo,
                                        idFechaExpediente, idAntecedenteMed, enfermedad, medicamento, alergia, idAntecedenteAG, ta, ng);
                }
                
            }
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Actialización de expediente</title>");  
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
                if(priv == 'A' || priv == 'E' || priv == 'M') {
                    /* 
                    *   Si el expediente se actualizó correctamente...
                    */
                    if(verifActualizacion != false) {
                        out.println("<h2>Buscar paciente</h2>");
                        out.println("<form method='POST' action='BusqPaciente'>");
                            out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' placeholder='Introducir nombre' id='nombrePaciente' name='nombrePaciente' required/>");
                            out.println("</div>");
                            out.println("<br/><input type='submit' class='button fit special' value='Buscar'/>");
                        out.println("</form>");
                        out.println("<br/>");
                        ArrayList<Paciente>edadesPacientes = new ArrayList<>();
                        edadesPacientes = cn.calcularEdad(idPaciente);
                        String tipoExpedienteA = "";
                        tipoExpedienteA = cn.obtenerTipoExpediente(edadesPacientes);
                        sesion.removeAttribute("tipoExpediente");
                        sesion.setAttribute("tipoExpediente", tipoExpedienteA);
                        if(tipoExpedienteA.equals("pediatrico")) {
                            expedientePediatrico = null;
                            expedientePediatrico = cn.buscarPacientePediatricoID(idPaciente);
                            if(!expedientePediatrico.isEmpty()){
                                tPacientes = expedientePediatrico.size();
                                out.println("<center>");
                                    out.println("<h5 style='color:green;'>¡El expediente se actualizó exitosamente!</h5>");
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
                        else if(tipoExpedienteA.equals("adulto") || tipoExpedienteA.equals("geriatrico")){
                            expedientesAG = null;
                            expedientesAG = cn.buscarPacienteAG_ID(idPaciente);
                            if(!expedientesAG.isEmpty()) {
                                tPacientes = expedientesAG.size();
                                out.println("<center>");
                                out.println("<h5 style='color:green;'>¡El expediente se actualizó exitosamente!</h5>");
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
                                            out.println("<td style='font-weight: bold;'>Sexo::</td>");
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
                        }
                        
                    }
                    
                    /* 
                    *   Si el expediente no se pudo actualizar
                    */
                    else {
                        if(tipoExpediente.equals("pediatrico")) {
                                out.println("<h2>Editar datos del paciente pediátrico</h2>");
                                out.println("<h5 style='color:red;'>Los datos del paciente no se pudieron actualizar, intente de nuevo. Si persiste el problema contacte al desarrollador de la aplicación</h5><br/>");
                                out.println("<form method='POST' action='ActualizarPaciente'>");
                                    out.println("   <h5>Nombres:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+nombrePaciente+"' placeholder='Nombres *' name='nombres' id='nombres' value='"+"' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Apellido paterno:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+apellidoPaterno+"' placeholder='Apellido paterno *' name='apellidoPaterno' id='apellidoPaterno' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Apellido materno:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+apellidoMaterno+"' placeholder='Apellido materno *' name='apellidoMaterno' id='apellidoMaterno' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Sexo:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                        out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                            switch (idSexo){
                                                case 1:
                                                    out.println("<option selected='selected' value='1'>- Masculino</option>");
                                                    out.println("<option value='2'>- Femenino</option>");
                                                    break;
                                                case 2:
                                                    out.println("<option value='1'>- Masculino</option>");
                                                    out.println("<option selected='selected' value='2'>- Femenino</option>");
                                                    break;
                                                default:
                                                    out.println("<option value='1'>- Masculino</option>");
                                                    out.println("<option value='2'>- Femenino</option>");
                                                    break;
                                            }
                                        out.println("</select>");
                                    out.println("</div>");
                                    out.println("   <h5>Fecha de nacimiento:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='date' value='"+fechaNacimiento+"' style='color:black;' min='1900-01-01' max='2016-06-01' placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Escolaridad:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                        out.println("   <input type='text' value='"+ocupacion+"' placeholder='Escolaridad *' id='ocupacion' name='ocupacion' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Domicilio:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+domicilio+"' placeholder='Domicilio *' id='domicilio' name='domicilio' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Teléfono del tutor:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='tel' value='"+telefono1+"' placeholder='Teléfono *' pattern='[0-9]{10}' name='telefono' id='telefono' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Teléfono de recados:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='tel' value='"+telefono2+"' placeholder='Teléfono de recados *' pattern='[0-9]{10}' name='telefono2' id='telefono2' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Correo electrónico del tutor:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='email' value='"+email+"' placeholder='Correo electrónico *' name='email' id='email' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Facebook:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+facebook+"' placeholder='Cuenta de facebook (opcional)' name='facebook' id='facebook'/>");
                                    out.println("</div>");
                                    out.println("<br/>");
                                    out.println("<h3>Antecedentes médicos</h3>");
                                    out.println("   <h5>Tiempo de gestación:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+tiempoGestacion+"' placeholder='Tiempo de gestación *' id='tiempogestacion' name='tiempogestacion' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Tipo de parto:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                        out.println("<select style=\"width='50%';\" name='tipoparto' id='tipoparto'>");
                                            switch (idTipoParto) {
                                                case 1:
                                                        out.println("<option selected='selected' value='1'>- Normal</option>");
                                                        out.println("<option value='2'>- Cesaria</option>");
                                                        out.println("<option value='3'>- Agua</option>");
                                                        out.println("<option value='4'>- Prematuro</option>");
                                                        out.println("<option value='5'>- Inducido</option>");
                                                    break;
                                                case 2:
                                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                                        out.println("<option selected='selected' value='2'>- Cesaria</option>");
                                                        out.println("<option value='3'>- Agua</option>");
                                                        out.println("<option value='4'>- Prematuro</option>");
                                                        out.println("<option value='5'>- Inducido</option>");
                                                    break;
                                                case 3:
                                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                                        out.println("<option value='2'>- Cesaria</option>");
                                                        out.println("<option selected='selected' value='3'>- Agua</option>");
                                                        out.println("<option value='4'>- Prematuro</option>");
                                                        out.println("<option value='5'>- Inducido</option>");
                                                    break;
                                                case 4:
                                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                                        out.println("<option value='2'>- Cesaria</option>");
                                                        out.println("<option value='3'>- Agua</option>");
                                                        out.println("<option selected='selected' value='4'>- Prematuro</option>");
                                                        out.println("<option value='5'>- Inducido</option>");
                                                    break;
                                                case 5:
                                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                                        out.println("<option value='2'>- Cesaria</option>");
                                                        out.println("<option value='3'>- Agua</option>");
                                                        out.println("<option value='4'>- Prematuro</option>");
                                                        out.println("<option selected='selected' value='5'>- Inducido</option>");
                                                    break;
                                                default:
                                                        out.println("<option value='1'>- Tipo de parto normal</option>");
                                                        out.println("<option value='2'>- Cesaria</option>");
                                                        out.println("<option value='3'>- Agua</option>");
                                                        out.println("<option value='4'>- Prematuro</option>");
                                                        out.println("<option value='5'>- Inducido</option>");
                                                    break;
                                            }
                                        out.println("</select>");
                                    out.println("</div>");
                                    out.println("   <h5>Incubadora:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+incubadora+"' placeholder='Incubadora *' id='incubadora' name='incubadora' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Complicación:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+complicacion+"' placeholder='Complicación' id='complicacion' name='complicacion' />");
                                    out.println("</div>");
                                    out.println("   <h5>Padece enfermedad:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+enfermedad+"' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' />");
                                    out.println("</div>");
                                    out.println("   <h5>Medicamento:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+medicamento+"' placeholder='medicamento' id='medicamento' name='medicamento' />");
                                    out.println("</div>");
                                    out.println("   <h5>Alergia:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+alergia+"' placeholder='alergia' id='alergia' name='alergia' />");
                                    out.println("</div>");
                                    out.println("<br/>");
                                    out.println("   <input type='submit' class='button fit special' value='Actualizar'/>");
                                    out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                                out.println("</form>");
                        }
                        else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")) {
                                if(tipoExpediente.equals("adulto")){
                                    out.println("<h2>Editar datos del paciente adulto</h2>");
                                } else if(tipoExpediente.equals("geriatrico")){
                                    out.println("<h2>Editar datos del paciente geriátrico</h2>");
                                }
                                out.println("<h5 style='color:red;'>Los datos del paciente no se pudieron actualizar, intente de nuevo. Si persiste el problema contacte al desarrollador de la aplicación</h5><br/>");
                                out.println("<form method='POST' action='ActualizarPaciente'>");
                                    out.println("   <h5>Nombres:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + nombrePaciente + "' placeholder='Nombres *' name='nombres' id='nombres' value='"+"' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Apellido paterno::</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + apellidoPaterno + "' placeholder='Apellido paterno *' name='apellidoPaterno' id='apellidoPaterno' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Apellido materno:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + apellidoMaterno + "' placeholder='Apellido materno *' name='apellidoMaterno' id='apellidoMaterno' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Sexo:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                        out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                            switch (idSexo){
                                                case 1:
                                                    out.println("<option selected='selected' value='1'>- Masculino</option>");
                                                    out.println("<option value='2'>- Femenino</option>");
                                                    break;
                                                case 2:
                                                    out.println("<option value='1'>- Masculino</option>");
                                                    out.println("<option selected='selected' value='2'>- Femenino</option>");
                                                    break;
                                                default:
                                                    out.println("<option value='1'>- Masculino</option>");
                                                    out.println("<option value='2'>- Femenino</option>");
                                                    break;
                                            }
                                        out.println("</select>");
                                    out.println("</div>");
                                    out.println("   <h5>Fecha de nacimiento:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='date' value='" + fechaNacimiento + "' style='color:black;' min='1900-01-01' max='2016-06-01' placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Ocupación:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + ocupacion + "' placeholder='Ocupación *' id='ocupacion' name='ocupacion' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Domicilio:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + domicilio + "' placeholder='Domicilio *' id='domicilio' name='domicilio' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Teléfono:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='tel' value='" + telefono1 + "' placeholder='Teléfono *' pattern='[0-9]{10}' name='telefono' id='telefono' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Teléfono de recados:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='tel' value='" + telefono2 + "' placeholder='Teléfono de recados *' pattern='[0-9]{10}' name='telefono2' id='telefono2' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Correo electrónico:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='email' value='" + email + "' placeholder='Correo electrónico *' name='email' id='email' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Facebook:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + facebook + "' placeholder='Cuenta de facebook (opcional)' name='facebook' id='facebook'/>");
                                    out.println("</div>");
                                    out.println("<br/>");
                                    out.println("<h3>Antecedentes médicos</h3>");
                                    out.println("   <h5>Padece enfermedad:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + enfermedad + "' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' required/>");
                                    out.println("</div>");
                                    out.println("   <h5>Medicamento:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + medicamento + "' placeholder='Medicamento' id='medicamento' name='medicamento' />");
                                    out.println("</div>");
                                    out.println("   <h5>Alergia:</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + alergia + "' placeholder='Alergia' id='alergia' name='alergia' />");
                                    out.println("</div>");
                                    out.println("   <h5>T.A.</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + ta + "' placeholder='T.A. *' id='ta' name='ta' required />");
                                    out.println("</div>");
                                    out.println("   <h5>N.G.</h5>");
                                    out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='" + ng + "' placeholder='N.G. *' id='ng' name='ng' required />");
                                    out.println("</div>");
                                    out.println("<br/>");
                                    out.println("   <input type='submit' class='button fit special' value='Actualizar'/>");
                                    out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                                out.println("</form>");
                        }
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
            Logger.getLogger(ActualizarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ActualizarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
