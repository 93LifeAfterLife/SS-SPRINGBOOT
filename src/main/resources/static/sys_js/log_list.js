$(function () {
    var url = "doPageUI";
    // 异步加载分页页面
    $("#pageId").load(url, doGetObjects);
    // 搜索按钮注册点击事件
    $(".input-group-btn").on("click", doQueryObjects).on("click", doDeleteObjects);
    // thead中checkbox对象事件注册
    $("#checkAll").onchange = doChangeTBodyCheckboxState; 
});

// 异步查询用户行为日志
function doGetObjects() {
    // console.log("doGetObjects()");
    // debugger 断点调试
    //1. 定义请求的url
    const url = "log/doFindPageObjects";
    //2. 定义请求的参数
    var pageCurrent = $("#pageId").data("pageCurrent");
    //2.1 undefined判定
    if (!pageCurrent) {
        pageCurrent = 1;
    }
    var params = { "pageCurrent": pageCurrent };
    //2.2 保存搜索框内的username
    var username = $("#searchNameId").val();
    if (username) {
        //2.3 向JSON对象中再添加一条 K-V
        params.username = username;
    }
    //3. 发送异步请求(借助jquery中的ajax函数get, 封装了XHR过程)
    $.getJSON(url, params, function (result) {
        // result 对应服务端的JsonResult, 由函数parse->js对象, 即text(String)->json(Object) 
        // console.log(result);
        doHandleResponseResult(result);
    });
    // 创建XHR
    // var xhr = new XMLHttpRequest();  
    // 注册状态监听
    // xhr.onreadystatechange = function (result) { if (xhr.status == 200 && xhr.readyState == 4) { console.log(result) } }
    // 开启请求
    // xhr.open("GET", "log/doFindPageObjects?pageCurrent=1", true);
    // 发送请求, 当状态变化时会被监听函数获取并打印  
    // xhr.send(null);
};

function doHandleResponseResult(result) {
    // 处理查询响应结果
    if (result.state == 1) {
        //1. 将日志记录添加到tbody中
        doSetTableBodyRows(result.data.records);
        //2. 将日志分页信息添加到pageId位置
        doSetPagination(result.data);
    } else {
        alert(result.message);
    }
};

function doSetTableBodyRows(records) {
    //1. 获取tbody对象, 并清空数据
    var tBody = $("#tbodyId");
    tBody.empty();
    //2. 迭代records, 并追加到tbody中
    for (let i = 0; i < records.length; i++) {
        //2.1 创建tr对象(一行)
        var tr = $("<tr></tr>");
        //2.2 创建多个td对象(多列)
        var tds = doCreateTds(records[i]);
        //2.3 将td追加到tr对象中
        tr.append(tds);
        //2.4 将tr追加到tbody中
        tBody.append(tr);
    }
};

function doCreateTds(record) {
    var tds = "<td><input type='checkbox' class='cBox' name='cItem' value='" + record.id + "'></td>"
        + "<td>" + record.username + "</td>"
        + "<td>" + record.operation + "</td>"
        + "<td>" + record.method + "</td>"
        + "<td>" + record.params + "</td>"
        + "<td>" + record.ip + "</td>"
        + "<td>" + record.time + "</td>";
    return tds;
};

// 点击查询按钮执行查询操作
function doQueryObjects() {
    //1. 初始化当前页码值为1
    $("#pageId").data("pageCurrent", 1);
    //2. 基于用户名查询日志信息
    doGetObjects();
    //3. 
};

// 执行删除业务
function doDeleteObjects() {

}