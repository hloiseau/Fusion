<template>
<header>
    <el-menu theme="dark" mode="horizontal" style="background-color: rgb(240, 240, 240);">
        <el-menu-item class="header-navicon" index="1">
            <el-button type="text" @click="openSidebar">
                <i class="md-icon">menu</i>
            </el-button>
        </el-menu-item>
        <span class="header-title" style='color:#2e3d49'>FUSION</span>
        <el-submenu class="header-menu" index="3">
            <template slot="title">Compte</template>
                <el-menu-item index="/logout">Se déconnecter</el-menu-item>
            </el-submenu>
        </el-menu>
    </header>
</template>

<script>
export default {
  name: 'Header',
  props: ['openSidebar', 'title']
}
</script>

<style lang="scss">
// You can import all your SCSS variables using webpack alias
@import '../../../styles/vars.scss';
@import './style.scss';

</style>