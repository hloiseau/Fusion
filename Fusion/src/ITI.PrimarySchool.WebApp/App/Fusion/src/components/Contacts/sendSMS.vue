<template>
    <div>
        <div class="mb-4">
            <h1>Envoyer un SMS</h1>
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
                <input type="text" v-model="item.Extern" class="form-control" required>
            </div>

            <div class="form-group">
                <label class="required">Message</label>
                <input type="textarea" v-model="item.message" class="form-control" required>
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
                errors: []
            }
        },

        async mounted() {
            this.id = this.$route.params.id;

            try {
                // Here, we use "executeAsyncRequest" action. When an exception is thrown, it is not catched: you have to catch it.
                // It is useful when we have to know if an error occurred, in order to adapt the user experience.
                const item = await this.executeAsyncRequest(() => ContactApiService.getContactAsync(this.id));

                this.item = item;
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

                if(!this.item.phoneNumber) errors.push("Numero")

                this.errors = errors;

                if(errors.length == 0) {
                    try {
                        await this.executeAsyncRequest(() => SMSApiService.createSMSAsync(this.item));
                        this.$router.replace('/contacts');
                    }
                    catch(error) {
                        // Custom error management here.
                        // In our application, errors throwed when executing a request are managed globally via the "executeAsyncRequest" action: errors are added to the 'app.errors' state.
                        // A custom component should react to this state when a new error is added, and make an action, like showing an alert message, or something else.
                        // By the way, you can handle errors manually for each component if you need it...
                    }
                }
            }
        }
    }
</script>

<style lang="scss">

</style>