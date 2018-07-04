<template>
    <div>
        <p></p>
        <form @submit="urlSubmit($event)">
            <label>Envoyer cette URL :</label>
            <input type="text" v-model="item" class="form-control">
            
            <el-button  type="submit" class="btn btn-primary">Envoyer</el-button>
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

