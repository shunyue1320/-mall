<script setup lang="ts">
import { ref } from 'vue'
import { ThemeSwitch } from '@/components/ThemeSwitch'
import { useAppStore } from '@/store/modules/app'
import { useDesign } from '@/hooks/web/useDesign'
import { useI18n } from '@/hooks/web/useI18n'
import { underlineToHump } from '@/utils'

const appStore = useAppStore()

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('login')

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
  <div :class="prefixCls" class="h-full relative xl:bg-v-dark">
    <div class="relative h-full flex mx-auto">
      <div :class="`${prefixCls}__left flex-1 relative lg:block hidden`">
        <div class="flex items-center relative text-white p-5">
          <div class="p-2 mr-4 rounded-lg bg-white">
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
        <div class="flex justify-between items-center text-white 2xl:justify-end xl:justify-end">
          <div class="flex items-center 2xl:hidden xl:hidden">
            <img src="@/assets/imgs/logo.png" alt="logo" class="w-12 h-12 mr-2" />
            <span class="text-20px font-bold">{{ underlineToHump(appStore.getTitle) }}</span>
          </div>

          <div class="flex justify-end items-center space-x-2">
            <ThemeSwitch />
          </div>
        </div>
        <Transition appear enter-active-class="animate__animated animate__bounceInRight">
          <div
            class="h-full flex items-center m-auto w-[100%] @2xl:max-w-500px @xl:max-w-500px @md:max-w-500px @lg:max-w-500px"
          >
            <!-- <LoginForm
            v-if="isLogin"
            class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)"
            @to-register="toRegister"
          />
          <RegisterForm
            v-else
            class="p-20px h-auto m-auto <xl:(rounded-3xl light:bg-white)"
            @to-login="toLogin"
          /> -->
          </div>
        </Transition>
      </div>
    </div>
  </div>
</template>

<style lang="less" scoped>
@prefix-cls: ~'@{namespace}-login';

.@{prefix-cls} {
  &__left {
    &::before {
      position: absolute;
      top: 0;
      left: 0;
      z-index: -1;
      width: 100%;
      height: 100%;
      background-color: #8dcfff;
      content: '';
    }
  }
}
</style>
