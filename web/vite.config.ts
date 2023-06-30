import { resolve } from 'path'
import { loadEnv } from 'vite'
import EslintPlugin from 'vite-plugin-eslint'
import vue from '@vitejs/plugin-vue'
import type { ConfigEnv, UserConfig } from 'vite'
import DefineOptions from 'unplugin-vue-define-options/vite'
import progress from 'vite-plugin-progress'

const root = process.cwd()

function pathResolve(dir) {
  return resolve(root, '.', dir)
}

// https://vitejs.dev/config/
export default ({ command, mode }: ConfigEnv): UserConfig => {
  let env = {} as any
  env = loadEnv(mode, root)

  return {
    base: env.VITE_BASE_PATH,
    plugins: [
      vue(),
      progress(),
      EslintPlugin({
        cache: false,
        include: ['src/**/*.vue', 'src/**/*.ts', 'src/**/*.tsx'] // 检查的文件
      }),
      DefineOptions()
    ],
    resolve: {
      extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.less', '.css', '.vue'],
      alias: [
        {
          find: /\@\//,
          replacement: `${pathResolve('src')}/`
        }
      ]
    },
    server: {
      host: '0.0.0.0',
      port: 4321,
      proxy: {
        '/api': {
          target: 'http://127.0.0.1:8080',
          changeOrigin: true,
          rewrite: (_path) => _path.replace(/^\/api/, '')
        }
      },
      hmr: {
        overlay: false
      }
    }
  }
}
