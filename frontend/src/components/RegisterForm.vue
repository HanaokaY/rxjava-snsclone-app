<template>
  <div id="register">
    <h1>会員登録</h1>
    <form @submit.prevent="register">
      <div>
        <label for="username">ユーザー名:</label>
        <input type="text" id="username" v-model="username" required />
      </div>
      <div>
        <label for="password">パスワード:</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <button type="submit">登録</button>
    </form>
    <p v-if="message">{{ message }}</p>
  </div>
</template>

<script>
export default {
    name: 'RegisterForm',
    data() {
        return {
        username: '',
        password: '',
        message: ''
        };
    },
    methods: {
        register() {
        this.message = `ようこそ、${this.username}さん！`;
        }
    }
};
</script>

<style scoped>
</style>
