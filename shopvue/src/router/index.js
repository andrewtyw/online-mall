import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import header from '../components/Header'
import HelloWorld from "@/components/HelloWorld";
import AllGoods from "@/views/AllGoods"
import UserCart from "@/views/UserCart"
import UserOrders from "@/views/UserOrders";
import SellerManage from "@/views/SellerManage";
const routes = [
  {
    path: '/',
    redirect:"/home", //重定向到home
    name: 'header',
    component: header,
    children: [
      {
        path: "/home",
        name: "Home",
        component: Home
      },
      {
        path: "/allgoods",
        name: "allgoods",
        component: AllGoods
      },
      {
        path: "/usercart",
        name: "usercart",
        component: UserCart
      },
      {
        path: "/userorders",
        name: "userorders",
        component: UserOrders
      },
      {
        path: '/about',
        name: 'About',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
      }
    ]
  },
  {
    path:"/sellermanage",
    name:"sellermanage",
    component: SellerManage
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
