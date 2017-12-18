package br.edu.ifam.dad.mbean;

import br.edu.ifam.dad.controle.ConsultaCidadeBean;
import br.edu.ifam.dad.controle.ConsultaEstadoBean;
import br.edu.ifam.dad.controle.ConsultaPessoaBean;
import br.edu.ifam.dad.controle.PessoaBean;
import br.edu.ifam.dad.modelo.Cidade;
import br.edu.ifam.dad.modelo.Estado;
import br.edu.ifam.dad.modelo.Pessoa;
import br.edu.ifam.dad.modelo.Sexo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Carlos
 */
@ManagedBean(name = "mbpessoa")
@SessionScoped
public class MBeanPessoa implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private List<Estado> estados = new ArrayList<Estado>();
    private List<Cidade> cidades = new ArrayList<Cidade>();
    //Temporario para testar JSF
    private Estado estado;
    private Cidade cidade;
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
        this.cidades = consultaCidadeBean.listarTodas();
    }

    public String insere() {
        System.out.println(pessoa.toString());
        this.cidade.setEstado(estado);
        this.pessoa.getLogradouro().setCidade(cidade);
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

    public Cidade consultaCidade(Long id) {
        return consultaCidadeBean.consultar(id);
    }

    public Estado consultaEstado(Long id) {
        return consultaEstadoBean.consultar(id);
    }

    public void listaCidades(ValueChangeEvent event) {
        this.cidades = consultaCidadeBean.listarPorEstado(this.pessoa.getLogradouro().getCidade().getEstado());
    }

    public List<SelectItem> getSexos() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        itens.add(new SelectItem(Sexo.F, "Feminino"));
        itens.add(new SelectItem(Sexo.M, "Masculino"));
        return itens;
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
//Temporario para testar JSF
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

}
