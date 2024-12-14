package com.os;

import java.util.concurrent.Semaphore;

public class TaOffice {
  private final Semaphore studentSemaphore = new Semaphore(0);
  private final Semaphore taSemaphore = new Semaphore(0);
  private final Semaphore remainingMutex = new Semaphore(1);
  private final Semaphore waitingMutex = new Semaphore(1);
  private final Semaphore currentTaCountMutex = new Semaphore(1);
  private final static IntWrapper remainingCount = new IntWrapper(0);
  private final static IntWrapper studentsNum = new IntWrapper(0);
  private final static IntWrapper chairsNum = new IntWrapper(0);
  private final static IntWrapper totalTaCount = new IntWrapper(0);
  private final static IntWrapper waitingCount = new IntWrapper(0);
  private final static IntWrapper currentTaCount = new IntWrapper(0);
  private static MainSceneController controller;

  TaOffice(int studentsNum, int chairsNum, int tasNum, MainSceneController controller) {
    TaOffice.chairsNum.integer = chairsNum;
    TaOffice.remainingCount.integer = studentsNum;
    TaOffice.studentsNum.integer = studentsNum;
    TaOffice.totalTaCount.integer = tasNum;
    TaOffice.controller = controller;

    for (int i = 1; i <= tasNum; i++) {
      new Ta(
          this.studentSemaphore,
          this.taSemaphore,
          this.currentTaCountMutex,
          this.remainingMutex).start();
    }

    for (int i = 1; i <= studentsNum; i++) {
      new Student(this.studentSemaphore,
          this.taSemaphore,
          this.waitingMutex).start();
      try {
        Thread.sleep(RandomInt.randomInt());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }

  static void printStatus() {
    int TAsWorking = currentTaCount.integer;
    int TAsSleeping = totalTaCount.integer - currentTaCount.integer;
    int waitingStudents = waitingCount.integer - totalTaCount.integer + TAsSleeping;
    int comingStudents = remainingCount.integer - waitingStudents - currentTaCount.integer;
    int out = studentsNum.integer - remainingCount.integer;

    if (chairsNum.integer == 0) {
      waitingStudents = 0;
      comingStudents += waitingStudents;
    }

    controller.updateWorkingLabel(TAsWorking);
    controller.updateSleepingLabel(TAsSleeping);
    controller.updateWaitingLabel(waitingStudents);
    controller.updateLaterLabel(comingStudents);
    controller.updateOutLabel(out);
    System.out.println("TAs working: " + TAsWorking);
    System.out.println("TAs Sleeping: " + TAsSleeping);
    System.out.println("Students Waiting on chairs: " + waitingStudents);
    System.out.println("Students that will come later: " + comingStudents);
    System.out.println("out: " + out);
    System.out.println("");

    // try {
    // if (firstInit.integer == 1) {
    // firstInit.integer--;
    // Thread.sleep((studentsNum.integer / 2 * 1000));
    // }
    // } catch (InterruptedException e) {
    // Thread.currentThread().interrupt();
    // }
  }

  public static IntWrapper getRemainingCount() {
    return remainingCount;
  }

  public static IntWrapper getStudentsNum() {
    return studentsNum;
  }

  public static IntWrapper getChairsNum() {
    return chairsNum;
  }

  public static IntWrapper getTotalTaCount() {
    return totalTaCount;
  }

  public static IntWrapper getWaitingCount() {
    return waitingCount;
  }

  public static IntWrapper getCurrentTaCount() {
    return currentTaCount;
  }
}