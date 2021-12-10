<template>
  <div id="nav">
<!--    <router-link to="/">Home</router-link> |-->
<!--    <router-link to="/about">About</router-link>-->
    <div style="background-color: #2c3e50;height: 40px;margin-left: -8px;margin-right: -8px;margin-top: -8px;">
      <div style="float: left;font-weight: bold;margin-left: 20px;font-size: 25px;margin-top: 5px">
          <div style="color:orange">
            GoGoMart
          </div>
      </div>
      <div style="float: right;margin-right: 20px">



        <el-popover
            v-if="this.$store.state.status.loggedIn===true"
            placement="bottom"
            :width="100"
            trigger="hover"
        >
          <div style="margin-left: 25%">
            <el-button type="danger" @click="logOut">退出</el-button>
          </div>
          <template #reference>
            <el-button  type="text" >
              <div style="color: aliceblue">
                <i class="el-icon-s-flag"></i>
                欢迎: {{this.$store.state.status.username}}
              </div>
            </el-button>
          </template>
        </el-popover>
        <el-button v-else type="text" @click="login">
          <div style="color: aliceblue">
            <i class="el-icon-s-flag"></i>
            更多功能请先登录
          </div>
        </el-button>




        <template v-if="this.$store.state.status.userType===1">
          <el-button type="text" @click="myOrders">
            <div style="color: aliceblue">
              <i class="el-icon-s-order"></i>
              我的订单
            </div>
          </el-button>
          <el-button type="text" @click="myCarts">
            <div style="color: aliceblue">
              <i class="el-icon-shopping-cart-1"></i>
              <el-badge :value="this.$store.state.status.cartNum" class="cartItem">
                购物车&nbsp&nbsp&nbsp
              </el-badge>
            </div>
          </el-button>
        </template>
        <template v-if="this.$store.state.status.userType===2">
          <el-button type="text" @click="goToManage">
            <div style="color: aliceblue">
              <i class="el-icon-s-order"></i>
              后台管理
            </div>
          </el-button>
        </template>




      </div>
    </div>
  </div>
  <el-dialog v-model="loginDialogVisible" title="用户登录" width="380px">
    <el-form
        ref="loginFrom"
        :model="loginForm"
        label-width="80px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input style="width: 200px;float: left" v-model="loginForm.username" placeholder="User name"></el-input>
      </el-form-item>
      <el-form-item label="密码&nbsp&nbsp" prop="password">
        <el-input style="width: 200px;float: left" type="password" v-model="loginForm.password" placeholder="Password"></el-input>
      </el-form-item>
      <div style="margin-bottom: 10px">
        <el-radio v-model="loginUsertype" label="1">买家</el-radio>
        <el-radio v-model="loginUsertype" label="2">商家</el-radio>
      </div>
      <div style="margin-left: -70px">
        <el-form-item>
          <el-button type="primary" @click="loginConfirm"
          >&nbsp&nbsp&nbsp登录&nbsp&nbsp&nbsp</el-button
          >
          <el-button @click="register" type="success">&nbsp&nbsp&nbsp注册&nbsp&nbsp&nbsp</el-button>
        </el-form-item>
      </div>

    </el-form>
  </el-dialog>

  <el-dialog v-model="registerDialogVisible" title="注册" width="450px">
    <el-form
        ref="registerForm"
        :model="registerForm"
        label-width="100px"
    >
      <div style="margin-bottom: 10px">
        <span style="margin-bottom: 30px;margin-right: 30px;font-weight: bold">选择身份:</span>
        <el-radio v-model="registerForm.usertype" label="1">买家</el-radio>
        <el-radio v-model="registerForm.usertype" label="2">商家</el-radio>
      </div>
      <el-form-item v-if="registerForm.usertype==='1'" label="用户名" prop="username">
        <el-input style="width: 300px;float: left" v-model="registerForm.username" placeholder="Username"></el-input>
      </el-form-item>
      <el-form-item v-else label="商店名" prop="username">
        <el-input style="width: 300px;float: left" v-model="registerForm.username" placeholder="Username"></el-input>
      </el-form-item>
      <el-form-item label="&nbsp&nbsp&nbsp密码&nbsp&nbsp&nbsp" prop="password">
        <el-input type="password" style="width: 300px;float: left" v-model="registerForm.password" placeholder="Password"></el-input>
      </el-form-item>
      <el-form-item label="重复密码" prop="repassword">
        <el-input type="password" style="width: 300px;float: left" v-model="registerForm.repassword" placeholder="Repassword"></el-input>
      </el-form-item>
      <el-form-item label="电子邮箱" prop="email">
        <el-input style="width: 300px;float: left" v-model="registerForm.email" placeholder="your email"></el-input>
      </el-form-item>
      <el-form-item v-if="registerForm.usertype==='1'" label="收货地址" prop="uaddress">
        <el-input style="width: 300px;float: left" v-model="registerForm.uaddress" placeholder="your Address"></el-input>
      </el-form-item>



      <div style="margin-left: -70px">
        <el-form-item>
          <el-button type="success" @click="registerConfirm"
          >&nbsp&nbsp&nbsp确认注册&nbsp&nbsp&nbsp</el-button
          >
        </el-form-item>
      </div>

    </el-form>
  </el-dialog>
