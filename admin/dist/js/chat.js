/**
 * chat.js
 * @zzy
 * @date    2015-03-31 14:19:15
 * @version 1.000
 */
var chatUserlist = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/sy/st/m",
	page: 0,
	pageSize: 20,
	init: function() {
		var me = this;
		me.getList(me.pageSize, me.page);
		me._bendEvent();
	},
	getList: function(pageSize, pageNum) {
		var me = this;
		$.ajax({
				url: me.getUrl + "/mo/all",
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
		$("#chatUserlist").html("");
		for (var i = 0; i < data.length; i++) {
			var htmlstr = '<li role="presentation" data-rid="' + data[i].rid + '"  data-uid="' + data[i].uid + '"><a href="javascript:void(0);">' + data[i].uid + '</a></li>';
			$("#chatUserlist").append(htmlstr);
		}
	},
	//绑定事件
	_bendEvent: function() {
		var list = this;
		$(".chat-user-list li").unbind('click').bind("click", function() {
			var me = $(this),
				uid = me.data('uid'),
				rid = me.data('rid');
			$(".chat-user-list li").removeClass("active");
			me.addClass("active");
			chatDOM.updateDom(uid, rid, true);
		});
		$('a[data-label="Pre"]').unbind('click').bind('click', function() {
			list.prevPage();
		});
		$('a[data-label="Next"]').unbind('click').bind('click', function() {
			list.nextPage();
		});
	}
};
var chatDOM = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/sy/st/m",
	uid: "", //用户ID
	rid: "", //机器人ID
	loop: false,
	loopTime: 3000,
	//更新整个dom
	updateDom: function(uid, rid, needRefresh) {
		var me = this;
		me.uid = uid || me.uid;
		me.rid = rid || me.rid;
		$.ajax({
				url: me.getUrl + "/mo/single",
				type: 'GET',
				dataType: 'jsonp',
				data: {
					"uid": me.uid,
					"rid": me.rid,
					"userid": parseInt(g_loginuser.xunai_uid)
				},
				jsonp: "callbackparam",
				jsonpCallback: "callback"
			})
			.done(function(data) {
				if (needRefresh === true) {
					me.destroyLoop();
					me.renderUserinfo(data.robot, data.user);
					me.renderChatcontent(data.chats, data.robot, data.user);
					me._bendEvent();
				} else {
					me.renderChatcontent(data.chats, data.robot, data.user);
				}
			});
	},
	//渲染用户信息
	renderUserinfo: function(rData, uData) {
		var me = this;
		//机器人信息
		$("#robotFace").attr('src', rData.face);
		$("#robotName").text(rData.nick ? rData.nick : "未知");
		var rAge = rData.birth ? parseInt((new Date() - new Date(rData.birth)) / (365 * 24 * 60 * 60 * 1000)) : "未知",
			rHeight = rData.height ? rData.height + "cm" : "未知",
			rEdu = rData.edu ? eduMap[rData.edu] : "未知",
			rJob = rData.job ? jobMap[rData.job] : "未知",
			rMarry = rData.marry ? marryMap[rData.marry] : "未知",
			rLocation = me._getLocation(rData);
		var robotInfo = "年龄：" + rAge + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身高：" + rHeight + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;教育程度：" + rEdu + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职业：" + rJob + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;婚姻状况：" + rMarry;
		$("#robotInfo").html(robotInfo);
		$("#robotCity").text("所在城市：" + rLocation);
		//用户信息
		$("#userFace").attr('src', uData.face);
		$("#userName").text(uData.nick ? uData.nick : "未知");
		var uAge = uData.birth ? parseInt((new Date() - new Date(uData.birth)) / (365 * 24 * 60 * 60 * 1000)) : "未知",
			uHeight = uData.height ? uData.height + "cm" : "未知",
			uEdu = uData.edu ? eduMap[uData.edu] : "未知",
			uJob = uData.job ? jobMap[uData.job] : "未知",
			uMarry = uData.marry ? marryMap[uData.marry] : "未知",
			uLocation = me._getLocation(uData);
		var userInfo = "年龄：" + uAge + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身高：" + uHeight + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;教育程度：" + uEdu + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;职业：" + uJob + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;婚姻状况：" + uMarry;
		$("#userInfo").html(userInfo);
		$("#userCity").text("所在城市：" + uLocation);
	},
	//渲染聊天内容
	renderChatcontent: function(chatContent, rData, uData) {
		var me = this,
			renderChat = function(cData, rData, uData) {
				var dom = $(document.createElement("div")),
					name = "",
					face = "";
				dom.addClass('direct-chat-msg');
				//判断是否是机器人所发消息
				if (cData.fuid == me.rid) {
					dom.addClass('right');
					nick = rData.nick;
					face = rData.face;
				} else {
					nick = uData.nick;
					face = uData.face;
				}
				var chatText = '<div class="direct-chat-info clearfix">' +
					'<span class="direct-chat-name">' + nick +
					'</span><span class="direct-chat-timestamp">' + new Date(cData.ctime).format("yyyy-MM-dd hh:mm:ss") +
					'</span></div>' +
					'<img class="direct-chat-img" src="' + face + '" alt="message user image" />' +
					'<div class="direct-chat-text">' + me._renderMsg(cData.content) + '</div>';
				dom.html(chatText);
				$("#chat-list").append(dom);
			};
		$("#chat-list").html("");
		for (var i = 0; i < chatContent.length; i++) {
			renderChat(chatContent[i], rData, uData);
		}
		$("#chat-list").scrollTo('.direct-chat-msg:last', 500, {
			margin: true
		});
		me.loop = window.setTimeout(function() {
			me.updateDom();
		}, me.loopTime);
	},
	//发送消息
	sendMsg: function() {
		var me = this,
			sendContent = $("#sendinput").val(),
			sendData = "";
		if (sendContent == "") {
			return false;
		} else {
			sendData = '{"chatType":1,"content":"' + sendContent + '"}';
		}
		$.ajax({
				url: me.getUrl + "/mo/send",
				type: 'GET',
				dataType: 'jsonp',
				data: {
					"uid": me.uid,
					"rid": me.rid,
					"content": encodeURI(sendData),
					"userid": parseInt(g_loginuser.xunai_uid)
				},
				jsonp: "callbackparam",
				jsonpCallback: "callback"
			})
			.done(function() {
				me.updateDom(me.uid, me.rid, false);
				$("#sendinput").val("");
			});
	},
	//忽略用户
	ignoreUser: function() {
		var me = this;
		$.ajax({
				url: me.getUrl + "/mo/ignore",
				type: 'GET',
				dataType: 'jsonp',
				data: {
					"uid": me.uid,
					"rid": me.rid,
					"userid": parseInt(g_loginuser.xunai_uid)
				},
				jsonp: "callbackparam",
				jsonpCallback: "callback"
			})
			.done(function() {
				var n = noty({
					text: '已忽略！',
					closeWith: ['click'],
					theme: 'relax',
					type: 'information',
					layout: 'bottomRight',
					timeout: 2000,
					animation: {
						open: {
							height: 'toggle'
						},
						close: {
							height: 'toggle'
						},
						easing: 'swing',
						speed: 500
					}
				});
				me.destroyLoop();
				chatUserlist.init();
			});
	},
	//清除循环查询
	destroyLoop: function() {
		var me = this;
		if (me.loop) {
			window.clearTimeout(me.loop);
			me.loop = false;
		}
	},
	//渲染消息
	_renderMsg: function(contentData) {
		//转json
		try {
			var data = eval("(" + contentData + ")");
		} catch (e) {
			var data = contentData;
		}
		//1文字 2图片 3语音 4心跳
		switch (data.chatType) {
			case 1:
				return data.content;
				break;
			case 2:
				return '<img src="' + data.url + '" alt="">';
				break;
			case 3:
				return '<i class="fa fa-volume-up playaudio" onclick="play_A_NoUi(this)" data-url="' + data.url + '"></i>';
				break;
			case 4:
				return '<i class="fa fa-heart heartbeat"></i>';
				break;
			default:
				return data;
				break;
		}
	},
	//获取用户所在位置
	_getLocation: function(data) {
		var lProvince = data.province ? locationMap[data.province] + " - " : "",
			lCity = data.city ? locationMap[data.city] + " - " : "",
			lDistrict = data.district ? locationMap[data.district] : "未知";
		return lProvince + lCity + lDistrict;
	},
	//绑定事件
	_bendEvent: function() {
		var list = this;
	}
};
var chatInfo = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/sy/st/m",
	init: function() {
		var me = this;
		$.ajax({
			url: me.getUrl + "/admin/action",
			type: 'GET',
			dataType: 'jsonp',
			data: {
				"userid": parseInt(g_loginuser.xunai_uid),
				"type": 1
			},
			jsonp: "callbackparam",
			jsonpCallback: "callback"
		}).done(function(data) {
			me.renderInfo(data);
		});
	},
	renderInfo: function(data) {
		$("#chatinfo").html("今天已发送：" + data.chats + "句，与" + data.chatUsers + "个用户完成聊天");
		chatUserlist.init();
	}
};
$(function() {
	$("#chatUserlist").height($(".top-box").height() - $(".bottom-box").height() - 65);
	chatInfo.init();
	//监听回车键
	$("#sendinput").keydown(function(event) {
		if (event.which == '13') {
			chatDOM.sendMsg();
			return false;
		}
	});
	//忽略用户
	$("#ignoreBtn").unbind('click').bind('click', function(event) {
		chatDOM.ignoreUser();
	});
	//发送消息
	$("#sendBtn").unbind('click').bind('click', function(event) {
		chatDOM.sendMsg();
	});
});
//播放音频
function play_A_NoUi(e) {
	var u = $(e).data("url");
	if ($("#audioReplyPlay") && ($("#audioReplyPlay embed").attr("src") == u)) {
		$("#audioReplyPlay").remove();
		return;
	} else if ($("#audioReplyPlay") && ($("#audioReplyPlay embed").attr("src") != u)) {
		$("#audioReplyPlay").remove();
	}
	var pv = '';
	pv += '<param name="src" value="' + u + '">';
	pv += '<param name="controller" value="true">';
	pv += '<param name="type" value="video/quicktime">';
	pv += '<param name="autoplay" value="true">';
	pv += '<param name="target" value="myself">';
	pv += '<param name="bgcolor" value="black">';
	pv += '<param name="pluginspage" value="http://www.apple.com/quicktime/download/index.html">';
	pv += '<embed src="' + u + '" width="0" height="0" controller="true" align="middle" bgcolor="transparent" target="myself" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/index.html"></embed>';
	var player = document.createElement("object");
	$(player).html(pv);
	$(player).attr({
		id: "audioReplyPlay",
		width: 0,
		height: 0,
		classid: "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B",
		codebase: "http://www.apple.com/qtactivex/qtplugin.cab"
	});
	$("body").append(player);
}