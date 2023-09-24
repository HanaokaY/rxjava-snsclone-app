<template>
  <div id="app">
    <transition name="fade">
      <RegisterForm v-if="!isRegistered" @registration-success="onRegistrationSuccess"/>
    </transition>
    <transition name="fade">
      <MemberPage v-if="isRegistered"/>
    </transition>
  </div>
</template>

<script>
import RegisterForm from './components/RegisterForm.vue';
import MemberPage from './components/MemberPage.vue';

export default {
  components: {
    RegisterForm,
    MemberPage,
  },
  data() {
    return {
      isRegistered: false,
    };
  },
  methods: {
    onRegistrationSuccess() {
      this.isRegistered = true;
    }
  }
};
</script>
<style>
/* フェードエフェクト */
.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */ {
  opacity: 0;
}
</style>