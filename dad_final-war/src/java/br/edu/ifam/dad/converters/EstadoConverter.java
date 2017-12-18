/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.dad.converters;

import br.edu.ifam.dad.mbean.MBeanPessoa;
import br.edu.ifam.dad.modelo.Estado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter(value = "estadoConverter")
public class EstadoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        MBeanPessoa mBean = (MBeanPessoa) context.getApplication().evaluateExpressionGet(context, "#{mbpessoa}", MBeanPessoa.class);

        if (value == null || value.isEmpty()) {
            return null;
        }
        Estado estado = mBean.consultaEstado(Long.parseLong(value));
        return estado;
        //return mBean.consultaEstado(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Estado estado = (Estado) value;
        if (estado.getId() == 0) {
            return null;
        }
        return String.valueOf(estado.getId());
    }

}
