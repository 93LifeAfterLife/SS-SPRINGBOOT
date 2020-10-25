var zTree; //zTree是第三方扩展的一个Jquery插件
//初始化zTree时会用到
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",  //节点数据中保存唯一标识的属性名称
            pIdKey: "parentId",  //节点数据中保存其父节点唯一标识的属性名称
            rootPId: null  //根节点id
        }//json 格式javascript对象
    }
};//json 格式的javascript对象

$(function () {
    //加载菜单树的事件注册
    $(".form-group")
        .on("click", ".load-sys-menu",
            doLoadZtreeNodes);
    //box-footer中按钮事件注册

    $(".box-footer")
        .on("click", ".btn-cancel", doCancel)
        .on("click", ".btn-save,.btn-update", doSaveOrUpdate)

    //menuLayer中按钮事件注册
    $("#menuLayer")
        .on("click", ".btn-confirm", doSetSelectedNode)
        .on("click", ".btn-cancel", doHideZtree);
    //获取mainContentId上值
    var rowData =
        $("#mainContentId").data("rowData");
    //假如有值说明是修改,则基于此值初始化编辑页面.
    if (rowData) doInitEditFormData(rowData);
});

//初始化表单数据
function doInitEditFormData(rowData) {
    $("input[value=" + rowData.type + "]").prop("checked", true);
    $("#nameId").val(rowData.name);
    $("#sortId").val(rowData.sort);
    $("#permissionId").val(rowData.permission);
    $("#urlId").val(rowData.url);
    $("#parentId").val(rowData.parentName);
    $("#parentId").data("parentId", rowData.parentId);
};

//回到列表页面
function doCancel() {
    var url = "menu/doMenuListUI";
    $("#mainContentId").load(url);
};

//将表单数据发送到服务端
function doSaveOrUpdate() {
    //debugger
    //1.获取表单中用户输入数据
    var params = doGetEditFormData();
    var rowData = $("#mainContentId").data("rowData");
    if (rowData) params.id = rowData.id;
    //2.异步提交数据到服务端
    var insertUrl = "menu/doSaveObject";
    var updateUrl = "menu/doUpdateObject";
    var url = rowData ? updateUrl : insertUrl;

    $.post(url, params, function (result) {
        if (result.state == 1) {
            alert(result.message);
            doCancel();
        } else {
            alert(result.message);
        }
    })
};

function doGetEditFormData() {
    var params = {
        "type": $("input[name='typeId']:checked").val(),
        "name": $("#nameId").val(),
        "parentId": $("#parentId").data("parentId"),
        "url": $("#urlId").val(),
        "sort": $("#sortId").val(),
        "permission": $("#permissionId").val()
    }
    return params;
};

//设置选中节点(上级菜单)
function doSetSelectedNode() {
    console.log(zTree);
    //1.获取选中节点
    var nodes = zTree.getSelectedNodes();
    if (!nodes || nodes.length == 0) {
        doHideZtree();
        return;
    }
    //2.将选中节点内容填充到页面
    $("#parentId").data("parentId", nodes[0].id);
    $("#parentId").val(nodes[0].name);
    //3.隐藏zTree
    doHideZtree();
};

//隐藏zTree
function doHideZtree() {
    $("#menuLayer").css("display", "none");
}
//加载zTree菜单
function doLoadZtreeNodes() {
    //1.url
    var url = "menu/doFindZtreeMenuNodes";
    //2.request
    $.getJSON(url, function (result) {
        console.log(result);
        if (result.state == 1) {
            zTree = $.fn.zTree.init(
                $("#menuTree"),
                setting,
                result.data);
            $("#menuLayer").css("display", "block");
        } else {
            alert(result.message);
        }
    });
};