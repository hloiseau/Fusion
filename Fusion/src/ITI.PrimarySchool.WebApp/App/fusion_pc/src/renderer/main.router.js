// Vue router setup
import Vue from 'vue';
import VueRouter from 'vue-router';
Vue.use(VueRouter);

// Components
import Home from './components/Home.vue'

import ContactList from './Components/contacts/ContactList.vue'
import sendSMS from './components/contacts/sendSMS.vue'

const routes = [
    { path: '', component: Home },
    
    { path: '/contacts', component: ContactList },
    { path: '/contacts/sendSMS/:id', component: sendSMS }
];

export default new VueRouter({
    mode: 'history',
    base: '/Home',
    routes
});