import { getAsync, postFormDataAsync, postAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/file";

class FileApiService {
    constructor() {
    }

    async stockFileAsync(model) {
        return await postFormDataAsync(endpoint, model);
    }

    async sendURLtoAndroid(model) {
        return await postAsync(`${endpoint}/urlsend`, model);
    }

}

export default new FileApiService()