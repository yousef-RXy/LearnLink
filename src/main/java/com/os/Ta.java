package com.os;

import java.util.concurrent.Semaphore;

class Ta extends Thread {
  Semaphore studentSemaphore;
  Semaphore taSemaphore;
  Semaphore currentTaCountMutex;
  Semaphore remainingMutex;
  IntWrapper totalTaCount;
  IntWrapper currentTaCount;
  IntWrapper remainingCount;
  IntWrapper waitingCount;
  IntWrapper chairsNum;
  IntWrapper studentsNum;
  // IntWrapper firstInit;

  Ta(Semaphore studentSemaphore,
      Semaphore taSemaphore,
      Semaphore currentTaCountMutex,
      Semaphore remainingMutex) {
    this.studentSemaphore = studentSemaphore;
    this.taSemaphore = taSemaphore;
    this.currentTaCountMutex = currentTaCountMutex;
    this.remainingMutex = remainingMutex;
    this.totalTaCount = TaOffice.getTotalTaCount();
    this.currentTaCount = TaOffice.getCurrentTaCount();
    this.remainingCount = TaOffice.getRemainingCount();
    this.waitingCount = TaOffice.getWaitingCount();
    this.chairsNum = TaOffice.getChairsNum();
    this.studentsNum = TaOffice.getStudentsNum();
    // this.firstInit = TaOffice.;
  }

  @Override
  public void run() {
    while (true) {
      try {
        this.studentSemaphore.acquire();
        this.taSemaphore.release();

        this.currentTaCountMutex.acquire();
        this.currentTaCount.integer++;
        this.currentTaCountMutex.release();

        Thread.sleep(RandomInt.randomInt() * 3);

        this.currentTaCountMutex.acquire();
        TaOffice.printStatus();
        this.currentTaCount.integer--;
        this.currentTaCountMutex.release();

        this.remainingMutex.acquire();
        this.remainingCount.integer--;
        if (this.remainingCount.integer < this.totalTaCount.integer) {
          TaOffice.printStatus();
          this.remainingMutex.release();
          break;
        }
        this.remainingMutex.release();

      } catch (Exception e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}