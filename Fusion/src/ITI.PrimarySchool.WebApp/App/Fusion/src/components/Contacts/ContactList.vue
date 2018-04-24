<template>
    <div>
        <div class="mb-4 d-flex justify-content-between">
            <h1>Gestion des classes</h1>
        </div>

       <!--< <el-table :data="contactList" style="width: 100%">
            <el-table-column prop="FirstName" label="Prénom"></el-table-column>
            <el-table-column prop="LastName" label="Nom"></el-table-column>
            <el-table-column prop="Numéro" label="Numéro"></el-table-column>
        </el-table>-->

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
                
                <tr v-for="i of contactList" :key="i.ContactId">
                    <td>{{ i.Firstname }}</td>
                    <td>
                        <router-link :to="`classes/edit/${i.ContactId}`">
                            <i class="fa fa-pencil"></i>
                        </router-link>
                        <a href="#" @click="deleteClass(i.ContactId)">
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
import ContactApiService from '../../services/ContactApiService'

export default {
    data() {
        return {
            contactList: []
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
                this.contactList = await ContactApiService.getContactListAsync();
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