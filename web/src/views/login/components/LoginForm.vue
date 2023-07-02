<script setup lang="ts">
import { reactive, ref, unref, watch } from 'vue'
import { ElButton, ElCheckbox, ElLink } from 'element-plus'
import { RouteLocationNormalizedLoaded, useRouter } from 'vue-router'
import { Form } from '@/components/Form'
import { useI18n } from '@/hooks/web/useI18n'
import { useForm } from '@/hooks/web/useForm'
import { FormSchema } from '@/types/form'
import { useValidator } from '@/hooks/web/useValidator'
import { useUserStore } from '@/store/modules/user'
import { LoginPayload } from '@/api/login'

const { t } = useI18n()
const emit = defineEmits(['to-register'])
const userStore = useUserStore()
const { required } = useValidator()

const { currentRoute, addRoute, push } = useRouter()
const redirect = ref<string>('') // 登录后重定向页面
watch(
  () => currentRoute.value,
  (route: RouteLocationNormalizedLoaded) => {
    redirect.value = route?.query?.redirect as string
  },
  {
    immediate: true
  }
)

const rules = {
  username: [required()],
  password: [required()]
}

const schema = reactive<FormSchema[]>([
  {
    field: 'title',
    colProps: {
      span: 24
    }
  },
  {
    field: 'username',
    label: t('login.username'),
    value: 'admin',
    component: 'Input',
    colProps: {
      span: 24
    },
    componentProps: {
      placeholder: t('login.usernamePlaceholder')
    }
  },
  {
    field: 'password',
    label: t('login.password'),
    value: 'admin',
    component: 'InputPassword',
    colProps: {
      span: 24
    },
    componentProps: {
      style: {
        width: '100%'
      },
      placeholder: t('login.passwordPlaceholder')
    }
  },
  {
    field: 'tool',
    colProps: {
      span: 24
    }
  },
  {
    field: 'login',
    colProps: {
      span: 24
    }
  },
  {
    field: 'other',
    component: 'Divider',
    label: t('login.otherLogin'),
    componentProps: {
      contentPosition: 'center'
    }
  },
  {
    field: 'otherIcon',
    colProps: {
      span: 24
    }
  }
])

const iconSize = 30
const iconColor = '#999'
const remember = ref(false)
const loading = ref(false)

const { register, elFormRef, methods } = useForm()

// 登录
const signIn = async () => {
  const formRef = unref(elFormRef)
  await formRef?.validate(async (isValid) => {
    if (!isValid) return

    loading.value = true
    const { getFormData } = methods
    const formData = await getFormData<LoginPayload>()

    try {
      await userStore.login(formData)
      // const data = await userStore.userInfo()
      // const { menus } = data
      push({ path: '/' })
    } finally {
      loading.value = false
    }
  })
}

const toRegister = () => {
  emit('to-register')
}
</script>

<template>
  <Form
    :schema="schema"
    :rules="rules"
    label-position="top"
    hide-required-asterisk
    class="p-5 py-20 sm:px-10 rounded-3xl border border-slate-200 dark:border-slate-600"
    @register="register"
  >
    <template #title>
      <h2 class="text-2xl font-bold text-center w-full">{{ t('login.login') }}</h2>
    </template>

    <template #tool>
      <div class="flex justify-between items-center w-[100%]">
        <ElCheckbox v-model="remember" :label="t('login.remember')" size="small" />
        <ElLink type="primary" :underline="false">{{ t('login.forgetPassword') }}</ElLink>
      </div>
    </template>

    <template #login>
      <div class="w-full">
        <ElButton :loading="loading" type="primary" class="w-[100%]" @click="signIn">
          {{ t('login.login') }}
        </ElButton>
      </div>
      <div class="w-full mt-4">
        <ElButton class="w-[100%]" @click="toRegister">
          {{ t('login.register') }}
        </ElButton>
      </div>
    </template>

    <template #otherIcon>
      <div class="flex justify-between w-[100%]">
        <Icon
          icon="ant-design:github-filled"
          :size="iconSize"
          class="cursor-pointer anticon"
          :color="iconColor"
        />
        <Icon
          icon="ant-design:wechat-filled"
          :size="iconSize"
          class="cursor-pointer anticon"
          :color="iconColor"
        />
        <Icon
          icon="ant-design:alipay-circle-filled"
          :size="iconSize"
          :color="iconColor"
          class="cursor-pointer anticon"
        />
        <Icon
          icon="ant-design:weibo-circle-filled"
          :size="iconSize"
          :color="iconColor"
          class="cursor-pointer anticon"
        />
      </div>
    </template>
  </Form>
</template>

<style lang="less" scoped>
:deep(.anticon) {
  &:hover {
    color: var(--el-color-primary) !important;
  }
}
</style>
