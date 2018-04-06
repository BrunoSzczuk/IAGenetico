/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iafernando;

/**
 *
 * @author bruno.szczuk
 */
import iafernando.dao.ConexaoDAO;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;

public class Relatorio {

    private HashMap parametros = new HashMap();
//    private Connection conection;
    private String relatorio;
    private String nomejob;
    private String outfilename;
    private String descricao;
    private String assunto;
    private String[] destinos = {};
    private String sql = "";
    private String filial;
    private int nrseqenvio;
    private boolean objectList = false;
    Collection<?> listaDeObjetos = new ArrayList<Object>();

    public Relatorio() {
        assunto = "";
    }

    public String getOutfilename() {
        return outfilename;
    }

    public void setOutfilename(String outfilename) {
        this.outfilename = outfilename.replace('\\', '/').replaceAll("/", "//");
    }

    public HashMap getParametros() {
        return parametros;
    }

    public void setParametros(HashMap parametros) {
        if (parametros == null) {
            this.parametros = new HashMap();
        } else {
            this.parametros = parametros;
        }
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public void emitirRelatorio() {
        try {
            /*
             * if (conection == null) { setConection(); }
             */
            if (parametros == null) {
                setParametros(null);
            }
            byte[] arquivo = new byte[0];


            // se a SQL está no proprio relatorio, gera ele direto.
            // se a SQL for na aplicação, cria um data source
            JasperPrint print = null;
            print = JasperFillManager.fillReport(relatorio, parametros, ConexaoDAO.getConnect());

            arquivo = JasperExportManager.exportReportToPdf(print);

            outfilename = "./spool/" + outfilename;
            FileOutputStream outPut = new FileOutputStream(outfilename + ".pdf");
            outPut.write(arquivo);
            outPut.flush();
            outPut.close();

            // se o tamanho do arquivo é 1KB, pulo o processo de envio pois o arquivo está vazio
            File f = new File(outfilename + ".pdf");
           

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage() + " " +  nomejob);
        }
    }

    /**
     * @return the nomejob
     */
    public String getNomejob() {
        return nomejob;
    }

    /**
     * @param nomejob the nomejob to set
     */
    public void setNomejob(String nomejob) {
        this.nomejob = nomejob;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the destinos
     */
    public String[] getDestinos() {
        return destinos;
    }

    /**
     * @param destinos the destinos to set
     */
    public void setDestinos(String[] destinos) {
        this.destinos = destinos;
    }

    /**
     * @return the assunto
     */
    public String getAssunto() {
        return assunto;
    }

    /**
     * @param assunto the assunto to set
     */
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }


    /**
     * @return the filial
     */
    public String getFilial() {
        return filial;
    }

    /**
     * @param filial the filial to set
     */
    public void setFilial(String filial) {
        this.filial = filial;
    }

    /**
     * @return the nrseqenvio
     */
    public int getNrseqenvio() {
        return nrseqenvio;
    }

    /**
     * @param nrseqenvio the nrseqenvio to set
     */
    public void setNrseqenvio(int nrseqenvio) {
        this.nrseqenvio = nrseqenvio;
    }

    public void setObjectList(boolean objectList) {
        this.objectList = objectList;
    }

    public void setListaDeObjetos(Collection<?> listaDeObjetos) {
        this.listaDeObjetos = listaDeObjetos;
    }

    public boolean isObjectList() {
        return objectList;
    }

    public Collection<?> getListaDeObjetos() {
        return listaDeObjetos;
    }
    
    
}
