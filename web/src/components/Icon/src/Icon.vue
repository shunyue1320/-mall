<script setup lang="ts">
import { computed, nextTick, ref, unref, watch } from 'vue'
import { ElIcon } from 'element-plus'
import { Icon } from '@iconify/vue'
import { useDesign } from '@/hooks/web/useDesign'
import { propTypes } from '@/utils/propTypes'

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('icon')

const props = defineProps({
  icon: propTypes.string,
  color: propTypes.string,
  size: propTypes.number.def(16) // 默认 16px
})

const isLocal = computed(() => props.icon.startsWith('svg-icon:'))

const symbolId = computed(() => {
  return unref(isLocal) ? `#icon-${props.icon.split('svg-icon:')[1]}` : props.icon
})

const getIconifyStyle = computed(() => {
  const { color, size } = props
  return {
    fontSize: `${size}px`,
    color
  }
})
</script>

<template>
  <ElIcon :class="prefixCls" :size="size" :color="color">
    <svg v-if="isLocal" aria-hidden="true">
      <use :xlink:href="symbolId" />
    </svg>
    <span v-else :class="$attrs.class" :style="getIconifyStyle">
      <Icon :icon="symbolId" />
    </span>
  </ElIcon>
</template>
