<script lang="tsx">
import { PropType, computed, defineComponent, onMounted, ref, unref, watch } from 'vue'
import { ElCol, ElForm, ElFormItem, ElRow, ElTooltip } from 'element-plus'
import { set } from 'lodash-es'
import { FormSchema, FormSetPropsType } from '@/types/form'
import { propTypes } from '@/utils/propTypes'
import { useDesign } from '@/hooks/web/useDesign'
import { FormProps } from './types'
import { getSlot } from '@/utils/tsxHelper'
import { componentMap } from './componentMap'
import {
  setGridProp,
  setItemComponentSlots,
  setFormItemSlots,
  setTextPlaceholder,
  setComponentProps,
  initModel
} from './helper'
import { useRenderSelect } from './components/useRenderSelect'
import { useRenderRadio } from './components/useRenderRadio'
import { useRenderCheckbox } from './components/useRenderCheckbox'
import { Icon } from '@/components/Icon'

const { getPrefixCls } = useDesign()
const prefixCls = getPrefixCls('form')

export default defineComponent({
  name: 'Form',
  props: {
    // 生成Form的布局结构数组
    schema: {
      type: Array as PropType<FormSchema[]>,
      default: () => []
    },
    // 是否需要栅格布局
    isCol: propTypes.bool.def(true),
    // 表单数据对象
    model: {
      type: Object as PropType<Recordable>,
      default: () => ({})
    },
    // 是否自动设置placeholder
    autoSetPlaceholder: propTypes.bool.def(true),
    // 是否自定义内容
    isCustom: propTypes.bool.def(false),
    // 表单label宽度
    labelWidth: propTypes.oneOfType([String, Number]).def('auto')
  },

  emits: ['register'],

  setup(props, { slots, expose, emit }) {
    // element form 实例
    const elFormRef = ref<ComponentRef<typeof ElForm>>()

    onMounted(() => {
      emit('register', unref(elFormRef)?.$parent, unref(elFormRef))
    })

    // 表单数据
    const formModel = ref<Recordable>({})

    // useForm传入的props
    const outsideProps = ref<FormProps>({})
    const mergeProps = ref<FormProps>({})
    const getProps = computed(() => {
      const propsObj = { ...props }
      Object.assign(propsObj, unref(mergeProps))
      return propsObj
    })

    const setValues = (data: Recordable = {}) => {
      formModel.value = Object.assign(unref(data))
    }

    const setProps = (props: FormProps) => {
      mergeProps.value = Object.assign(unref(mergeProps), props)
      outsideProps.value = props
    }

    const delSchema = (field: string) => {
      const { schema } = unref(getProps)
      const index = schema.findIndex((v) => v.field === field)
      if (index > -1) {
        schema.splice(index, 1)
      }
    }

    const addSchema = (formSchema: FormSchema, index?: number) => {
      const { schema } = unref(getProps)
      if (index !== void 0) {
        schema.splice(index, 0, formSchema)
      }
      schema.push(formSchema)
    }

    const setSchema = (schemaProps: FormSetPropsType[]) => {
      const { schema } = unref(getProps)
      for (const v of schema) {
        for (const item of schemaProps) {
          if (v.field === item.field) {
            set(v, item.path, item.value)
          }
        }
      }
    }

    const getElFormRef = (elFormRef): ComponentRef<typeof ElForm> => {
      return unref(elFormRef) as ComponentRef<typeof ElForm>
    }

    expose({
      formModel,
      setValues,
      setProps,
      delSchema,
      addSchema,
      setSchema,
      getElFormRef
    })

    // 监听表单结构化数组，重新生成formModel
    watch(
      () => unref(getProps).schema,
      (schema = []) => {
        formModel.value = initModel(schema, unref(formModel))
      },
      {
        immediate: true,
        deep: true
      }
    )

    // 过滤传入Form组件的属性
    const getFormBindValue = () => {
      // 避免在标签上传入不属于 ElForm 的属性
      const delKeys = ['schema', 'isCol', 'autoSetPlaceholder', 'isCustom', 'model']
      const props = { ...unref(getProps) }
      for (const key in props) {
        if (delKeys.indexOf(key) !== -1) {
          delete props[key]
        }
      }
      return props
    }

    const renderWrap = () => {
      const { isCol } = unref(getProps)
      const content = isCol ? (
        <ElRow gutter={20}>{renderFormItemWrap()}</ElRow>
      ) : (
        renderFormItemWrap()
      )
      return content
    }

    // 是否要渲染el-col
    const renderFormItemWrap = () => {
      // hidden属性表示隐藏，不做渲染
      const { schema = [], isCol } = unref(getProps)

      return schema
        .filter((v) => !v.hidden)
        .map((item) => {
          // 如果是 Divider 组件，需要自己占用一行
          const isDivider = item.component === 'Divider'
          const Com = componentMap['Divider'] as ReturnType<typeof defineComponent>
          return isDivider ? (
            <Com {...{ contentPosition: 'left', ...item.componentProps }}>{item?.label}</Com>
          ) : isCol ? (
            // 如果需要栅格，需要包裹 ElCol
            <ElCol {...setGridProp(item.colProps)}>{renderFormItem(item)}</ElCol>
          ) : (
            renderFormItem(item)
          )
        })
    }

    // 渲染formItem
    const renderFormItem = (item: FormSchema) => {
      // 单独给只有options属性的组件做判断
      const notRenderOptions = ['SelectV2', 'Cascader', 'Transfer']
      const slotsMap: Recordable = {
        ...setItemComponentSlots(slots, item?.componentProps?.slots, item.field)
      }

      if (
        item?.component !== 'SelectV2' &&
        item?.component !== 'Cascader' &&
        item?.componentProps?.options
      ) {
        slotsMap.default = () => renderOptions(item)
      }

      // 获取当前 FormItem 的所有插槽
      const formItemSlots: Recordable = setFormItemSlots(slots, item.field)
      // 如果有 labelMessage，自动使用插槽渲染
      if (item?.labelMessage) {
        formItemSlots.label = () => {
          return (
            <>
              <span>{item.label}</span>
              <ElTooltip placement="right" raw-content>
                {{
                  content: () => <span v-html={item.labelMessage}></span>,
                  default: () => (
                    <Icon
                      icon="ep:warning"
                      size={16}
                      color="var(--el-color-primary)"
                      class="ml-2px relative top-1px"
                    ></Icon>
                  )
                }}
              </ElTooltip>
            </>
          )
        }
      }

      return (
        <ElFormItem {...(item.formItemProps || {})} prop={item.field} label={item.label || ''}>
          {{
            ...formItemSlots,
            default: () => {
              const Com = componentMap[item.component as string] as ReturnType<
                typeof defineComponent
              >

              const { autoSetPlaceholder } = unref(getProps)

              return slots[item.field] ? (
                // 用户有写插槽获取使用
                getSlot(slots, item.field, formModel.value)
              ) : (
                // 用户没写插槽自己创建
                <Com
                  vModel={formModel.value[item.field]}
                  {...(autoSetPlaceholder && setTextPlaceholder(item))}
                  {...setComponentProps(item)}
                  style={item.componentProps?.style}
                  {...(notRenderOptions.includes(item?.component as string) &&
                  item?.componentProps?.options
                    ? { options: item?.componentProps?.options || [] }
                    : {})}
                >
                  {/* FormItem 的子插槽 */}
                  {{ ...slotsMap }}
                </Com>
              )
            }
          }}
        </ElFormItem>
      )
    }

    // 渲染options
    const renderOptions = (item: FormSchema) => {
      switch (item.component) {
        case 'Select':
          const { renderSelectOptions } = useRenderSelect(slots)
          return renderSelectOptions(item)
        case 'Radio':
        case 'RadioButton':
          const { renderRadioOptions } = useRenderRadio()
          return renderRadioOptions(item)
        case 'Checkbox':
        case 'CheckboxButton':
          const { renderCheckboxOptions } = useRenderCheckbox()
          return renderCheckboxOptions(item)
        default:
          break
      }
    }

    return () => (
      <ElForm
        ref={elFormRef}
        {...getFormBindValue()}
        model={props.isCustom ? props.model : formModel}
        class={prefixCls}
      >
        {{
          default: () => {
            const { isCustom } = unref(getProps)
            // 自定义直接取 default 插槽内容
            return isCustom ? getSlot(slots, 'default') : renderWrap()
          }
        }}
      </ElForm>
    )
  }
})
</script>
