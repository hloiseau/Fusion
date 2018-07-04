<template>
    <div>
        <p></p>
        <div class="mb-4 d-flex justify-content-between">
            <h1>Gestion des Contacts</h1>
        </div>
        
        <!--<el-table :data="contactList" style="width: 100%">
            <el-table-column prop="contactId" label="ID"></el-table-column>
            <el-table-column prop="firstName" label="Prénom"></el-table-column>
            <el-table-column prop="lastName" label="Nom"></el-table-column>
            <el-table-column prop="mail" label="mail"></el-table-column>
            <el-table-column prop="phoneNumber" label="Numéro"></el-table-column>
            <el-table-column label="Opérations">
                <template slot-scope="scope">
                     <router-link :to="`Contacts/sendSMS/${contactList[0].contactId}`"><i class="el-icon-message"></i></router-link>
                </template>
            </el-table-column>
        </el-table>-->
        
        

        <table class="table table-striped table-hover table-bordered">
            <thead>
                <tr>    
                    <th>ID</th>
                    <th>Prénom</th>
                    <th>Nom</th>
                    <th>Mail</th>
                    <th> Téléphone
                    </th>
                    <th> Opérations</th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="i of contactList">
                    <td>{{ i.contactId }}</td>
                    <td>{{ i.firstName }}</td>
                    <td>{{ i.lastName }}</td>
                    <td>{{ i.mail }}</td>
                    <td>{{ i.phoneNumber }}</td>
                    <router-link :to="`contacts/sendSMS/${i.contactId}`"><i class="el-icon-message"></i></router-link>
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

      GoToSendSMS(row) {
        this.$refs.singleTable.setCurrentRow(row);
      }
    }
}
</script>

<style lang="scss">

</style>