import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useGlobalFilterStore = defineStore('globalFilter', () => {
  // 4 个独立筛选器：型号 / 专业 / 子系统 / 试验基地，组合作用于全局上下文
  const model = ref('')
  const profession = ref('')
  const subsystem = ref('')
  const testBase = ref('')

  const setModel = (val: string) => {
    model.value = val
  }

  const setProfession = (val: string) => {
    profession.value = val
  }

  const setSubsystem = (val: string) => {
    subsystem.value = val
  }

  const setTestBase = (val: string) => {
    testBase.value = val
  }

  return {
    model,
    profession,
    subsystem,
    testBase,
    setModel,
    setProfession,
    setSubsystem,
    setTestBase
  }
})
