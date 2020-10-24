$(function () {
    var url = "doPageUI";
    // 异步加载分页页面
    $("#pageId").load(url, doGetObjects);
    // 搜索按钮注册点击事件
    $(".input-group-btn").on("click", ".btn-search", doQueryObjects);
    $(".input-group-btn").on("click", ".btn-delete", doDeleteObjects);
    // thead中checkbox对象事件注册
    $("#checkAll").on("change", doChangeTBodyCheckboxState);
    // tbody中checkbox对象事件注册
    $("#tbodyId").on("change", ".cBox", doChangeTHeadCheckboxState);
});

// 异步查询用户行为日志
function doGetObjects() {
    // 0. 重置全选框的状态值为false
    $("#checkAll").prop("checked", false);
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
    // debugger
    // console.log("doDeleteObjects()");
    //1. 获取选中的id值
    var ids = doGetChechedIds();
    // 以下校验在客户端和服务端双方都进行判定
    if (ids.length == 0) {
        alert("请至少选择一项进行操作!!");
        return;
    }
    //2. 异步请求执行删除操作
    const url = "log/doDeleteObjectsByIds";
    var params = { "ids": ids.toString() };
    // console.log(params);
    $.post(url, params, function (result) {
        if (result.state == 1) {
            alert(result.message);
            //2.1 刷新策略(重新查询, 清空tboby内容);
            doRefresh();
            //2.2 若在最后一页执行删除操作时, 页面应该返回上一页
            // doChangePageCurrent();
            //2.3 执行初始化操作
            // doGetObjects();
        } else {
            alert(result.message);
        }
    });
};

function doGetChechedIds() {
    //1. 定义一个数组, 用于存储选中的checkbox 的id值
    var array = [];
    //2. 获取tbody中所有类型为checkbox的input元素, 并迭代这些元素, 每发现一个元素都执行回调函数
    $("#tbodyId input[type='checkbox']").each(function () {
        //2.1 假如此元素的checked属性为true
        // console.log("this", this);
        // console.log("this.checkedProp", $(this).prop("checked"));
        if ($(this).prop("checked")) {
            //2.2 调用数组对象的push方法, 将选中对象的值存储到数组
            array.push($(this).val());
        }
    });
    return array;
};

function doChangeTBodyCheckboxState() {
    // console.log("doChangeTBodyCheckboxState()");
    //1. 获取全选checkAll对象的状态值
    let flag = $(this).prop("checked");
    //2. 修改tbody中checkbox对象的状态值
    $("#tbodyId input[type='checkbox']").each(function () {
        $(this).prop("checked", flag);
    });
};

function doChangeTHeadCheckboxState() {
    // console.log("doChangeTHeadCheckboxState()");
    //1. 获取所有tbody中checkbox对象的状态相与的结果
    let flag = true;
    //1.1 逻辑与: 只要有一个是false, 那么flag的值就是false
    $("#tbodyId input[type='checkbox']").each(function () {
        flag = flag && $(this).prop("checked");
    });
    // console.log("flag", flag);
    //2. 修改checkAll对象的状态值
    $("#checkAll").prop("checked", flag);
};

// 刷新策略
function doRefresh() {
    //1. 获取当前页码值, 总页数
    let pageCurrent = $("#pageId").data("pageCurrent");
    let pageCount = $("#pageId").data("pageCount");
    //2. 获取checkAll的状态值
    var flag = $("#checkAll").prop("checked");
    //3. 判断并修改当前页码值 
    //3.1 若删除完最后一页且是唯一一页, 则直接清空数据, 不再执行查询
    if (pageCurrent == pageCount && pageCurrent == 1 && flag) {
        $("#tbodyId").empty();
        doInitPagination();
        // 并重置全选框
        $("#checkAll").prop("checked", false);
        $("#tbodyId").html("记录已不存在!");
        return;
    }
    //3.2 不为首页, 没有勾选'全选', 即全选删除要跳回前一页, 且不是最后一页
    if (pageCurrent != 1 && flag && pageCurrent == pageCount) {
        pageCurrent--;
        $("#pageId").data("pageCurrent", pageCurrent);
    }
    //3.3 执行默认操作
    doGetObjects();
};
