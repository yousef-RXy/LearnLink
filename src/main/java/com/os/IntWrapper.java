package com.os;

public class IntWrapper {
  public int integer;

  IntWrapper(int integer) {
    this.integer = integer;
  }

  @Override
  public String toString() {
    return "" + integer;
  }
}
