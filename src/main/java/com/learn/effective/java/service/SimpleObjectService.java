package com.learn.effective.java.service;

import com.learn.effective.java.model.SimpleObject;

import java.util.HashSet;
import java.util.Set;

public class SimpleObjectService {
  private Set<SimpleObject> simpleObjectStore = new HashSet<>();

  public void put(SimpleObject object) {
    simpleObjectStore.add(object);
  }

  public SimpleObject get(String field) {
    return simpleObjectStore.stream().filter(object -> object.getField().equals(field)).findFirst().orElseGet(null);
  }

  public Set<SimpleObject> getStore(){
    return simpleObjectStore;
  }
}
