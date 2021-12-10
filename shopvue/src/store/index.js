import { createStore } from 'vuex'

export default createStore({
  state: {
    status:{
      loggedIn:false,
      username:"游客",
      userType:0,
      userId:0,//userType=0表示游客
      userEmail:"无",
      cartNum:0,
      search:"",
    }
  },
  mutations: {
    userLogin(state, res) {
      state.status.loggedIn = true;
      state.status.userId = res.uid;
      state.status.username = res.uname;
      state.status.userType = 1;
      state.status.userEmail = res.uemail;
    },
    sellerLogin(state,res){
      state.status.loggedIn = true;
      state.status.userId = res.sid;
      state.status.username = res.sname;
      state.status.userType = 2;
      state.status.userEmail = res.semail;
    },
    logout(state) {
      state.status = {
        loggedIn: false,
        userId: 0,
        username: "游客",
        userType: 0,
        userEmail: "无",
        cartNum:0,
        search:"",
      };
    },
  },
  actions: {
  },
  modules: {
  }
})
