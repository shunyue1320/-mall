<script setup lang="ts">
import { ref } from 'vue'
import { ThemeSwitch } from '@/components/ThemeSwitch'
import { useAppStore } from '@/store/modules/app'
import { useDesign } from '@/hooks/web/useDesign'
import { useI18n } from '@/hooks/web/useI18n'
import { underlineToHump } from '@/utils'
import { LoginForm, RegisterForm } from './components'

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')

const appStore = useAppStore()

const { t } = useI18n()

const isLogin = ref(true)
const toLogin = () => {
  isLogin.value = true
}
const toRegister = () => {
  isLogin.value = false
}
</script>

<template>
  <div :class="prefixCls" class="h-full relative">
    <div class="relative h-full flex mx-auto">
      <div class="lg:block flex-1 relative hidden bg-sky-300 dark:bg-sky-950">
        <div class="flex items-center relative text-white p-5">
          <div class="p-2 mr-4 rounded-lg bg-white border border-slate-100">
            <img src="@/assets/imgs/logo.png" key="1" alt="logo" class="w-10 h-10" />
          </div>
          <h1 class="text-2xl font-bold">{{ underlineToHump(appStore.getTitle) }}</h1>
        </div>
        <div class="flex justify-center items-center h-[calc(100%-60px)]">
          <TransitionGroup
            appear
            tag="div"
            enter-active-class="animate__animated animate__bounceInLeft"
          >
            <img src="@/assets/svgs/login-box-bg.svg" key="1" alt="" class="w-96" />
            <div class="text-3xl text-white" key="2">{{ t('login.welcome') }}</div>
            <div class="mt-5 font-normal text-white text-14px" key="3">
              {{ t('login.message') }}
            </div>
          </TransitionGroup>
        </div>
      </div>
      <div class="relative flex-1 p-2 sm:p-5 dark:bg-slate-800">
        <div class="flex justify-between items-center">
          <div class="flex items-center lg:opacity-0">
            <div class="p-2 mr-4 bg-white rounded-lg border border-slate-100">
              <img
                src="@/assets/imgs/logo.png"
                key="1"
                alt="logo"
                class="w-5 h-5 sm:w-10 sm:h-10"
              />
            </div>
            <span class="sm:text-xl font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
          </div>

          <div class="flex justify-end items-center space-x-2">
            <ThemeSwitch />
          </div>
        </div>
        <Transition appear enter-active-class="animate__animated animate__bounceInRight">
          <div class="h-full flex items-center m-auto w-full sm:max-w-lg">
            <LoginForm v-if="isLogin" @to-register="toRegister" />
            <RegisterForm
              v-else
              class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)"
              @to-login="toLogin"
            />
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>
