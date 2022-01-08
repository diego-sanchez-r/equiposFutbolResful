/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Diego_Sanchez
 */
public class Crud {
    //UN PRODUCTO
     public static Equipos getEquipo(int id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT p FROM Equipos p WHERE p.id=" + id; //consulta en JPQL 
        Query q = manager.createQuery(sql,Equipos.class); //método para consultas en JPQL
        Equipos productosBD =  ( Equipos )q.getSingleResult();
        return productosBD;
        } 
    //MAS PRODUCTOS
    public static List<Equipos> getEquipos() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM equipos";
        Query q = manager.createNativeQuery(sql,Equipos.class); //método para consultas en SQL
        List<Equipos> productosBD =  q.getResultList();

        return productosBD;        
        }
     public static List<Equipos> getEquiposPaginado(int offset,int lineas_pagina) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
        EntityManager manager = factory.createEntityManager();
        String sql = "SELECT * FROM equipos";
        Query q = manager.createNativeQuery(sql,Equipos.class); //método para consultas en SQL
        q.setFirstResult(offset);
        q.setMaxResults(lineas_pagina);
        List<Equipos> productosBD =  q.getResultList();

        return productosBD;        
        }
    
        public static int destroyEquipo(int id) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
            EntityManager manager = factory.createEntityManager();
            String sql = "DELETE from Equipos p WHERE p.id = " + id;
            Query q = manager.createQuery(sql);
            manager.getTransaction().begin();
            int filasAfectadas = q.executeUpdate(); //para las consultas de modif. datos se usa el método executeUpdate
            manager.getTransaction().commit();
            return filasAfectadas;  
    }
        
        public static int actualizaEquipo(Equipos Eq) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
            EntityManager manager = factory.createEntityManager();
            String sql = "UPDATE Equipos p SET p.nombre = :nombre, p.ciudad = :ciudad, p.goles = :goles "
                    + "WHERE p.id = :id";
            Query q = manager.createQuery(sql,Equipos.class);
            q.setParameter("nombre", Eq.getNombre());
            q.setParameter("ciudad", Eq.getCiudad());
            q.setParameter("goles", Eq.getGoles());
            q.setParameter("id", Eq.getId());
            manager.getTransaction().begin();
            int filasAfectadas = q.executeUpdate();
            manager.getTransaction().commit();
            //manager.close();
            return filasAfectadas;      
    }
        
        public static void insertaEquipo(Equipos Equipo) {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("unidad");
            EntityManager manager = factory.createEntityManager();
            manager.getTransaction().begin();
//            Productos producto = new Productos();
//            producto.setNombre("Espinacas");
//            producto.setPrecio(10);
//            producto.setImagen("espinacas.jpg");
//            producto.setCategoria("complementos");
            manager.merge(Equipo);
            manager.getTransaction().commit();
        }


    


}
