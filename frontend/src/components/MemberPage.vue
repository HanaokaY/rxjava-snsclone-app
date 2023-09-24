<template>
    <div>
        <TweetList/>
    </div>
</template>
<script>
import TweetList from './TweetList.vue';
export default {
  name: 'MemberPage',
  components: {
    TweetList,
  },
  data() {
    return {
      userInfo: null
    };
  },
//   mounted() {
//     this.fetchUserInfo();
//   },
  methods: {
    async fetchUserInfo() {
      try {
        const response = await fetch("http://localhost:8080/getUserInfo");
        const data = await response.json();
        this.userInfo = data;
      } catch (error) {
        console.error("Error fetching user info:", error);
      }
    }
  }
}
</script>
