// url
var M_URL = "/csv-upload/UploadServlet";
// 总页数
var PAGES = 0;
//当前页
var CUR_PAGE = 1;
//当前是否在操作数据库
var DB_FLAG = true;

// 入口
$(document).ready(function() {
	initClick();
	upload();
	monitorKeyboard();
});

// 键盘监听
function monitorKeyboard() {
	$(document).keydown(function(e) {
		// ctrl + s
		if (e.ctrlKey == true && e.keyCode == 83) {
			saveFile();
			_log("ctrl + s");
			return false;
		}
	});
}

function saveFile() {
	$("#my-download-file-a")[0].click();
//	document.getElementById("my-download-file-a").click();
}

// 初始化按钮点击事件
function initClick() {
	$("#my-readdb-btn").click(function() {
		loadDB();
	});
}

// 加载数据库资源
function loadDB() {
	$.ajax({
		url : M_URL,
		type : "POST",
		data : "method=loadDB",
		dataType : 'json',
		success : function(res) {}
	});
}

// 文件上传
function upload() {
	layui.use('upload', function() {
		layui.upload({
			url : M_URL + "?method=upload",
			title : '请选择CSV文件',
			method : 'post',
			ext : 'csv',
			before : function(input) {},
			success : function(res, input) {
				_log(res);
			}
		});
	});
}

// 切换csv操作或数据库操作时的页面部分显示的刷新
function refreshStatus() {
	if (DB_FLAG) {

	} else {

	}
}

// 打印控制台日志
function _log(msg) {
	console.log(msg);
}