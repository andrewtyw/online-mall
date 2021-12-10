<template >

  <div>
    <!--    走马灯显示商品-->
<!--    <el-carousel :interval="4000" :height="imgHeight" type="card" >-->
<!--      <el-carousel-item v-for="item in carouselImgs" >-->
<!--&lt;!&ndash;        <el-image&ndash;&gt;-->
<!--&lt;!&ndash;            style="height: 300px;width: 600px;background-color: white"&ndash;&gt;-->
<!--&lt;!&ndash;            :src="item"&ndash;&gt;-->
<!--&lt;!&ndash;            :fit="fit"&ndash;&gt;-->
<!--&lt;!&ndash;        ></el-image>&ndash;&gt;-->
<!--        <img ref="imgH" :src='item' style="width:100%;height:auto" alt="" @load="imgLoad">-->
<!--      </el-carousel-item>-->
<!--    </el-carousel>-->
    <el-carousel :interval="4000" type="card" height="400px">
      <el-carousel-item v-for="item in carouselImgs" :key="item" @click="carouselImgsClisk">
<!--        <h3 class="medium">{{ item }}</h3>-->
        <img class="carouselImg" ref="imgH" :src='item' style="width:100%;height:auto" alt="" @load="imgLoad">
      </el-carousel-item>
    </el-carousel>

    <div style="font-size: 25px;float: left">
      大卖特卖
    </div>
  </div>
<!--分界线-->
  <div>
    <el-divider style="margin-top: 50px"></el-divider>
  </div>
<!--  显示图片-->
  <div v-loading="goodsLoading">

    <el-row>
      <el-col
          style="padding: 25px"
          v-for="(item, index) in hotGoods"
          :span="6"
      >
        <el-card shadow="hover" :body-style="{ padding: '0px' }" @click="clickGoods(item)">
          <el-image
              style="width:200px;height: 200px"
              :src="item.gcover"
              :fit="fit"
          ></el-image>
          <div style="padding: 14px">
            <div style="font-size: 15px;padding-bottom: 10px">
              <span>{{item.gname}}</span>
            </div>

            <div style="font-size: 10px;color: darkgray">
              <span>{{item.gdescribe}}</span>
            </div>
            <div style="font-size: 14px;padding: 5px">
              <span>
<!--                顺便计算价格-->
                <span style="color: orangered">${{item.gprice*item.gdiscount}}</span>
                <span>&nbsp</span>
                <span style="text-decoration: line-through;color: #99a9bf">${{item.gprice}}</span>
              </span>
            </div>
          </div>
        </el-card>
      </el-col>
      <div style="font-size: 10px;margin-top: 200px;margin-left: 5%">
        <div style="margin-top: -60px" @click="this.$router.push('/allgoods')">
          <el-button type="primary" circle style="font-size: 70px"><i class="el-icon-right"></i></el-button>
        </div>

      </div>

    </el-row>
  </div>
<!--  商品详情dialog-->
  <div>
    <el-dialog v-model="goodsDetailDialogVisible" title="商品详情" width="60%" style="height: 70%;" >
      <el-card>
        <el-col style="width: 50%">
          <el-image
              style="width:80%;height: 80%;margin-bottom: 10%"
              :src="goodsDetails.gcover"
              :fit="fit"
          ></el-image>
        </el-col>
        <el-col style="width: 50%">
<!--          介绍名字-->
          <div>
            <span style="font-size: 25px;font-weight: bold">{{goodsDetails.gname}}</span>
          </div>
          <el-row>
            <div style="margin-top: 20px;margin-bottom: 10px">
            <p style="color: #99a9bf;font-size: 15px">
              {{goodsDetails.gparagraph}}
            </p>
            </div >
          </el-row>

<!--          显示厂家-->
          <el-row>
            <div style="margin-top: 20px;float: left">
            <span style="font-size: 20px;color: orange">
              商家:{{goodsDetails.sname}}
            </span>
            </div>
          </el-row>
<!--          显示价格-->
          <el-row style="margin-top: 40px">
            <div>
              <span style="font-size: 30px">
                <span style="color: orangered">${{Math.round(goodsDetails.gprice*goodsDetails.gdiscount*100)/100}}</span>
                <span>&nbsp</span>
                <span style="text-decoration: line-through;color: #99a9bf">${{goodsDetails.gprice}}</span>
              </span>
            </div>
          </el-row>


        </el-col>
      </el-card>
      <div class="button">
        <el-button class="shop-cart" type="success"  @click="addShoppingCart">加入购物车</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
import {notice_fail,notice_success} from "@/api/elementApi";
import request from "@/utils/request";
import axios from 'axios'
import {sessionStoreRefresh} from "@/api/storage";

window.onbeforeunload = function(){
  alert("刷新!")
}

