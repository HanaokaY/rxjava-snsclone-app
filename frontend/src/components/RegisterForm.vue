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
      async register() {
          const user = {
              username: this.username,
              password: this.password,
          };

          try {
              const response = await fetch("http://localhost:8080/register", {
                  method: 'POST',
                  headers: {
                  'Content-Type': 'application/json',
                  },
                  body: JSON.stringify(user),
              });

              if (response.ok) {
                  const responseData = await response.json();
                  // JSON配列を文字列に変換してmessageにセット
                  this.message = JSON.stringify(responseData, null, 2);
              } else {
                  const responseData = await response.json();
                  this.message = responseData.message || 'エラーが発生しました。';
              }
          } catch (error) {
              this.message = 'サーバーエラーが発生しました。';
          }
      },
    }
};
</script>

<style scoped>
</style>
