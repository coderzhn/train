<template>
  <a-button type="primary" @click="showModal">新增</a-button>
  <a-modal v-model:visible="visible" title="乘车人" @ok="handleOK">
    <p>Some contents...</p>
    <p>Some contents...</p>
    <p>Some contents...</p>
  </a-modal>
</template>
<script>
import {defineComponent,ref} from "vue";

export default defineComponent({
  setup(){
    const visible = ref(false);

    const showModal = () => {
      visible.value = true;
    };

    const handleOK = e => {
      console.log(e);
      visible.value = false;
    };
    return{
      visible,
      showModal,
      handleOK,
    };
  },
});
</script>

<style>
</style>