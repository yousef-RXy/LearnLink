package com.os;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainSceneController {

  @FXML
  private TextField chairs;

  @FXML
  private TextField students;

  @FXML
  private TextField ta;

  @FXML
  private Label later;

  @FXML
  private Label out;

  @FXML
  private Label sleeping;

  @FXML
  private Label waiting;

  @FXML
  private Label working;

  @FXML
  void btnOkClicked(ActionEvent event) {
    try {
      final int studentsNum = Integer.parseInt(students.getText());
      final int chairsNum = Integer.parseInt(chairs.getText());
      final int taNum = Integer.parseInt(ta.getText());
      if (!(studentsNum > 0 && chairsNum > 0 && taNum > 0)) {
        throw new NumberFormatException("Error");
      }
      new Thread(() -> {
        new TaOffice(studentsNum, chairsNum, taNum, this);
      }).start();
    } catch (NumberFormatException e) {
      showError();
    }
  }

  void showError() {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("message.fxml"));
      Stage stage = new Stage();
      Scene scene = new Scene(root);
      stage.setTitle("Error");
      stage.setScene(scene);
      stage.show();
    } catch (IOException e1) {
      Thread.currentThread().interrupt();
    }
  }

  void updateWorkingLabel(int workingNum) {
    Platform.runLater(() -> working.setText("" + workingNum));
  }

  void updateSleepingLabel(int sleepingNum) {
    Platform.runLater(() -> sleeping.setText("" + sleepingNum));
  }

  void updateWaitingLabel(int waitingNum) {
    Platform.runLater(() -> waiting.setText("" + waitingNum));
  }

  void updateLaterLabel(int laterNum) {
    Platform.runLater(() -> later.setText("" + laterNum));
  }

  void updateOutLabel(int outNum) {
    Platform.runLater(() -> out.setText("" + outNum));
  }
}
