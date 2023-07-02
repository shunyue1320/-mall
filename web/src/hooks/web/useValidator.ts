import { useI18n } from '@/hooks/web/useI18n'

const { t } = useI18n()

export const useValidator = () => {
  const required = (message?: string) => {
    return {
      required: true,
      message: message || t('common.required')
    }
  }
  return {
    required
  }
}
