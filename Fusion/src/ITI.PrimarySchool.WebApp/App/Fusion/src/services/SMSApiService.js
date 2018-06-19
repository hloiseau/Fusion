import { getAsync, postAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/SMS";

class SMSApiService {
    constructor() {

    }

    async getContactListAsync() {
        return await getAsync(endpoint);
    }

    async getSMSByContactAsync(phoneNumber) {
        return await getAsync(`${endpoint}/${phoneNumber}`);
    }

    async createSMSAsync(model) {
        return await postAsync(`${endpoint}/sendnewsms`, model);
    }

    async findPhone() {
        return await postAsync(`${endpoint}/foundPhone`);
    }
}

export default new SMSApiService()