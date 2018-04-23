<template>
    <div>
        <div class="mb-4 d-flex justify-content-between">
            <h1>Gestion des classes</h1>
        </div>

        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="Prénom" label="Date"></el-table-column>
            <el-table-column prop="Nom" label="Name"></el-table-column>
            <el-table-column prop="Numéro" label="Address"></el-table-column>
        </el-table>

        <table class="table table-striped table-hover table-bordered">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Niveau</th>
                    <th>Options</th>
                </tr>
            </thead>

            <tbody>
                <tr v-if="classList.length == 0">
                    <td colspan="4" class="text-center">Il n'y a actuellement aucune classe.</td>
                </tr>

                <tr v-for="i of classList" :key="i.classId">
                    <td>{{ i.classId }}</td>
                    <td>{{ i.name }}</td>
                    <td>{{ i.level }}</td>
                    <td>
                        <router-link :to="`classes/edit/${i.classId}`">
                            <i class="fa fa-pencil"></i>
                        </router-link>
                        <a href="#" @click="deleteClass(i.classId)">
                            <i class="fa fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import ClassApiService from '../../services/ClassApiService'

export default {
    data() {
        return {
            contactList: [],
            tableData: [{
            date: '2016-05-03',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-02',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-04',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }, {
            date: '2016-05-01',
            name: 'Tom',
            address: 'No. 189, Grove St, Los Angeles'
          }]
        }
    },

    async mounted() {
        await this.refreshList();
    },

    methods: {
        ...mapActions(['notifyLoading', 'notifyError']),

        async refreshList() {
            try {
                this.notifyLoading(true);
                this.classList = await ContactApiService.getContactListAsync();
            }
            catch (error) {
                this.notifyError(error);
            }
            finally {
                this.notifyLoading(false);
            }
        },

        async deleteClass(classId) {
            try {
                this.notifyLoading(true);
                await ClassApiService.deleteClassAsync(classId);
                this.notifyLoading(false);

                await this.refreshList();
            }
            catch (error) {
                this.notifyError(error);
            }
            finally {
                this.notifyLoading(false);
            }
        }
    }
}
</script>

<style lang="scss">

</style>