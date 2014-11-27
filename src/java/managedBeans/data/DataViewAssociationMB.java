package managedBeans.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "dataViewAssociationMB")
@SessionScoped
public class DataViewAssociationMB {

    private String newFileName = "";
    private UploadedFile file;
    private ArrayList<String[]> data;
    private List<String[]> resultado;
    private List<String> colNameData = new ArrayList<>();
    private String sep;
    private boolean header;
    private String classData;
    private List<String> classesData;
    private String classValue;
    private List<String> classValues;
    
    @PostConstruct
    public void init() {
        header = true;
    }

    private void copyFile(String fileName, InputStream in) {
        try {
            try (OutputStream out = new FileOutputStream(new File(fileName))) {
                int read;
                byte[] bytes = new byte[1024];
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                in.close();
                out.flush();
            }
            System.out.println("El nuevo fichero fue creado con éxito!");
        } catch (IOException e) {
            System.out.println("Error 4 en " + this.getClass().getName() + ":" + e.toString());
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException ex) {
            Logger.getLogger(DataViewAssociationMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        newFileName = file.getFileName();
    }

    public void loadDataTable() {
        colNameData = new ArrayList<String>();
        data = new ArrayList<>();

        try {
            String line;
            InputStreamReader isr;
            BufferedReader buffer;
            String[] tupla;

            isr = new InputStreamReader(file.getInputstream());

            buffer = new BufferedReader(isr);
            //Leer el la informacion del archivo linea por linea                       
            ArrayList<String> rowFileData;

            if (header == true) {
                String nameHeader = buffer.readLine();
                colNameData = new ArrayList<String>(Arrays.asList(nameHeader.replace("\"","").split(sep)));
            }
            else{
                String nameHeader = buffer.readLine();
                tupla = nameHeader.split(sep);
                data.add(tupla);
                colNameData = new ArrayList<String>(Arrays.asList(nameHeader.split(sep)));
                for (int i = 0; i < 10; i++) {
                    colNameData.set(i, "Columna_"+i);
                }
            }
            
            while ((line = buffer.readLine()) != null) {
                tupla = line.replace("\"","").split(sep);
                data.add(tupla);
                System.out.println(tupla);
            }
        } catch (IOException ex) {
            Logger.getLogger(DataViewAssociationMB.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    public void loadClassValues(){
        int p=0;
        System.out.println("1"+classData);
        for(int i=0; i<= colNameData.size()-1; i++){
            if(colNameData.get(i).equals(classData)){
                p=i;
                break;
            }
        }
        
        Set<String> diff = new HashSet<String>();
           for(int i=0; i<= data.size()-1; i++){
               diff.add(data.get(i)[p]);
           } 
          classValues = new ArrayList(diff);
          
          
               
    }

    public void filter(FilterEvent event) {
        DataTable table = (DataTable) event.getSource();
        resultado = table.getFilteredData();
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    public ArrayList<String[]> getData() {
        return data;
    }

    public void setData(ArrayList<String[]> data) {
        this.data = data;
    }

    public List<String[]> getResultado() {
        return resultado;
    }

    public void setResultado(List<String[]> resultado) {
        this.resultado = resultado;
    }

    public List<String> getColNameData() {
        return colNameData;
    }

    public void setColNameData(List<String> colNameData) {
        this.colNameData = colNameData;
    }

    public String getSep() {
        return sep;
    }

    public void setSep(String sep) {
        this.sep = sep;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public String getClassData() {
        return classData;
    }

    public void setClassData(String classData) {
        this.classData = classData;
    }

    public List<String> getClassesData() {
        return classesData;
    }

    public void setClassesData(List<String> classesData) {
        this.classesData = classesData;
    }

    public String getClassValue() {
        return classValue;
    }

    public void setClassValue(String classValue) {
        this.classValue = classValue;
    }

    public List<String> getClassValues() {
        return classValues;
    }

    public void setClassValues(List<String> classValues) {
        this.classValues = classValues;
    }  
    
}
