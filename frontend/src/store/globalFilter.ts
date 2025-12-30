import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useGlobalFilterStore = defineStore('globalFilter', () => {
  // Cascader values: [Model, Profession, Subsystem, TestBase]
  // We can store them as separate values or an array.
  // Requirement says "4 Cascader Select". Usually "Model" might be level 1, etc. or independent.
  // The requirement says "4 separate cascader select" or "4 items corresponding to...".
  // "4 个级联下拉框 (Cascader Select)" usually implies 4 separate controls, but if they depend on each other, they might need logic.
  // Let's assume they are separate but affect the global context.

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
