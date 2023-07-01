import type { App } from 'vue'
import { Icon } from './Icon'

export const setupGlobComponent = (app: App<Element>): void => {
  app.component('Icon', Icon)
}
