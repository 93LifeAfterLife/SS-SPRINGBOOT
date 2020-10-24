// 页面加载就绪函数, 加载完成后执行内部方法
$(function () {
    // 点击事件注册: 当点击指定id对应的对象时, 执行操作
    $("#load-log-id").click(function () {
        const url = "log/doLogListUI";
        // load函数是一个jquery中的异步加载函数, 封装了ajax调用过程
        $("#mainContentId").load(url);
    });
});