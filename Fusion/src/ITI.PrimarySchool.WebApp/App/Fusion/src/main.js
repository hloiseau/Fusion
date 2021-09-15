import './main.polyfills'
import './main.vendors'
import './main.auth'

import Vue from 'vue'
import store from './store'
import router from './main.router'
import App from './components/App.vue'
import ElementUI from 'element-ui'

import Header from './components/navigations/Header/index.vue'
import Sidebar from './components/navigations/Sidebar/index.vue'
import SidebarHome from './components/navigations/Sidebar/sidebarhome.vue'
import Dimmer from './components/navigations/Dimmer/index.vue'

Vue.use(ElementUI);

Vue.use(require('vue-pubnub'), {
  subscribeKey: "sub-c-55b78e0a-745b-11e8-902b-b2b3cb3accda", // Only the subscribeKey option is mandatory.
  publishKey: "pub-c-b840de4f-c044-4bc9-a823-c21724913221",
  logVerbosity: true,
  ssl: true,
  presenceTimeout: 130
})


Vue.component('header-component', Header);
Vue.component('sidebar-component', Sidebar);
Vue.component('side-component',SidebarHome);
Vue.component('Dimmer', Dimmer);

// Creation of the root Vue of the application
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
});