package br.edu.ifam.dad.mbean;

import br.edu.ifam.dad.controle.ConsultaCidadeBean;
import br.edu.ifam.dad.controle.ConsultaEstadoBean;
import br.edu.ifam.dad.controle.ConsultaPessoaBean;
import br.edu.ifam.dad.controle.PessoaBean;
import br.edu.ifam.dad.modelo.Cidade;
import br.edu.ifam.dad.modelo.Estado;
import br.edu.ifam.dad.modelo.Logradouro;
import br.edu.ifam.dad.modelo.Pessoa;
import br.edu.ifam.dad.modelo.Sexo;
import br.edu.ifam.dad.xml.CepXML;
import br.edu.ifam.dad.xml.CidadeXML;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

/**
 *
 * @author Carlos
 */
@ManagedBean(name = "mbpessoa")
@ViewScoped
public class MBeanPessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();
    private List<Estado> estados = new ArrayList<Estado>();
    private List<CidadeXML> cidades = new ArrayList<CidadeXML>();
    //Temporario para testar JSF
    private Estado estado;
    private CidadeXML cidade;
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
        this.getPessoa().setLogradouro(new Logradouro());
        this.pessoas = consultaPessoaBean.listarTodas();
        this.estados = consultaEstadoBean.listarTodas();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public String insere() {
        this.pessoa.getLogradouro().setCidade(this.insereCidade(this.getCidade().getNome(),
                this.getCidade().getUf(), this.getCidade().getCodigoIbge9()));
        System.out.println(pessoa.toString());
        pessoaBean.inserir(pessoa);
        this.clearAll();
        this.setPessoas(this.consultaPessoaBean.listarTodas());
        return "listarPessoa";
    }

    private Cidade insereCidade(String nome, String uf, String ibge) {
        Cidade aux = consultaCidadeBean.consultar(nome, uf);
        if (aux == null) {
            aux = this.criaNova(ibge, nome, this.getEstado());
        }
        return aux;
    }

    private Cidade criaNova(String ibge, String nome, Estado estado) {
        Cidade aux = new Cidade();
        aux.setCodigoIBGE(ibge);
        aux.setNome(nome);
        aux.setEstado(estado);
        return aux;
    }

    private void clearAll() {
        this.setPessoa(new Pessoa());
        this.setCidade(new CidadeXML());
        this.setEstado(new Estado());
    }

    public String edita(Long pessoaId) {
        this.setPessoa(consultaPessoaBean.consultar(pessoaId));
        this.setCidade(this.convertCidade(this.getPessoa().getLogradouro().getCidade()));
        this.setEstado(this.getPessoa().getLogradouro().getCidade().getEstado());
        this.listaCidades(null);
        return "/pages/alterarPessoa";
    }

    public CidadeXML convertCidade(Cidade cidade) {
        CidadeXML aux = new CidadeXML();
        aux.setCodigoIbge9(cidade.getCodigoIBGE());
        aux.setNome(cidade.getNome());
        aux.setUf(cidade.getEstado().getSigla());
        return aux;
    }

    public String exclui(long id) {
        Pessoa pessoaExc = consultaPessoaBean.consultar(id);
        pessoaBean.remover(pessoaExc);
        this.setPessoas(this.consultaPessoaBean.listarTodas());
        return "";
    }

    public String altera() {
        this.pessoa.getLogradouro().setCidade(this.insereCidade(this.getCidade().getNome(),
                this.getCidade().getUf(), this.getCidade().getCodigoIbge9()));
        System.out.println(pessoa.toString());
        pessoaBean.atualizar(pessoa);
        this.clearAll();
        this.setPessoas(this.consultaPessoaBean.listarTodas());
        return "listarPessoa";
    }

    public void consultaCep() {
        String uri = "http://viacep.com.br/ws/"
                + this.getPessoa().getLogradouro().getCep() + "/xml/";
        Client client = Client.create();
        WebResource wr = client.resource(uri);
        String erro = wr.get(String.class);
        if (!erro.contains("erro")) {
            CepXML cepXML = wr.get(CepXML.class);
            this.getPessoa().setLogradouro(convertCepXMLtoLogradouro(cepXML));
            this.setEstado(this.consultaEstadoBean.consultar(cepXML.getUf()));
            this.listaCidades(null);
            this.setCidade(this.convertCidade(this.getPessoa().getLogradouro().getCidade()));
        }
    }

    private Logradouro convertCepXMLtoLogradouro(CepXML cepXML) {
        Logradouro aux = this.getPessoa().getLogradouro();
        aux.setCep(cepXML.getCep());
        aux.setDescricao(cepXML.getLogradouro());
        aux.setBairro(cepXML.getBairro());
        aux.setCidade(this.insereCidade(cepXML.getLocalidade(), cepXML.getUf(), cepXML.getIbge()));
        return aux;
    }

    public void listaCidades(AjaxBehaviorEvent event) {
        this.setCidades(this.listaCidadesWS());
    }

    public Estado consultaEstado(Long id) {
        return consultaEstadoBean.consultar(id);
    }

    public CidadeXML consultaCidade(String id) {
        CidadeXML retorno = null;
        for (CidadeXML cidadeXML : this.getCidades()) {
            if (id.equals(cidadeXML.getId())) {
                retorno = cidadeXML;
                break;
            }
        }
        return retorno;
    }

    private List<CidadeXML> listaCidadesWS() {
        String uri = "http://dadosabertos.almg.gov.br/ws/brasil/localidades/ufs/"
                + this.getEstado().getSigla() + "/municipios";
        Client client = Client.create();
        WebResource wr = client.resource(uri);
        List<CidadeXML> cidadesXML = wr.get(new GenericType<List<CidadeXML>>() {
        });
        return cidadesXML;
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

    public List<CidadeXML> getCidades() {
        return cidades;
    }

    public void setCidades(List<CidadeXML> cidades) {
        this.cidades = cidades;
    }
//Temporario para testar JSF

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public CidadeXML getCidade() {
        return cidade;
    }

    public void setCidade(CidadeXML cidade) {
        this.cidade = cidade;
    }

}
