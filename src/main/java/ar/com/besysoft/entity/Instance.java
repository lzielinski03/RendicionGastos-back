package ar.com.besysoft.entity;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;

/**
 * Created by lzielinski on 12/07/2016.
 */
public class Instance implements Serializable{

    public String id;
    public String description;
    public int status;
    public StringDate recivedDate;
    public StringDate deadlineDate;

    public Instance(String id, String description, int status, XMLGregorianCalendar recivedDate, XMLGregorianCalendar deadlineDate) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.recivedDate = new StringDate(recivedDate);
        if (deadlineDate == null)
            this.deadlineDate = null;
        else
            this.deadlineDate = new StringDate(deadlineDate);
    }

    private  class StringDate {
        public String anio;
        public String mes;
        public String dia;
        public String hora;
        public String min;

        private StringDate(XMLGregorianCalendar date) {
            this.anio = Integer.toString(date.getYear());
            this.mes = Integer.toString(date.getMonth());
            this.dia = Integer.toString(date.getDay());
            this.hora = Integer.toString(date.getHour());
            this.min = Integer.toString(date.getMinute());
        }
    }
}
