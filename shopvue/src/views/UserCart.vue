<template>
  <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
    <i class="el-icon-shopping-cart-1"></i>
    <span style="font-size: 30px">购物车</span>


  </div>
  <span style="font-size: 30px;font-weight: bold;float: right">
    <i class="el-icon-tickets"></i>
    共
    <span style="color: #42b983">{{this.$store.state.status.cartNum}}</span>
    件,总价
    <span style="color: #42b983">
      ${{goodBudgetPrice}}
    </span>
    已优惠
    <span style="color: orangered">${{sum_goodBudgetPrice}}</span>
  </span>
  <div v-if="cartList.length===0" class="centerClass">
    <el-empty  >
      <el-button type="success" style="font-size: 30px;font-weight: bold" @click="this.$router.push('/home')">
        <i class="el-icon-goods"></i>
        现在去购物!
      </el-button>
    </el-empty>
  </div>

  <el-table v-else :data="cartList" stripe style="width: 100%">
    <el-table-column>
      <template #default="scope">
        <el-image
            fit="cover"
            style="width: 70px; height: 70px"
            :src="scope.row.gcover"
            :preview-src-list="[scope.row.gcover]"
            :initial-index="1"
        >
        </el-image>
      </template>
    </el-table-column>
    <el-table-column prop="gname" label="商品名称"  align="center"/>
    <el-table-column prop="gprice" label="商品单价($)"  align="center"/>
    <el-table-column prop="gdiscount" label="商品折扣"  align="center"/>
    <el-table-column label="订购数量"  align="center">
      <template #default="scope">
        <el-input-number v-model="scope.row.num" :min="1" :max="99" @change="addNum(scope.$index,scope.row)" />
      </template>
    </el-table-column>
    <el-table-column  align="center">
      <template #default="scope">
        <el-button type="danger" @click="deleteCart(scope.row)">
          删除
        </el-button>
      </template>
    </el-table-column>
  </el-table>
  <div style="height: 100px;width: 100%" v-if="cartList.length!==0">
    <div>
      <el-button type="success" style="width: 20%;margin-top: 20px;font-size: 20px" @click="submitOrder">
        <i class="el-icon-s-order"></i>
        点击提交订单
      </el-button>
    </div>
  </div>




</template>

<script>
import {notice_fail,notice_success} from "@/api/elementApi";
import request from "@/utils/request";
import {sessionStoreRefresh} from "@/api/storage";
export default {
  name: "UserCart",
  data(){
    return{
      goodBudgetPrice:0,
      sum_goodBudgetPrice:0,
      cartList:[
      ],
    }
  },
  created() {
    if (this.$store.state.status.loggedIn ){
      this.loadCart()
    }
    else {
      notice_fail("请先登录")
      this.$router.push("/home")
    }

  },
  methods:{
    submitOrder(){
      request.post("/cart/submit"+this.$store.state.status.userId).then(res=>{
          if(res.data.code==="0"){
            notice_success("成功提交订单!")
            this.$store.state.status.cartNum = 0
            this.goodBudgetPrice = 0
            this.sum_goodBudgetPrice = 0
            this.cartList = []
          }
          else {
            notice_fail("订单提交失败!")
          }
      })

    },
    calSumPrice(){
      var goodBudgetPrice = 0
      var sum = 0;
      if(this.cartList.length===0){return 0}
      else{
         //原价
         //优惠后的价格
        for (const index in this.cartList) {
          goodBudgetPrice+=this.cartList[index].gprice*this.cartList[index].gdiscount*this.cartList[index].num
          sum+=this.cartList[index].gprice*this.cartList[index].num
        }
        this.goodBudgetPrice = Math.round(goodBudgetPrice*100)/100 ;
        this.sum_goodBudgetPrice = Math.round((sum-goodBudgetPrice)*100)/100;
      }
    },
    vueCalCartNum(){
      let num = 0;
      if(this.cartList.length===0){return 0}
      else{
        //原价
        //优惠后的价格
        for (const index in this.cartList) {
          num+=this.cartList[index].num
        }
      }
      return num
    },
    loadCart(){
      request.get("/cart/findbyuid"+this.$store.state.status.userId).then(res=>{
        if(res.data.code==="0"){
          this.cartList = res.data.data.records
          this.calSumPrice()
          this.calCartNum()
        }
        else {
          //do nothing
        }
      })

    },
    addNum(index,row){
      console.log("UserCart.vue addNum",row)
      request.put("/cart/updatecartnum",row).then(res=>{
        if(res.data.code==="0"){
          notice_success("修改数量成功")
          this.cartList[index].num = row.num
          this.calSumPrice()
          this.calCartNum()
          this.$store.state.status.cartNum = this.vueCalCartNum()
          console.log("UserCart 正在存储",this.$store.state.status)
          // sessionStorage.setItem("userInfo",JSON.stringify(this.$store.state.status))
          sessionStoreRefresh(this.$store.state.status) //更新store中的内容
        }
        else {
          notice_fail("增加数量失败")
        }
      })
    },
    deleteCart(row){
      const len = this.cartList.length;
      request.delete("/cart/deletebyid"+row.cartid).then(res=>{
        if(res.data.code==="0"){
          notice_success("商品"+row.gname+" 删除成功")
          if(len===1){
            this.cartList = [];
            this.$store.state.status.cartNum =0
          }
        else {
            this.loadCart()
          }

        }
        else {
          notice_fail("删除失败")
        }
      })
    },
    calCartNum(){
      request.get("/cart/cartnumbyuid"+this.$store.state.status.userId).then(res=>{
        this.$store.state.status.cartNum = res.data.data
        console.log("get Num: ",res.data.data)
      })
    }
  }

}
</script>

<style scoped>
.centerClass{
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
</style>