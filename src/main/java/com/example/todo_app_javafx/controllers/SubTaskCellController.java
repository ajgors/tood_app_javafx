package com.example.todo_app_javafx.controllers;

import com.example.todo_app_javafx.dao.Dao;
import com.example.todo_app_javafx.model.Model;
import com.example.todo_app_javafx.model.Subtask;
import com.example.todo_app_javafx.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

@Getter
@Setter
public class SubTaskCellController implements Initializable {
    @FXML
    private Label taskTitleLbl;
    @FXML
    private Button delete_btn;
    private String description;

    @FXML
    private Button editBtn;
    private final Subtask subtask;
    private final TreeView<Object> treeView;

    public SubTaskCellController(Subtask task, TreeView<Object> treeView) {
        this.subtask = task;
        this.treeView = treeView;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskTitleLbl.setText(subtask.getTitle());
        delete_btn.setOnAction(e -> {
            TreeItem<Object> treeItem = treeView.getRoot().getChildren().filtered(i -> i.getValue().equals(subtask.getTask())).stream().findFirst().get();
            treeItem.getChildren().removeIf(o-> o.getValue().equals(subtask));
            subtask.getTask().getSubtasks().remove(subtask);
            Dao.delete(subtask);
        });
        editBtn.setOnAction(e -> ViewFactory.openEditWindow(subtask, this));
    }
}
