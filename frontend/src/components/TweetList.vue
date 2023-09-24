<template>
  <div>
    <OneTweet v-for="tweet in tweets" :key="tweet.id" :tweet="tweet" />
    <TweetPostButton />
  </div>
</template>

<script>
import OneTweet from './OneTweet.vue';
import TweetPostButton from './TweetPostButton.vue';

export default {
  components: {
    OneTweet,
    TweetPostButton
  },
  data() {
    return {
      tweets: [
        // 仮のデータ
      ]
    };
  }
};
</script>
