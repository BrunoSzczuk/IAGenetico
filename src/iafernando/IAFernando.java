package iafernando;

import iafernando.dao.ConexaoDAO;
import iafernando.dao.IAFernandoController;
import iafernando.obj.Gene;
import iafernando.obj.GenePK;
import iafernando.obj.Populacao;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;

public class IAFernando {

    public static List<Gene> casais;
    public static List<List<Gene>> populacoes;
    public static int qtdgeracoes;
    public static boolean unico;

    public static void main(String[] args) {
        int cromossomos = 7;
        String msg = JOptionPane.showInputDialog(null, "Informe a quantidade de gerações");
        if (msg == null) {
            System.exit(0);
        }
        qtdgeracoes = Integer.parseInt(msg);
        
        msg = JOptionPane.showInputDialog(null, "Quantos casais gostaria de criar?");
        if (msg == null){
            System.exit(0);
        }
        int resp = JOptionPane.showConfirmDialog(null,"Deseja fazer o cruzamento apenas com outros genes? (não será possível ser sorteado o mesmo gene para o cruzamento)");
        if (resp == 2) System.exit(0);
        unico = resp != 1;
            
        System.out.println(resp);
        int qtdcasais = Integer.parseInt(msg);
        //Gerar os cados da população que vou criar
        Populacao pop = new Populacao();
        pop.setDt_emissao(new Date());
        pop.setQtGene(qtdcasais * 2);
        pop.setQtCromossomos(cromossomos);
        
        pop.setCdPopulacao((int)ConexaoDAO.getInstance().getEm().createQuery("select coalesce(max(cdPopulacao),0)+1 as cod from Populacao").getSingleResult());
        /*try {
        //Também da pra fazer usando resultset, mas usando o EntityManager é mais resumido
                  ResultSet cd = ConexaoDAO.getStatement().executeQuery("select coalesce(max(cd_populacao),0) +1 as cod from populacao");
            cd.next();
            pop.setCdPopulacao(cd.getInt("cod"));
            cd.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        // ------------------------------------------------------------------

        //Geração da população de Genes
        casais = new ArrayList<>();
        populacoes = new ArrayList<List<Gene>>();

        //Gerando a primeira geração e criando as pks
        for (int i = 0; i < qtdcasais * 2; i++) {
            Gene g = new Gene();
            GenePK pk = new GenePK();
            pk.setPop(pop);
            pk.setNr_iteracao(0);
            pk.setCd_gene(i);
            g.setPk(pk);
            int valor = new Random().nextInt((int) (Math.pow(2,cromossomos) - 1));

            g.setVlBinario(Integer.toBinaryString(valor));
            for (int j = g.getVlBinario().length(); j < cromossomos; j++) {
                g.setVlBinario("0" + g.getVlBinario());
            }
            g.setVlGene(valor);
            casais.add(g);
        }
        populacoes.add(casais);
        // -------------------------------------------------------------------

        //Gerando as novas populaçoes de genes
        for (int i = 1; i <= qtdgeracoes; i++) {
            int corte = new Random().nextInt(cromossomos - 2) + 1;
            populacoes.add(IAFernandoController.novaPopulacao(populacoes.get(i - 1), corte, i, pop));
        }
        pop.setQtGeracoes(populacoes.size());
        try {
            ConexaoDAO ax = ConexaoDAO.getInstance();
            ax.startTransaction();
            ax.persist(pop);
            ax.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        IAFernandoController.salvaPopulacao(populacoes);
        System.out.println("População " + pop.getCdPopulacao() +" salva com sucesso");
        Relatorio relatorio = new Relatorio();
        relatorio.setRelatorio("grafico");
        relatorio.setNomejob("grafico");
        relatorio.setOutfilename("grafico_pop" + pop.getCdPopulacao());
        String path = "";
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        relatorio.setRelatorio(path + "//report//" + relatorio.getRelatorio() + ".jasper");
        relatorio.getParametros().put("populacao", pop.getCdPopulacao());
        relatorio.emitirRelatorio();

        File pdf = new File(relatorio.getOutfilename()+ ".pdf");
        try{
            Desktop.getDesktop().open(pdf );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
