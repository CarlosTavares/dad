/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.dad.controle;

import br.edu.ifam.dad.modelo.Cidade;
import br.edu.ifam.dad.modelo.Estado;
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
public class ConsultaCidadeBean {

    @PersistenceContext(name = "dad_final_bdPU")
    private EntityManager em;

    public Cidade consultar(long id) {
        return em.find(Cidade.class, id);
    }

    public List<Cidade> listarTodas() {
        Query query = em.createQuery("FROM Cidade");
        List<Cidade> cidades = query.getResultList();
        return cidades;
    }

    public List<Cidade> listarPorEstado(Estado estado) {
        Query query = em.createQuery("FROM Cidade c where c.estado= :estado");
        query.setParameter("estado", estado);
        List<Cidade> cidades = query.getResultList();
        return cidades;
    }
}
