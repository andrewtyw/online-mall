<template>
 <div class="header">
   <el-menu
       class="el-menu-demo"
       mode="horizontal"
       @select="handleSelect"
   >
     <el-menu-item index="1" @click="$router.push('/home')">GoGoMart首页</el-menu-item>
     <el-menu-item index="2" @click="$router.push('/allgoods')">全部商品</el-menu-item>
     <el-menu-item index="3" @click="$router.push('/about')">About</el-menu-item>
<!--     <div style="margin-top: 10px;margin-left: 10px">-->
<!--       <el-input-->
<!--           v-model="search"-->
<!--           placeholder="请输入商品名字"-->
<!--           class="input-with-select" clearable>-->
<!--         <template #append>-->
<!--           <el-button  type="success" >-->
<!--             <i class="el-icon-search"></i>-->
<!--             查询商品-->
<!--           </el-button>-->
<!--         </template>-->
<!--       </el-input>-->
<!--     </div>-->
   </el-menu>
   <el-divider style="margin-top: -1px"></el-divider>
   <router-view></router-view>　
 </div>
</template>

<script>
export default {
  name: "Header",
  data(){
    return{

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