import WebStorageCache from 'web-storage-cache'

type CacheType = 'sessionStorage' | 'localStorage'

/**
 * 配置浏览器本地存储的方式，可直接存储对象数组。
 */
export const useCache = (type: CacheType = 'localStorage') => {
  const wsCache = new WebStorageCache({ storage: type })

  return {
    wsCache
  }
}
