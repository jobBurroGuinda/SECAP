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
public class EditarPaciente extends HttpServlet {

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
                out.println("<title>Editar paciente</title>"); 
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
                    if(tipoExpediente.equals("pediatrico")) {
                        for(int i=0 ; i<expedientePediatrico.size() ; i++){
                            out.println("<h2>Editar datos del paciente pediátrico</h2><br/>");
                            out.println("<form method='POST' action='ActualizarPaciente'>");
                                out.println("   <h5>Nombres:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getNombre()+"' placeholder='Nombres *' name='nombres' id='nombres' value='"+"' required/>");
                                out.println("</div>");
                                out.println("   <h5>Apellido paterno:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getApellidoPaterno()+"' placeholder='Apellido paterno *' name='apellidoPaterno' id='apellidoPaterno' required/>");
                                out.println("</div>");
                                out.println("   <h5>Apellido materno:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getApellidoMaterno()+"' placeholder='Apellido materno *' name='apellidoMaterno' id='apellidoMaterno' required/>");
                                out.println("</div>");
                                out.println("   <h5>Sexo:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                        switch (expedientePediatrico.get(i).getIdSexo()){
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
                                out.println("   <input type='date' value='"+expedientePediatrico.get(i).getFechaNacimeinto()+"' style='color:black;' min='1900-01-01' max='2016-06-01' placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                                out.println("</div>");
                                out.println("   <h5>Escolaridad:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("   <input type='text' value='"+expedientePediatrico.get(i).getOcupacion()+"' placeholder='Escolaridad *' id='ocupacion' name='ocupacion' required/>");
                                out.println("</div>");
                                out.println("   <h5>Domicilio:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getDomicilio()+"' placeholder='Domicilio *' id='domicilio' name='domicilio' required/>");
                                out.println("</div>");
                                out.println("   <h5>Teléfono del tutor:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='tel' value='"+expedientePediatrico.get(i).getTelefono()+"' placeholder='Teléfono *' pattern='[0-9]{10}' name='telefono' id='telefono' required/>");
                                out.println("</div>");
                                out.println("   <h5>Teléfono de recados:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='tel' value='"+expedientePediatrico.get(i).getTelefono2()+"' placeholder='Teléfono de recados *' pattern='[0-9]{10}' name='telefono2' id='telefono2' required/>");
                                out.println("</div>");
                                out.println("   <h5>Correo electrónico del tutor:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='email' value='"+expedientePediatrico.get(i).getEmail()+"' placeholder='Correo electrónico *' name='email' id='email' required/>");
                                out.println("</div>");
                                out.println("   <h5>Facebook:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getFacebook()+"' placeholder='Cuenta de facebook (opcional)' name='facebook' id='facebook'/>");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("<h3>Antecedentes médicos</h3>");
                                out.println("   <h5>Tiempo de gestación:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getTiempoGestacion()+"' placeholder='Tiempo de gestación *' id='tiempogestacion' name='tiempogestacion' required/>");
                                out.println("</div>");
                                out.println("   <h5>Tipo de parto:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("<select style=\"width='50%';\" name='tipoparto' id='tipoparto'>");
                                        switch (expedientePediatrico.get(i).getIdTipoParto()) {
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
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getIncubadora()+"' placeholder='Incubadora *' id='incubadora' name='incubadora' required/>");
                                out.println("</div>");
                                out.println("   <h5>Complicación:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getComplicacion()+"' placeholder='Complicación' id='complicacion' name='complicacion' />");
                                out.println("</div>");
                                out.println("   <h5>Padece enfermedad:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getEnfermedad()+"' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' />");
                                out.println("</div>");
                                out.println("   <h5>Medicamento:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getMedicamento()+"' placeholder='medicamento' id='medicamento' name='medicamento' />");
                                out.println("</div>");
                                out.println("   <h5>Alergia:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='"+expedientePediatrico.get(i).getAlergia()+"' placeholder='alergia' id='alergia' name='alergia' />");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("   <input type='submit' class='button fit special' value='Actualizar'/>");
                                out.println("   <input type='reset' class='button special' value='Borrar todo'/>");
                            out.println("</form>");
                        }
                    } /////////////////////////////////////
                    else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")) {
                        for(int i=0 ; i<expedientesAG.size() ; i++){
                            if(tipoExpediente.equals("adulto")){
                                out.println("<h2>Editar datos del paciente adulto</h2><br/>");
                            } else if(tipoExpediente.equals("geriatrico")){
                                out.println("<h2>Editar datos del paciente geriátrico</h2><br/>");
                            }
                            out.println("<form method='POST' action='ActualizarPaciente'>");
                                out.println("   <h5>Nombres:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getNombre() + "' placeholder='Nombres *' name='nombres' id='nombres' value='"+"' required/>");
                                out.println("</div>");
                                out.println("   <h5>Apellido paterno::</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getApellidoPaterno() + "' placeholder='Apellido paterno *' name='apellidoPaterno' id='apellidoPaterno' required/>");
                                out.println("</div>");
                                out.println("   <h5>Apellido materno:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getApellidoMaterno() + "' placeholder='Apellido materno *' name='apellidoMaterno' id='apellidoMaterno' required/>");
                                out.println("</div>");
                                out.println("   <h5>Sexo:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                    out.println("<select style=\"width='50%';\" name='sexo' id='sexo'>");
                                        switch (expedientesAG.get(i).getIdSexo()){
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
                                out.println("   <input type='date' value='" + expedientesAG.get(i).getFechaNacimeinto() + "' style='color:black;' min='1900-01-01' max='2016-06-01' placeholder='Fecha de nacimiento' id='fechaNacimiento' name='fechaNacimiento' required/>");
                                out.println("</div>");
                                out.println("   <h5>Ocupación:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getOcupacion() + "' placeholder='Ocupación *' id='ocupacion' name='ocupacion' required/>");
                                out.println("</div>");
                                out.println("   <h5>Domicilio:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getDomicilio() + "' placeholder='Domicilio *' id='domicilio' name='domicilio' required/>");
                                out.println("</div>");
                                out.println("   <h5>Teléfono:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='tel' value='" + expedientesAG.get(i).getTelefono() + "' placeholder='Teléfono *' pattern='[0-9]{10}' name='telefono' id='telefono' required/>");
                                out.println("</div>");
                                out.println("   <h5>Teléfono de recados:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='tel' value='" + expedientesAG.get(i).getTelefono2() + "' placeholder='Teléfono de recados *' pattern='[0-9]{10}' name='telefono2' id='telefono2' required/>");
                                out.println("</div>");
                                out.println("   <h5>Correo electrónico:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='email' value='" + expedientesAG.get(i).getEmail() + "' placeholder='Correo electrónico *' name='email' id='email' required/>");
                                out.println("</div>");
                                out.println("   <h5>Facebook:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getFacebook() + "' placeholder='Cuenta de facebook (opcional)' name='facebook' id='facebook'/>");
                                out.println("</div>");
                                out.println("<br/>");
                                out.println("<h3>Antecedentes médicos</h3>");
                                out.println("   <h5>Padece enfermedad:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getEnfermedad() + "' placeholder='Padece enfermedad' id='enfermedad' name='enfermedad' required/>");
                                out.println("</div>");
                                out.println("   <h5>Medicamento:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getMedicamento() + "' placeholder='Medicamento' id='medicamento' name='medicamento' />");
                                out.println("</div>");
                                out.println("   <h5>Alergia:</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getAlergia() + "' placeholder='Alergia' id='alergia' name='alergia' />");
                                out.println("</div>");
                                out.println("   <h5>T.A.</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getTa() + "' placeholder='T.A. *' id='ta' name='ta' required />");
                                out.println("</div>");
                                out.println("   <h5>N.G.</h5>");
                                out.println("<div class='row uniform 50%'>");
                                out.println("   <input type='text' value='" + expedientesAG.get(i).getNg() + "' placeholder='N.G. *' id='ng' name='ng' required />");
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
            Logger.getLogger(EditarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EditarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
