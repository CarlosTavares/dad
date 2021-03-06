package br.edu.ifam.dad.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @Temporal(TemporalType.DATE)
    private Calendar nascimento;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private String ocupacao;
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Logradouro logradouro = new Logradouro();

    public Pessoa() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Calendar getNascimento() {
        return nascimento;
    }

    public void setNascimento(Calendar nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getIdade() {
        if (this.getNascimento() != null) {
            GregorianCalendar hoje = new GregorianCalendar();
            int anoAtual = hoje.get(Calendar.YEAR);
            int anoNascimento = this.getNascimento().get(Calendar.YEAR);
            return anoAtual - anoNascimento;
        } else {
            return 0;
        }
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Logradouro getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(Logradouro logradouro) {
        this.logradouro = logradouro;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "id=" + id + ", nome=" + nome + ", nascimento=" + nascimento +
                ", sexo=" + sexo + ", ocupacao=" + ocupacao + ", logradouro=" + logradouro.toString() + '}';
    }

}
