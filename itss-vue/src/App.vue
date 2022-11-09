<template>
  <div id="vue-itss">
    <router-view v-if="isRouterAlive"/>
  </div>
</template>

<script>
  export default {
    name: 'App',
    mounted() {},
    data () {
      return {
        isRouterAlive: true
      }
    },
    methods: {
      reload () {
        this.isRouterAlive = false
        this.$nextTick(() => (this.isRouterAlive = true))
      }
    }
  }
</script>
