package com.learn.effective.java;

import com.learn.effective.java.model.SimpleObject;
import com.learn.effective.java.service.LFUCache;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LfuCacheServiceTest {
  @Test
  public void check_cache_max_size_is_10() {
    LFUCache LFUCache = new LFUCache(10);
    for (int i = 0; i < 20; i++) {
      SimpleObject object = new SimpleObject(RandomStringUtils.random(7, true, true));
      LFUCache.put(object.getField(), object);
    }
    assertEquals(LFUCache.getMap().size(), 10);
  }

  @Test
  public void check_that_cache_clear_by_frequency() {
    LFUCache LFUCache = new LFUCache(2);
    SimpleObject object = new SimpleObject(RandomStringUtils.random(7, true, true));
    SimpleObject uselessObject = new SimpleObject(RandomStringUtils.random(7, true, true));
    SimpleObject newObject = new SimpleObject(RandomStringUtils.random(7, true, true));

    LFUCache.put(object.getField(), object);
    LFUCache.put(uselessObject.getField(), object);
    LFUCache.get(object.getField());
    LFUCache.put(newObject.getField(), newObject);
    assertEquals(LFUCache.getMap().size(), 2);
    assertNotNull(LFUCache.getMap().get(object.getField()));
    assertNotNull(LFUCache.getMap().get(newObject.getField()));
    assertNull(LFUCache.getMap().get(uselessObject.getField()));
  }
}
