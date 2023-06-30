import variables from '@/styles/variables.module.less'

export const useDesign = () => {
  const getPrefixCls = (scope: string) => {
    return `${variables.namespace}-${scope}`
  }

  return {
    variables,
    getPrefixCls
  }
}
