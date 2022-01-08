/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Crud;
import modelo.Equipos;

/**
 *
 * @author Diego_Sanchez
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {
final int NUM_LINEAS_PAGINA = 5;
 int pagina=1;
 int offset=0;
 int num_paginas=0;

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
          String op = "";
            if(request.getParameter("op")!=null){
               op=request.getParameter("op");
            }
            if (op.equals("listar")) {
                listar(request, response);
            }
            if (op.equals("borrar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                if(Crud.destroyEquipo(id)>0){
                    request.setAttribute("mensaje", "Producto con id "+id+"Borrado");
                }else{
                    request.setAttribute("mensaje", "No se ha borrado ningun producto");
                }
                listar(request, response);
            }
            if (op.equals("actualizar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Equipos miEquipo = Crud.getEquipo(id);
                request.setAttribute("operacion", "actualizardatos");
                request.setAttribute("equipo", miEquipo);
                request.getRequestDispatcher("actualizar.jsp").forward(request, response);
            }
            if (op.equals("actualizardatos")) {
                request.setAttribute("operacion", "insertar");
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String ciudad = request.getParameter("ciudad");
                int goles = Integer.parseInt(request.getParameter("goles"));
                Equipos p = new Equipos();
                p.setId(id);
                p.setNombre(nombre);
                p.setCiudad(ciudad);
                p.setGoles(goles);
                Crud.actualizaEquipo(p);
                if(Crud.actualizaEquipo(p)>0){
                    request.setAttribute("mensaje", "Equipol con id "+id+"Actualizado");
                }else{
                    request.setAttribute("mensaje", "No se ha actualizaro ningun producto");
                }
                request.setAttribute("producto", p);
                request.getRequestDispatcher("actualizar.jsp").forward(request, response);
            }
            if (op.equals("insertar")) {
                request.setAttribute("operacion", "insertarDatos");
                request.setAttribute("mensaje", "");
                request.getRequestDispatcher("actualizar.jsp").forward(request, response);
            }
            if (op.equals("insertarDatos")) {
//                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String ciudad = request.getParameter("ciudad");
                int goles = Integer.parseInt(request.getParameter("goles"));
                Equipos p = new Equipos();
                p.setNombre(nombre);
                p.setCiudad(ciudad);
                p.setGoles(goles);
                Crud.insertaEquipo(p);
//                if(Crud.actualizaProducto(p)>0){
//                    request.setAttribute("mensaje", "Producto insertado");
//                }else{
//                    request.setAttribute("mensaje", "No se ha insertado ningun producto");
//                }
               listar(request, response);
            }
    }
        protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                     
            List<Equipos> listaEquipos =Crud.getEquipos();
            /* cálculos para la paginación */
                if(request.getParameter("pagina") != null){
                    pagina = Integer.parseInt(request.getParameter("pagina"));
                    offset = ((pagina-1) * NUM_LINEAS_PAGINA);
                }
            int num_paginas = (int) Math.ceil(listaEquipos.size()/ NUM_LINEAS_PAGINA)+1;
                listaEquipos = Crud.getEquiposPaginado(offset, NUM_LINEAS_PAGINA);
            
            request.setAttribute("listado", listaEquipos);
            request.setAttribute("pagina", pagina);
            request.setAttribute("num_paginas", num_paginas);
            
            request.setAttribute("mensaje", "");
            request.getRequestDispatcher("listar.jsp").forward(request,response);
         
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
