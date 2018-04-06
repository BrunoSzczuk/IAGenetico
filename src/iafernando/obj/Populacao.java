/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iafernando.obj;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author bruno.szczuk
 */
@Entity
@Table(name = "populacao", catalog = "iagenetico", schema = "public")

public class Populacao implements Serializable {

    @Id
    @Column(name = "cd_populacao")
    private int cdPopulacao;

    @Column(name = "dt_emissao")
    @Temporal(TemporalType.DATE)
    private Date dtEmissao;

    @Column(name = "qt_gene")
    private int qtGene;

    @Column(name = "qt_geracoes")
    private int qtGeracoes;
    @Column(name = "qt_cromossomos")
    private int qtCromossomos;

    public Date getDt_emissao() {
        return dtEmissao;
    }

    public int getQtCromossomos() {
        return qtCromossomos;
    }

    public void setQtCromossomos(int qtCromossomos) {
        this.qtCromossomos = qtCromossomos;
    }

    public void setDt_emissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    @Override
    public String toString() {
        return "Populacao{" + "cdPopulacao=" + cdPopulacao + ", dtEmissao=" + dtEmissao + ", qtGene=" + qtGene + ", qtGeracoes=" + qtGeracoes + '}';
    }

    public int getCdPopulacao() {
        return cdPopulacao;
    }

    public void setCdPopulacao(int cdPopulacao) {
        this.cdPopulacao = cdPopulacao;
    }

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public void setDtEmissao(Date dtEmissao) {
        this.dtEmissao = dtEmissao;
    }

    public int getQtGene() {
        return qtGene;
    }

    public void setQtGene(int qtGene) {
        this.qtGene = qtGene;
    }

    public int getQtGeracoes() {
        return qtGeracoes;
    }

    public void setQtGeracoes(int qtGeracoes) {
        this.qtGeracoes = qtGeracoes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.cdPopulacao;
        hash = 17 * hash + Objects.hashCode(this.dtEmissao);
        hash = 17 * hash + this.qtGene;
        hash = 17 * hash + this.qtGeracoes;
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
        final Populacao other = (Populacao) obj;
        if (this.cdPopulacao != other.cdPopulacao) {
            return false;
        }
        if (!Objects.equals(this.dtEmissao, other.dtEmissao)) {
            return false;
        }
        if (this.qtGene != other.qtGene) {
            return false;
        }
        if (this.qtGeracoes != other.qtGeracoes) {
            return false;
        }
        return true;
    }

    public Populacao() {
    }

}
