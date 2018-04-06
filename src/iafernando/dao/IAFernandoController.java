/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iafernando.dao;

import iafernando.IAFernando;
import iafernando.obj.Gene;
import iafernando.obj.GenePK;
import iafernando.obj.Populacao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author bruno.szczuk
 */
public class IAFernandoController {

    private static float soma = 0f;

    private static void salvaGenes(List<Gene> genes) {
        try {
            for (Gene f : genes) {
                ConexaoDAO.getInstance().persist(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Gene mutacao(Gene g) {
        int valor = new Random().nextInt(200);
        char[] c = g.getVlBinario().toCharArray();
        String binario = "";
        for (int i = 0; i < c.length; i++) {
            if (valor == (c[i])) {
                c[i] = c[i] == '0' ? '1' : '0';
            }
            binario = binario + c[i];
        }
        g.setVlBinario(binario);
        g.setVlGene(Integer.parseInt(g.getVlBinario(), 2));

        return new Gene(g);
    }

    private static Gene sorteaGeneUnico(List<Gene> genes, Gene escolhido) {
        List listaUnica = new ArrayList(genes);
        listaUnica.remove(listaUnica.indexOf(escolhido));
        return new Gene(sorteaGene(listaUnica));
    }

    private static Gene sorteaGene(List<Gene> genes) {
        soma = 0f;
        for (Gene x : genes) {
            soma += x.getVlGene();
        }
        double rd = Math.random() * 100;

        float anterior = 0f;
        for (Gene x : genes) {
            x.setPcChance(anterior);
            x.setPcChanceFim(anterior + ((x.getVlGene() / soma) * 100));
            if ((rd > x.getPcChance()) && (rd < x.getPcChanceFim())) {
                return new Gene(x);
            }
            anterior = x.getPcChanceFim();
        }
        return new Gene(genes.get(new Random().nextInt(genes.size())));
    }

    public static List<Gene> novaPopulacao(List<Gene> genes, int corte, int nrgeracao, Populacao p) {
        List<Gene> geracao = new ArrayList<>();
        for (int i = 0; i < genes.get(1).getPk().getPop().getQtGene(); i = i + 2) {
            Gene tmp0 = sorteaGene(genes);
            Gene tmp1;
            if (IAFernando.unico) {
                tmp1 = sorteaGeneUnico(genes, tmp0);
            } else {
                tmp1 = sorteaGene(genes);
            }

            //faz o crossover
            tmp0.setVlBinario(tmp0.getVlBinario().substring(0, corte) + tmp1.getVlBinario().substring(corte, tmp1.getVlBinario().length()));
            tmp1.setVlBinario(tmp1.getVlBinario().substring(0, corte) + tmp0.getVlBinario().substring(corte, tmp0.getVlBinario().length()));
            //System.out.println("Corte: " + corte + " " + tmp0.getVlBinario() + " Casal 1 " + " " + tmp1.getVlBinario());

            tmp0.setVlCorte(corte);
            tmp1.setVlCorte(corte);

            tmp0 = IAFernandoController.mutacao(tmp0);
            tmp1 = IAFernandoController.mutacao(tmp1);

            GenePK pk1 = new GenePK();
            GenePK pk2 = new GenePK();
            pk1.setPop(p);
            pk2.setPop(p);
            pk1.setCd_gene(i);
            pk1.setNr_iteracao(nrgeracao);
            pk2.setCd_gene(i + 1);
            pk2.setNr_iteracao(nrgeracao);
            tmp0.setPk(pk1);
            tmp1.setPk(pk2);

            System.out.println(tmp0);
            System.out.println(tmp1);
            System.out.println();
            geracao.add(tmp0);
            geracao.add(tmp1);
        }
        return geracao;
    }

    public static void salvaPopulacao(List<List<Gene>> pop) {
        try {
            for (List<Gene> p : pop) {
                salvaGenes(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
