/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifam.dad.converters;

import br.edu.ifam.dad.mbean.MBeanPessoa;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Carlos
 */
@FacesConverter(value = "calendarConverter")
public class CalendarConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = (Date) sdf.parse(value);
            cal.setTime(date);
        } catch (ParseException ex) {
            Logger.getLogger(CalendarConverter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cal;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        /**
        Date data = (Date) value;
        **/
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = (Calendar) value;//Calendar.getInstance();
        //cal.setTime(data);
        return sdf.format(cal.getTime());
    }

}
