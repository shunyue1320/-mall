<script setup lang="ts">
import { reactive, ref, unref } from 'vue'
import { ElButton, ElInput, FormRules, ElMessage } from 'element-plus'
import { Form } from '@/components/Form'
import { useValidator } from '@/hooks/web/useValidator'
import { useForm } from '@/hooks/web/useForm'
import { useI18n } from '@/hooks/web/useI18n'
import type { FormSchema } from '@/types/form'
import { registerApi } from '@/api/login'

const { t } = useI18n()
const emit = defineEmits(['to-login'])
const { required } = useValidator()
const { register, elFormRef, methods } = useForm()
const loading = ref(false)

const rules: FormRules = {
  username: [required()],
  password: [required()],
  check_password: [required()],
  code: [required()]
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
    value: '',
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
    value: '',
    component: 'InputPassword',
    colProps: {
      span: 24
    },
    componentProps: {
      style: {
        width: '100%'
      },
      strength: true,
      placeholder: t('login.passwordPlaceholder')
    }
  },
  {
    field: 'check_password',
    label: t('login.checkPassword'),
    value: '',
    component: 'InputPassword',
    colProps: {
      span: 24
    },
    componentProps: {
      style: {
        width: '100%'
      },
      strength: true,
      placeholder: t('login.passwordPlaceholder')
    }
  },
  // {
  //   field: 'code',
  //   label: t('login.code'),
  //   colProps: {
  //     span: 24
  //   }
  // },
  {
    field: 'register',
    colProps: {
      span: 24
    }
  }
])

const loginRegister = async () => {
  const formRef = unref(elFormRef)
  formRef?.validate(async (valid) => {
    if (valid) {
      const { getFormData } = methods
      const formData = await getFormData()
      if (formData.password !== formData.check_password) {
        ElMessage.warning('两次密码不一致！')
        return
      }

      try {
        loading.value = true
        await registerApi(formData)
        ElMessage.success('注册成功！')
        toLogin()
      } finally {
        loading.value = false
      }
    }
  })
}

const toLogin = () => {
  emit('to-login')
}
</script>

<template>
  <Form
    :schema="schema"
    :rules="rules"
    label-position="top"
    hide-required-asterisk
    size="large"
    class="p-5 py-20 sm:px-10 rounded-3xl border border-slate-200 dark:border-slate-600"
    @register="register"
  >
    <template #title>
      <h2 class="text-2xl font-bold text-center w-full">{{ t('login.register') }}</h2>
    </template>

    <template #code="form">
      <div class="w-full flex">
        <ElInput v-model="form['code']" :placeholder="t('login.codePlaceholder')" />
      </div>
    </template>

    <template #register>
      <div class="w-full">
        <ElButton type="primary" class="w-full" :loading="loading" @click="loginRegister">
          {{ t('login.register') }}
        </ElButton>
      </div>
      <div class="w-full mt-4">
        <ElButton class="w-full" @click="toLogin">
          {{ t('login.hasUser') }}
        </ElButton>
      </div>
    </template>
  </Form>
</template>
