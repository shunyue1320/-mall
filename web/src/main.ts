import '@/styles/index.less'
import 'animate.css'

import { createApp } from 'vue'
import App from './App.vue'
import { setupI18n } from '@/plugins/vueI18n'
import { setupRouter } from './router'
import { setupStore } from './store'
import { setupGlobComponent } from '@/components/index'
import { setupElementPlus } from '@/plugins/elementPlus'
import { setupPermission } from './directives'



const bootstrap = async () => {
  const app = createApp(App)

  // 注入国际化语言
  await setupI18n(app)
  // 注入 pinia 状态管理
  setupStore(app)
  // 注册路由
  setupRouter(app)
  // 注册全局组件
  setupGlobComponent(app)
  // 全局引入一些 element-plus 组件
  setupElementPlus(app)
  // 注册权限指令
  setupPermission(app)
  app.mount('#app')
}

bootstrap()
