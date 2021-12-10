<template>
  <div class="header">
<!--    头部的导航breadcrumb-->
    <div style="margin-top: 10px;margin-bottom: 10px">
      <el-breadcrumb separator=" > ">
        <el-breadcrumb-item style="font-size: 18px" :to="{ path: '/' }">
          商场首页
        </el-breadcrumb-item>
        <el-breadcrumb-item style="font-size: 18px;">
          后台
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
<!--el tabs 商品管理 后台销售 客户订单 客户浏览日志-->
  <el-tabs v-model="tabsActiveName" @tab-click="tabClickEvent">
<!--    商品管理-->
    <el-tab-pane label="商品管理" name="first">
<!--      最顶上的小图标-->
      <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
        <i class="el-icon-s-management"></i>
        <span style="font-size: 30px">商品管理</span>
      </div>
      <el-button type="primary" style="float: left;margin-left: 30px;margin-top: 5px" @click="addGoods">新增商品</el-button>
<!--商品表格-->
      <el-table :data="goodsPage.goodsList" stripe style="width: 100%">
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
        <el-table-column prop="gname" label="商品名称" align="center"/>
        <el-table-column prop="gprice" label="商品单价($)" align="center"/>
        <el-table-column prop="gdiscount" label="商品折扣" align="center"/>
        <el-table-column prop="gdescribe" label="商品概要" align="center"/>
        <el-table-column prop="gparagraph" label="商品详情" align="center"/>
        <el-table-column prop="gcata" label="商品类别" align="center"/>
        <el-table-column label="在售状态" align="center">
            <template #default="scope">
              <el-tag v-if="scope.row.gstates==='在售'" type="success" style="font-size: 16px;font-weight: bold">在售</el-tag>
              <el-tag v-else type="warning" style="font-size: 16px;font-weight: bold">{{scope.row.gstates}}</el-tag>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150px" align="center">
          <template #default="scope">
            <el-button type="primary" @click="updateGoods(scope.row)" size="mini">
              修改
            </el-button>
            <el-button v-if="scope.row.gstates==='在售'" type="danger" @click="deleteGoods(scope.row)" size="mini">
              下架
            </el-button>
            <el-button v-else type="success" @click="uploadGoods(scope.row)" size="mini">
              上架
            </el-button>
          </template>
        </el-table-column>

      </el-table>
<!--      商品管理分页-->
      <el-pagination
          style="margin-top: 10px"
          background
          v-model:currentPage="goodsPage.currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="goodsPage.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="goodsPage.total"
          @size-change="GoodsHandleSizeChange"
          @current-change="GoodsHandleCurrentChange"
      >
      </el-pagination>
    </el-tab-pane>
