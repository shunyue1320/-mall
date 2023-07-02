import request from '@/api/axios'

export interface LoginPayload {
  username: string
  password: string
}

export interface LoginResult {
  token: string
  tokenHead: string
}

export const login = (data: LoginPayload): Promise<LoginResult> => {
  return request.post({ url: '/admin/login', data })
}

export interface Menu {
  id: number
  parentId: number // 父级ID
  createTime: string // 创建时间
  title: string // 菜单名称
  level: number // 菜单级数
  sort: number // 菜单排序
  name: string // 名称
  icon: string // 前端图标
  hidden: number // 前端隐藏
}

export interface UserInfoResult {
  username: string
  menus: Menu[]
  icon: string
  roles?: string[]
}

export const userInfo = (): Promise<UserInfoResult> => {
  return request.get({ url: '/admin/info' })
}
