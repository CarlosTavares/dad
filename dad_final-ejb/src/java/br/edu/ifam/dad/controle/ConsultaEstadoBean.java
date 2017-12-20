/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.dad.controle;

import br.edu.ifam.dad.modelo.Estado;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
@Stateless
public class ConsultaEstadoBean {

    @PersistenceContext(name = "dad_final_bdPU")
    private EntityManager em;

    public Estado consultar(long id) {
        return em.find(Estado.class, id);
    }

    public Estado consultar(String uf) {
        Query query = em.createQuery("FROM Estado c where c.sigla= :uf");
        query.setParameter("uf", uf);
        Estado estado;
        try {
            estado = (Estado) query.getSingleResult();
        } catch (NoResultException exp) {
            estado = null;
        }
        return estado;
    }

    public List<Estado> listarTodas() {
        Query query = em.createQuery("FROM Estado");
        List<Estado> estados = query.getResultList();
        return estados;
    }

}
