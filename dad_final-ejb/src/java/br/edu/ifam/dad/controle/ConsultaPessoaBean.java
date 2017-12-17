package br.edu.ifam.dad.controle;

import br.edu.ifam.dad.modelo.Pessoa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
@Stateless
public class ConsultaPessoaBean {

    @PersistenceContext(name = "dad_final_bdPU")
    private EntityManager em;

    public Pessoa consultar(long id) {
        return em.find(Pessoa.class, id);
    }

    public List<Pessoa> listarTodas() {
        Query query = em.createQuery("FROM Pessoa");
        List<Pessoa> pessoas = query.getResultList();
        return pessoas;
    }

}
