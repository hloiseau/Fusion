<template>
    <div id="sidebar" :class="{'open': active}">
        <el-menu theme="light" router style="background-color: #4fe6cc;">
            <div class="logo-container">
                <img  class="logo" src="https://i.imgur.com/AMV4NR4.png" />
            </div>
            <el-menu-item index="/contacts">Contacts</el-menu-item>
            <el-menu-item index="/file">File Sender</el-menu-item>
            <el-menu-item index="/rtc">Appel vidéo</el-menu-item>
       </el-menu>
   </div>
</template>

<script>
export default {
  name: 'Sidebar',
  props: ['active'],
  computed () {
      active: () => {
          console.log(this)
          return this.active
      }
  }
}
</script>

<style lang="scss">
// You can import all your SCSS variables using webpack alias
@import '../../../styles/vars.scss';

@import './style.scss';
</style>