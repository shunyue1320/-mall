import { createApp } from 'vue'
import 'element-plus/dist/index.css'
import App from './App.vue'
import { setupRouter } from './router'
import { setupElementPlus } from '@/plugins/elementPlus'
import { setupPermission } from './directives'



const bootstrap = async () => {
  const app = createApp(App)
  // 注册路由
  setupRouter(app)
  // 全局引入一些 element-plus 组件
  setupElementPlus(app)
  // 注册权限指令
  setupPermission(app)
  app.mount('#app')
}

bootstrap()
