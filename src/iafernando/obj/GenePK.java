/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iafernando.obj;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author bruno.szczuk
 */
@Embeddable
public class GenePK implements Serializable {

    public GenePK(Populacao pop, int cd_gene, int nr_iteracao) {
        this.pop = pop;
        this.cd_gene = cd_gene;
        this.nr_iteracao = nr_iteracao;
    }

    @Basic(optional = false)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cd_populacao", referencedColumnName = "cd_populacao")
    private Populacao pop;
    
    @Column(name = "cd_gene")
    private int cd_gene;
    
    @Column(name = "nr_iteracao")
    private int nr_iteracao;

    public Populacao getPop() {
        return pop;
    }

    public void setPop(Populacao pop) {
        this.pop = pop;
    }

    public int getCd_gene() {
        return cd_gene;
    }

    public void setCd_gene(int cd_gene) {
        this.cd_gene = cd_gene;
    }

    public int getNr_iteracao() {
        return nr_iteracao;
    }

    public void setNr_iteracao(int nr_iteracao) {
        this.nr_iteracao = nr_iteracao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.pop);
        hash = 89 * hash + this.cd_gene;
        hash = 89 * hash + this.nr_iteracao;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenePK other = (GenePK) obj;
        if (!Objects.equals(this.pop, other.pop)) {
            return false;
        }
        if (this.cd_gene != other.cd_gene) {
            return false;
        }
        if (this.nr_iteracao != other.nr_iteracao) {
            return false;
        }
        return true;
    }

   
    @Override
    public String toString() {
        return "GenePK{" + "cd_gene=" + cd_gene + ", nr_iteracao=" + nr_iteracao + '}';
    }

    public GenePK() {
    }
   
    

}
