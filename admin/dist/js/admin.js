/**
 * admin.js
 * @zzy
 * @date    2015-03-28 16:14:29
 * @version $Id$
 */
//用户权限数组
var roleArray = ["头像审核", "身份审核", "房产审核", "车产审核", "学历审核", "相亲树", "聊天", "管理"];
//兼职管理列表
var adminList = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/sy/st/m",
	userGroup: [],
	userIndex: 0,
	insertText: "",
	load: "",
	init: function() {
		var me = this;
		for (var i in g_usermap) {
			me.userGroup.push(i);
		}
		if (me.load === "") {
			me.load = g_loadMode.init($("#adminContent"));
		} else {
			me.load.show();
		}
		me.renderItem();
	},
	//渲染用户数据列表
	renderItem: function() {
		var me = this,
			userData = g_usermap[me.userGroup[me.userIndex]],
			userRole = userData.role.split(""),
			roleStr = "";
		me.insertText = "";
		for (var i = 0; i < roleArray.length; i++) {
			if (userRole[i] == "1") {
				roleStr += "<p>" + roleArray[i] + "</p>";
			}
		}
		me.insertText += "<td><p>" + userData.nick + "(" + userData.uid + ")" + "</p></td>" +
			"<td>" + roleStr + "</td>";
		//先获取今天数据
		me.getToday();
	},
	//渲染当天数据
	renderToday: function(data) {
		var me = this,
			userData = g_usermap[me.userGroup[me.userIndex]],
			rowStr = "<td><p>已聊用户：" + data.chatUsers + "</p>" +
			"<p>已聊：" + data.chats + "</p>" +
			"<p>已审核头像：" + data.faceVerifies + "</p>" +
			"<p>已审核身份：" + data.idcardVerifies + "</p>" +
			"<p>已审核相亲树：" + data.lovetreeVerifies + "</p></td>";
		me.insertText += rowStr;
		//渲染完当天数据后再渲染上个月数据
		me.getLastmonth();
	},
	//渲染上月数据
	renderLastMonth: function(data) {
		var me = this,
			userData = g_usermap[me.userGroup[me.userIndex]],
			rowStr = "<td><p>已聊用户：" + data.chatUsers + "</p>" +
			"<p>已聊：" + data.chats + "</p>" +
			"<p>已审核头像：" + data.faceVerifies + "</p>" +
			"<p>已审核身份：" + data.idcardVerifies + "</p>" +
			"<p>已审核相亲树：" + data.lovetreeVerifies + "</p></td>";
		me.insertText += rowStr;
		var row = document.createElement("tr");
		$(row).html(me.insertText);
		$("#userInfoList").append(row);
		//渲染完上月数据开始下一个用户数据渲染
		me.userIndex++;
		if (me.userIndex >= me.userGroup.length) {
			me.load.fadeOut();
		} else {
			me.renderItem();
		}
	},
	//获取一个用户今天数据
	getToday: function() {
		var me = this;
		$.ajax({
			url: me.getUrl + "/admin/action",
			type: 'GET',
			dataType: 'jsonp',
			data: {
				"userid": me.userGroup[me.userIndex],
				"type": 1
			},
			jsonp: "callbackparam",
			jsonpCallback: "callback"
		}).done(function(data) {
			me.renderToday(data);
		});
	},
	//获取一个用户上月数据
	getLastmonth: function() {
		var me = this;
		$.ajax({
			url: me.getUrl + "/admin/action",
			type: 'GET',
			dataType: 'jsonp',
			data: {
				"userid": me.userGroup[me.userIndex],
				"type": 2
			},
			jsonp: "callbackparam",
			jsonpCallback: "callback"
		}).done(function(data) {
			me.renderLastMonth(data);
		});
	}
};
//反馈列表
var feedList = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/sy/st/m",
	page: 0,
	pageSize: 20,
	load: "",
	init: function() {
		var me = this;
		me.getList(me.pageSize, me.page);
		me._bendEvent();
	},
	getList: function(pageSize, pageNum) {
		var me = this;
		if (me.load === "") {
			me.load = g_loadMode.init($("#adminContent"));
		} else {
			me.load.show();
		}
		$.ajax({
			url: me.getUrl + "/report/list",
			type: 'GET',
			dataType: 'jsonp',
			data: {
				"size": pageSize,
				"page": pageNum,
				"userid": parseInt(g_loginuser.xunai_uid)
			},
			jsonp: "callbackparam",
			jsonpCallback: "callback"
		})
			.done(function(data) {
				if (data === null) {
					me.load.fadeOut();
					return;
				}
				me.page = pageNum;
				me._render(data);
				me._bendEvent();
			});
	},
	//下一页
	nextPage: function() {
		var me = this;
		me.getList(me.pageSize, me.page + 1);
	},
	//前一页
	prevPage: function() {
		var me = this;
		me.getList(me.pageSize, me.page - 1);
	},
	//渲染列表
	_render: function(data) {
		var me = this;
		$("#feedlist").html("");
		for (var i = 0; i < data.length; i++) {
			var htmlstr = '<tr><td>' + new Date(data[i].ctime).format("yyyy-MM-dd hh:mm:ss") + '</td><td>' + data[i].uid + '</td>' +
				'<td>' + data[i].content + '</td></tr>';
			$("#feedlist").append(htmlstr);
		}
		me.load.fadeOut();
	},
	//绑定事件
	_bendEvent: function() {
		var list = this;
		$('a[data-label="Pre"]').unbind('click').bind('click', function() {
			list.prevPage();
		});
		$('a[data-label="Next"]').unbind('click').bind('click', function() {
			list.nextPage();
		});
	}
};
$(function() {
	adminList.init();
	$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
		switch (e.target.id) {
			case "adminlink":
				adminList.init();
				break;
			case "feedlink":
				feedList.init();
				break;
			default:
				break;
		}
	});
});