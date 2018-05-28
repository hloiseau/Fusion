<template>
  <div id="app">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <header>
  
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <router-link class="navbar-brand" to="/">Fusion</router-link>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item">
              <router-link class="nav-link" to="/contacts">Contacts</router-link>
            </li>
           <!--<li class="nav-item">
              <router-link class="nav-link" to="/SMS">SMS</router-link>
            </li>-->
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
<script>

import { mapGetters, mapActions } from 'vuex'
import '../directives/requiredProviders'

export default {
  computed: {
    ...mapGetters(['isLoading']),
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