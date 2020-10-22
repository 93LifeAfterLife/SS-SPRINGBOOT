// 原生ajax, 不使用JQuery
window.onload = function () {
    var searchBtn = document.getElementById("searchId");
    searchBtn.onclick = doGetObjects;
};

function doGetObjects() {
    const url = "doFindPageObjects";
    const params = "pageCurrent=1";
    getMyJson(url, params, function (response) {
        // console.log(result);
        doHandleResponse(response);
    });
};

function getMyJson(url, params, callback) {
    //1. 构建XHR对象
    var xhr = new XMLHttpRequest;
    //2. 监听事件, 回调函数
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            // console.log("response OK!");
            var jsonObj = JSON.parse(xhr.responseText);
            // console.log(jsonObj);
            callback(jsonObj);
        }
    }
    //3. 建立连接
    let getUrl = url + "?" + params;
    xhr.open("GET", getUrl, true);
    //4. 发送请求
    xhr.send(null);
};

function doHandleResponse(response) {
    if (response.state == 1) {
        doSetTableBodyRows(response.data.records);
    } else {
        alert(result.message);
    }
};

function doSetTableBodyRows(records) {
    var tBody = document.getElementById("tbodyId");
    tBody.innerHTML = "";
    for (let i in records) {
        let tr = document.createElement("tr");
        let idTd = document.createElement("td");
        idTd.innerText = records[i].id;
        let timeTd = document.createElement("td");
        timeTd.innerText = records[i].createdTime;
        tr.appendChild(idTd);
        tr.appendChild(timeTd);
        tBody.appendChild(tr);
    }
};