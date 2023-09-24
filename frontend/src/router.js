import Vue from 'vue';
import Router from 'vue-router';
import App from './App.vue';
import MemberPage from './components/MemberPage.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: App
    },
    {
      path: '/member',
      name: 'member',
      component: MemberPage
    }
  ]
});
