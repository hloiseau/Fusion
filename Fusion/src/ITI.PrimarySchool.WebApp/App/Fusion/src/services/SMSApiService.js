import { getAsync, postAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/SMS";

class SMSApiService {
    constructor() {

    }

    async createSMSAsync(model) {
        return await postAsync(`${endpoint}/sendnewsms`, model);
    }
}

export default new SMSApiService()