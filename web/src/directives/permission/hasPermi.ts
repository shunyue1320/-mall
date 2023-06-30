import { App, Directive, DirectiveBinding } from 'vue'

// 全部权限
// const all_permission = ['*.*.*']

const hasPermission = (value: string | string[]): boolean => {
  // const permissions = ['*.*.*', ...value]
  if (!value) {
    throw new Error('请设置操作权限值')
  }
  return true
}

function hasPermi(el: Element, binding: DirectiveBinding) {
  const value = binding.value

  const flag = hasPermission(value)
  if (!flag) {
    el.parentNode?.removeChild(el)
  }
}

const mounted = (el: Element, binding: DirectiveBinding<any>) => {
  hasPermi(el, binding)
}

const permiDirective: Directive = {
  mounted
}

export const setupPermissionDirective = (app: App<Element>) => {
  app.directive('hasPermi', permiDirective)
}

export default permiDirective
