/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.util;

import java.io.Serializable;

/**
 *
 * @author Omar Ernesto Cabrera Rosero
 */
public class ItemList implements Serializable{

    private String valueHeader;
    private Integer posHeader;

    public ItemList(){
        
    }
    public ItemList(String valueHeader, Integer posHeader) {
        this.posHeader = posHeader;
        this.valueHeader = valueHeader;
    }

    public String getValueHeader() {
        return valueHeader;
    }

    public void setValueHeader(String valueHeader) {
        this.valueHeader = valueHeader;
    }

    public Integer getPosHeader() {
        return posHeader;
    }

    public void setPosHeader(Integer posHeader) {
        this.posHeader = posHeader;
    }
    
    @Override
    public String toString(){
        return valueHeader;
    }
    
}
