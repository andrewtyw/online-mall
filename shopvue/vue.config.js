module.exports = {
    devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
        port: 9897,
        proxy: {                 //设置代理，必须填
            '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                target: 'http://120.24.230.237:9898',     //代理的目标地址http://localhost:9898
                changeOrigin: true,              //是否设置同源，输入是的
                pathRewrite: {                   //路径重写
                    '/api': ''                     //忽略拦截器中的这个词
                }
            }
        }
    }
}