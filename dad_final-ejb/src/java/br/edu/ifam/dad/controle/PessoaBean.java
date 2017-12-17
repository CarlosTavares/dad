package br.edu.ifam.dad.controle;

import br.edu.ifam.dad.modelo.Pessoa;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos
 */
@Stateful
public class PessoaBean {
    
    @PersistenceContext(name="dad_final_bdPU")
    private EntityManager em;

    public void inserir(Pessoa pessoa) {
        em.persist(pessoa);
    }
    
    public Pessoa atualizar(Pessoa pessoa) {
        return em.merge(pessoa);
    }
    
    public void remover(Pessoa pessoa) {
        em.remove(em.merge(pessoa));
    }
}
