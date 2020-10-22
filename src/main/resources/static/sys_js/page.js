// 初始化分页信息
function doSetPagination(pageObject) {
    //1. 初始化总记录数
    $(".rowCount").html("总记录数(" + pageObject.rowCount + ")");
    //2. 初始化总页数
    $(".pageCount").html("总记页数(" + pageObject.pageCount + ")");
    //3. 初始化当前页码
    $(".pageCurrent").html("当前页(" + pageObject.pageCurrent + ")");
    //4. 保存当前页码值和总页数, 供外界访问
    $("#pageId").data("pageCurrent", pageObject.pageCurrent);
    $("#pageId").data("pageCount", pageObject.pageCount);
};

// 注册点击事件, 同时为多个节点注册函数
$(function () {
    $(".pagination").on("click", ".first,.pre,.next,.last", doJumpToPage);
});

function doJumpToPage() {
    //1. 获取被点击对象class属性的值
    let cls = $(this).prop("class");
    // console.log(cls);
    //2. 基于class属性的值修改当前页码值
    let pageCurrent = $("#pageId").data("pageCurrent");
    let pageCount = $("#pageId").data("pageCount");;
    if (cls == "first") {
        pageCurrent = 1;
    } else if (cls == "pre" && pageCurrent > 1) {
        pageCurrent--;
    } else if (cls == "next" && pageCurrent < pageCount) {
        pageCurrent++;
    } else if (cls == "last") {
        pageCurrent = pageCount;
    } else {
        return;
    }
    // console.log("pageCurrent", pageCurrent);
    //3. 保存新的pageCurrent
    $("#pageId").data("pageCurrent", pageCurrent);
    //4. 基于pageCurrent执行新的查询
    doGetObjects();
};