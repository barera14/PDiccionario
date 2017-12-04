/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ANivel;
import Modelos.HibernateUtil;
import Modelos.Niveles;
import Modelos.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Alejandro Bernal
 */
public class ANivelesController extends HttpServlet {
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("crear")) {

            registrar(request, response);

        } else if (action.equalsIgnoreCase("AdminAN")) {
            administrar(request, response);
        } else if (action.equalsIgnoreCase("updateAN")) {
            actualizar(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            Eliminar(request, response);

        }
    }

    private void actualizar(HttpServletRequest request, HttpServletResponse response) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        ANivel AN = (ANivel) sesion.get(ANivel.class, Integer.parseInt(request.getParameter("id")));

        if (request.getMethod().equalsIgnoreCase("GET")) {
            request.setAttribute("ANiveles", AN);
            try {
                request.getRequestDispatcher("updateAN.jsp").forward(request, response);
            } catch (ServletException ex) {
                Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
               
                int Niveles = Integer.parseInt(request.getParameter("niveles"));
                         Niveles Ni = (Niveles) sesion.get(Niveles.class, Integer.parseInt(request.getParameter("niveles"))); 
                
                int Persona = Integer.parseInt(request.getParameter("persona"));
                         Persona persona = (Persona) sesion.get(Persona.class, Integer.parseInt(request.getParameter("persona")));
            
            
          
                         Date Fecha = new Date();
                         
         
            AN.setFecha(Fecha);
            AN.setPersona(persona);
            AN.setNiveles(Ni);
            sesion.beginTransaction();
            sesion.saveOrUpdate(AN);
            sesion.getTransaction().commit();
            try {
                response.sendRedirect("ANivelesController?action=AdminAN");
            } catch (IOException ex) {
                Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sesion.close();

    }

    private void administrar(HttpServletRequest request, HttpServletResponse response) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        Query q = sesion.createQuery("FROM ANiveles");
        ArrayList ANiveles = (ArrayList) q.list();

        ArrayList<ANivel> ANI = new ArrayList<ANivel>();
        for (Object anive : ANiveles) {
            ANivel ani= (ANivel) anive;
            ANI.add(ani);
        }

        request.setAttribute("listaANiveles", ANI);

        try {
            request.getRequestDispatcher("AdminAN.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        sesion.close();
    }

    private void Eliminar(HttpServletRequest request, HttpServletResponse response) {

        Session sesion = HibernateUtil.getSessionFactory().openSession();
        ANivel nivel = (ANivel) sesion.get(ANivel.class, Integer.parseInt(request.getParameter("id")));
        sesion.beginTransaction();
        sesion.delete(nivel);
        sesion.getTransaction().commit();
        sesion.close();
        try {
            response.sendRedirect("ANivelesController?action=AdminAN");
        } catch (IOException ex) {
            Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) {
        Session sesion = HibernateUtil.getSessionFactory().openSession();
        
          int Niveles = Integer.parseInt(request.getParameter("niveles"));
                         Niveles niveles = (Niveles) sesion.get(Niveles.class, Integer.parseInt(request.getParameter("niveles"))); 
         
           int Persona = Integer.parseInt(request.getParameter("persona"));
                         Persona persona = (Persona) sesion.get(Persona.class, Integer.parseInt(request.getParameter("persona")));                   
                         

      
     
           Date fecha = new Date();
            
        ANivel ANi = new ANivel(niveles,persona,fecha);

        sesion.beginTransaction();
        sesion.save(ANi);
        sesion.getTransaction().commit();
        sesion.close();

        try {
            response.sendRedirect("ANivelesController?action=AdminAN");
        } catch (IOException ex) {
            Logger.getLogger(ANivelesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
