package com.learn.effective.java;

import com.learn.effective.java.model.SimpleObject;
import com.learn.effective.java.service.LruCacheService;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LruCacheServiceTest {

  @Test
  public void check_cache_max_size_is_10000() {
    LruCacheService cacheService = new LruCacheService();
    for (int i = 0; i <= 11000; i++) {
      SimpleObject object = new SimpleObject(RandomStringUtils.random(7, true, true));
      cacheService.put(object);
    }
    cacheService.getStore().forEach(object -> {
      cacheService.getFromCache(object.getField());
    });
    assertEquals(cacheService.getCache().size(), 10000);
  }

  @Test
  public void check_cache_life_time_is_5_seconds() throws InterruptedException {
    LruCacheService cacheService = new LruCacheService();
    SimpleObject object = new SimpleObject("test");
    cacheService.put(object);
    cacheService.getFromCache("test");
    assertEquals(cacheService.getCache().size(), 1);
    cacheService.getFromCache("test");
    Thread.sleep(5000);
    SimpleObject newObject = new SimpleObject("Hello");
    cacheService.put(newObject);
    cacheService.getFromCache("Hello");
    assertNull(cacheService.getCache().getIfPresent("test"));
  }
}
