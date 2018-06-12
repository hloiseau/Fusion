<template>
  <div id="app">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <header>


      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <router-link class="navbar-brand" to="/">ITI.PrimarySchool</router-link>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
          aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <!--<button onclick="bamboula('name_exemple :\nmessage_exemple')">Notify me!</button>
        <button v-on:click="invoke()">lulz</button> -->
        <!-- message <= 25 of length => else just a notif like "new message of contact_name" -->

        <div class="collapse navbar-collapse" id="navbarSupportedContent" v-if="auth.isConnected">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <router-link class="nav-link" to="/contacts">Contacts</router-link>
            </li>
           <li class="nav-item">
              <router-link class="nav-link" to="/file">File Sender</router-link>
            </li>
          </ul>

          <ul class="navbar-nav my-2 my-md-0">
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
                aria-expanded="false">
                {{ auth.email }}
              </a>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <router-link class="dropdown-item" to="/logout">Se déconnecter</router-link>
              </div>
            </li>
          </ul>
        </div>
      </nav>

      <div class="progress" v-if="isLoading">
        <div class="progress-bar progress-bar-striped progress-bar-animated" role="progressbar" style="width: 100%"></div>
      </div>
    </header>

    <main role="main" class="p-3 p-md-4 p-lg-5">
      <router-view class="child"></router-view>
    </main>
  </div>
</template>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script src="~/lib/signalr/signalr.js"></script>

<script>
  import AuthService from '../services/AuthService'
  import {
    mapGetters,
    mapActions
  } from 'vuex'
  import '../directives/requiredProviders'

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
      this.connection = new signalR.HubConnectionBuilder().withUrl("/vue").configureLogging(signalR.LogLevel.Information).build()
    
      this.connection.on("send", data => {
        console.log(data)
      });
      this.connection.on("test", (name, message) =>{
        notif = name + "\n" + message,
        bamboula(notif)
      });
      this.connection.start().catch(err => console.log(err.toString()));
    },
    computed: {
      ...mapGetters(['isLoading']),
      auth: () => AuthService
    },
    methods:{
      invoke() {
        this.connection.invoke("smsReceived").catch(err => console.error(err.toString()));
      }
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
  @import "../styles/global.scss";
</style>