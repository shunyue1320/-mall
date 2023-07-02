export const isFunction = (val: unknown): val is Function => {
  return typeof val === 'function'
}

export const isDark = (): boolean => {
  return window.matchMedia('(prefers-color-scheme: dark)').matches
}