<!--    后台销售-->
    <el-tab-pane label="后台销售" name="2" >
      <!--      最顶上的小图标-->
      <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
        <i class="el-icon-s-data"></i>
        <span style="font-size: 30px">后台销售</span>
      </div>
      <el-table :data="salesPage.salesList" stripe style="width: 100%">
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
        <el-table-column prop="gname" label="商品名称" align="center"/>
        <el-table-column prop="gprice" label="商品单价($)" align="center"/>
        <el-table-column prop="gdiscount" label="商品折扣" align="center"/>
        <el-table-column label="在售状态" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.gstates==='在售'" type="success" style="font-size: 16px;font-weight: bold">在售</el-tag>
            <el-tag v-else type="warning" style="font-size: 16px;font-weight: bold">{{scope.row.gstates}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="gsales" label="销量" align="center" sortable>
          <template #default="scope">
            <span v-if="scope.row.gsales===0" style="font-size: 20px;font-weight: bold;color: orangered">{{scope.row.gsales}}</span>
            <span v-else  style="font-size: 20px;font-weight: bold;color: #42b983">{{scope.row.gsales}}</span>
          </template>
        </el-table-column>
      </el-table>
      <!--      商品管理分页-->
      <el-pagination
          background
          v-model:currentPage="salesPage.currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="salesPage.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="salesPage.total"
          @size-change="salesHandleSizeChange"
          @current-change="salesHandleCurrentChange"
      >
      </el-pagination>

    </el-tab-pane>
<!--    客户订单-->
    <el-tab-pane label="客户订单" name="3">
      <!--      最顶上的小图标-->
      <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
        <i class="el-icon-s-order"></i>
        <span style="font-size: 30px">客户购买订单</span>
      </div>
      <el-table :data="ordersPage.ordersList" stripe style="width: 100%">
        <el-table-column prop="gname" label="商品名称" align="center"/>
        <el-table-column prop="uname" label="买家" align="center"/>
        <el-table-column prop="gnum" label="购买数量" align="center"/>
        <el-table-column prop="oprice" label="总额" align="center"/>
        <el-table-column prop="uemail" label="买家邮箱" align="center"/>
        <el-table-column prop="uaddress" label="买家地址" align="center"/>
<!--        <el-table-column prop="otime" label="下单时间" align="center"/>-->
        <el-table-column prop="otime" label="下单时间" align="center">
          <template #default="scope">
            {{getDate(scope.row.otime)}}
          </template>
        </el-table-column>
        <el-table-column prop="stage" label="订单状态" align="center">
          <template #default="scope">
            <span style="font-size: 16px;font-weight: bold">
              <span v-if="scope.row.stage==='未发货'" style="color: orangered"> 未发货</span>
              <span v-else-if="scope.row.stage==='已发货'" style="color: #42b983">
                已发货
                <div style="font-size: 5px;color: #99a9bf">等待对方签收</div>
              </span>
              <span v-else-if="scope.row.stage==='已签收'" style="color: dodgerblue"> 已签收</span>
              <span v-else style="color: darkgray"> 未知状态</span>
            </span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150px" align="center">
          <template #default="scope">


            <el-popover v-if="scope.row.stage==='未发货'" placement="top" :width="180" trigger="hover">
              <p>需要发邮件提醒买家吗?</p>
              <div style="text-align: right; margin: 0">
                <el-button size="mini" type="text" @click="deliverOrder(scope.row)"
                >不需要</el-button
                >
                <el-button type="primary" size="mini" @click="sendEmailThenDeliver(scope.row)"
                >需要发邮件</el-button
                >
              </div>
              <template #reference>
                <el-button  type="success" size="mini">
                  发货
                </el-button>
              </template>
            </el-popover>
            <el-button v-else disabled type="success" size="mini">
              发货
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="margin-top: 10px"
          background
          v-model:currentPage="ordersPage.currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="ordersPage.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="ordersPage.total"
          @size-change="ordersHandleSizeChange"
          @current-change="ordersHandleCurrentChange"
      >
      </el-pagination>
    </el-tab-pane>
<!--    客户浏览日志-->
    <el-tab-pane label="客户浏览日志" name="4">
      <!--      最顶上的小图标-->
      <div style="font-size: 40px;font-weight: bold;float: left;margin-bottom: 10px">
        <i class="el-icon-s-marketing"></i>
        <span style="font-size: 30px">客户浏览日志</span>
      </div>
      <el-table :data="clicksPage.clicksList" stripe style="width: 100%">
        <el-table-column prop="uname" label="买家" align="center"/>
        <el-table-column prop="uemail" label="买家邮箱" align="center"/>
        <el-table-column prop="gname" label="浏览的商品名称" align="center"/>
        <el-table-column prop="otime" label="浏览时间" align="center" sortable>
          <template #default="scope">
            {{getDate(scope.row.ctime)}}
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="margin-top: 10px"
          background
          v-model:currentPage="clicksPage.currentPage"
          :page-sizes="[10,15,20]"
          :page-size="clicksPage.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="clicksPage.total"
          @size-change="clicksHandleSizeChange"
          @current-change="clicksHandleCurrentChange"
      >
      </el-pagination>
    </el-tab-pane>
  </el-tabs>
    <el-dialog v-model="dialogVisible" title="新增" width="30%">
      <el-form :model="newGoodsForm" ref="newGoodsForm" class="newGoodsForm" label-width="120px">
        <el-form-item label="商品名" style="width: 80%" prop="gname">
          <el-input v-model="newGoodsForm.gname"></el-input>
        </el-form-item>
        <el-form-item label="商品原价(未折扣前)" style="width: 80%" prop="gprice">
          <el-input v-model="newGoodsForm.gprice"></el-input>
        </el-form-item>
        <el-form-item label="商品折扣" style="width: 80%" prop="gdiscount">
          <el-input v-model="newGoodsForm.gdiscount"></el-input>
        </el-form-item>
        <el-form-item label="商品概要" style="width: 80%" prop="gdescribe">
          <el-input v-model="newGoodsForm.gdescribe"></el-input>
        </el-form-item>
        <el-form-item label="商品详情" style="width: 80%" prop="gparagraph">
          <el-input v-model="newGoodsForm.gparagraph"></el-input>
        </el-form-item>
        <el-upload ref="uploadImg"
                   class="upload-demo"
                   list-type="picture"
                   action="/api/files/upload"
                   :before-upload="beforeUpload"
                   :on-success="fileUploadSuccess"
                   :limit='1'
        >
          <el-button type="text" round>
            <span v-if="newFormMode">点击修改图片</span>
            <span v-else>点击上传商品图片</span>
          </el-button>
          <template #tip>
            <div class="el-upload__tip">
              单张图片且大小不超500kb
            </div>
          </template>
        </el-upload>
      </el-form>
      <el-button type="primary" @click="confirmUpdateGoods" style="margin-top: 10px">
        <span v-if="newFormMode">确定修改</span>
        <span v-else>确定新增</span>
      </el-button>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import {notice_fail,notice_success,getDate} from "@/api/elementApi";
export default {
  name: "SellerManage",
  created() {
    if(!this.$store.state.status.loggedIn){
      this.$router.push("/home")
      notice_fail("请先登录")
    }
    else{
      this.loadSellerGoods()
    }
  },
  data(){
    return{
      popoverVisible: false,
      tempCover:"", //防止还没确认修改就修改图片了
      newFormMode : 0,
      newGoodsForm:{
        gid:"",
        gname:"",
        sname:this.$store.state.status.username,
        sid:this.$store.state.status.userId,
        gprice:0.0,
        gdescribe: "",
        gparagraph: "",
        gdiscount: 0.0,
        gcata:"手机",
        gstates: "在售",
        gsales:0,
        gcover:""
      },
      dialogVisible:false,
      tabsActiveName:"first",
      goodsPage:{currentPage:1,pageSize:5,total:100,goodsList:[],},
      salesPage:{currentPage:1,pageSize:5,total:100,salesList:[],},
      ordersPage:{currentPage:1,pageSize:5,total:100,ordersList:[],},
      clicksPage:{currentPage:1,pageSize:10,total:100,clicksList:[],},
    }
  },
  methods:{
    sendEmail(row){
      console.log("sendEmail(row)",this.$store.state.status.userEmail)
      var emailInfo = {
        fromEmail:this.$store.state.status.userEmail,
        toEmail:row.uemail,
        uname:row.uname,
        gname:row.gname,
        uaddress:row.uaddress,
        sname:row.sname
      }
      request.post("/orders/email",emailInfo).then(res=>{
        if(res.data.code==="0"){notice_success("发送邮件成功")}
        else {notice_fail("发送邮件失败")}
      })
    },
    sendEmailThenDeliver(row){
      this.sendEmail(row)
      this.deliverOrder(row)
    },
    deliverOrder(row){
      //this.newGoodsForm = row //同样的, 这里希望它马上改变, 所以不需要深复制
      console.log("deliverOrder(row)",row)
      request.put("/orders/changstagebyid"+row.oid,{newStage:"已发货"}).then(res=>{
        if(res.data.code==="0"){
          notice_success("发货成功")
          row.stage = "已发货"
        }
        else {
          notice_fail("发货失败")
        }
      })
    },
    deleteGoods(row){
      this.newGoodsForm = row //这里不用深复制, 直接让它改变
      this.newGoodsForm.gstates = "下架"
      this.confirmUpdateGoods()

    },
    uploadGoods(row){
      this.newGoodsForm = row //这里不用深复制, 直接让它改变
      this.newGoodsForm.gstates = "在售"
      this.confirmUpdateGoods()
    },
    addGoods(){
      this.newGoodsForm = {
        gid:"",
        gname:"",
        sname:this.$store.state.status.username,
        sid:this.$store.state.status.userId,
        gprice:null,
        gdescribe: "",
        gparagraph: "",
        gdiscount: null,
        gcata:"手机",
        gstates: "在售",
        gsales:0,
        gcover:""
      },
      this.newFormMode = 0 //表示新增模式
      this.dialogVisible = true

    },
    updateGoods(row){

      this.newFormMode = 1 //表示修改模式
      this.newGoodsForm = JSON.parse(JSON.stringify(row))  //深复制
      this.dialogVisible = true
      console.log("SellerManage.vue updateGoods(row)",row)


    },
    checkGoodsForm(){
      var info = "";
      const discountLt1 = this.newGoodsForm.gdiscount<1 && this.newGoodsForm.gdiscount>0
      const gpriceGt0 = this.newGoodsForm.gprice>0
      const coverStartWithHttp = this.newGoodsForm.gcover.startsWith("http://")
      if(!discountLt1)info+="折扣必须是一个0-1之间的值\n"
      if(!gpriceGt0) info+="商品价格必须大于0\n"
      if(!coverStartWithHttp) info+="商品封面上传失败"
      if(info!=="")notice_fail(info)
      return discountLt1&&gpriceGt0&&coverStartWithHttp
    },
    confirmUpdateGoods(){

      console.log(this.newGoodsForm)
      if(this.checkGoodsForm()){
        request.put("/goods/update",this.newGoodsForm).then(res=>{
          if(res.data.code==="0"){
            notice_success("操作成功")
            this.dialogVisible=false
            this.resetForm("newGoodsForm")
            this.loadSellerGoods() //修改或者新增完成之后重新加载
          }
          else {
            notice_fail("新增或更新失败")
          }
        })
      }
      this.$refs['uploadImg'].clearFiles();
    },
    beforeUpload(file){
      console.log(file.type)
      const isJPG = (file.type === 'image/png')||(file.type === 'image/jpeg')
      const isLt1M = file.size / 1024 / 1024 < 1
      if (!isJPG) {
        notice_fail('上传头像图片只能是 jpg/png 格式!')
      }
      if (!isLt1M) {
        notice_fail("上传头像图片大小不能超过 2MB!")
      }
      return isJPG && isLt1M
    },
    fileUploadSuccess(res){
      console.log(res)
      // this.tempCover = res.data
      this.newGoodsForm.gcover = res.data
      console.log("res.data",res.data)
    },
    tabClickEvent(tab, event){
      if(tab.props.label==="商品管理")
      {
        this.loadSellerGoods()
      }
      else if(tab.props.label==="后台销售")
      {
        this.loadSales()
      }
      else if(tab.props.label==="客户订单")
      {
        this.loadOrders()
      }
      else if(tab.props.label==="客户浏览日志")
      {
        this.loadClicks()
      }
    },
    clicksHandleSizeChange(pagesize){
      this.clicksPage.pageSize = pagesize
      this.loadClicks()
    },
    clicksHandleCurrentChange(curpage){
      this.clicksPage.currentPage = curpage
      this.loadClicks()
    },
    ordersHandleSizeChange(pagesize){
      this.ordersPage.pageSize = pagesize
      this.loadOrders()
    },
    ordersHandleCurrentChange(curpage){
      this.ordersPage.currentPage = curpage
      this.loadOrders()
    },
    salesHandleSizeChange(pagesize){
      this.salesPage.pageSize = pagesize
      this.loadSales()
    },
    salesHandleCurrentChange(curpage){
      this.salesPage.currentPage = curpage
      this.loadSales()
    },
    GoodsHandleSizeChange(pagesize){
      this.goodsPage.pageSize = pagesize
      console.log("pagesize:",this.goodsPage)
      this.loadSellerGoods()
    },
    GoodsHandleCurrentChange(curpage){
      this.goodsPage.currentPage = curpage
      console.log("curpage:",this.goodsPage)
      this.loadSellerGoods()
    },
    loadSellerGoods(){
      request.get("/goods/findbysid"+this.$store.state.status.userId,
          {
            params:{
              pageNum:this.goodsPage.currentPage,
              pageSize:this.goodsPage.pageSize,
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.goodsPage.total=res.data.data.total
          this.goodsPage.goodsList = res.data.data.records
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    },
    loadSales(){
      request.get("/goods/findbysid"+this.$store.state.status.userId,
          {
            params:{
              pageNum:this.salesPage.currentPage,
              pageSize:this.salesPage.pageSize,
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.salesPage.total=res.data.data.total
          this.salesPage.salesList = res.data.data.records
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    },
    loadOrders(){
      request.get("/orders/findbysid"+this.$store.state.status.userId,
          {
            params:{
              pageNum:this.ordersPage.currentPage,
              pageSize:this.ordersPage.pageSize,
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.ordersPage.total=res.data.data.total
          this.ordersPage.ordersList = res.data.data.records
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    },
    loadClicks(){
      request.get("/clicks/findbysid"+this.$store.state.status.userId,
          {
            params:{
              pageNum:this.clicksPage.currentPage,
              pageSize:this.clicksPage.pageSize,
            }
          }
      ).then(res=>{
        if(res.data.code==="0"){
          this.clicksPage.total=res.data.data.total
          this.clicksPage.clicksList = res.data.data.records
        }
        else {
          notice_fail(res.data.msg)
        }
      })
    },
    getDate,
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }

  }
}
</script>

<style scoped>
.header{
  width: 80%;
  margin-left: 10%;

}
</style>