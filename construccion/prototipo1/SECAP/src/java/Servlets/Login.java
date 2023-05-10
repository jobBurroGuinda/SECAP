/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Logica.ConexionDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class Login extends HttpServlet {

    private boolean verificador = false;
    private String username = null;
    private String password = null;
    private char vLogin = 'F';
    
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
        request.setCharacterEncoding("UTF-8");
        ConexionDB cn = new ConexionDB();
        username = request.getParameter("username");
        password = request.getParameter("passw");
        vLogin = cn.login(username, password);
        try (PrintWriter out = response.getWriter()) {
            switch (vLogin) {
                case 'M':
                case 'A':
                case 'E':
                    HttpSession sesion = request.getSession();
                    sesion.getAttribute(username);
                    sesion.setAttribute("username", username);
                    request.setAttribute("Privilegio", vLogin);
                    RequestDispatcher rd = request.getRequestDispatcher("Home");
                    rd.forward(request, response);
                    break;
                case 'R':
                    HttpSession sesionR = request.getSession();
                    sesionR.getAttribute(username);
                    sesionR.setAttribute("username", username);
                    request.setAttribute("Privilegio", vLogin);
                    RequestDispatcher rdd = request.getRequestDispatcher("HomeRedes");
                    rdd.forward(request, response);
                    break;
                default:
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
                    out.println("<title>Inicio de sesión incorrecto</title>");
                    out.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
                    out.println("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
                    out.println("<meta name='keywords' content='Badge Sign In Form template Responsive, Login form web template,Flat Pricing tables,Flat Drop downs  Sign up Web Templates, Flat Web Templates, Login sign up Responsive web template, SmartPhone Compatible web template, free WebDesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design' />");
                    out.println("<script type='application/x-javascript'> addEventListener(\"load\", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>");
                    out.println("<!-- Custom Theme files -->");
                    out.println("<link href='css/style.css' rel='stylesheet' type='text/css' media='all' />");
                    out.println("<!-- //Custom Theme files -->");
                    out.println("<!-- web font -->");
                    out.println("<link href='//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'><!--web font-->");  
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<!-- main -->");
                    out.println("<div class='main'>");
                    out.println("<h1>Sistema de Expedientes y Agenda de Pacientes</h1>");
                    out.println("<div class='login-form'> ");
                    out.println("<form action='Login' method='post'>");
                    out.println("<h2 style='color:rgb(240,0,0);'>Nombre de usuario y/o contraseña incorrectos</h2>");
                    out.println("<div class='agileits-top'>");
                    out.println("<div class='styled-input'>");
                    out.println("<input type='text' name='username' required/>");
                    out.println("<label>Nombre de usuario</label>");
                    out.println("<span></span>");
                    out.println("</div>");
                    out.println("<div class='styled-input'>");
                    out.println("<input type='password' name='passw' required> ");
                    out.println("<label>Contraseña</label>");
                    out.println("<span></span>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<div class='agileits-bottom'>");
                    out.println("<input type='submit' value='Entrar'>");
                    out.println("</div>");
                    out.println("</form>");
                    out.println("</div>");
                    out.println("</div>");
                    out.println("<!-- //main -->");
                    out.println("<!-- copyright -->");
                    out.println("<!-- <p> © 2016 Badge Sign In Form . All rights reserved | Design by <a href=\"http://w3layouts.com/\" target=\"_blank\">W3layouts</a></p> -->");
                    out.println("<!-- //copyright -->");
                    out.println("</body>");
                    out.println("</html>");
                    break;
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
