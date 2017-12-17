package br.edu.ifam.dad.mbean;

import br.edu.ifam.dad.controle.ConsultaCidadeBean;
import br.edu.ifam.dad.controle.ConsultaEstadoBean;
import br.edu.ifam.dad.controle.ConsultaPessoaBean;
import br.edu.ifam.dad.controle.PessoaBean;
import br.edu.ifam.dad.modelo.Cidade;
import br.edu.ifam.dad.modelo.Estado;
import br.edu.ifam.dad.modelo.Pessoa;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Carlos
 */
@ManagedBean(name="mbpessoa")
@RequestScoped
public class MBeanPessoa {
    
    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private List<Estado> estados = new ArrayList<Estado>();
    private List<Cidade> cidades = new ArrayList<Cidade>();
    @EJB
    private PessoaBean pessoaBean;
    @EJB
    private ConsultaPessoaBean consultaPessoaBean;
    @EJB
    private ConsultaEstadoBean consultaEstadoBean;
    @EJB
    private ConsultaCidadeBean consultaCidadeBean;
    
    @PostConstruct
    public void iniciar() {
        this.pessoas = consultaPessoaBean.listarTodas();
        this.estados = consultaEstadoBean.listarTodas();
    }

    public String insere() {
        pessoaBean.inserir(pessoa);
        return "";
    }
    
    public String edita() {
        String pessoaId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pessoaId");
        this.pessoa = consultaPessoaBean.consultar(new Long(pessoaId));
        return "alteraPessoa.xhtml";
    }
    
    public String exclui(long id) {
        Pessoa pessoaExc = consultaPessoaBean.consultar(id);
        pessoaBean.remover(pessoaExc);
        return "";
    }
    
    public void consultaCep() {
        
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
    
}
