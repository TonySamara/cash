package com.learn.effective.java.model;

import lombok.Data;

@Data
public class CacheNode {
  public String key;
  public Object value;
  public int frequency;
  public CacheNode prev;
  public CacheNode next;

  public CacheNode(String key, Object value, int frequency){
    this.key   = key;
    this.value = value;
    this.frequency = frequency;
  }
}
