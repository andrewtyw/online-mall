

const sessionStoreRefresh = function (item) {
    sessionStorage.clear() //首先清空sessionStorage中的内容
    console.log("is storing !!!:",JSON.stringify(item))
    sessionStorage.setItem("userInfo",JSON.stringify(item))
}

export {sessionStoreRefresh}