/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import Logica.Expediente.AntecedentesMedicos.AntecedenteAG;
import Logica.Expediente.AntecedentesMedicos.AntecedentePediatrico;
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
public class BorrarPaciente extends HttpServlet {

    private char priv = 'F';
    private int idAntecedenteAG = 0;
    private int idAntecedentePed = 0;
    private int idAntecedenteMed = 0;
    private int idExpediente = 0;
    private int idFechaExpediente = 0;
    private int idPaciente = 0;
    private int idContacto = 0;
    private String tipoExpediente = "";
    
    private boolean bajaPaciente = false;
    
    
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
            if(priv == 'A' || priv == 'E') {
                tipoExpediente = (String)sesion.getAttribute("tipoExpediente");
                if(tipoExpediente.equals("pediatrico")){
                    expedientePediatrico = (List<AntecedentePediatrico>)sesion.getAttribute("expedientePediatrico");
                    for(int i=0 ; i<expedientePediatrico.size() ; i++){
                        idPaciente = expedientePediatrico.get(i).getIdPaciente();
                        idExpediente = expedientePediatrico.get(i).getIdExpediente();
                        idFechaExpediente = expedientePediatrico.get(i).getIdFechasExpediente();
                        idContacto = expedientePediatrico.get(i).getIdContacto();
                        idPaciente = expedientePediatrico.get(i).getIdPaciente();
                        idAntecedenteMed = expedientePediatrico.get(i).getIdAntecedenteMedico();
                        idAntecedentePed = expedientePediatrico.get(i).getIdAntecedentePediatrico();
                    }
                    bajaPaciente = cn.bajaPacientePediatrico(idAntecedentePed, idAntecedenteMed, idExpediente, idFechaExpediente,
                                                                                                                idPaciente, idContacto);
                }
                else if(tipoExpediente.equals("adulto") || tipoExpediente.equals("geriatrico")){
                    expedientesAG = (List<AntecedenteAG>)sesion.getAttribute("expedienteAG");
                    expedientePediatrico = cn.buscarPacientePediatricoID(idPaciente);
                    if(!expedientePediatrico.isEmpty()) {
                        for(int i=0 ; i<expedientesAG.size() ; i++){
                            idPaciente = expedientesAG.get(i).getIdPaciente();
                            idExpediente = expedientesAG.get(i).getIdExpediente();
                            idFechaExpediente = expedientesAG.get(i).getIdFechasExpediente();
                            idContacto = expedientesAG.get(i).getIdContacto();
                            idPaciente = expedientesAG.get(i).getIdPaciente();
                            idAntecedenteMed = expedientesAG.get(i).getIdAntecedenteMedico();
                            idAntecedenteAG = expedientesAG.get(i).getIdAntecedenteAG();
                        }
                        for(int j=0 ; j<expedientePediatrico.size() ; j++){
                            idAntecedentePed = expedientePediatrico.get(j).getIdAntecedentePediatrico();
                        }
                        bajaPaciente = cn.bajaPacienteAGconPediatrico(idAntecedenteAG, idAntecedentePed, idAntecedenteMed,
                                                                            idExpediente, idFechaExpediente, idPaciente, idContacto);
                    } 
                    else {
                        for(int i=0 ; i<expedientesAG.size() ; i++){
                            idPaciente = expedientesAG.get(i).getIdPaciente();
                            idExpediente = expedientesAG.get(i).getIdExpediente();
                            idFechaExpediente = expedientesAG.get(i).getIdFechasExpediente();
                            idContacto = expedientesAG.get(i).getIdContacto();
                            idPaciente = expedientesAG.get(i).getIdPaciente();
                            idAntecedenteMed = expedientesAG.get(i).getIdAntecedenteMedico();
                            idAntecedenteAG = expedientesAG.get(i).getIdAntecedenteAG();
                        }
                        bajaPaciente = cn.bajaPacienteAGnormal(idAntecedenteAG, idAntecedenteMed, idExpediente, idFechaExpediente,
                                                                                                                    idPaciente, idContacto);
                    }
                }
            }
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Eliminar paciente del sistema</title>"); 
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
                if(priv == 'A' || priv == 'E'){
                    if(bajaPaciente != false){
                        out.println("<h2 style='color:green;'>El expediente paciente se dio de baja exitosamente del sistema</h2>");
                        out.println("<a href=\"BuscarPaciente\" class=\"button fit\">Aceptar</a>");
                    } else {
                        out.println("<h2 style='color:red;'>El paciente no se pudo dar de baja</h2>");
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
            Logger.getLogger(BorrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(BorrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
