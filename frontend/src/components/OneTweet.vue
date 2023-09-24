<template>
  <el-card class="tweet-card">
    <div>{{ tweet.text }}</div>
    <div>{{ tweet.createdAt }}</div>
  </el-card>
</template>

<script>
export default {
  props: {
    tweet: {
      type: Object,
      required: true
    }
  }
};
</script>

<style scoped>
.tweet-card {
  margin-bottom: 16px;
}
</style>
