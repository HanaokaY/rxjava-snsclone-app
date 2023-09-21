<template>
  <div id="app">
    <button @click="fetchData">Fetch Data</button>
    <div v-if="message">{{ message }}</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      message: null,
    };
  },
  methods: {
    async fetchData() {
      try {
        const response = await fetch("http://localhost:8080/test");
        const data = await response.text();
        this.message = data;
      } catch (error) {
        console.error("There was a problem fetching data:", error);
      }
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
