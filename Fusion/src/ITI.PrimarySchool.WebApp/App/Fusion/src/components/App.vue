<template>
  <div id="app">

    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">

    <div class="page-layout">
      <div v-if="!auth.isConnected">
        <side-component/>
      </div>
      <div v-if="auth.isConnected">
        <sidebar-component :active="sidebarOpened" />
      </div>
      <div class="page-layout-inner">
        <header-component :openSidebar="openSidebar" />
        <div class="progress" v-if="isLoading">
          <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: 100%"></div>
        </div>
        <main role="main" class="p-3 p-md-4 p-lg-5">
          <div class="main-content">
            <el-row class="container">
              <router-view class="child"></router-view>
            </el-row>
          </div>
        </main>
      </div>
      <dimmer :active="obfuscatorActive" :closeSidebar="closeSidebar" />
    </div>
  </div>
</template>

<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="~/lib/signalr/signalr.js"></script>

<script>
  import AuthService from '../services/AuthService'
  import {
    mapGetters,
    mapActions,
    mapState
  } from 'vuex'
  import '../directives/requiredProviders'
  import SMSApiService from '../services/SMSApiService'
  import FileApiService from '../services/FileApiService'
  export default {

    data() {
      return {
        signalR: null,
        connection: null
      }
    },
    mounted() {
      var notif = null
      var signalR = require("@aspnet/signalr")
      this.connection = new signalR.HubConnectionBuilder().withUrl("/vue").configureLogging(signalR.LogLevel.Information)
        .build()

      this.connection.on("send", data => {
        console.log(data)
      });
      this.connection.on("test", (name, message) => {
        if (Notification.permission !== "granted")
          Notification.requestPermission();
        else {
          var notification = new Notification("SMS de : " + name, {
            icon: 'https://i.imgur.com/AMV4NR4.png',
            body: message,
          });
        }
      });
      this.connection.on("newCall", number => {
        if (Notification.permission !== "granted")
          Notification.requestPermission();
        else {
          var notification = new Notification("Appelle de : " + number, {
            icon: 'https://i.imgur.com/AMV4NR4.png',
            body: "Cliquez ici pour répondre",
          });

          notification.onclick = function () {
            SMSApiService.takeCall()
            notification.close()
          };
        }
      });
      this.connection.on("receivedURL", url => {
        if (Notification.permission !== "granted")
          Notification.requestPermission();
        else {
          var notification = new Notification("URL reçu:" + url, {
            icon: 'https://i.imgur.com/AMV4NR4.png',
            body: "Cliquez ici pour ouvrir le lien",
          });

          notification.onclick = function () {
            window.open(url)
          };
        }
      })
      this.connection.on("Filesending", name => {
        window.location.href = '/api/file/' + name
      })
      this.connection.start().catch(err => console.log(err.toString()));
    },
    computed: {
      ...mapGetters(['isLoading']),
      auth: () => AuthService,
      ...mapState({
        sidebarOpened: state => {
          return state.ui.sidebarOpened
        },
        obfuscatorActive: state => {
          return state.ui.obfuscatorActive
        }
      })
    },
    methods: {
      ...mapActions(['handleResize', 'openSidebar', 'closeSidebar']),
      invoke() {
        this.connection.invoke("smsReceived").catch(err => console.error(err.toString()));
      }
    },
    created: function () {
      window.addEventListener('resize', this.handleResize)
    }
  }
</script>

<style lang="scss" scoped>
  .progress {
    margin: 0px;
    padding: 0px;
    height: 5px;
  }

  a.router-link-active {
    font-weight: bold;
  }
</style>

<style lang="scss">
  @import '../styles/vars.scss';
  @import "../styles/global.scss";
</style>