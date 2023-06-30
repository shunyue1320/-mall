<script lang="tsx">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'Layout',
  setup() {
    return () => <div>Layout</div>
  }
})
</script>

<style lang="less" scoped>
@prefix-cls: ~'@{namespace}-layout';

.@{prefix-cls} {
  background-color: var(--app-content-bg-color);
  :deep(.@{elNamespace}-scrollbar__view) {
    height: 100% !important;
  }
}
</style>
