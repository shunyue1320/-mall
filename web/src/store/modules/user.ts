import { defineStore } from 'pinia'
import { useCache } from '@/hooks/web/useCache'
import { LoginPayload, LoginResult, login, Menu, userInfo } from '@/api/login'
import { store } from '../index'
import { asyncRouterMap, constantRouterMap } from '@/router/index'

const { wsCache } = useCache()

interface userState {
  token: string
  name: string
  avatar: string
  menus: Menu[]
  roles?: string[]
}

export const useUserStore = defineStore('user', {
  state: (): userState => {
    return {
      token: wsCache.get('token'),
      name: '',
      avatar: '',
      menus: [],
      roles: []
    }
  },
  getters: {
    routes() {
      const accessedRouters = asyncRouterMap.filter((v) => {
        if (hasPermission(this.menus, v)) {
          if (v.children && v.children.length > 0) {
            v.children = v.children.filter((child) => {
              if (hasPermission(this.menus, child)) {
                return child
              }
              return false
            })
            return v
          } else {
            return v
          }
        }
        return false
      })
      sortRouters(accessedRouters)
      return accessedRouters
    }
  },
  actions: {
    setToken(token: string): void {
      this.token = token
      wsCache.set('token', token)
    },
    async login(formData: LoginPayload): Promise<LoginResult> {
      const data = await login(formData)
      this.setToken(data.token)
      return data
    },
    // 获取用户信息
    async userInfo() {
      const data = await userInfo()
      const { username, icon, menus, roles } = data
      this.name = username
      this.avatar = icon
      this.menus = menus
      this.roles = roles
      return data
    },
    logout() {
      wsCache.delete('token')
    }
  }
})

//判断是否有权限访问该菜单
function hasPermission(menus, route) {
  if (route.name) {
    const currMenu = getMenu(route.name, menus)
    if (currMenu != null) {
      //设置菜单的标题、图标和可见性
      if (currMenu.title != null && currMenu.title !== '') {
        route.meta.title = currMenu.title
      }
      if (currMenu.icon != null && currMenu.title !== '') {
        route.meta.icon = currMenu.icon
      }
      if (currMenu.hidden != null) {
        route.hidden = currMenu.hidden !== 0
      }
      if (currMenu.sort != null && currMenu.sort !== '') {
        route.sort = currMenu.sort
      }
      return true
    } else {
      route.sort = 0
      if (route.hidden !== undefined && route.hidden === true) {
        route.sort = -1
        return true
      } else {
        return false
      }
    }
  } else {
    return true
  }
}

//根据路由名称获取菜单
function getMenu(name, menus) {
  for (let i = 0; i < menus.length; i++) {
    const menu = menus[i]
    if (name === menu.name) {
      return menu
    }
  }
  return null
}

//对菜单进行排序
function sortRouters(accessedRouters) {
  for (let i = 0; i < accessedRouters.length; i++) {
    const router = accessedRouters[i]
    if (router.children && router.children.length > 0) {
      router.children.sort(compare('sort'))
    }
  }
  accessedRouters.sort(compare('sort'))
}
//降序比较函数
function compare(p) {
  return function (m, n) {
    const a = m[p]
    const b = n[p]
    return b - a
  }
}

export const useUserStoreWithOut = () => {
  return useUserStore(store)
}
