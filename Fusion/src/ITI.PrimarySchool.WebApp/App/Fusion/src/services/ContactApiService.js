import { getAsync, postAsync, putAsync, deleteAsync } from '../helpers/apiHelper'

const endpoint = "/api/contact";

class ContactApiService {
    constructor() {

    }

    async getContactListAsync() {
        return await getAsync(endpoint);
    }

    async getContactAsync(contactId) {
        return await getAsync(`${endpoint}/${contactId}`);
    }

    async createContactAsync(model) {
        return await postAsync(endpoint, model);
    }

    async updateContactAsync(model) {
        return await putAsync(`${endpoint}/${model.contactId}`, model);
    }

    async deleteContactAsync(contactId) {
        return await deleteAsync(`${endpoint}/${contactId}`);
    }
}

export default new ContactApiService()