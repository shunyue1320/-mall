import type { App } from 'vue'
import { createI18n } from 'vue-i18n'
import type { I18n, I18nOptions } from 'vue-i18n'
import { useLocaleStoreWithOut } from '@/store/modules/locale'
import { setHtmlPageLang } from './helper'

const createI18nOptions = async (): Promise<I18nOptions> => {
  const localeStore = useLocaleStoreWithOut()
  const locale = localeStore.getCurrentLocale
  const localeMap = localeStore.getLocaleMap
  const defaultLocal = await import(`../../locales/${locale.lang}.ts`) // 懒加载国际化文件
  const message = defaultLocal.default ?? {}

  setHtmlPageLang(locale.lang)
  // localeStore.setCurrentLocale(locale.lang)

  return {
    legacy: false, // 是否启用 Vue 2.x 的兼容模式（默认为 false）
    locale: locale.lang, // 当前语言环境（例如：zh-CN）
    fallbackLocale: locale.lang, // 回退语言环境（例如：en）
    messages: {
      [locale.lang]: message // 只注册当前需要的国际化语言环境
    },
    availableLocales: localeMap.map((v) => v.lang), // 所有可选的语言环境数组，用于在用户界面设置中显示（例如：['zh-CN', 'en']）
    sync: true, // 是否开启同步加载方式（默认为 true）
    silentTranslationWarn: true, // 是否静默处理翻译警告（默认为 true）
    missingWarn: false, // 是否忽略缺失翻译警告（默认为 false）
    silentFallbackWarn: true // 是否静默处理回退警告（默认为 false）
  }
}

export let i18n: ReturnType<typeof createI18n>
export const setupI18n = async (app: App<Element>) => {
  const options = await createI18nOptions()
  i18n = createI18n(options) as I18n
  app.use(i18n)
}
