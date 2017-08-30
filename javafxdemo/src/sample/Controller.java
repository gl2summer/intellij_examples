package sample;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;


class SampleData{
    private SimpleBooleanProperty isNew;
    private SimpleIntegerProperty id;
    private SimpleStringProperty code;
    private SimpleStringProperty mac;

    public SampleData(Boolean isNew, Integer id, String code, String mac) {
        this.isNew = new SimpleBooleanProperty(isNew);
        this.id = new SimpleIntegerProperty(id);
        this.code = new SimpleStringProperty(code);
        this.mac = new SimpleStringProperty(mac);
    }

    public Boolean getIsNew(){
        return  isNew.get();
    }
    public void setIsNew(Boolean newValue){
        isNew.set(newValue);
    }
    public BooleanProperty isNewProperty(){
        return isNew;
    }

    public Integer getId(){
        return  id.get();
    }
    public void setId(Integer newValue){
        id.set(newValue);
    }
    public IntegerProperty idProperty(){
        return id;
    }

    public String getCode(){
        return  code.get();
    }
    public void setCode(String newValue){
        code.set(newValue);
    }
    public StringProperty codeProperty(){
        return code;
    }

    public String getMac(){
        return  mac.get();
    }
    public void setMac(String newValue){
        mac.set(newValue);
    }
    public StringProperty macProperty(){
        return mac;
    }
}











public class Controller {
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
    private TableView<SampleData> table;
    @FXML
    private TableColumn<SampleData, Boolean> t_isnew;
    @FXML
    private TableColumn<SampleData, Integer> t_id;
    @FXML
    private TableColumn<SampleData, String> t_code;
    @FXML
    private TableColumn<SampleData, String> t_mac;

    private ObservableList<SampleData> sampleData = FXCollections.observableArrayList();

    private MySqlOp mySql = new MySqlOp();

    @FXML
    private void initialize() {
        t_isnew.setCellValueFactory(cellData -> cellData.getValue().isNewProperty());
        t_id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        t_code.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
        t_mac.setCellValueFactory(cellData -> cellData.getValue().macProperty());
        table.setItems(sampleData);
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
            sampleData.removeAll();
            sampleData.clear();
            try {
                while((rs!=null)&&(rs.next())) {
                    System.out.println(rs.getInt(1));
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));
                    sampleData.add(new SampleData(true, rs.getInt(1), rs.getString(2), rs.getString(3)));
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
