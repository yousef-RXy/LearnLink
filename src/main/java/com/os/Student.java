package com.os;

import java.util.concurrent.Semaphore;

class Student extends Thread {
  Semaphore studentSemaphore;
  Semaphore taSemaphore;
  Semaphore waitingMutex;
  IntWrapper chairsNum;
  IntWrapper totalTaCount;
  IntWrapper waitingCount;

  Student(Semaphore studentSemaphore,
      Semaphore taSemaphore,
      Semaphore waitingMutex) {
    this.studentSemaphore = studentSemaphore;
    this.taSemaphore = taSemaphore;
    this.waitingMutex = waitingMutex;
    this.chairsNum = TaOffice.getChairsNum();
    this.totalTaCount = TaOffice.getTotalTaCount();
    this.waitingCount = TaOffice.getWaitingCount();
  }

  @Override
  public void run() {
    while (true) {
      try {
        waitingMutex.acquire();
        if (this.waitingCount.integer == this.totalTaCount.integer && this.chairsNum.integer == 0) {
          waitingMutex.release();
          Thread.sleep(RandomInt.randomInt());
          continue;
        } else if (this.waitingCount.integer == this.chairsNum.integer && this.chairsNum.integer != 0) {
          waitingMutex.release();
          Thread.sleep(RandomInt.randomInt());
          continue;
        } else {
          this.waitingCount.integer++;
        }
        waitingMutex.release();

        studentSemaphore.release();
        taSemaphore.acquire();

        waitingMutex.acquire();
        this.waitingCount.integer--;
        waitingMutex.release();

        break;
      } catch (Exception e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
