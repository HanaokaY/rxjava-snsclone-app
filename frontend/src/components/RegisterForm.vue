<template>
  <div id="register">
    <div class="register-container">
      <h1>会員登録</h1>
      <el-form @submit.native.prevent="register" class="form">
        <el-form-item label="ユーザー名:">
          <el-input type="text" v-model="username" required></el-input>
        </el-form-item>
        <el-form-item label="パスワード:">
          <el-input type="password" v-model="password" required></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">登録</el-button>
        </el-form-item>
      </el-form>
      <el-alert v-if="message" :title="message" type="info" show-icon :closable="false"></el-alert>
    </div>
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
                  this.message = `ようこそ、${this.username}さん！登録が完了しました。`;
                  this.$emit('registration-success');
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
#register {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f3f4f6;
}

.register-container {
  width: 450px;
  padding: 40px;
  border-radius: 12px;
  box-shadow: 0px 0px 15px 0px rgba(0, 0, 0, 0.08);
  background-color: #ffffff;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-weight: 500;
}

.form {
  margin-top: 20px;
}

el-button {
  width: 100%;
}
</style>