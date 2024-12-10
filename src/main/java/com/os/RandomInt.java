package com.os;

import java.util.Random;

public class RandomInt {
  private static Random rand = new Random();

  public static int randomInt() {
    int max = 700, min = 300;
    return rand.nextInt(max - min + 1) + min;
  }
}
