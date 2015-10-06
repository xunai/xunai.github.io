/**
 * checkhead.js
 * @zzy
 * @date    2015-03-28 16:14:29
 * @version $Id$
 */
$(function() {
	carList.init();
});

var carList = {
	getUrl: g_host + "/ba/1/ck/x/7u/1n/a/2i/ve/ri/fy",
	page: [0, 0, 0, 0],
	state: 1,
	load: "",
	//初始化
	init: function() {
		var me = this;
		me.getList(me.state, me.page[me.state]);
		me._bendEvent();
	},
	//获取未审核总数
	getCount: function(state) {
		var me = this;
		$.ajax({
				url: me.getUrl + "/cv/count",
				type: 'GET',
				dataType: 'jsonp',
				data: {
					"state": state,
					"userid": parseInt(g_loginuser.xunai_uid)
				},
				jsonp: "callbackparam",
				jsonpCallback: "callbackcount"
			})
			.done(function(data) {
				switch (me.state) {
					case 1:
						$("#nocount").text('现有未审核数:' + data.count);
						break;
					case 2:
						$("#nocount").text('现有不通过审核数:' + data.count);
						break;
					case 3:
						$("#nocount").text('现有通过审核数:' + data.count);
						break;
				}
			});
	},
	//获取当前页列表
	getList: function(state, pageNum) {
		var me = this;
		if (me.load === "") {
			me.load = g_loadMode.init($("#adminContent"));
		} else {
			me.load.show();
		}
		$.ajax({
				url: me.getUrl + "/cv/list",
				type: 'GET',
				dataType: 'jsonp',
				data: {
					"state": state,
					"page": pageNum,
					"userid": parseInt(g_loginuser.xunai_uid)
				},
				jsonp: "callbackparam",
				jsonpCallback: "callbacklist"
			})
			.done(function(data) {
				me.page[me.state] = pageNum;
				if (data === null) {
					me.load.fadeOut();
					return;
				}
				switch (state) {
					//未审核
					case 1:
						me._renderNocheck(data);
						me.getCount(me.state);
						me._rebendEvent();
						me.load.fadeOut();
						break;
						//审核拒绝
					case 2:
						me._renderRefusecheck(data);
						me.getCount(me.state);
						me._rebendEvent();
						me.load.fadeOut();
						break;
						//已审核
					case 3:
						me._renderPasscheck(data);
						me.getCount(me.state);
						me._rebendEvent();
						me.load.fadeOut();
						break;
				}
			});
	},
	//下一页
	nextPage: function() {
		var me = this;
		me.getList(me.state, me.page[me.state] + 1);
	},
	//前一页
	prevPage: function() {
		var me = this;
		me.getList(me.state, me.page[me.state] - 1);
	},
	//通过审核
	passCheck: function(idArray) {
		var me = this,
			i = 0;
		var passFun = function(index) {
			if (index >= idArray.length) {
				return;
			}
			id = idArray[index];
			$.ajax({
					url: me.getUrl + "/cv/update/uid",
					type: 'GET',
					dataType: 'jsonp',
					data: {
						"state": 3,
						"uid": id,
						"userid": parseInt(g_loginuser.xunai_uid)
					},
					jsonp: "callbackparam",
					jsonpCallback: "callbackpass"
				})
				.done(function(data) {
					$(".head-check-item[data-checkid=" + id + "]").fadeOut(function() {
						var dom = $(this);
						dom.remove();
					});
					index++;
					passFun(index);
				});
		};
		passFun(0);
	},
	//审核拒绝
	refuseCheck: function(idArray) {
		var me = this,
			i = 0;
		var refuseFun = function(index) {
			if (index >= idArray.length) {
				return;
			}
			id = idArray[index];
			$.ajax({
					url: me.getUrl + "/cv/update/uid",
					type: 'GET',
					dataType: 'jsonp',
					data: {
						"state": 2,
						"uid": id,
						"userid": parseInt(g_loginuser.xunai_uid)
					},
					jsonp: "callbackparam",
					jsonpCallback: "callbackrefuse"
				})
				.done(function(data) {
					$(".head-check-item[data-checkid=" + id + "]").fadeOut(function() {
						var dom = $(this);
						dom.remove();
						index++;
						refuseFun(index);
					});
				});
		};
		refuseFun(0);
	},
	//删除审核
	deleteCheck: function(idArray) {

	},
	//获取选择的id列表
	_getIdlist: function(btn) {
		var me = this;
		var idArray = [];
		btn.parents(".tab-pane").find(".head-check-item.active").each(function(index, el) {
			var checkId = $(el).data('checkid');
			idArray.push(checkId);
		});
		return idArray;
	},
	_renderNocheck: function(data) {
		var me = this;
		$("#noCheckList").html("");
		for (var i = 0; i < data.length; i++) {
			var htmlstr = '<div class="head-check-item" data-checkid="' + data[i].uid + '"><i class="fa fa-square-o"></i><i class="fa fa-check-square-o"></i>' +
				'<input type="checkbox" name="id" value="' + data[i].uid + '"">' +
				'<img src="' + data[i].carUrl + '" class="img-head" alt="User Image" /></div>';
			$("#noCheckList").append(htmlstr);
		}
	},
	//渲染不通过审核
	_renderRefusecheck: function(data) {
		var me = this;
		$("#refuseList").html("");
		for (var i = 0; i < data.length; i++) {
			var htmlstr = '<div class="head-check-item" data-checkid="' + data[i].uid + '"><i class="fa fa-square-o"></i><i class="fa fa-check-square-o"></i>' +
				'<input type="checkbox" name="id" value="' + data[i].uid + '"">' +
				'<img src="' + data[i].carUrl + '" class="img-head" alt="User Image" /></div>';
			$("#refuseList").append(htmlstr);
		}
	},
	_renderPasscheck: function(data) {
		var me = this;
		$("#passList").html("");
		for (var i = 0; i < data.length; i++) {
			var htmlstr = '<div class="head-check-item" data-checkid="' + data[i].uid + '"><i class="fa fa-square-o"></i><i class="fa fa-check-square-o"></i>' +
				'<input type="checkbox" name="id" value="' + data[i].uid + '"">' +
				'<img src="' + data[i].carUrl + '" class="img-head" alt="User Image" /></div>';
			$("#passList").append(htmlstr);
		}
	},
	//绑定固定事件
	_bendEvent: function() {
		var list = this;
		$('a[data-label="Pre"]').unbind('click').bind('click', function() {
			list.prevPage();
		});
		$('a[data-label="Next"]').unbind('click').bind('click', function() {
			list.nextPage();
		});
		$('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
			list.state = parseInt($(e.target).data('state'));
			list.init();
		});
		$(".selectall").unbind('click').bind("click", function() {
			var me = $(this);
			me.parents(".tab-pane").find("input[type=checkbox]").attr("checked", true);
			me.parents(".tab-pane").find(".head-check-item").addClass("active");
		});
		$(".selectreverse").unbind('click').bind("click", function() {
			var me = $(this);
			me.parents(".tab-pane").find("input[type=checkbox]").each(function(index, element) {
				$(element).attr("checked", !$(element).attr("checked"));
			});
			me.parents(".tab-pane").find(".head-check-item").toggleClass("active");
		});
		$(".passcheck").unbind('click').bind("click", function(event) {
			var me = $(this);
			var idArray = list._getIdlist(me);
			list.passCheck(idArray);
		});
		$(".nopasscheck").unbind('click').bind("click", function(event) {
			var me = $(this);
			var idArray = list._getIdlist(me);
			list.refuseCheck(idArray);
		});
	},
	//重新绑定事件
	_rebendEvent: function() {
		$(".img-head").unbind('click').bind("click", function() {
			var me = $(this);
			var checkbox = me.prev("input[type=checkbox]");
			if (checkbox.attr("checked")) {
				checkbox.attr("checked", false);
			} else {
				checkbox.attr("checked", true);
			}
			me.parent().toggleClass("active");
		});
		$(".head-check-item .fa-square-o").unbind('click').bind("click", function() {
			var me = $(this);
			me.parent().addClass("active");
			me.parent().children("input[type=checkbox]").attr("checked", true);
		});
		$(".head-check-item .fa-check-square-o").unbind('click').bind("click", function() {
			var me = $(this);
			me.parent().removeClass("active");
			me.parent().children("input[type=checkbox]").attr("checked", false);
		});
	}
};