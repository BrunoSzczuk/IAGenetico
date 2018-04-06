package iafernando.obj;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno.szczuk
 */
@Entity
@Table(name = "gene", catalog = "iagenetico", schema = "public")

public class Gene implements Serializable {

    @EmbeddedId
    protected GenePK pk;

    @Column(name = "vl_binario")
    private String vlBinario;

    @Column(name = "vl_corte")
    private int vlCorte;

    @Column(name = "vl_gene")
    private int vlGene;

    @Column(name = "pc_chance")
    private float pcChance;
    
    @Column(name = "pc_chancefim")
    private float pcChanceFim;
    public Gene() {
    }

    public GenePK getPk() {
        return pk;
    }

    public Gene(Gene g) {
        this.pcChance = g.pcChance;
        this.pk = g.pk;
        this.vlBinario = g.vlBinario;
        this.vlCorte = g.vlCorte;
        this.vlGene = g.vlGene;
        this.pcChanceFim = g.pcChanceFim;
    }

    public void setPk(GenePK pk) {
        this.pk = pk;
    }

    public float getPcChanceFim() {
        return pcChanceFim;
    }

    public void setPcChanceFim(float pcChanceFim) {
        this.pcChanceFim = pcChanceFim;
    }

    public Gene(GenePK pk, String vlBinario, int vlCorte, int vlGene, float pcChance) {
        this.pk = pk;
        this.vlBinario = vlBinario;
        this.vlCorte = vlCorte;
        this.vlGene = vlGene;
        this.pcChance = pcChance;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.pk);
        hash = 43 * hash + Objects.hashCode(this.vlBinario);
        hash = 43 * hash + this.vlCorte;
        hash = 43 * hash + this.vlGene;
        hash = 43 * hash + Float.floatToIntBits(this.pcChance);
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
        final Gene other = (Gene) obj;
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.vlBinario, other.vlBinario)) {
            return false;
        }
        if (this.vlCorte != other.vlCorte) {
            return false;
        }
        if (this.vlGene != other.vlGene) {
            return false;
        }
        if (Float.floatToIntBits(this.pcChance) != Float.floatToIntBits(other.pcChance)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Gene{" + "pk=" + pk + ", vlBinario=" + vlBinario + ", vlCorte=" + vlCorte + ", vlGene=" + vlGene + ", pcChance=" + pcChance + '}';
    }

    public String getVlBinario() {
        return vlBinario;
    }

    public void setVlBinario(String vlBinario) {
        this.vlBinario = vlBinario;
    }

    public int getVlCorte() {
        return vlCorte;
    }

    public void setVlCorte(int vlCorte) {
        this.vlCorte = vlCorte;
    }

    public int getVlGene() {
        return vlGene;
    }

    public void setVlGene(int vlGene) {
        this.vlGene = vlGene;
    }

    public float getPcChance() {
        return pcChance;
    }

    public void setPcChance(float pcChance) {
        this.pcChance = pcChance;
    }

}
