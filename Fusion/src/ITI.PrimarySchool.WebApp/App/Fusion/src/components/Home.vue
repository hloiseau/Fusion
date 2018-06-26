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

        <form @submit="urlSubmit($event)">
            <button type="submit" class="btn btn-primary">Envoyer</button>
        </form>
    </section>
</template>

<script>
  import {
    mapGetters,
    mapActions,
    mapState
  } from 'vuex'
  import '../directives/requiredProviders'
import FileApiService from '../services/FileApiService';

export default {
data(){
        return {
            item: "https://google.com/"
        }
    },

    methods: {
        ...mapActions(['notifyLoading', 'notifyError']),
        ...mapActions(['executeAsyncRequest']),


        async urlSubmit(e) {
            e.preventDefault();
            
            var errors = [];
            this.errors = errors;
            if(errors.length  == 0) {
                try {
                    console.log(this.item);
                    await this.executeAsyncRequest(() => FileApiService.OpenUrl(this.item));
                }
                catch(error){
                    this.notifyError(error);
                }
                finally {
                    this.notifyLoading(false);
                }
            }
        }
    }
}
</script>

<style lang="scss">

</style>