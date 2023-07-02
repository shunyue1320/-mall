import axios from 'axios'
import type { AxiosInstance, InternalAxiosRequestConfig, AxiosError } from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStoreWithOut } from '@/store/modules/user'

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASEPATH, // api 的 base_url
  timeout: 6000 // 请求超时时间
})

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const userStore = useUserStoreWithOut()
    const token = userStore.token
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error: AxiosError) => {
    console.log('request====', error)
    Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const userStore = useUserStoreWithOut()
    const res = response.data
    if (response.config.responseType === 'blob') {
      // 如果是文件流，直接过
      return response
    } else if (res.code === 200) {
      return res.data
    } else {
      ElMessage.error(res.message)

      if (res.code === 401) {
        ElMessageBox.confirm('你已被登出，去重新登录！', '确定登出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          userStore.logout()
          location.reload() // 为了重新定位到登录页
        })
      }

      return Promise.reject(new Error(res.message || 'Error'))
    }
  },
  (error: AxiosError) => {
    console.log('response====', error)
    ElMessage.error(error.message || 'Error')
    return Promise.reject(error)
  }
)

export default service
