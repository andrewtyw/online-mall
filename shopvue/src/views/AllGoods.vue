<template>

  <div>

<!--    <el-tabs type="border-card">-->
<!--      <el-tab-pane label="手机">-->
<!--        手机-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="电脑">-->
<!--        电脑-->
<!--      </el-tab-pane>-->
<!--      <el-tab-pane label="画">画</el-tab-pane>-->
<!--      <el-tab-pane label="食物">食物</el-tab-pane>-->
<!--      <el-tab-pane label="其它">其它</el-tab-pane>-->
<!--    </el-tabs>-->
  </div>
  <!--  显示图片-->
  <div v-loading="false">
    <el-row>

      <el-button type="info" @click="showAllGoods" style="margin-left: 0px;width: 10%" plain >显示所有商品</el-button>
      <div style="margin-left: 0px;width: 90%">
        <el-input
            v-model="tempSearch"
            placeholder="请输入商品名字 (支持模糊搜素) "
            class="input-with-select" clearable>
          <template #append>
            <el-button  type="success" @click="searchNow">
              <i class="el-icon-search"></i>
              查询商品
            </el-button>
          </template>
        </el-input>
      </div>

    </el-row>
    <el-row>
      <div style="margin-left: 20px;margin-top: 10px">
        <span v-if="search!==''">包含 '
          <span style="font-weight: bold">{{search}}</span>
          ' 的商品:</span>
      </div>
    </el-row>
    <el-row>
      <el-col
          style="padding: 25px"
          v-for="(item, index) in allGoodsList"
          :span="6"
      >
        <el-card shadow="hover" :body-style="{ padding: '0px' }" @click="clickAllGoods(item)">
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
                <span style="color: orangered">${{Math.round(item.gprice*item.gdiscount*100)/100}}</span>
                <span>&nbsp</span>
                <span style="text-decoration: line-through;color: #99a9bf">${{item.gprice}}</span>
              </span>
            </div>
          </div>
        </el-card>
      </el-col>

    </el-row>
  </div>
  <div>
    <el-dialog v-model="allGoodsDetailDialogVisible" title="商品详情" width="60%" style="height: 70%;" >
      <el-card>
        <el-col style="width: 50%">
          <el-image
              style="width:80%;height: 80%;margin-bottom: 10%"
              :src="allGoodsDetails.gcover"
              :fit="fit"
          ></el-image>
        </el-col>
        <el-col style="width: 50%">
          <!--          介绍名字-->
          <div>
            <span style="font-size: 25px;font-weight: bold">{{allGoodsDetails.gname}}</span>
          </div>
          <el-row>
            <div style="margin-top: 20px;margin-bottom: 10px">
              <p style="color: #99a9bf;font-size: 15px">
                {{allGoodsDetails.gparagraph}}
              </p>
            </div >
          </el-row>

          <!--          显示厂家-->
          <el-row>
            <div style="margin-top: 20px;float: left">
            <span style="font-size: 20px;color: orange">
              商家:{{allGoodsDetails.sname}}
            </span>
            </div>
          </el-row>
          <!--          显示价格-->
          <el-row style="margin-top: 40px">
            <div>
              <span style="font-size: 30px">
                <span style="color: orangered">${{Math.round(allGoodsDetails.gprice*allGoodsDetails.gdiscount*100)/100}}</span>
                <span>&nbsp</span>
                <span style="text-decoration: line-through;color: #99a9bf">${{allGoodsDetails.gprice}}</span>
              </span>
            </div>
          </el-row>


        </el-col>
      </el-card>
      <div class="button">
        <el-button class="shop-cart" type="success"  @click="allGoodsAddShoppingCart">加入购物车</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import {notice_fail, notice_success} from "@/api/elementApi";
import {sessionStoreRefresh} from "@/api/storage";

export default {
  name: "AllGoods",
  data(){
    return{
      fit:'fill',
      allGoodsDetailDialogVisible:false,
      allGoodsDetails:{},
      search:'',
      tempSearch:'',
      allGoodsList:[],
    }
  },
  created() {
    this.$nextTick(()=>{
      this.loadAllGoodsList()
    })
  },
  methods:{
    clickAllGoods(item){
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
      this.allGoodsDetailDialogVisible=true
      this.allGoodsDetails = item
      console.log("in AllGoods clickGoods:",item)
    },
    allGoodsAddShoppingCart(){
      if(this.$store.state.status.userType===1)
      {
        //把商品加入购物车
        var cartInfo = {
          uid:this.$store.state.status.userId,
          gid:this.allGoodsDetails.gid
        }
        console.log("cartInfo",cartInfo)
        request.post("/cart/addcart",cartInfo).then(res=>{
          if(res.data.code==="0"){
            notice_success("成功加入到购物车")
            this.$store.state.status.cartNum +=1  //修改购物车数量
            sessionStoreRefresh(this.$store.state.status) //更新store中的内容
            this.allGoodsDetailDialogVisible=false
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
    },
    loadAllGoodsList(){
      request.get("/goods/find",
          {
            params:{
              pageNum:1,
              pageSize:10000,
              search:this.search
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.allGoodsList = res.data.data.records;
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    },
    searchNow(){
      this.search = this.tempSearch
      console.log(this.tempSearch)
      this.loadAllGoodsList()
    },
    showAllGoods(){
      this.search=this.tempSearch = ''
      this.loadAllGoodsList()
    }
  },

}
</script>

<style scoped>

</style>