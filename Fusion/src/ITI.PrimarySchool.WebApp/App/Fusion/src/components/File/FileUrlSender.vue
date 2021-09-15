<template>
    <div>
        <p></p>
        </br></br>
        <h1 style="color: #02758c;">Envoyer un URL</h1>
        </br>
        <form @submit="urlSubmit($event)">
            <label>Envoyer cette URL :</label>
            <input type="text" v-model="item" class="form-control">
            </br>
            <button  type="submit" class="btn btn-primary" style="background-color:#3f9dff; color:white;">Envoyer</button>
        </form>
    </div>
</template>

<script>
import {mapActions} from 'vuex'
import FileApiService from '../../services/FileApiService'

export default {
    data(){
        return {
            selectedFile: null,
            item: null
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
                    await this.executeAsyncRequest(() => FileApiService.sendURLtoAndroid(this.item));
                    this.$router.replace('/');
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
 .el-row {
    margin-bottom: 20px;
  }

  .el-col {
    border-radius: 4px;
  }


</style>

