$(function() {
      g_loginuser.updateInfo();
      $("#loginOut").click(function(event) {
            g_loginuser.loginOut();
      });
      var window_height = $(window).height();
      $(".top-box").each(function(index, el) {
            var tbox = $(el);
            if (tbox.hasClass('no-toolbar')) {
                  tbox.css('height', window_height - $('.main-footer').outerHeight() - $('.bottom-box').outerHeight());
            } else if(tbox.hasClass('innerbox')){
                  tbox.css('height', window_height - $('.main-footer').outerHeight() - $('.bottom-box').outerHeight() - 185);
            }
            else {
                  tbox.css('height', window_height - $('.main-footer').outerHeight() - $('.bottom-box').outerHeight() - 125);
            }
      });
      var otheight = 0;
      $(".direct-chat-messages").parent().parent().children(".box-body:gt(0)").each(function(index, element) {
            otheight += $(element).height();
      });
      $(".direct-chat-messages").css("height", $(".top-box").height() - $(".top-box").children(".box-header").height() - otheight - 165);

});
//登录权限检查模块
var g_loginuser = {
      xunai_nick: '',
      xunai_username: '',
      xunai_role: '',
      xunai_uid: '',
      timeout: 12 * 60 * 60 * 1000,
      //获取登录人信息
      getLoginstate: function() {
            var me = this,
                  timerange = new Date().getTime() - me.timeout;
            if (window.localStorage) {
                  var storage = window.localStorage;
                  //未检测到用户名自动登出
                  if (!storage.getItem("xunai_username")) {
                        window.location.href = "login.html";
                  }
                  //登录超时自动登出
                  else if (storage.xunai_lasttime < timerange) {
                        storage.xunai_nick = "";
                        storage.xunai_username = "";
                        storage.xunai_role = "";
                        storage.xunai_uid = "";
                        storage.xunai_lasttime = "";
                        window.location.href = "login.html";
                  } else {
                        me.xunai_nick = storage.xunai_nick;
                        me.xunai_username = storage.xunai_username;
                        me.xunai_role = storage.xunai_role;
                        me.xunai_uid = storage.xunai_uid;
                        me.setLoginInfo();
                  }
            }
      },
      //设置登录人信息
      setLoginInfo: function() {
            var me = this,
                  roleinf,
                  hrefarray = [],
                  hasRole = false;
            $(".loginnick").text(me.xunai_username);
            $(".loginname").text(me.xunai_nick);
            //判断权限
            roleinf = me.xunai_role.split("");
            $("#roleList").children('li').each(function(index, el) {
                  if (parseInt(roleinf[index]) === 1) {
                        $(el).show();
                        hrefarray.push($(el).find("a").attr('href'));
                  }
            });
            var locationHref = window.location.pathname.split("/")[window.location.pathname.split("/").length -1]
            for (var i = 0; i < hrefarray.length; i++) {
                  if (hrefarray[i] == locationHref) {
                        hasRole = true;
                  }
            }
            if (!hasRole) {
                  window.location.href = hrefarray[0];
            }
      },
      //更新登录状态
      updateInfo: function() {
            var me = this;
            me.getLoginstate();
      },
      //登出
      loginOut: function() {
            if (window.localStorage) {
                  var storage = window.localStorage;
                  storage.xunai_nick = "";
                  storage.xunai_username = "";
                  storage.xunai_role = "";
                  storage.xunai_uid = "";
                  storage.xunai_lasttime = "";
            }
            window.location.href = "login.html";
      }
};
//全局ajax请求地址
// var g_host = "http://aus.appforwhom.com/aus/";
var g_host = "http://123.59.52.215:8090/aus/";
//日期格式化
Date.prototype.format = function(format) {
      var o = {
            "M+": this.getMonth() + 1, //month  
            "d+": this.getDate(), //day  
            "h+": this.getHours(), //hour  
            "m+": this.getMinutes(), //minute  
            "s+": this.getSeconds(), //second  
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter  
            "S": this.getMilliseconds() //millisecond  
      };
      if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
            if (new RegExp("(" + k + ")").test(format))
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
      return format;
};
//加载dom模块
var g_loadMode = {
      load: '',
      init: function(dom){
            var me = this,
            parent = dom,
            loadDom = document.createElement("div");
            $(loadDom).addClass('load-modal');
            $(loadDom).html('<span class="loading-inner"></span><span class="loading-middle"></span><span class="loading-exterior"></span>');
            parent.prepend(loadDom);
            me.load = $(loadDom);
            return me.load;
      },
      show: function(){
            var me = this;
            me.load.show();
      },
      hide: function(){
            var me = this;
            me.load.fadeOut();
      }
};