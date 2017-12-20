/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.dad.converters;

import br.edu.ifam.dad.mbean.MBeanPessoa;
import br.edu.ifam.dad.xml.CidadeXML;
//import br.edu.ifam.dad.modelo.Cidade;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter(value = "cidadeXMLConverter")
public class CidadeXMLConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        MBeanPessoa mBean = (MBeanPessoa) context.getApplication().evaluateExpressionGet(context, "#{mbpessoa}", MBeanPessoa.class);

        if (value == null || value.isEmpty()) {
            return null;
        }
        return mBean.consultaCidade(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        CidadeXML cidade = (CidadeXML) value;
        if (cidade == null || cidade.getId().isEmpty()) {
            return null;
        }
        return cidade.getId();
    }

}
