import axios from "axios";

export default class PostService {
    static async getAll() {
        axios.defaults.crossDomain = true;
        const response = await axios.get('http://10.0.2.2:8080/api/v1.0/survey/')
        return response
    }
}
