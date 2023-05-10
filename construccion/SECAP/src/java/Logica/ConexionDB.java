/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Logica.Expediente.AntecedentesMedicos.AntecedenteAG;
import Logica.Expediente.AntecedentesMedicos.AntecedentePediatrico;
import Logica.Expediente.Expediente;
import Logica.Expediente.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Job
 */
public class ConexionDB {
    // Se inicializan las variables para conexión
    private static Connection cn = null;
    private static Statement st = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    
    // Se inicializan las variables para el acceso a la DB
        // Indicamos el nombre del servidor de la base de datos
    private static final String servidor = "localhost";
        // Introducimos el nombre de la DB
    private static final String nombreBD =  "dbSECAP";
        // Escribimos el nombre de usuario de MySQL que vamos a usar
    private static final String usuarioMySQL = "root";
        // Introducimos la contraseña del usuario de MySQL
    private static final String contraseñaMySQL = "t1nocota+";
    
    private boolean verificador = false;
    
    private String nomUsuarioSECAP = null;
    private String passwordSECAP = null;
    private char veri = 'F';
    
    
    // El siguiente método servirá para realizar la conexión
    public void conectar(){
        try{
            // Especificamos el Driver a utilizar, en este caso será el JDBC de MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Agregamos el Driver, el nombre del servidor, el puerto y el nombre de la base de datos en el URL o ruta 
            String url = "jdbc:mysql://" + servidor + ":3306/" + nombreBD;
            // Le pasamos el URL, el nombre de usuario y contraseña de MySQL para realizar la conexión
            cn = DriverManager.getConnection(url, usuarioMySQL, contraseñaMySQL);
            
        }
        // Este error se presentaría si la aplicación no cuenta con la librería de MySQL que contiene el Driver
        catch(ClassNotFoundException ex){System.out.println("Se produjo un error de la url SQL");}
        catch(SQLException ex){String msg = "";// Esta excepción se produciría en caso de que alguno de los datos del gestor o le la DB sea incorrecto
            // El error 1049 significa que la base de datos especificada no existe
            switch (ex.getErrorCode()) {
            // El error 1044 significa que el usuario especificado no existe
                case 1049:
                    msg = "La base de datos: "+nombreBD+" no existe";
                    break;
            // El error 1045 indica que la contraseña es incorrecta
                case 1044:
                    msg = "El usuario: "+usuarioMySQL+" no existe";
                    break;
            // Por último, el error cero significa que el servidor de base de datos está inactivo
            // Esto se podría presentar si no tenemos correctamente instalado a MySQL
                case 1045:
                    msg = "Contraseña SQL incorrecta";
                    break;
                case 0:
                    msg = "La conexión con la base de datos no se puede realizar\nParece que el servidor no esta activo";
                    break;
                default:
                    break;
            }
            // En caso de que se haya presentado alguno de estos errores con el gestor
            // se desplegará un mensaje con informando la situación
                System.out.println(msg + "\n\n" + ex.getMessage());
        }
    }
    
    
    public char login(String username, String password) throws SQLException {
        try {
            this.nomUsuarioSECAP = username;
            this.passwordSECAP = password;
            // Si no se ha realizado la conexión con la base de datos la iniciamos
            if(cn == null) {
                conectar();
            }
            String csp = "";
            // Especificamos el comando SQL de la consulta
            // La tabla "vusuarios", en realidad es una vista o view que sirve para desplegar únicamente el nombre de usuario
            // y contraseña
            String sql = "SELECT * FROM mUsuario WHERE nom_usu=? AND psw_usu=password(?)";
                ps = cn.prepareStatement(sql);
                ps.setString(1, nomUsuarioSECAP);
                ps.setString(2, passwordSECAP);
                // Mandamos el comando al gestor
                rs = ps.executeQuery(); 
                // Si el nombre de usuario y contraseña son correctos proseguimos con lo siguiente, de lo contrario 
                       // el verificador tomará el valor de false, lo que indicará que alguno de los dos datos o los dos son incorrectos
                // Si todo salió bien buscamos todo lo que haya en la tabla...
                while(rs.next()){
                    // ..., especificamente en el campo "id_prv" para asegurarnos si el usuario tiene algún privilegio
                    // y "guardamos" lo que encontremos en la variable "csp"
                    csp=rs.getString("idP_usu");
                }
            // Verificamos que el usuario introducido sea el administrador, ya que por ahora sólo él puede acceder
            // si es así...
            switch (csp) {
                case "1":
                    // Usuario mostrador
                    veri = 'M';
                    break;
                case "2":
                    // Usuario administrador
                    veri = 'A';
                    break;
                case "3":
                    // Usuario especialista
                    veri = 'E';
                    break;
                case "4":
                    // Usuario redes
                    veri = 'R';
                    break;
                default:
                    // Usuario no registrado
                    veri = 'F';
                    break;
            }
            rs.close();
            ps.close();
             // Devolvemos el valor del verificador
             return veri;
        } finally {
        }
    }
    
    
    public boolean registrarUsuarioMostrador(String username, String password, String nombres, String apellidoPaterno, String apellidoMaterno){
        this.nomUsuarioSECAP = username;
        this.passwordSECAP = password;
        if(cn == null) {
           conectar();
        }
        int r1 = 0, r2=0, rST=0, rR, rC;
            String sql1 = null, sql2=null, sqlST, sqlR, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "INSERT INTO mUsuario (nom_usu, psw_usu, idP_usu) VALUES (?, password(?), 1)";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, nomUsuarioSECAP);
                ps.setString(2, passwordSECAP);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0) {
                try{
                    // Obtener el ID que se registró del registro mUsuario
                    String sqlLastC = "SELECT @lastU := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                }catch(SQLException ex){ex.printStackTrace();}
                try{
                sql2 = "INSERT INTO mMostrador (nom_mos, app_mos, apm_mos, idU_mos) VALUES (?, ?, ?, @lastU)";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, nombres);
                    ps.setString(2, apellidoPaterno);
                    ps.setString(3, apellidoMaterno);
                r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0 && r1 != 0) {
                    try{
                    // Finalizar transacción
                        sqlC = "COMMIT";
                        ps = cn.prepareStatement(sqlC);
                        rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                    verificador = true;
                }
                else {
                        try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else {
                    try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    verificador = false;
            }
        
        
        return verificador;
    }
    
    
    
    public boolean registrarUsuarioEspecialista(String username, String password, String nombres, String apellidoPaterno, String apellidoMaterno){
        this.nomUsuarioSECAP = username;
        this.passwordSECAP = password;
        if(cn == null) {
           conectar();
        }
        int r1 = 0, r2=0, rST=0, rR, rC;
            String sql1 = null, sql2=null, sqlST, sqlR, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "INSERT INTO mUsuario (nom_usu, psw_usu, idP_usu) VALUES (?, password(?), 3)";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, nomUsuarioSECAP);
                ps.setString(2, passwordSECAP);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0) {
                try{
                    // Obtener el ID que se registró del registro mUsuario
                    String sqlLastC = "SELECT @lastU := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                }catch(SQLException ex){ex.printStackTrace();}
                try{
                sql2 = "INSERT INTO mEspecialista (nom_esp, app_esp, apm_esp, idU_esp) VALUES (?, ?, ?, @lastU)";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, nombres);
                    ps.setString(2, apellidoPaterno);
                    ps.setString(3, apellidoMaterno);
                r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0 && r1 != 0) {
                    try{
                    // Finalizar transacción
                        sqlC = "COMMIT";
                        ps = cn.prepareStatement(sqlC);
                        rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                    verificador = true;
                }
                else {
                        try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else {
                    try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    verificador = false;
            }
        
        
        return verificador;
    }
    
    
    
    public boolean registrarUsuarioAdministrador(String username, String password, String nombres, String apellidoPaterno, String apellidoMaterno){
        this.nomUsuarioSECAP = username;
        this.passwordSECAP = password;
        if(cn == null) {
           conectar();
        }
        int r1 = 0, r2=0, rST=0, rR, rC;
            String sql1 = null, sql2=null, sqlST, sqlR, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "INSERT INTO mUsuario (nom_usu, psw_usu, idP_usu) VALUES (?, password(?), 2)";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, nomUsuarioSECAP);
                ps.setString(2, passwordSECAP);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0) {
                try{
                    // Obtener el ID que se registró del registro mUsuario
                    String sqlLastC = "SELECT @lastU := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                }catch(SQLException ex){ex.printStackTrace();}
                try{
                sql2 = "INSERT INTO mAdministrador (nom_adm, app_adm, apm_adm, idU_adm) VALUES (?, ?, ?, @lastU)";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, nombres);
                    ps.setString(2, apellidoPaterno);
                    ps.setString(3, apellidoMaterno);
                r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0 && r1 != 0) {
                    try{
                    // Finalizar transacción
                        sqlC = "COMMIT";
                        ps = cn.prepareStatement(sqlC);
                        rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                    verificador = true;
                }
                else {
                        try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else {
                    try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    verificador = false;
            }
        
        
        return verificador;
    }
    
    
    
    public ArrayList<Paciente> calcularEdad(int idPaciente) throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        Paciente paciente = new Paciente();
        String sql = null;
        if(cn == null) {
                conectar();
        }
        sql = "SELECT (YEAR(CURDATE())-YEAR(fhn_pte)) - (RIGHT(CURDATE(),5)<RIGHT(fhn_pte,5)) AS edad FROM mPaciente WHERE idL_pte=?";
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        rs = ps.executeQuery();
        while(rs.next()) {
            paciente.setEdad(rs.getInt(1));
            pacientes.add(paciente);
        }
        
        return pacientes;
    }
    
    
    public ArrayList<Paciente> calcularEdad(String fecha) throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList<>();
        Paciente paciente = new Paciente();
        int r1 = 0;
        String sql = null;
        if(cn == null) {
                conectar();
        }
        sql = "SELECT (YEAR(CURDATE())-YEAR('"+fecha+"')) - (RIGHT(CURDATE(),5)<RIGHT('"+fecha+"',5))";
        st = cn.createStatement();
        rs = st.executeQuery(sql);
        while(rs.next()) {
            paciente.setEdad(rs.getInt(1));
            pacientes.add(paciente);
        }
        
        return pacientes;
    }
    
    
    public String obtenerTipoExpediente(ArrayList<Paciente> pacientes) {
        String tipoExpediente = null;
        int edad = 0;
            for(int i=0 ; i<pacientes.size() ; i++) {
                edad = pacientes.get(i).getEdad();
                    if(edad >= 0 && edad < 15) {
                        tipoExpediente = "pediatrico";
                    } else if(edad >= 15 && edad < 40) {
                        tipoExpediente = "adulto";
                    } else if(edad >= 40) {
                        tipoExpediente = "geriatrico";
                    } else {
                        tipoExpediente = "";
                    }
            }
        
        return tipoExpediente;
    }
    
    
    
    public ArrayList<AntecedentePediatrico> buscarPacientePediatricoID(int idPaciente) throws SQLException {
        ArrayList<AntecedentePediatrico> expedientesPediatricos = new ArrayList<>();
        AntecedentePediatrico pacientePediatrico = null;
         if(cn == null) {
            conectar();
        }
        String sql = "SELECT * FROM vExpedientesPediatricos WHERE idPaciente=?";
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        rs = ps.executeQuery(); 
        while(rs.next()){
            pacientePediatrico = new AntecedentePediatrico();
            pacientePediatrico.setIdExpediente(rs.getInt(1));
            pacientePediatrico.setFolio(rs.getString(2));
            pacientePediatrico.setIdFechasExpediente(rs.getInt(3));
            pacientePediatrico.setFechaExpedicion(rs.getString(4));
            pacientePediatrico.setFechaActualizacion(rs.getString(5));
            pacientePediatrico.setIdPaciente(rs.getInt(6));
            pacientePediatrico.setNombre(rs.getString(7));
            pacientePediatrico.setApellidoPaterno(rs.getString(8));
            pacientePediatrico.setApellidoMaterno(rs.getString(9));
            pacientePediatrico.setOcupacion(rs.getString(10));
            pacientePediatrico.setFechaNacimeinto(rs.getString(11));
            pacientePediatrico.setEdad(rs.getInt(12));
            pacientePediatrico.setIdSexo(rs.getInt(13));
            pacientePediatrico.setIdContacto(rs.getInt(14));
            pacientePediatrico.setDomicilio(rs.getString(15));
            pacientePediatrico.setTelefono(rs.getLong(16));
            pacientePediatrico.setTelefono2(rs.getLong(17));
            pacientePediatrico.setEmail(rs.getString(18));
            pacientePediatrico.setFacebook(rs.getString(19));
            pacientePediatrico.setIdAntecedenteMedico(rs.getInt(20));
            pacientePediatrico.setEnfermedad(rs.getString(21));
            pacientePediatrico.setMedicamento(rs.getString(22));
            pacientePediatrico.setAlergia(rs.getString(23));
            pacientePediatrico.setIdAntecedentePediatrico(rs.getInt(24));
            pacientePediatrico.setTiempoGestacion(rs.getString(25));
            pacientePediatrico.setComplicacion(rs.getString(26));
            pacientePediatrico.setIncubadora(rs.getString(27));
            pacientePediatrico.setIdTipoParto(rs.getInt(28));
            expedientesPediatricos.add(pacientePediatrico);
        }
        
        return expedientesPediatricos;
    }
    
    
    
    public ArrayList<AntecedenteAG> buscarPacienteAG_ID(int idPaciente) throws SQLException {
        ArrayList<AntecedenteAG> expedientesAG = new ArrayList<>();
        AntecedenteAG pacienteAG = null;
         if(cn == null) {
            conectar();
        }
        String sql = "SELECT * FROM vExpedientesAG WHERE idPaciente=?";
        ps = cn.prepareStatement(sql);
        ps.setInt(1, idPaciente);
        rs = ps.executeQuery(); 
        while(rs.next()){
            pacienteAG = new AntecedenteAG();
            pacienteAG.setIdExpediente(rs.getInt(1));
            pacienteAG.setFolio(rs.getString(2));
            pacienteAG.setIdFechasExpediente(rs.getInt(3));
            pacienteAG.setFechaExpedicion(rs.getString(4));
            pacienteAG.setFechaActualizacion(rs.getString(5));
            pacienteAG.setIdPaciente(rs.getInt(6));
            pacienteAG.setNombre(rs.getString(7));
            pacienteAG.setApellidoPaterno(rs.getString(8));
            pacienteAG.setApellidoMaterno(rs.getString(9));
            pacienteAG.setOcupacion(rs.getString(10));
            pacienteAG.setFechaNacimeinto(rs.getString(11));
            pacienteAG.setEdad(rs.getInt(12));
            pacienteAG.setIdSexo(rs.getInt(13));
            pacienteAG.setIdContacto(rs.getInt(14));
            pacienteAG.setDomicilio(rs.getString(15));
            pacienteAG.setTelefono(rs.getLong(16));
            pacienteAG.setTelefono2(rs.getLong(17));
            pacienteAG.setEmail(rs.getString(18));
            pacienteAG.setFacebook(rs.getString(19));
            pacienteAG.setIdAntecedenteMedico(rs.getInt(20));
            pacienteAG.setEnfermedad(rs.getString(21));
            pacienteAG.setMedicamento(rs.getString(22));
            pacienteAG.setAlergia(rs.getString(23));
            pacienteAG.setIdAntecedenteAG(rs.getInt(24));
            pacienteAG.setTa(rs.getString(25));
            pacienteAG.setNg(rs.getString(26));
            expedientesAG.add(pacienteAG);
        }
        
        return expedientesAG;
    }
    
    
    public boolean actualizarCumpleanero(int idAntecedenteMed, int idFechaExpediente) {
            if(cn == null) {
                conectar();
            }
            int r1 = 0, r2=0, rST=0, rR, rC;
            String sql1 = null, sql2=null, sqlST, sqlR, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "insert into dAntecedentesMedAG (taa_aag, ngg_aag, idH_aag) values ('Sin especificar', 'Sin especificar', ?)";
                ps = cn.prepareStatement(sql1);
                ps.setInt(1, idAntecedenteMed);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0) {
                    try{
                        sql2 = "UPDATE dFechaExpediente SET fac_fxp=now()  WHERE idF_fxp=?";
                            ps = cn.prepareStatement(sql2);
                            ps.setInt(1, idFechaExpediente);
                        r2 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                    
                    if(r2 != 0){
                            try{
                                // Finalizar transacción
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                            verificador = true;
                    }
                    else {
                        try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    // Finalizar transacción
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                            verificador = false;
                    }
                        
            }
            else {
                try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            // Finalizar transacción
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                    verificador = false;
            }
        
        
        return verificador;
    }
    
    
    public boolean registrarPaciente(String nombre, String apellidoPaterno, String apellidoMaterno, String ocupacion,
                            int sexo, String fechaNacimiento, String domicilio, Long telefono,
                                                                                Long telefono2, String email, String facebook) {
            if(cn == null) {
                conectar();
            }
            int r1 = 0, r2 = 0, rST=0, rR, rC;
            String sql1 = null, sql2 = null, sqlST, sqlR, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "insert into dContacto (dom_con, tel_con, tel_con_2, mal_con, fbk_con) values (?, ?, ?, ?, ?)";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, domicilio);
                ps.setLong(2, telefono);
                ps.setLong(3, telefono2);
                ps.setString(4, email);
                ps.setString(5, facebook);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
        
            // Si se registró correctamente la tabla contacto...
            if(r1 != 0){
                try{
                    // Obtener el ID que se registró del registro contacto
                    String sqlLastC = "SELECT @lastC := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                    }catch(SQLException ex){ex.printStackTrace();}
                    // Insertar nuevo registro en la tabla mPaciente
                try{
                    sql2 = "INSERT INTO mPaciente (idO_pte, nom_pte, app_pte, apm_pte, fhn_pte, ocp_pte, idC_pte) VALUES (?, ?, ?, ?, ?, ?, @lastC)";
                        ps = cn.prepareStatement(sql2);
                        ps.setInt(1, sexo);
                        ps.setString(2, nombre);
                        ps.setString(3, apellidoPaterno);
                        ps.setString(4, apellidoMaterno);
                        ps.setString(5, fechaNacimiento);
                        ps.setString(6, ocupacion);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                // Si el registro de la tabla mCliente se llevó a cabo con éxito...
                if(r2 != 0){
                        try{
                            // Finalizar transacción
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                        verificador = true;
                }
                // De lo contrario..., si el registro mCliente no se realizó...
                else{
                    // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            // Finalizar transacción
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                    verificador = false;
                }
            }
            // De lo contrario..., si no se registró el registro de contacto, regresarr al estado previo
            else{
                try{
                    sqlR = "ROLLBACK";
                    ps = cn.prepareStatement(sqlR);
                    rR= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                try{
                    // Finalizar transacción
                    sqlC = "COMMIT";
                    ps = cn.prepareStatement(sqlC);
                    rC= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                    verificador = false;
            }
        
        return verificador;
    }
    
    
    
    public boolean registrarExpedientePediatrico(int idPaciente, String enfermedad, String medicamento, String alergia,
                                                                String tiempoGestacion, String complicacion, String incubadora, int tipoParto) {
            if(cn == null) {
                conectar();
            }
            int r1 = 0, r2 = 0, r3=0, r4=0, rST=0, rR=0, rC=0;
            String sql1=null, sql2=null, sql3=null, sqlST=null, sqlR=null, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "INSERT INTO dFechaExpediente (fex_fxp, fac_fxp) VALUES (now(), now())";
                ps = cn.prepareStatement(sql1);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
        
            // Si se registró correctamente la tabla contacto...
            if(r1 != 0){
                try{
                    // Obtener el ID que se registró del registro contacto
                    String sqlLastC = "SELECT @lastFE := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                    }catch(SQLException ex){ex.printStackTrace();}
                    // Insertar nuevo registro en la tabla mCliente
                try{
                    Expediente e = new Expediente();
                    String folio = e.obtenerFolio();
                    sql2 = "INSERT INTO dExpediente (fol_exp, idL_exp, idF_exp) VALUES (?, ?, @lastFE)";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, folio);
                    ps.setInt(2, idPaciente);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                // Si el registro de la tabla mCliente se llevó a cabo con éxito...
                if(r2 != 0){
                    try{
                        // Obtener el ID que se registró del registro contacto
                        String sqlLastC = "SELECT @lastE := LAST_INSERT_ID()";
                            st = cn.createStatement();
                            st.executeQuery(sqlLastC);
                        }catch(SQLException ex){ex.printStackTrace();}
                        try{
                            sql3 = "INSERT INTO dAntecedenteMed (idL_atm, enf_atm, med_atm, alg_atm, idN_atm, idF_atm) VALUES (?, ?, ?, ?, @lastE, @lastFE)";
                            ps = cn.prepareStatement(sql3);
                            ps.setInt(1, idPaciente);
                            ps.setString(2, enfermedad);
                            ps.setString(3, medicamento);
                            ps.setString(4, alergia);
                            r3 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                        
                        if(r3 != 0) {
                            try{
                                // Obtener el ID que se registró del registro contacto
                                String sqlLastC = "SELECT @lastAM := LAST_INSERT_ID()";
                                    st = cn.createStatement();
                                    st.executeQuery(sqlLastC);
                            }catch(SQLException ex){ex.printStackTrace();}
                            try{
                                String sql4 = "INSERT INTO dAntecedenteMedPed (tmg_atp, cpl_atp, inc_atp, idT_atp, idH_atp) VALUES (?, ?, ?, ?, @lastAM)";
                                ps = cn.prepareStatement(sql4);
                                ps.setString(1, tiempoGestacion);
                                ps.setString(2, complicacion);
                                ps.setString(3, incubadora);
                                ps.setInt(4, tipoParto);
                                r4 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                            
                            if(r4 != 0) {
                                try{
                                    // Finalizar transacción
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                                verificador = true;
                            }   
                                // De lo contrario..., si el registro mCliente no se realizó...
                                else{
                                    // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            // Finalizar transacción
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                                    verificador = false;
                                }
                            
                        }
                        // De lo contrario..., si el registro mCliente no se realizó...
                        else{
                            // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    // Finalizar transacción
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                            verificador = false;
                        }
                }
                    // De lo contrario..., si el registro mCliente no se realizó...
                    else{
                        // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                // Finalizar transacción
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                        verificador = false;
                    }
            }
            // De lo contrario..., si no se registró el registro de contacto, regresarr al estado previo
            else{
                try{
                    sqlR = "ROLLBACK";
                    ps = cn.prepareStatement(sqlR);
                    rR= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                try{
                    // Finalizar transacción
                    sqlC = "COMMIT";
                    ps = cn.prepareStatement(sqlC);
                    rC= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                    verificador = false;
            }
        
        return verificador;
    }
    
    
    
    public boolean registrarExpedienteAG(int idPaciente, String enfermedad, String medicamento, String alergia,
                                                                                                String ta, String ng) {
            if(cn == null) {
                conectar();
            }
            int r1 = 0, r2 = 0, r3=0, r4=0, rST=0, rR=0, rC=0;
            String sql1=null, sql2=null, sql3=null, sqlST=null, sqlR=null, sqlC=null;
            
            try{
                sqlST = "START TRANSACTION";
                ps = cn.prepareStatement(sqlST);
                rST= ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
            
            try{
            sql1 = "INSERT INTO dFechaExpediente (fex_fxp, fac_fxp) VALUES (now(), now())";
                ps = cn.prepareStatement(sql1);
            r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
        
            // Si se registró correctamente la tabla contacto...
            if(r1 != 0){
                try{
                    // Obtener el ID que se registró del registro contacto
                    String sqlLastC = "SELECT @lastFE := LAST_INSERT_ID()";
                        st = cn.createStatement();
                        st.executeQuery(sqlLastC);
                    }catch(SQLException ex){ex.printStackTrace();}
                    // Insertar nuevo registro en la tabla mCliente
                try{
                    Expediente e = new Expediente();
                    String folio = e.obtenerFolio();
                    sql2 = "INSERT INTO dExpediente (fol_exp, idL_exp, idF_exp) VALUES (?, ?, @lastFE)";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, folio);
                    ps.setInt(2, idPaciente);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                // Si el registro de la tabla mCliente se llevó a cabo con éxito...
                if(r2 != 0){
                    try{
                        // Obtener el ID que se registró del registro contacto
                        String sqlLastC = "SELECT @lastE := LAST_INSERT_ID()";
                            st = cn.createStatement();
                            st.executeQuery(sqlLastC);
                        }catch(SQLException ex){ex.printStackTrace();}
                        try{
                            sql3 = "INSERT INTO dAntecedenteMed (idL_atm, enf_atm, med_atm, alg_atm, idN_atm, idF_atm) VALUES (?, ?, ?, ?, @lastE, @lastFE)";
                            ps = cn.prepareStatement(sql3);
                            ps.setInt(1, idPaciente);
                            ps.setString(2, enfermedad);
                            ps.setString(3, medicamento);
                            ps.setString(4, alergia);
                            r3 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                        
                        if(r3 != 0) {
                            try{
                                // Obtener el ID que se registró del registro contacto
                                String sqlLastC = "SELECT @lastAM := LAST_INSERT_ID()";
                                    st = cn.createStatement();
                                    st.executeQuery(sqlLastC);
                            }catch(SQLException ex){ex.printStackTrace();}
                            try{
                                String sql4 = "INSERT INTO dAntecedentesMedAG (taa_aag, ngg_aag, idH_aag) VALUES (?, ?, @lastAM)";
                                ps = cn.prepareStatement(sql4);
                                ps.setString(1, ta);
                                ps.setString(2, ng);
                                r4 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                            
                            if(r4 != 0) {
                                try{
                                    // Finalizar transacción
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                // Asignarle el valor TRUE al verificador para indicar que el registro se llevó acabo satisfactoriamente
                                verificador = true;
                            }   
                                // De lo contrario..., si el registro mCliente no se realizó...
                                else{
                                    // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            // Finalizar transacción
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                                    verificador = false;
                                }
                            
                        }
                        // De lo contrario..., si el registro mCliente no se realizó...
                        else{
                            // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    // Finalizar transacción
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                            verificador = false;
                        }
                }
                    // De lo contrario..., si el registro mCliente no se realizó...
                    else{
                        // Borrar el registro de dContacto que se almacenó previamente, para volver al estado anterior a la operación
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                // Finalizar transacción
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                        verificador = false;
                    }
            }
            // De lo contrario..., si no se registró el registro de contacto, regresarr al estado previo
            else{
                try{
                    sqlR = "ROLLBACK";
                    ps = cn.prepareStatement(sqlR);
                    rR= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                try{
                    // Finalizar transacción
                    sqlC = "COMMIT";
                    ps = cn.prepareStatement(sqlC);
                    rC= ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                // Le asignamos el valor FALSE  al verificador para indicarle que el registro no se pudo llevar a cabo
                    verificador = false;
            }
        
        return verificador;
    }
    
    
    
    public boolean actualizarPacientePediatrico(int idPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMaterno,
            String ocupacion, String fechaNacimiento, int idContacto, String domicilio, Long telefono1, Long telefono2, String email, String facebook,
                                        int idSexo, int idFechaExpediente, int idAntecedenteMed, String enfermedad, String medicamento, String alergia,
                                                                     int idAntecedentePediatrico, String tiempoGestacion, String complicacion, String incubadora, int idTipoParto) {
        
        if(cn == null) {
            conectar();
        }
        int r1 = 0, r2=0, r3=0, r4=0, r5=0, rST=0, rR, rC;
        String sql1 = null, sql2=null, sql3=null, sql4=null, sql5=null, sqlST, sqlR, sqlC=null;
        
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        try{
            sql1 = "UPDATE dContacto SET dom_con=? , tel_con=? , tel_con_2=? , mal_con=? , fbk_con=? WHERE idC_con=?";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, domicilio);
                ps.setLong(2, telefono1);
                ps.setLong(3, telefono2);
                ps.setString(4, email);
                ps.setString(5, facebook);
                ps.setInt(6, idContacto);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
        
        if(r1 != 0) {
            try{
                sql2 = "UPDATE mPaciente SET nom_pte=? , app_pte=? , apm_pte=? , ocp_pte=? , fhn_pte=? , idO_pte=?  WHERE idL_pte=?";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, nombrePaciente);
                    ps.setString(2, apellidoPaterno);
                    ps.setString(3, apellidoMaterno);
                    ps.setString(4, ocupacion);
                    ps.setString(5, fechaNacimiento);
                    ps.setInt(6, idSexo);
                    ps.setInt(7, idPaciente);
                r2 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
            
            if(r2 != 0) {
                try{
                    sql3 = "UPDATE dFechaExpediente SET fac_fxp=now()  WHERE idF_fxp=?";
                        ps = cn.prepareStatement(sql3);
                        ps.setInt(1, idFechaExpediente);
                    r3 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                
                if(r3 != 0) {
                    try{
                        sql4 = "UPDATE dAntecedenteMed SET enf_atm=? , med_atm=? , alg_atm=?   WHERE idH_atm=?";
                            ps = cn.prepareStatement(sql4);
                            ps.setString(1, enfermedad);
                            ps.setString(2, medicamento);
                            ps.setString(3, alergia);
                            ps.setInt(4, idAntecedenteMed);
                        r4 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                    
                    if(r4 != 0) {
                        try{
                            sql5 = "UPDATE dAntecedenteMedPed SET tmg_atp=? , cpl_atp=? , inc_atp=? , idT_atp=?   WHERE idK_atp=?";
                                ps = cn.prepareStatement(sql5);
                                ps.setString(1, tiempoGestacion);
                                ps.setString(2, complicacion);
                                ps.setString(3, incubadora);
                                ps.setInt(4, idTipoParto);
                                ps.setInt(5, idAntecedentePediatrico);
                            r5 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                        
                        if(r5 != 0) {
                            // Se actualizaron todos los datos correctamente
                            try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = true;
                        }
                        else { // No se pudo actualizar dAntecedenteMedPed
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = false;
                        }
                    }
                    else { // No se pudo actualizar dAntecedenteMed
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                try{
                                        sqlC = "COMMIT";
                                        ps = cn.prepareStatement(sqlC);
                                        rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                verificador = false;
                    }
                }
                else { // No se pudo actualizar dFechaExpediente
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = false;
                }
            }
            else { // No se pudo actualizar mPaciente
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
            }
        }
        else { // No se pudo actualizar dContacto
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = false;
        }
        
        
        return verificador;
    }
    
    
    
    public boolean actualizarPacienteAG(int idPaciente, String nombrePaciente, String apellidoPaterno, String apellidoMaterno,
            String ocupacion, String fechaNacimiento, int idContacto, String domicilio, Long telefono1, Long telefono2, String email, String facebook,
                                        int idSexo, int idFechaExpediente, int idAntecedenteMed, String enfermedad, String medicamento, String alergia,
                                                                                                                 int idAntecedenteAG, String ta, String ng) {
        
        if(cn == null) {
            conectar();
        }
        int r1 = 0, r2=0, r3=0, r4=0, r5=0, rST=0, rR, rC;
        String sql1 = null, sql2=null, sql3=null, sql4=null, sql5=null, sqlST, sqlR, sqlC=null;
        
        try{
            sqlST = "START TRANSACTION";
            ps = cn.prepareStatement(sqlST);
            rST= ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sqlST");ex.printStackTrace();}
        
        try{
            sql1 = "UPDATE dContacto SET dom_con=? , tel_con=? , tel_con_2=? , mal_con=? , fbk_con=? WHERE idC_con=?";
                ps = cn.prepareStatement(sql1);
                ps.setString(1, domicilio);
                ps.setLong(2, telefono1);
                ps.setLong(3, telefono2);
                ps.setString(4, email);
                ps.setString(5, facebook);
                ps.setInt(6, idContacto);
            r1 = ps.executeUpdate();
        }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
        
        if(r1 != 0) {
            try{
                sql2 = "UPDATE mPaciente SET nom_pte=? , app_pte=? , apm_pte=? , ocp_pte=? , fhn_pte=? , idO_pte=?  WHERE idL_pte=?";
                    ps = cn.prepareStatement(sql2);
                    ps.setString(1, nombrePaciente);
                    ps.setString(2, apellidoPaterno);
                    ps.setString(3, apellidoMaterno);
                    ps.setString(4, ocupacion);
                    ps.setString(5, fechaNacimiento);
                    ps.setInt(6, idSexo);
                    ps.setInt(7, idPaciente);
                r2 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
            
            if(r2 != 0) {
                try{
                    sql3 = "UPDATE dFechaExpediente SET fac_fxp=now()  WHERE idF_fxp=?";
                        ps = cn.prepareStatement(sql3);
                        ps.setInt(1, idFechaExpediente);
                    r3 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                
                if(r3 != 0) {
                    try{
                        sql4 = "UPDATE dAntecedenteMed SET enf_atm=? , med_atm=? , alg_atm=?   WHERE idH_atm=?";
                            ps = cn.prepareStatement(sql4);
                            ps.setString(1, enfermedad);
                            ps.setString(2, medicamento);
                            ps.setString(3, alergia);
                            ps.setInt(4, idAntecedenteMed);
                        r4 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                    
                    if(r4 != 0) {
                        try{
                            sql5 = "UPDATE dAntecedentesMedAG SET taa_aag=? , ngg_aag=?   WHERE idQ_aag=?";
                                ps = cn.prepareStatement(sql5);
                                ps.setString(1, ta);
                                ps.setString(2, ng);
                                ps.setInt(3, idAntecedenteAG);
                            r5 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                        
                        if(r5 != 0) {
                            // Se actualizaron todos los datos correctamente
                            try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = true;
                        }
                        else { // No se pudo actualizar dAntecedenteMedPed
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = false;
                        }
                    }
                    else { // No se pudo actualizar dAntecedenteMed
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                try{
                                        sqlC = "COMMIT";
                                        ps = cn.prepareStatement(sqlC);
                                        rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                verificador = false;
                    }
                }
                else { // No se pudo actualizar dFechaExpediente
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = false;
                }
            }
            else { // No se pudo actualizar mPaciente
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
            }
        }
        else { // No se pudo actualizar dContacto
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = false;
        }
        
        
        return verificador;
    }
    
    
    
    public boolean bajaPacientePediatrico(int idAntecedentePed, int idAntecedenteMed, int idExpediente, int idFechaExpediente,
                                                                                                    int idPaciente, int idContacto) {
        
        int r1 = 0, r2=0, r3=0, r4=0, r5=0, r6=0, rST=0, rR, rC;
        String sql1 = null, sql2=null, sql3=null, sql4=null, sql5=null, sql6=null, sqlST, sqlR, sqlC=null;
        if(cn == null) {
            conectar();
        }
        
            try{
                sql1 = "DELETE FROM dAntecedenteMedPed WHERE idK_atp=?";
                    ps = cn.prepareStatement(sql1);
                    ps.setInt(1, idAntecedentePed);
                r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0){
                try{
                    sql2 = "DELETE FROM dAntecedenteMed WHERE idH_atm=?";
                        ps = cn.prepareStatement(sql2);
                        ps.setInt(1, idAntecedenteMed);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0){
                    try{
                        sql3 = "DELETE FROM dExpediente WHERE idN_exp=?";
                            ps = cn.prepareStatement(sql3);
                            ps.setInt(1, idExpediente);
                        r3 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                    
                    if(r3 != 0){
                        try{
                            sql4 = "DELETE FROM dFechaExpediente WHERE idF_fxp=?";
                                ps = cn.prepareStatement(sql4);
                                ps.setInt(1, idFechaExpediente);
                            r4 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                        
                        if(r4 != 0){
                            try{
                                sql5 = "DELETE FROM mPaciente WHERE idL_pte=?";
                                    ps = cn.prepareStatement(sql5);
                                    ps.setInt(1, idPaciente);
                                r5 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                            
                            if(r5 != 0){
                                try{
                                    sql6 = "DELETE FROM dContacto WHERE idC_con=?";
                                        ps = cn.prepareStatement(sql6);
                                        ps.setInt(1, idContacto);
                                    r6 = ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sql6");ex.printStackTrace();}
                                
                                if(r6 != 0){
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = true;
                                }
                                else { // No se pudo eliminar el registro dContacto
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = false;
                                }
                            }
                            else { // No se pudo eliminar el registro mPaciente
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                try{
                                        sqlC = "COMMIT";
                                        ps = cn.prepareStatement(sqlC);
                                        rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                verificador = false;
                            }
                        }
                        else { // No se pudo eliminar el registro dFechaExpediente
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = false;
                        }
                    }
                    else { // No se pudo eliminar el registro dExpediente
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                    }
                }
                else { // No se pudo eliminar el registro dAntecedenteMed
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else { // No se pudo eliminar el registro dAntecedenteMedPed
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = false;
            }
            
        return verificador;
    }
    
    
    /*
    * Dar de baja a pacientes adultos o geriátricos que núnca fueron registrados como pediátricos
    */
    public boolean bajaPacienteAGnormal(int idAntecedenteAG, int idAntecedenteMed, int idExpediente, int idFechaExpediente,
                                                                                                           int idPaciente, int idContacto) {
        
        int r1 = 0, r2=0, r3=0, r4=0, r5=0, r6=0, rST=0, rR, rC;
        String sql1 = null, sql2=null, sql3=null, sql4=null, sql5=null, sql6=null, sqlST, sqlR, sqlC=null;
        if(cn == null) {
            conectar();
        }
        
            try{
                sql1 = "DELETE FROM dAntecedentesMedAG WHERE idQ_aag=?";
                    ps = cn.prepareStatement(sql1);
                    ps.setInt(1, idAntecedenteAG);
                r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0){
                try{
                    sql2 = "DELETE FROM dAntecedenteMed WHERE idH_atm=?";
                        ps = cn.prepareStatement(sql2);
                        ps.setInt(1, idAntecedenteMed);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0){
                    try{
                        sql3 = "DELETE FROM dExpediente WHERE idN_exp=?";
                            ps = cn.prepareStatement(sql3);
                            ps.setInt(1, idExpediente);
                        r3 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                    
                    if(r3 != 0){
                        try{
                            sql4 = "DELETE FROM dFechaExpediente WHERE idF_fxp=?";
                                ps = cn.prepareStatement(sql4);
                                ps.setInt(1, idFechaExpediente);
                            r4 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                        
                        if(r4 != 0){
                            try{
                                sql5 = "DELETE FROM mPaciente WHERE idL_pte=?";
                                    ps = cn.prepareStatement(sql5);
                                    ps.setInt(1, idPaciente);
                                r5 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                            
                            if(r5 != 0){
                                try{
                                    sql6 = "DELETE FROM dContacto WHERE idC_con=?";
                                        ps = cn.prepareStatement(sql6);
                                        ps.setInt(1, idContacto);
                                    r6 = ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sql6");ex.printStackTrace();}
                                
                                if(r6 != 0){
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = true;
                                }
                                else { // No se pudo eliminar el registro dContacto
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = false;
                                }
                            }
                            else { // No se pudo eliminar el registro mPaciente
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                try{
                                        sqlC = "COMMIT";
                                        ps = cn.prepareStatement(sqlC);
                                        rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                verificador = false;
                            }
                        }
                        else { // No se pudo eliminar el registro dFechaExpediente
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = false;
                        }
                    }
                    else { // No se pudo eliminar el registro dExpediente
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                    }
                }
                else { // No se pudo eliminar el registro dAntecedenteMed
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else { // No se pudo eliminar el registro dAntecedenteMedPed
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = false;
            }
            
        return verificador;
    }
    
    
    
    /*
    * Dar de baja a pacientes adultos o geriátricos que alguna vez se registró como pediátrico y sus datos siguen ahí
    */
    public boolean bajaPacienteAGconPediatrico(int idAntecedenteAG, int idAntecedentePed, int idAntecedenteMed, int idExpediente, int idFechaExpediente,
                                                                                                                                int idPaciente, int idContacto) {
        
        int r1 = 0, r2=0, r3=0, r4=0, r5=0, r6=0, r7=0, rST=0, rR, rC;
        String sql1 = null, sql2=null, sql3=null, sql4=null, sql5=null, sql6=null, sql7=null, sqlST, sqlR, sqlC=null;
        if(cn == null) {
            conectar();
        }
        
            try{
                sql1 = "DELETE FROM dAntecedentesMedAG WHERE idQ_aag=?";
                    ps = cn.prepareStatement(sql1);
                    ps.setInt(1, idAntecedenteAG);
                r1 = ps.executeUpdate();
            }catch(SQLException ex){System.out.println("sql1");ex.printStackTrace();}
            
            if(r1 != 0){
                try{
                    sql2 = "DELETE FROM dAntecedenteMedPed WHERE idK_atp=?";
                        ps = cn.prepareStatement(sql2);
                        ps.setInt(1, idAntecedentePed);
                    r2 = ps.executeUpdate();
                }catch(SQLException ex){System.out.println("sql2");ex.printStackTrace();}
                
                if(r2 != 0){
                    try{
                        sql3 = "DELETE FROM dAntecedenteMed WHERE idH_atm=?";
                            ps = cn.prepareStatement(sql3);
                            ps.setInt(1, idAntecedenteMed);
                        r3 = ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sql3");ex.printStackTrace();}
                    
                    if(r3 != 0){
                        try{
                            sql4 = "DELETE FROM dExpediente WHERE idN_exp=?";
                                ps = cn.prepareStatement(sql4);
                                ps.setInt(1, idExpediente);
                            r4 = ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sql4");ex.printStackTrace();}
                        
                        if(r4 != 0){
                            try{
                                sql5 = "DELETE FROM dFechaExpediente WHERE idF_fxp=?";
                                    ps = cn.prepareStatement(sql5);
                                    ps.setInt(1, idFechaExpediente);
                                r5 = ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sql5");ex.printStackTrace();}
                            
                            if(r5 != 0){
                                try{
                                    sql6 = "DELETE FROM mPaciente WHERE idL_pte=?";
                                        ps = cn.prepareStatement(sql6);
                                        ps.setInt(1, idPaciente);
                                    r6 = ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sql6");ex.printStackTrace();}
                                
                                if(r6 != 0){
                                    try{
                                        sql7 = "DELETE FROM dContacto WHERE idC_con=?";
                                            ps = cn.prepareStatement(sql7);
                                            ps.setInt(1, idContacto);
                                        r7 = ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sql7");ex.printStackTrace();}
                                    
                                    if(r7 != 0){
                                        try{
                                                sqlC = "COMMIT";
                                                ps = cn.prepareStatement(sqlC);
                                                rC= ps.executeUpdate();
                                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                        verificador = true;
                                        
                                    }
                                    else { // No se pudo eliminar el registro dContacto
                                        try{
                                            sqlR = "ROLLBACK";
                                            ps = cn.prepareStatement(sqlR);
                                            rR= ps.executeUpdate();
                                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                        try{
                                                sqlC = "COMMIT";
                                                ps = cn.prepareStatement(sqlC);
                                                rC= ps.executeUpdate();
                                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                        verificador = false;
                                    }
                                }
                                else { // No se pudo eliminar el registro dContacto
                                    try{
                                        sqlR = "ROLLBACK";
                                        ps = cn.prepareStatement(sqlR);
                                        rR= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                    try{
                                            sqlC = "COMMIT";
                                            ps = cn.prepareStatement(sqlC);
                                            rC= ps.executeUpdate();
                                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                    verificador = false;
                                }
                            }
                            else { // No se pudo eliminar el registro mPaciente
                                try{
                                    sqlR = "ROLLBACK";
                                    ps = cn.prepareStatement(sqlR);
                                    rR= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                                try{
                                        sqlC = "COMMIT";
                                        ps = cn.prepareStatement(sqlC);
                                        rC= ps.executeUpdate();
                                }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                                verificador = false;
                            }
                        }
                        else { // No se pudo eliminar el registro dFechaExpediente
                            try{
                                sqlR = "ROLLBACK";
                                ps = cn.prepareStatement(sqlR);
                                rR= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                            try{
                                    sqlC = "COMMIT";
                                    ps = cn.prepareStatement(sqlC);
                                    rC= ps.executeUpdate();
                            }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                            verificador = false;
                        }
                    }
                    else { // No se pudo eliminar el registro dExpediente
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                    }
                }
                else { // No se pudo eliminar el registro dAntecedenteMed
                        try{
                            sqlR = "ROLLBACK";
                            ps = cn.prepareStatement(sqlR);
                            rR= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                        try{
                                sqlC = "COMMIT";
                                ps = cn.prepareStatement(sqlC);
                                rC= ps.executeUpdate();
                        }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                        verificador = false;
                }
            }
            else { // No se pudo eliminar el registro dAntecedenteMedPed
                    try{
                        sqlR = "ROLLBACK";
                        ps = cn.prepareStatement(sqlR);
                        rR= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlR");ex.printStackTrace();}
                    try{
                            sqlC = "COMMIT";
                            ps = cn.prepareStatement(sqlC);
                            rC= ps.executeUpdate();
                    }catch(SQLException ex){System.out.println("sqlC");ex.printStackTrace();}
                    verificador = false;
            }
            
        return verificador;
    }
    
    
    
    public ArrayList<Paciente> buscarPaciente(String nombrePaciente) throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList();
        Paciente paciente = null;
        if(cn == null) {
            conectar();
        }
        String sql = "SELECT idL_pte, CONCAT(nom_pte, ' ', app_pte, ' ', apm_pte) FROM mPaciente WHERE CONCAT(nom_pte, ' ', app_pte, ' ', apm_pte) REGEXP ?";
         ps = cn.prepareStatement(sql);
         ps.setString(1, nombrePaciente);
         rs = ps.executeQuery(); 
         while(rs.next()){
             paciente = new Paciente();
             paciente.setIdPaciente(rs.getInt(1));
             paciente.setNombre(rs.getString(2));
             pacientes.add(paciente);
         }
        rs.close();
        ps.close();
        return pacientes;
    }
    
    
    public ArrayList<Paciente> obtenerDatosContactoPaciente() throws SQLException {
        ArrayList<Paciente> pacientes = new ArrayList();
        Paciente paciente = null;
         if(cn == null) {
            conectar();
        }
        String sql = "SELECT * FROM vDatosContactoPaciente";
         ps = cn.prepareStatement(sql);
         rs = ps.executeQuery(); 
         while(rs.next()){
             paciente = new Paciente();
             paciente.setNombre(rs.getString(1));
             paciente.setEdad(rs.getInt(2));
             paciente.setTelefono(rs.getLong(3));
             paciente.setEmail(rs.getString(4));
             paciente.setFacebook(rs.getString(5));
             pacientes.add(paciente);
         }
        rs.close();
        ps.close();
        return pacientes;
    }
    
    
    public int obtenerUltimoIDregistrado() throws SQLException{
        int id=0;
         // Si no se ha realizado la conexión con la base de datos la iniciamos
        if(cn == null) {
            conectar();
        }
     String csp = "";
     String sql = "SELECT @last := LAST_INSERT_ID() AS ultimoID";
         st = cn.createStatement();
         // Mandamos el comando al gestor
         rs = st.executeQuery(sql); 
     while(rs.next()){
             // ..., especificamente en el campo "id_prv" para asegurarnos si el usuario tiene algún privilegio
             // y "guardamos" lo que encontremos en la variable "csp"
             csp=rs.getString("ultimoID");
         }
         // Verificamos que el usuario introducido sea el administrador, ya que por ahora sólo él puede acceder
            // si es así...
         if(!csp.equals("0")){
                // El verificador tomará el valor de "true" para indicar que todos los datos son correctos y el usuario puede entrar
             id = Integer.parseInt(csp);
         }
         else{
             id = 0;
         }
     
        return id;
    }
    
    
}
