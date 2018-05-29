<template>
    <div>
        <el-row>
            <el-col :span="24"><div class="title">File Manager</div></el-col>
        </el-row>

        <el-row :gutter="20">
            <el-col :span="10" :offset="6"><div>Envoyer un fichier :</div></el-col>
        </el-row>

        <input type="file" @change="OnFileChanged">
        <button @click="onUpload">Upload !</button>
    </div>
</template>

<script>
export default {
    data(){

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

