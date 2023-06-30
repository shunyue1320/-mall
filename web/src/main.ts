import { createApp } from 'vue'
import 'element-plus/dist/index.css'
import App from './App.vue'
import { setupRouter } from './router'



const bootstrap = async () => {
  const app = createApp(App)
  // 注册路由
  setupRouter(app)
  app.mount('#app')
}

bootstrap()
