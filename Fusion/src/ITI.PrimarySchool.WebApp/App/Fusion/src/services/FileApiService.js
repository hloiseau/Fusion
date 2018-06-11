import { getAsync, postFormDataAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/file";

class FileApiService {
    constructor() {
    }

    async stockFileAsync(model) {
        return await postFormDataAsync(endpoint, model);
    }

}

export default new FileApiService()