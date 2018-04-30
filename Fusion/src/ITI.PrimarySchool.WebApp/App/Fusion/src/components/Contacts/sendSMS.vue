<template>
    <div>
        <div class="mb-4">
            <h1>Envoyer un SMS</h1>
        </div>

    <div>
        <v-if="studentList.length == 0">
    </div>

    <form @submit="onSubmit($event)">
        <div class="alert alert-danger" v-if="errors.length > 0">
            <b>Les champs suivants semblent invalides : </b>

            <ul>
                <li v-for="e of errors">{{e}}</li>
            </ul>
        </div>

        <div class="form-group">
            <label class="required">Message</label>
            <input type="text" v-model="item.body" class="form-control" required>
        </div>

        <button type="submit" class="btn btn-primary">Envoyer un SMS</button>
    </form>
    </div>
</template>

<script>
    import { mapActions } from 'vuex'
    import { DateTime } from 'luxon'
    import ContactApiService from '../../services/ContactApiService'
    import SMSApiService from '../../services/SMSApiService'

    export default {
        data () {
            return {
                item: {},
                id: null,
                errors: [],
                sms: {}               
            }
        },

        async mounted() {
            this.id = this.$route.params.id;

            try {
                // Here, we use "executeAsyncRequest" action. When an exception is thrown, it is not catched: you have to catch it.
                // It is useful when we have to know if an error occurred, in order to adapt the user experience.
                const item = await this.executeAsyncRequest(() => ContactApiService.getContactAsync(this.id));
                const sms = await this.executeAsyncRequest(() => SMSApiService.getSMSByContactAsync(this.id))

                this.item = item;
                this.item.address = item.phoneNumber;
            }
            catch(error) {
                console.error(error);

            }
        },

        methods: {
            ...mapActions(['executeAsyncRequest']),

            async onSubmit(e) {
                e.preventDefault();

                var errors = [];

                this.errors = errors;

                if(errors.length == 0) {
                    try {
                        await this.executeAsyncRequest(() => SMSApiService.createSMSAsync(this.item));
                        this.$router.replace('/contacts');
                    }
                    catch(error) {
                        console.error(error);
                    }
                }
            }
        }
    }
</script>

<style lang="scss">

</style>