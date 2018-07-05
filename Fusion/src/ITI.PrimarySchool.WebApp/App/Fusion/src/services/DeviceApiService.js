import { getAsync, postAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/device";

class DeviceApiService {
    constructor() {

    }

    async getDeviceListAsync() {
        return await getAsync(endpoint);
    }

    async getDeviceAsync(deviceId) {
        return await getAsync(`${endpoint}/${deviceId}`);
    }

    async updateClassAsync(model) {
        return await putAsync(`${endpoint}/${model.classId}`, model);
    }

    async sendRequesttoAndroid() {
        return await postAsync(`${endpoint}/storagesend`);
    }

}

export default new DeviceApiService()