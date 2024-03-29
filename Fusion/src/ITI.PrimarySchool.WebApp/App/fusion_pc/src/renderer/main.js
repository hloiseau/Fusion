import './main.polyfills'
import './main.vendors'

import Vue from 'vue'
import store from './store'
import router from './main.router'
import App from './components/App.vue'
import ElementUI from 'element-ui'

Vue.use(ElementUI);

// Creation of the root Vue of the application
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});