<!--  <el-button type="success" @click="test">-->
<!--     store测试-->
<!--  </el-button>-->

  <router-view/>
</template>

<script>
import {notice_success,notice_fail} from "@/api/elementApi";
import {sessionStoreRefresh} from "@/api/storage";
import request from "@/utils/request";
import axios from "axios";
export default {
  data(){
    return{
      getNum:12,
      registerDialogVisible:false,
      loginDialogVisible:false,
      loginUsertype:"1",
      loginForm:{
        username:"",
        password:"",
      },
      registerForm:{
        usertype:'1',
        username:"",
        password:"",
        repassword:"",
        email:"",
        uaddress:"",
      },
    }
  },
  created() {
    //sessionStorage.clear()
    const storedInfo = sessionStorage.getItem("userInfo");
    console.log("sessionStorage.getItem original String",storedInfo)
    const obj = JSON.parse(storedInfo);
    console.log("sessionStorage.getItem(\"userInfo\")",obj)
    if(storedInfo!==null){
      this.$store.state.status = obj
    }

  },
  methods:{
    logOut(){

      this.$store.commit("logout")
      this.$store.state.status.loggedIn=false //assure...
      sessionStorage.clear()
      this.$router.push("/")
      location.reload()  //here
    },
    goToManage(){
      this.$router.push("/sellermanage")
    },
    myOrders(){
      this.$router.push("/userorders")
    },
    myCarts(){
      this.$router.push("/usercart")
    },
    test(){
      var testForm = {
        uname:"tywang",
        password:"123456"
      }
      request.post("/user/login",testForm).then(res=>{
        console.log("res.data.data:",res.data.data)
        this.$store.commit("userLogin",res.data.data)
        console.log(this.$store.state.status)
      })


    },
    login(){
      this.loginDialogVisible=true
      this.$refs['loginFrom'].resetFields();


    },
    loginConfirm(){/*主动登录*/
      if(this.loginUsertype==="1"){
        var userLoginForm = {uname:"",password:""};
        userLoginForm.uname = this.loginForm.username;
        userLoginForm.password = this.loginForm.password;
        request.post("/user/login",userLoginForm).then(res=>{
          if(res.data.code==="0"){
            this.$store.commit("userLogin",res.data.data)
            console.log("登录成功res.data.data:",res.data.data)
            console.log("ssssstore:",this.$store.state.status.username)
            notice_success("登录成功, 欢迎用户"+this.$store.state.status.username)
            request.get("/cart/cartnumbyuid"+this.$store.state.status.userId).then(res=>{
              this.$store.state.status.cartNum = res.data.data
              console.log("App.vue 正在存储:",this.$store.state.status)
              sessionStoreRefresh(this.$store.state.status) //更新store中的内容
            })
            this.loginDialogVisible=false
            /*保存用户的登录表单到sessionStorage 中*/
            // sessionStorage.clear() //首先清空sessionStorage中的内容
            // sessionStorage.setItem("userInfo",JSON.stringify(this.$store.state.status))

          }
          else {
            notice_fail(res.data.msg)
          }
        })
      }
      else if(this.loginUsertype==="2"){
        var sellerLoginForm = {sname:"",password:""};
        sellerLoginForm.sname = this.loginForm.username;
        sellerLoginForm.password = this.loginForm.password;
        request.post("/seller/login",sellerLoginForm).then(res=>{
          if(res.data.code==="0"){
            this.$store.commit("sellerLogin",res.data.data)
            notice_success("登录成功, 欢迎商家"+this.$store.state.status.username)
            this.loginDialogVisible=false
            console.log("App.vue 正在存储:",this.$store.state.status)
            sessionStoreRefresh(this.$store.state.status) //更新store中的内容

          }
          else{
            notice_fail(res.data.msg)
          }
        })
      }
    },
    register(){
      this.loginDialogVisible=false
      this.registerDialogVisible=true
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    registerConfirm(){
      if(this.registerForm.usertype==='1'){
        var userForm = {
          uname:this.registerForm.username,
          password:this.registerForm.password,
          uemail:this.registerForm.email,
          uaddress:this.registerForm.uaddress
        }
        request.post("/user/register",userForm).then(res=>{
          if(res.data.code==="0"){
            notice_success("注册成功")
          }
          else {
            notice_fail("注册失败")
          }
        })
      }
      else if(this.registerForm.usertype==='2'){
        var sellerForm = {
          sname:this.registerForm.username,
          password:this.registerForm.password,
          semail:this.registerForm.email,
        }
        request.post("/seller/register",sellerForm).then(res=>{
          if(res.data.code==="0"){
            notice_success("注册成功")
          }
          else {
            notice_fail("注册失败")
          }
        })
      }
      else
      {
        notice_fail("不明身份")
      }
      this.registerDialogVisible = false
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 0px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
.cartItem{
  /*margin-top: 10px;*/
  /*margin-right: 40px;*/
}
</style>
