<script setup lang="ts">
import { ref } from 'vue'
import { ElSwitch } from 'element-plus'
import { useAppStore } from '@/store/modules/app'
import { useDesign } from '@/hooks/web/useDesign'
import { useIcon } from '@/hooks/web/useIcon'

const appStore = useAppStore()
const isDark = ref(appStore.getIsDark)

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('theme-switch')

const Sun = useIcon({ icon: 'emojione-monotone:sun', color: '#fde047' })
const CrescentMoon = useIcon({ icon: 'emojione-monotone:crescent-moon', color: '#fde047' })

const blackColor = 'var(--el-color-primary-light-8)'

const themeChange = (val: boolean) => {
  appStore.setIsDark(val)
}
</script>

<template>
  <ElSwitch
    :class="prefixCls"
    v-model="isDark"
    inline-prompt
    :border-color="blackColor"
    :inactive-color="blackColor"
    :active-color="blackColor"
    :active-icon="Sun"
    :inactive-icon="CrescentMoon"
    @change="themeChange"
  />
</template>

<style lang="less" scoped>
:deep(.el-switch__core .el-switch__inner .is-icon) {
  overflow: visible;
}
</style>
