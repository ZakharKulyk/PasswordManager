package panel;

import dto.FIleContent;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PasswordTableModel  extends AbstractTableModel {


    private  List<FIleContent> entries;
    private final String[] columnNames = {"Name", "Password", "Category", "Website", "Location", "Login"};

    public PasswordTableModel(List<FIleContent> entries) {
        this.entries = entries;
    }


    @Override
    public int getRowCount() {
        return entries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        FIleContent fIleContent = entries.get(rowIndex);

        if(columnIndex == 0){
            return fIleContent.getName();
        }
        if(columnIndex == 1){
            return fIleContent.getPassword();
        }
        if (columnIndex == 2) {
            return fIleContent.getCategory();
        }
        if (columnIndex == 3) {
            return fIleContent.getWebsite();
        }
        if (columnIndex == 4) {
            return fIleContent.getLocation();
        }
        if (columnIndex == 5) {
            return fIleContent.getLogin();
        }
        return null;
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        FIleContent fIleContent = entries.get(rowIndex);

        if (columnIndex==0){
            fIleContent.setName((String) aValue);
        }
        if (columnIndex==1){
            fIleContent.setPassword((String) aValue);
        }
        if (columnIndex==2){
            fIleContent.setCategory((String) aValue);
        }
        if (columnIndex==3){
            fIleContent.setWebsite((String) aValue);
        }
        if (columnIndex==4){
            fIleContent.setLocation((String) aValue);
        }
        if (columnIndex==5){
            fIleContent.setLogin((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex); // Уведомляем об изменении ячейки
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; // Все ячейки редактируемые
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public List<FIleContent> getEntries() {
        return entries;
    }

    public void setEntries(List<FIleContent> entries) {
        this.entries = entries;
    }


}
