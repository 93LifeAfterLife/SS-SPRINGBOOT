$(function () {
    var url = "doPageUI";
    // 异步加载分页页面
    $("#pageId").load(url, doGetObjects);
    // 异步查询用户行为日志
    function doGetObjects() {
        // console.log("doGetObjects()");
        // debugger 断点调试
        //1. 定义请求的url
        var url = "log/doFindPageObjects";
        //2. 定义请求的参数
        var params = { "pageCurrent": 1 };
        //3. 发送异步请求(借助jquery中的ajax函数)
        $.getJSON(url, params, function (result) {
            // result 对应服务端的JsonResult, 由函数parse->js对象, 即text(String)->json(Object) 
            // console.log(result);
            doHandleResponseResult(result);
        });
    };

    function doHandleResponseResult(result) {
        // 处理查询响应结果
        if (result.state == 1) {
            //1. 将日志记录添加到tbody中
            doSetTableBodyRows(result.data.records);
            //2. 将日志分页信息添加到pageId位置

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
    }
});
