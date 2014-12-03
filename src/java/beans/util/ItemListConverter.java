/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.util;

/**
 *
 * @author Omar Ernesto Cabrera Rosero
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;


@FacesConverter("itemListConverter")
public class ItemListConverter implements Converter{

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         String string;

        if(value == null){
            string="";
        }else{
            try{
                System.out.println(value.toString());
                string = String.valueOf(((ItemList)value).getPosHeader());
            }catch(ClassCastException cce){
                throw new ConverterException();
            }
        }
        return string;
    }
    @SuppressWarnings("unchecked")
    private ItemList getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<ItemList> dualList;
        try{
            dualList = (DualListModel<ItemList>) ((PickList)component).getValue();
            ItemList lin = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
            if(lin==null){
                lin = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
            }
             
            return lin;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
    private ItemList getObjectFromList(final List<?> list, final Integer identifier) {
        for(final Object object:list){
            final ItemList lin = (ItemList) object;
            if(lin.getPosHeader().equals(identifier)){
                return lin;
            }
        }
        return null;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component,value);
    }
    
}