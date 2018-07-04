<template>
    <div>
        <p></p>
        <el-card  v-for="i of DeviceList" :key="i.DevicesId" class="box-card">
            <div slot="header" class="clearfix">
                <img src="https://i.imgur.com/0jdQr6n.png" class="miniature">
                <span>{{ i.name }}</span>
                <table>
                    <tr>
                        <td><span>Batterie</span></td>
                        <td width="50%"></td>
                        <td><span>Stockage</span></td>
                    </tr>
                <tr><!-- Juste changer la valeur du % par la valeur de la battery-->
                    <td><el-progress type="circle" width=80 :percentage="50" color="lightgreen"></el-progress></td>
                    <td width="50%"></td>
                    <td><el-progress type="circle" width=80 :percentage=pourcentStockage color="lightblue"></el-progress></td>
                </tr>
                </table>
            </div>

            <el-collapse accordion><el-collapse-item title="Actions" name="1">
            <div v-if="i.type == 'Mobile'">
                <el-row>
                <router-link :to="`/contacts`">
                <el-button type="primary" >
                    <i class="fa fa-users" aria-hidden="true"></i> Contacts
                </el-button>
                </router-link>

                <router-link :to="`/file`">
                <el-button type="primary" >
                    <i class="fa fa-file" aria-hidden="true"></i> Envoyer un fichier
                </el-button>
                </router-link>
                </el-row>

                <el-row>
                <router-link :to="`/file/FileUrlSender`">
                <el-button type="primary" >
                    <i class="fa fa-link" aria-hidden="true"></i> Envoyer un URL
                </el-button>
                </router-link>

                <router-link :to="`/rtc`">
                <el-button type="primary" >
                    <i class="fa fa-phone" aria-hidden="true"></i> Appel-vidéo
                </el-button>
                </router-link>
                </el-row>

                <el-row>
                <el-button type="primary"  v-on:click="findPhone($event)">
                    <i class="fa fa-volume-control-phone" aria-hidden="true"></i> Trouver mon téléphone
                </el-button>
                </el-row>
            </div>
            </el-collapse-item></el-collapse>
        </el-card>
    </div>
</template>

<script>
import { mapActions } from 'vuex'
import DeviceApiService from '../services/DeviceApiService'
import SMSApiService from '../services/SMSApiService'

export default {
    data() {
        return {
            DeviceList: [],
        signalR: null,
        connection: null,
            pourcentStockage: null
        }
    },

    async mounted() {
    var signalR = require("@aspnet/signalr")
    this.connection = new signalR.HubConnectionBuilder().withUrl("/vue").configureLogging(signalR.LogLevel.Information)
        .build()
    await this.refreshList();
    this.connection.on("storageReceive", (used, total) => {
        this.pourcentStockage = (100*used)/total;
        console.log(this.pourcentStockage);
    })
    this.connection.start().catch(err => console.log(err.toString()));    
    },

    methods: {
        ...mapActions(['notifyLoading', 'notifyError']),
        ...mapActions(['executeAsyncRequest']),

        async refreshList() {
            try {
                this.notifyLoading(true);
                this.DeviceList = await DeviceApiService.getDeviceListAsync();
                this.StorageList = await DeviceApiService.sendRequesttoAndroid();
            }
            catch (error) {
                this.notifyError(error);
            }
            finally {
                this.notifyLoading(false);
            }
        },

        async findPhone(e) {
            e.preventDefault();
            
            var errors = [];
            this.errors = errors;
            if(errors.length  == 0) {
                try {
                    var notification = new Notification("Recherche du téléphone ", {
                        icon: 'https://i.imgur.com/AMV4NR4.png',
                        body: 'Votre téléphone va sonner',
                    });
                    await SMSApiService.findPhone();
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
.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: 480px;
}

.miniature {
    height: 25%;
}
</style>