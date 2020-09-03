package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormControler implements Initializable {

	private Department entity;

	private DepartmentService departmentService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<DataChangeListener>();
	
	@FXML
	private TextField txtId;

	@FXML
	private TextField txtNome;

	@FXML
	private Label labelError;

	@FXML
	private Button btnSave;

	@FXML
	private Button btnCancel;

	public void setDepartment(Department entity) {
		this.entity = entity;
	}

	public void setDepartmetService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	@FXML
	public void onBtnSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (departmentService == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			departmentService.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		} catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}

	private void notifyDataChangeListeners() {
		for(DataChangeListener d: dataChangeListeners) {
			d.onDataChanged();
		}
	}

	private Department getFormData() {
		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setName(txtNome.getText());
		return obj;
	}

	@FXML
	public void onBtnCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeNodes();
	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtNome, 30);
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entit was null");
		}
		this.txtId.setText(String.valueOf(entity.getId()));
		this.txtNome.setText(entity.getName());
	}

}
