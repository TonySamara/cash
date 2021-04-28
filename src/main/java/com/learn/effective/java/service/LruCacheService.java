package com.learn.effective.java.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.learn.effective.java.model.SimpleObject;
import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class LruCacheService extends SimpleObjectService{
   LoadingCache<String, SimpleObject> loadingCache = CacheBuilder.newBuilder()
      .maximumSize(10000)
      .expireAfterAccess(5, TimeUnit.SECONDS)
      .build(new CacheLoader<>() {
        @Override
        public SimpleObject load(final String field) throws Exception {
          return get(field);
        }
      });

  @SneakyThrows
  public SimpleObject getFromCache(String field) {
    return loadingCache.get(field);
  }

  public LoadingCache getCache() {
    return loadingCache;
  }

}
