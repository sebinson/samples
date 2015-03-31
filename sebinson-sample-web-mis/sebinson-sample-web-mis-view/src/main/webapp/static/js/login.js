$package('Gooagoo.sample.login');
Gooagoo.sample.login = function() {
	return {
		toLogin : function() {
			try {
				var $form = $("#loginForm");
				if ($form.form('validate')) {
					window.location = "verify.do?" + $form.formSerialize();
				}
			} catch (e) {
				Gooagoo.alert('提示', "系统出现异常，请联系管理员！", 'error');
			}
			return false;
		},
		loadVrifyCode : function() {// 刷新验证码
			// var _url = "ImageServlet?time="+new Date().getTime();
			// $(".vc-pic").attr('src',_url);
		},
		init : function() {
			if (window.top != window.self) {
				window.top.location = window.self.location;
			}
			// 验证码图片绑定点击事件
			// $(".vc-pic").click(Gooagoo.login.loadVrifyCode);

			var $form = $("#loginForm");
			$form.submit(Gooagoo.sample.login.toLogin);
		}
	}
}();

$(function() {
	Gooagoo.sample.login.init();
});