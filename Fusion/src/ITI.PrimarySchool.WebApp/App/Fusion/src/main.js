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