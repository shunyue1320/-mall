import { defineStore } from 'pinia'
import { store } from '../index'
import { useCache } from '@/hooks/web/useCache'

const { wsCache } = useCache()

interface AppState {
  title: string
  isDark: boolean
}

export const useAppStore = defineStore('app', {
  state: (): AppState => {
    return {
      title: import.meta.env.VITE_APP_TITLE, // 标题
      isDark: wsCache.get('isDark') || false // 是否是暗黑模式
    }
  },
  getters: {
    getTitle(): string {
      return this.title
    },
    getIsDark(): boolean {
      return this.isDark
    }
  },
  actions: {
    setIsDark(isDark: boolean) {
      this.isDark = isDark
      if (this.isDark) {
        document.documentElement.classList.add('dark')
        document.documentElement.classList.remove('light')
      } else {
        document.documentElement.classList.add('light')
        document.documentElement.classList.remove('dark')
      }
      wsCache.set('isDark', this.isDark)
    }
  }
})

export const useAppStoreWithOut = () => {
  return useAppStore(store)
}
