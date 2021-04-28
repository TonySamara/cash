package com.learn.effective.java.service;

import com.learn.effective.java.model.CacheNode;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class LFUCache {
  CacheNode head;
  CacheNode tail;
  Map<String, CacheNode> map = null;
  int capacity = 0;

  public LFUCache(int capacity) {
    this.capacity = capacity;
    this.map = new HashMap<>();
  }

  public Object get(String key) {

    if (map.get(key) == null) {
      return -1;
    }

    CacheNode item = map.get(key);
    // move to right position according to frequency
    removeNode(item);
    item.frequency = item.frequency+1;
    addNodeWithUpdatedFrequency(item);

    return item.value;
  }

  public void put(String key, Object value) {

    if (map.containsKey(key)) {
      CacheNode item = map.get(key);
      item.value = value;
      item.frequency = item.frequency + 1;
      // move to right position according to frequency
      removeNode(item);
      addNodeWithUpdatedFrequency(item);
    } else {
      if (map.size() >= capacity) {
        // delete head with least frequency and least recently used
        map.remove(head.key);
        removeNode(head);
      }

      // move to right position according to frequency
      CacheNode node = new CacheNode(key, value, 1);
      addNodeWithUpdatedFrequency(node);
      map.put(key, node);
    }
  }

  private void removeNode(CacheNode node) {

    if (node.prev != null) {
      node.prev.next = node.next;
    } else {
      head = node.next;
    }

    if (node.next != null) {
      node.next.prev = node.prev;
    } else {
      tail = node.prev;
    }
  }

  private void addNodeWithUpdatedFrequency(CacheNode node) {

    if (tail != null && head != null) {
      CacheNode temp = head;
      while(temp != null) {
        if(temp.frequency > node.frequency) {
          if(temp == head) {
            node.next = temp;
            temp.prev = node;
            head = node;
            break;
          }else {
            node.next = temp;
            node.prev = temp.prev;
            temp.prev.next = node;
            node.prev = temp.prev;
            break;
          }
        }else {
          temp = temp.next;
          if(temp == null) {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
            break;
          }
        }
      }
    } else {
      tail = node;
      head = tail;
    }
  }
}
