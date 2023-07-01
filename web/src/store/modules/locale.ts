import { defineStore } from 'pinia'
import { useCache } from '@/hooks/web/useCache'
import { LocaleDropdownType } from '@/types/localeDropdown'
import { store } from '../index'

import zhCn from 'element-plus/es/locale/lang/zh-cn'
import en from 'element-plus/es/locale/lang/en'

const { wsCache } = useCache()

const elLocaleMap = {
  'zh-CN': zhCn,
  en: en
}

interface LocaleState {
  currentLocale: LocaleDropdownType
  localeMap: LocaleDropdownType[]
}

export const useLocaleStore = defineStore('locales', {
  state: (): LocaleState => {
    const currentLang: LocaleType = wsCache.get('lang')

    return {
      currentLocale: {
        lang: currentLang || 'zh-CN',
        elLocale: elLocaleMap[currentLang || 'zh-CN']
      },
      localeMap: [
        {
          lang: 'zh-CN',
          name: '简体中文'
        },
        {
          lang: 'en',
          name: 'English'
        }
      ]
    }
  },
  getters: {
    getCurrentLocale(): LocaleDropdownType {
      return this.currentLocale
    },
    getLocaleMap(): LocaleDropdownType[] {
      return this.localeMap
    }
  },
  actions: {
    setCurrentLocale(lang: LocaleType): void {
      this.currentLocale.lang = lang
      this.currentLocale.elLocale = elLocaleMap[lang]
      wsCache.set('lang', lang)
    }
  }
})

export const useLocaleStoreWithOut = () => {
  return useLocaleStore(store)
}
