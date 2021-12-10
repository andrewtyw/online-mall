import axios from 'axios'


const request = axios.create({ //也就是在输入的url前面加上/api
    baseURL: "/api",
    timeout: 5000
})



export default request