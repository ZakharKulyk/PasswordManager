package panel;

import dto.FIleContent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PasswordTablePanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private static final String[] COLUMNS = {
            "Name", "Password", "Category", "Login", "Website", "Location"
    };

    public PasswordTablePanel(List<FIleContent> passwordList) {
        setLayout(new BorderLayout());
        initTable(passwordList);
    }

    private void initTable(List<FIleContent> passwordList) {
        model = new DefaultTableModel(COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (FIleContent content : passwordList) {
            model.addRow(new Object[]{
                    content.getName(),
                    content.getPassword(),
                    content.getCategory(),
                    content.getLogin(),
                    content.getWebsite(),
                    content.getLocation()
            });
        }

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setAutoCreateRowSorter(true); // Сортировка по клику по заголовку

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getModel() {
        return model;
    }
}