<template>
  <div>
    <!-- ツイート入力フォームを追加 -->
    <el-input
      type="textarea"
      placeholder="こちらにツイート内容を入力してください。"
      v-model="content"
      rows="4"
      show-word-limit
      maxlength="100"
    ></el-input>
    <el-button type="primary" icon="el-icon-edit" @click="postTweet">Tweet</el-button>
    <!-- 投稿結果のメッセージを表示するエリアを追加 -->
    <el-alert v-if="message" :title="message" type="info" show-icon :closable="false"></el-alert>
  </div>
</template>

<script>
export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      content: '',   // ツイート内容を保持するデータを追加
      message: ''    // 投稿結果のメッセージを保持するデータを追加
    };
  },
  methods: {
    async postTweet() {
      const tweet = {
        // userid: this.user.id,
        userId: 1,
        content: this.content,
      };

      try {
        const response = await fetch("http://localhost:8080/tweet", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(tweet),
        });

        if (response.ok) {
          this.message = `投稿が完了しました。`;
          this.content = '';
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
