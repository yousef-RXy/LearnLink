package com.os;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    new Thread(() -> {
      new TaOffice(Integer.parseInt(students.getText()),
          Integer.parseInt(chairs.getText()),
          Integer.parseInt(ta.getText()), this);
    }).start();
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
