package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Controller2 {
    @FXML
    private Button queryButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button createButton;
    @FXML
    private TableView<Map> table;

    private MySqlOp mySql = new MySqlOp();
    private ObservableList<Map> tableData = FXCollections.observableArrayList();

    private static final String isnewColMapKey = "A";
    private static final String idColMapKey = "B";
    private static final String codeColMapKey = "C";
    private static final String macColMapKey = "D";

    @FXML
    private void initialize() {
        TableColumn<Map, String> isnewCol = new TableColumn<>("NEW");
        TableColumn<Map, String> idCol = new TableColumn<>("ID");
        TableColumn<Map, String> codeCol = new TableColumn<>("CODE");
        TableColumn<Map, String> macCol = new TableColumn<>("MAC");

        isnewCol.setCellFactory(TextFieldTableCell.forTableColumn());
        isnewCol.setCellValueFactory(new MapValueFactory(isnewColMapKey));
        isnewCol.setMinWidth(40);
        isnewCol.setPrefWidth(40);

        idCol.setCellFactory(TextFieldTableCell.forTableColumn());
        idCol.setCellValueFactory(new MapValueFactory(idColMapKey));
        idCol.setMinWidth(40);
        idCol.setPrefWidth(40);

        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        codeCol.setCellValueFactory(new MapValueFactory(codeColMapKey));
        codeCol.setMinWidth(40);
        codeCol.setPrefWidth(40);

        macCol.setCellFactory(TextFieldTableCell.forTableColumn());
        macCol.setCellValueFactory(new MapValueFactory(macColMapKey));
        macCol.setMinWidth(200);
        macCol.setPrefWidth(200);

        table.getColumns().clear();
        table.setEditable(true);
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.getColumns().setAll(isnewCol, idCol, codeCol, macCol);
        table.setItems(tableData);
    }

    private boolean chkMySql(){
        if(mySql.isConnected()){
            return true;
        }
        mySql.connect("sample", "root", "1234");
        return mySql.isConnected();
    }

    public void onQueryButton(ActionEvent event){
        if(chkMySql()==true) {
            ResultSet rs = mySql.select("test1","*");
            tableData.clear();
            try {
                while((rs!=null)&&(rs.next())) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));

                    Map<String, String> dataRow = new HashMap<>();

                    dataRow.put(isnewColMapKey, "true");
                    dataRow.put(idColMapKey, new Integer(rs.getInt(1)).toString());
                    dataRow.put(codeColMapKey, rs.getString(2));
                    dataRow.put(macColMapKey, rs.getString(3));
                    tableData.add(dataRow);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void onAddButton(ActionEvent event){

    }
    public void onDeleteButton(ActionEvent event){

    }
    public void onUpdateButton(ActionEvent event){

    }
    public void onCreateButton(ActionEvent event){

    }
}
