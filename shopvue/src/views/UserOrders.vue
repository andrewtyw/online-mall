<template>
  <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
    <i class="el-icon-s-order"></i>
    <span style="font-size: 30px">我的订单</span>
  </div>
  <el-table :data="orderList" stripe style="width: 100%">
    <el-table-column prop="gname" label="商品名" />
    <el-table-column prop="sname" label="店家名" />
    <el-table-column prop="gnum" label="订购数量" />
    <el-table-column label="下单时间" >
      <template #default="scope">
        {{getDate(scope.row.otime)}}
      </template>
    </el-table-column>
    <el-table-column prop="oprice" label="总价($)" />
    <el-table-column prop="uaddress" label="送货地址" />
    <el-table-column prop="stage" label="订单状态" align="center">
      <template #default="scope">
            <span style="font-size: 16px;font-weight: bold">
              <span v-if="scope.row.stage==='未发货'" style="color: orangered"> 未发货</span>
              <span v-else-if="scope.row.stage==='已发货'" style="color: #42b983"> 已发货</span>
              <span v-else-if="scope.row.stage==='已签收'" style="color: dodgerblue"> 已签收</span>
              <span v-else style="color: darkgray"> 未知状态</span>
            </span>
      </template>
    </el-table-column>
    <el-table-column label="操作">
      <template #default="scope">
        <el-button v-if="scope.row.stage==='已发货'" type="success" @click="signOrder(scope.row)" size="mini">
          签收
        </el-button>
        <el-button v-else disabled type="success" size="mini">
          签收
        </el-button>
      </template>
    </el-table-column>
<!--    <el-table-column prop="uaddress" label="送货地址" />-->
  </el-table>
  <el-pagination
      style="margin-top: 10px"
      background
      v-model:currentPage="currentPage"
      :page-sizes="[10,15,20]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="ordersHandleSizeChange"
      @current-change="ordersHandleCurrentChange"
  >
  </el-pagination>
</template>

<script>
import {notice_fail, notice_success} from "@/api/elementApi";
import request from "@/utils/request";

export default {
  name: "UserOrders",
  created() {
    if (this.$store.state.status.loggedIn ){
      this.loadOrderList()
    }
    else {
      notice_fail("请先登录")
      this.$router.push("/home")
    }
  },
  data(){
    return{
      currentPage:1,
      pageSize:10,
      total:10,
      orderList:[
      ],
    }
  },
  methods:{
    signOrder(row){
      request.put("/orders/changstagebyid"+row.oid,{newStage:"已签收"}).then(res=>{
        if(res.data.code==="0"){
          notice_success("签收成功")
          row.stage = "已签收"
        }
        else {
          notice_fail("签收失败")
        }
      })
    },
    ordersHandleSizeChange(pagesize){
      this.pageSize = pagesize
      this.loadOrderList()
    },
    ordersHandleCurrentChange(curpage){
      this.currentPage = curpage
      this.loadOrderList()
    },
    getDate(date){
      return date.substring(0,10)+" "+date.substring(11,19)
    },
    loadOrderList(){
      request.get("/orders/findbyuid"+this.$store.state.status.userId,
          {
            params:{
              pageNum:this.currentPage,
              pageSize:this.pageSize,
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.orderList = res.data.data.records
          this.total=res.data.data.total
          this.salesList = res.data.data.records
        }
        else {
          notice_fail("您暂时还没有订单")
        }
      })
    }
  }
}
</script>

<style scoped>

</style>