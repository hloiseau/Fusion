<template>
    <section class="jumbotron text-center">
        <div class="container">
            <h1 class="jumbotron-heading">Bienvenue sur FUSION</h1>
            <p class="lead text-muted">Cette application est réalisée à l'aide du framework Vue 2.</p>
            <p>
                <a href="http://vuejs.org/v2/guide/" class="btn btn-primary my-2">Guide Vue 2</a>
                <a href="http://vuejs.org/v2/api/" class="btn btn-secondary my-2">Documentation API Vue 2</a>
                <router-link class="btn btn-secondary my-2" to="/playground">Playground</router-link>
            </p>
        </div>
    </section>
</template>

<script>
export default {
    mounted(){
const appId = 'electron-windows-notifications'
         
const {TileNotification} = require('electron-windows-notifications')

let notification = new TileNotification({
    template: `<toast><visual><binding template="ToastText01"><text id="1">%s</text></binding></visual></toast>`,
    strings: ['Hi!']
})

notification.on('activated', () => console.log('Activated!'))
notification.show()

    }
}



</script>

<style lang="scss">

</style>