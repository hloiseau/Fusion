<template>
    <div>
        <el-row>
            <el-col :span="24"><div class="title">File Manager</div></el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="10"><div>Envoyer un fichier :</div></el-col>
        </el-row>
        <form @submit="onSubmit($event)">
            <div class="large-12 medium-12 small-12 cell">
                <label>File
                    <input type="file"  @change="handleFileUpload"/>
                </label>
                    <input type ="submit" value="Upload"/>
            </div>
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
            fd: new FormData()
        }
    },

    methods: {
        ...mapActions(['notifyLoading', 'notifyError']),
        ...mapActions(['executeAsyncRequest']),
        
        handleFileUpload (event) {
            this.selectedFile = event.target.files[0];
        },

        async onSubmit(e) {
            e.preventDefault();
            
            var errors = [];
            this.errors = errors;
            if(errors.length  == 0) {
                try {
                    this.notifyLoading(true);

                    this.fd.append('file', this.selectedFile, this.selectedFile.name);

                    if(this.fd == null) console.log("null");

                    await this.executeAsyncRequest(() => FileApiService.stockFileAsync(this.fd));
                    this.$router.replace('/file');
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

 .title {
    font-family: "Helvetica Neue";
    color: rgb(111, 126, 131);
    font-size: 28px;
  }

</style>

