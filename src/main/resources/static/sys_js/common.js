// 页面加载就绪函数, 加载完成后执行内部方法
$(function () {
    // 点击事件注册: 当点击指定id对应的对象时, 执行操作
    $("#load-log-id").click(function () {
        const url = "log/doLogListUI";
        // load函数是一个jquery中的异步加载函数, 封装了ajax调用过程
        $("#mainContentId").load(url);
    });

    // 加载菜单页面
    doLoadUI("load-menu-id", "menu/doMenuListUI");
    // 加载部门页面
    doLoadUI("load-dept-id", "dept/doDeptListUI");
});

// 提取共性操作, 加载页面
function doLoadUI(id, url) {
    $("#" + id).click(function () {
        $("#mainContentId").load(url);
    });
};