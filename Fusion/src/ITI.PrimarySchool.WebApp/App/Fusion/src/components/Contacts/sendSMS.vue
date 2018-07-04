<template>
    <div>
        <p></p>
        </br></br>
        <div class="mb-4">
            <h1 <h1 style="color: #02758c;">SMS</h1>
        </div>

        <div v-for="i of sms">
            <el-row :gutter="12" v-if="i.direction == true">
                <el-col v-if="i.direction == true" :span="8">
                    <el-card shadow="always">
                        {{i.time}} {{i.message}}
                    </el-card>
                </el-col>
            </el-row>
            <el-row :gutter="12" v-if="i.direction == false">
                <el-col v-if="i.direction == false" :span="8" :offset="3">
                    <el-card shadow="always" class="box-card">
                        {{i.time}} {{i.message}}
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <form @submit="onSubmit($event)">
            <div class="alert alert-danger" v-if="errors.length > 0">
                <b>Les champs suivants semblent invalides : </b>

                <ul>
                    <li v-for="e of errors">{{e}}</li>
                </ul>
            </div>

            <div class="form-group">

                <label class="required">Numero:</label>
                <input type="text" v-model="item.address" class="form-control" required>

                <label class="required">Message</label>
                <input type="text" v-model="item.body" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">Envoyer un SMS</button>
        </form>
    </div>
</template>

<script>
    import {
        mapActions
    } from 'vuex'
    import {
        DateTime
    } from 'luxon'
    import ContactApiService from '../../services/ContactApiService'
    import SMSApiService from '../../services/SMSApiService'

    export default {
        data() {
            return {
                item: {},
                id: null,
                errors: [],
                sms: []
            }
        },

        async mounted() {
            this.id = this.$route.params.id;

            try {
                // Here, we use "executeAsyncRequest" action. When an exception is thrown, it is not catched: you have to catch it.
                // It is useful when we have to know if an error occurred, in order to adapt the user experience.
                const item = await this.executeAsyncRequest(() => ContactApiService.getContactAsync(this.id));
                this.item = item;

                const sms = await this.executeAsyncRequest(() => SMSApiService.getSMSByContactAsync(this.item.phoneNumber));

                this.sms = sms;
                this.item.address = item.phoneNumber;
            } catch (error) {
                console.error(error);

            }
        },

        methods: {
            ...mapActions(['executeAsyncRequest']),

            async onSubmit(e) {
                e.preventDefault();

                var errors = [];

                this.errors = errors;

                if (errors.length == 0) {
                    try {
                        await this.executeAsyncRequest(() => SMSApiService.createSMSAsync(this.item));
                        this.$router.replace('/contacts');
                    } catch (error) {
                        console.error(error);
                    }
                }
            }
        }
    }
</script>

<style lang="scss">
</style>