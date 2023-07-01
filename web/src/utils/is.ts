export const isDark = (): boolean => {
  return window.matchMedia('(prefers-color-scheme: dark)').matches
}