export default {
  name: 'Home',
  created() {
    // location.reload()
    this.$nextTick(()=>{
      request.get("/goods/findgoodsales").then(res=>{
        if(res.data.code==="0"){
          this.hotGoods = res.data.data;
          console.log("hotGoods:",this.hotGoods)
          this.goodsLoading=false
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    })

  },
  mounted() {
    window.onbeforeunload = () =>{

      console.log("before reload")
      this.clearViews();
      // return 'tips';
    }
  },
  data() {
    return {
      goodsLoading:true,
      imgHeight:'289',
      hotGoods:[],
      goodsDetailDialogVisible:false,
      goodsDetails:{
        gcover:"",
        gname:"",
        gdescribe:"",
        gparagraph:"",
        gprice:"",
        gstates:"",
        gdiscount:"",
        sname:"",
      },
      carouselImgs:[
        "http://120.24.230.237:9898/files/c14b051471134fbc9e00ab29ca12b226",
      "http://120.24.230.237:9898/files/a32e0b75fc314cbdb719d6be16e3a326",
          "http://120.24.230.237:9898/files/832da423f59a426fac5af011885cc6a6",
          "http://120.24.230.237:9898/files/2cdeb01167014426afb27b24c93c198b",
          "http://120.24.230.237:9898/files/d1694e012f8d4154a8eb4b62db654755",
          "http://120.24.230.237:9898/files/1753c1959c8f40a38eb98df7437ae903",
          "http://120.24.230.237:9898/files/1182f3bdf41940f68dda3bdf110c811e"
      ],
      url:'http://localhost:9090/files/454e1aa7a66249b29f49d13358f84a9b',
      fit: 'fill',
      urls:[
        'http://localhost:9090/files/454e1aa7a66249b29f49d13358f84a9b',
        'http://localhost:9090/files/749d6d7fa9254891af425d1b20477719',
        'http://localhost:9090/files/c5c66263dace4549a52b178d0bc0a170',
        'http://localhost:9090/files/efb54e91ba274617ad3cf702faa244b1',
        'http://localhost:9090/files/454e1aa7a66249b29f49d13358f84a9b',
        'http://localhost:9090/files/ec85132635d14e418faf015d1f68b979',
      ],

    }
  },
  methods:{
    clearViews(){
      console.log("push to /")
      this.$router.push("/")
    },
    imgLoad(){
      this.$nextTick(()=>{
        this.imgHeight = `${this.$refs.imgH[0].height}px`;
        console.log("this.imgHeight",this.imgHeight)
      })
    },
    getUrl(){
       var idx=  this.randomNum(0,6);
       return this.urls[idx]
    },
    randomNum(minNum,maxNum){
  switch(arguments.length){
    case 1:
      return parseInt(Math.random()*minNum+1,10);
      break;
    case 2:
      return parseInt(Math.random()*(maxNum-minNum+1)+minNum,10);
      break;
    default:
      return 0;
      break;
  }
},
    clickGoods(item){
      if(this.$store.state.status.userType!==2){
        var clickDate = {
          sid: item.sid,
          gid: item.gid,
          uid: this.$store.state.status.userId,
          uname: this.$store.state.status.username,
          gname: item.gname,
          uemail: this.$store.state.status.userEmail
        }
        console.log("clickDate",clickDate)

        request.post("/clicks/add",clickDate).then(res=>{
          if (res.data.code!=="0"){notice_fail("点击添加失败")}
        })
      }
      this.goodsDetailDialogVisible=true
      this.goodsDetails = item
      console.log("clickGoods:",item)
    },
    carouselImgsClisk(){

    },
    addShoppingCart(){
      if(this.$store.state.status.userType===1)
      {
        //把商品加入购物车
        var cartInfo = {
          uid:this.$store.state.status.userId,
          gid:this.goodsDetails.gid
        }
        console.log("cartInfo",cartInfo)
        request.post("/cart/addcart",cartInfo).then(res=>{
          if(res.data.code==="0"){
            notice_success("成功加入到购物车")
            this.$store.state.status.cartNum +=1  //修改购物车数量
            sessionStoreRefresh(this.$store.state.status) //更新store中的内容
            this.goodsDetailDialogVisible=false
          }
          else {
            notice_fail(res.data.msg)
          }
        })
      }
      else if(this.$store.state.status.userType===0)
      {
        notice_fail("请先登录")
      }
      else {
        notice_fail("必须是买家身份才能购买商品")
      }
    }

}

}
</script>

<style>
.el-carousel__item h3 {
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
}

.el-carousel__item:nth-child(2n + 1) {
}
.image {
  width: 100%;
  display: block;
}
.button {
  margin: 10px 0 20px 0;
  font-size: 25px;
}
.button .shop-cart {
  width: 50%;
}
.carouselImg{
  position: absolute;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -moz-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  -o-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  width: 99%;
}
.button .shop-cart:hover {
}

</style>
