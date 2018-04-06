/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iafernando.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

/**
 *
 * @author bruno.szczuk
 */
public class ConexaoDAO {

    private static ConexaoDAO instance = null;
    private EntityManager em;
    private EntityTransaction tx;
    private static Connection cnx = null;

    private static final String url = "jdbc:postgresql://localhost:5432/iagenetico";
    private static final String usuario = "pereira";
    private static final String senha = "q27pptz8";

    public ConexaoDAO() {
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("hibernate.connection.username", usuario);
            map.put("hibernate.connection.driver_class", "org.postgresql.Driver");
            map.put("hibernate.connection.password", senha);
            map.put("hibernate.connection.url", url);
            map.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

            map.put("hibernate.connection.lc_ctype", "LATIN1");
            //map.put("hibernateshow_sql", "false");
            map.put("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
            //map.put("hibernate.hbm2ddl.auto", "update");

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("IAFernandoPU", map);
            em = emf.createEntityManager();
            em.setFlushMode(FlushModeType.AUTO);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception e) {
            System.out.println("Erro" + e.getMessage());
        }
    }

    public static Connection getConnect() throws Exception {
        if (cnx == null) {
            cnx = DriverManager.getConnection(url, usuario, senha);
        }
        return cnx;
    }

    public static ConexaoDAO getInstance() {
        if (instance == null) {
            instance = new ConexaoDAO();
        }
        return instance;
    }

    public EntityManager getEm() {
        return em;
    }

    public EntityTransaction getTx() {
        return tx;
    }

    public boolean remove(Object obj) {
        try {
            em.remove(obj);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void startTransaction() {
        tx = em.getTransaction();
        if (tx.isActive() && tx.getRollbackOnly()) {
            tx.rollback();
        }
        if (!tx.isActive()) {
            tx.begin();
        }
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    public void commit() {
//        startTransaction();
        if (tx != null && tx.isActive()) {
            try {
                tx.commit();
            } catch (Exception cex) {
                try {
                    cex.printStackTrace();
                    tx.rollback();
                } catch (Exception rex) {
                    System.out.println("Erro RollBack ");
                    rex.printStackTrace();
                }
                throw new RuntimeException("Erro gravando alteraçoes no Banco de Dados.");
            }
        }
    }

    public void rollback() {
        if (tx != null && tx.isActive()) {
            try {
                tx.rollback();
            } catch (Exception rex) {
                rex.printStackTrace();
            }
        }
    }

    public Object persist(Object obj) {
        startTransaction();
        try {
            Object novo = em.merge(obj);
            obj = novo;
            commit();
        } catch (Exception ex) {
            try {
                em.persist(obj);
            } catch (Exception pex) {
                rollback();
                pex.printStackTrace();
                return null;
            }
        }
        return obj;
    }

    public static Statement getStatement() throws Exception {
        return getConnect().createStatement();
    }
}
