// Vue router setup
import Vue from 'vue';
import VueRouter from 'vue-router';
Vue.use(VueRouter);

import requireAuth from './helpers/requireAuth';

// Components
import Home from './components/Home.vue'
import Login from './components/Login.vue'
import Logout from './components/Logout.vue'

import ClassList from './components/classes/ClassList.vue'
import ClassEdit from './components/classes/ClassEdit.vue'

import TeacherList from './components/teachers/TeacherList.vue'
import TeacherEdit from './components/teachers/TeacherEdit.vue'
import TeacherAssign from './components/teachers/TeacherAssign.vue'

import ContactList from './Components/contacts/ContactList.vue'
import sendSMS from './components/contacts/sendSMS.vue'

import FileHome from './components/file/FileHome.vue'
import FileUrlSender from './components/file/FileUrlSender.vue'

import FollowingList from './components/github/FollowingList.vue'

import PlaygroundPage from './components/Playground/PlaygroundPage.vue'

import Rtc from './components/rtc.vue'

const routes = [
    { path: '', component: Home, beforeEnter: requireAuth },
    {path: '/rtc', component: Rtc},
    { path: '/login', component: Login },
    { path: '/logout', component: Logout, beforeEnter: requireAuth },

    { path: '/playground', component: PlaygroundPage, beforeEnter: requireAuth },

    { path: '/classes', component: ClassList, beforeEnter: requireAuth },
    { path: '/classes/:mode([create|edit]+)/:id?', component: ClassEdit, beforeEnter: requireAuth },

    { path: '/teachers', component: TeacherList, beforeEnter: requireAuth },
    { path: '/teachers/:mode([create|edit]+)/:id?', component: TeacherEdit, beforeEnter: requireAuth },
    { path: '/teachers/assign/:id', component: TeacherAssign, beforeEnter: requireAuth },

    { path: '/contacts', component: ContactList, beforeEnter: requireAuth },
    { path: '/contacts/sendSMS/:id', component: sendSMS, beforeEnter: requireAuth },

    { path: '/file', component: FileHome, beforeEnter: requireAuth },
    { path: '/file/FileUrlSender', component: FileUrlSender, beforeEnter: requireAuth },

    { path: '/github/following', component: FollowingList, beforeEnter: requireAuth, meta: { requiredProviders: ['GitHub'] } }
];

export default new VueRouter({
    mode: 'history',
    base: '/Home',
    routes
});