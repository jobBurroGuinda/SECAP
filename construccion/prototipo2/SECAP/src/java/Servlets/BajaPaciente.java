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
public class BajaPaciente extends HttpServlet {

    private char priv = 'F';
    private int idPaciente = 0;
    private String tipoExpediente = "";
    
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
            if(priv == 'A' || priv == 'E') {
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
                out.println("<title>Confirmación de baja del paciente</title>");   
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
                if(priv == 'A' || priv == 'E') {
                    if(tipoExpediente.equals("pediatrico")) {
                        if(!expedientePediatrico.isEmpty()){
                            out.println("<br/>");
                            for(int i=0 ; i<expedientePediatrico.size() ; i++){
                                    String nombre = expedientePediatrico.get(0).getNombre();
                                    String apellidoPaterno = expedientePediatrico.get(0).getApellidoPaterno();
                                    String apellidoMaterno = expedientePediatrico.get(0).getApellidoMaterno();
                                    out.println("<h2 style='color:orange;'>¿En verdad desea dar de baja al paciente: '" + nombre + " " + apellidoPaterno + " " + apellidoMaterno + "'?</h2>");
                                out.println("<h4>Todo su expediente se eliminará por completo de la base de datos</h4>");
                                    out.println("<a class='button special fit small' href='BorrarPaciente?idPaciente=" + expedientePediatrico.get(i).getIdPaciente() + "'>Aceptar</a>");
                                    out.println("<br/>");
                                    out.println("<a class='button special fit small' href='DetallesPaciente?idPaciente=" + expedientePediatrico.get(i).getIdPaciente() + "'>Cancelar</a>");
                            }
                        }
                        else {
                                out.println("<br/>");
                                out.println("<h1 style='color: red;'>El paciente con ID '"+idPaciente+"', no existe</h1>");
                        }
                    } else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")) {
                        if(!expedientesAG.isEmpty()) {
                            out.println("<br/>");
                            for(int i=0 ; i<expedientesAG.size() ; i++){
                                    String nombre = expedientesAG.get(0).getNombre();
                                    String apellidoPaterno = expedientesAG.get(0).getApellidoPaterno();
                                    String apellidoMaterno = expedientesAG.get(0).getApellidoMaterno();
                                    out.println("<h2 style='color:orange;'>¿En verdad desea dar de baja al paciente: '" + nombre + " " + apellidoPaterno + " " + apellidoMaterno + "'?</h2>");
                                out.println("<h4>Todo su expediente se eliminará por completo de la base de datos</h4>");
                                    out.println("<a class='button special fit small' href='BorrarPaciente?idPaciente=" + expedientesAG.get(i).getIdPaciente() + "'>Aceptar</a>");
                                    out.println("<br/>");
                                    out.println("<a class='button special fit small' href='DetallesPaciente?idPaciente=" + expedientesAG.get(i).getIdPaciente() + "'>Cancelar</a>");
                            }
                        }
                        else {
                                out.println("<br/>");
                                out.println("<h1 style='color: red;'>El paciente con ID '"+idPaciente+"', no existe</h1>");
                        }
                    }
                }
                else {
                        out.println("<h2 style='color: red;'>Usted no tiene permiso de acceder a esta sección de la aplicación</h2>");
                        if(priv == 'M'){
                            out.println("<h4>El usuario Mostrador sólo puede ver los datos y editarlos</h4>");
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
            Logger.getLogger(BajaPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BajaPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
