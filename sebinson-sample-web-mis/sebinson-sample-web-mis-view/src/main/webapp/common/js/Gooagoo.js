$package('Gooagoo');
var Gooagoo = {
	/* Json 工具类 */
	isJson : function(str) {
		var obj = null;
		try {
			obj = Gooagoo.paserJson(str);
		} catch (e) {
			return false;
		}
		var result = typeof (obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
		return result;
	},
	paserJson : function(str) {
		return eval("(" + str + ")");
	},
	/* 警告框 */
	alert : function(title, msg, icon, callback) {
		$.messager.alert(title, msg, icon, callback);
	},
	warning : function(title, msg, callback) {
		$.messager.alert(title, msg, 'warning', callback);
	},
	error : function(title, msg, callback) {
		$.messager.alert(title, msg, 'error', callback);
	},
	info : function(title, msg, callback) {
		$.messager.alert(title, msg, 'info', callback);
	},
	question : function(title, msg, callback) {
		$.messager.alert(title, msg, 'question', callback);
	},
	/* 确认框 */
	confirm : function(title, msg, callback) {
		$.messager.confirm(title, msg, callback);
	},
	progress : function(title, msg) {
		var win = $.messager.progress({
			title : title || '请稍等',
			msg : msg || '数据加载中...'
		});
	},
	closeProgress : function() {
		$.messager.progress('close');
	},
	/* 重新登录页面 */
	verify : function() {
		window.top.location = "verify.do";
	},
	checkLogin : function(data) {// 检查是否登录超时
		if (data.logoutFlag) {
			Gooagoo.closeProgress();
			Gooagoo.alert('提示', "登录超时,点击确定重新登录.", 'error', Gooagoo.verify);
			return false;
		}
		return true;
	},
	ajaxSubmit : function(form, option) {
		form.ajaxSubmit(option);
	},
	ajaxJson : function(url, option, callback) {
		Gooagoo.progress('请稍等', '处理中...');
		jQuery.ajax({
			url : url,
			type : 'post',
			dataType : 'json',
			data : option,
			success : function(data) {
				Gooagoo.closeProgress();
				// 坚持登录
				if (!Gooagoo.checkLogin(data)) {
					return true;
				}
				if ($.isFunction(callback)) {
					callback(data);
				}
			},
			error : function(response, textStatus, errorThrown) {
				Gooagoo.closeProgress();
				try {
					var data = $.parseJSON(response.responseText);
					// 检查登录
					if (!Gooagoo.checkLogin(data)) {
						return false;
					} else {
						Gooagoo.alert('提示', data.msg || "请求出现异常,请联系管理员", 'error');
					}
				} catch (e) {
					Gooagoo.alert('提示', "请求出现异常,请联系管理员.", 'error');
				}
			},
			complete : function() {
				Gooagoo.closeProgress();
			}
		});
	},
	submitForm : function(form, callback, dataType) {
		var option = {
			type : 'post',
			dataType : dataType || 'json',
			success : function(data) {
				Gooagoo.closeProgress();
				if ($.isFunction(callback)) {
					callback(data);
				}

			},
			error : function(response, textStatus, errorThrown) {
				try {
					Gooagoo.closeProgress();
					var data = $.parseJSON(response.responseText);
					// 检查登录
					if (!Gooagoo.checkLogin(data)) {
						return false;
					} else {
						Gooagoo.alert('提示', data.msg || "请求出现异常,请联系管理员", 'error');
					}
				} catch (e) {
					Gooagoo.alert('提示', "请求出现异常,请联系管理员.", 'error');
				}
			},
			complete : function() {
				Gooagoo.closeProgress();
			}
		}
		Gooagoo.ajaxSubmit(form, option);
	},
	saveForm : function(form, callback) {
		if (form.form('validate')) {
			Gooagoo.progress('请稍等', '保存中...');
			// ajax提交form
			Gooagoo.submitForm(form, function(data) {
				Gooagoo.closeProgress();
				if (data.success) {
					if (callback) {
						callback(data);
					} else {
						Gooagoo.alert('提示', '保存成功.', 'info');
					}
				} else {
					Gooagoo.alert('提示', data.msg, 'error');
				}
			});
		}
	},

	getById : function(url, option, callback) {
		Gooagoo.progress('请稍等', '数据加载中...');
		Gooagoo.ajaxJson(url, option, function(data) {
			if (data.success) {
				if (callback) {
					callback(data);
				}
			} else {
				Gooagoo.alert('提示', data.msg, 'error');
			}
		});
	},
	deleteForm : function(url, option, callback) {
		Gooagoo.ajaxJson(url, option, function(data) {
			if (data.success) {
				if (callback) {
					callback(data);
				}
			} else {
				Gooagoo.alert('提示', data.msg, 'error');
			}
		});
	}
}

/* 自定义密码验证 */
$.extend($.fn.validatebox.defaults.rules, {
	equals : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '前后输入不一致！'
	}
});

/* 表单转成json数据 */
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

var ie = (function() {
	var undef, v = 3, div = document.createElement('div'), all = div.getElementsByTagName('i');
	while (div.innerHTML = '<!--[if gt IE ' + (++v) + ']><i></i><![endif]-->', all[0])
		;
	return v > 4 ? v : undef;
}());
/**
 * 针对panel window dialog三个组件调整大小时会超出父级元素的修正 如果父级元素的overflow属性为hidden，则修复上下左右个方向
 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
 * 
 * @param width
 * @param height
 * @returns
 */
var easyuiPanelOnResize = function(width, height) {
	if (!$.data(this, 'window') && !$.data(this, 'dialog'))
		return;

	if (ie === 8) {
		var data = $.data(this, "window") || $.data(this, "dialog");
		if (data.pmask) {
			var masks = data.window.nextAll('.window-proxy-mask');
			if (masks.length > 1) {
				$(masks[1]).remove();
				masks[1] = null;
			}
		}
	}
	if ($(this).panel('options').maximized == true) {
		$(this).panel('options').fit = false;
	}
	$(this).panel('options').reSizing = true;
	if (!$(this).panel('options').reSizeNum) {
		$(this).panel('options').reSizeNum = 1;
	} else {
		$(this).panel('options').reSizeNum++;
	}
	var parentObj = $(this).panel('panel').parent();
	var left = $(this).panel('panel').position().left;
	var top = $(this).panel('panel').position().top;

	if ($(this).panel('panel').offset().left < 0) {
		$(this).panel('move', {
			left : 0
		});
	}
	if ($(this).panel('panel').offset().top < 0) {
		$(this).panel('move', {
			top : 0
		});
	}

	if (left < 0) {
		$(this).panel('move', {
			left : 0
		}).panel('resize', {
			width : width + left
		});
	}
	if (top < 0) {
		$(this).panel('move', {
			top : 0
		}).panel('resize', {
			height : height + top
		});
	}
	if (parentObj.css("overflow") == "hidden") {
		var inline = $.data(this, "window").options.inline;
		if (inline == false) {
			parentObj = $(window);
		}

		if ((width + left > parentObj.width()) && $(this).panel('options').reSizeNum > 1) {
			$(this).panel('resize', {
				width : parentObj.width() - left
			});
		}

		if ((height + top > parentObj.height()) && $(this).panel('options').reSizeNum > 1) {
			$(this).panel('resize', {
				height : parentObj.height() - top
			});
		}
	}
	$(this).panel('options').reSizing = false;
};
/**
 * 针对panel window dialog三个组件拖动时会超出父级元素的修正 如果父级元素的overflow属性为hidden，则修复上下左右个方向
 * 如果父级元素的overflow属性为非hidden，则只修复上左两个方向
 * 
 * @param left
 * @param top
 * @returns
 */
var easyuiPanelOnMove = function(left, top) {
	if ($(this).panel('options').reSizing)
		return;
	var parentObj = $(this).panel('panel').parent();
	var width = $(this).panel('options').width;
	var height = $(this).panel('options').height;
	var right = left + width;
	var buttom = top + height;
	var parentWidth = parentObj.width();
	var parentHeight = parentObj.height();

	if (left < 0) {
		$(this).panel('move', {
			left : 0
		});
	}
	if (top < 0) {
		$(this).panel('move', {
			top : 0
		});
	}

	if (parentObj.css("overflow") == "hidden") {
		var inline = $.data(this, "window").options.inline;
		if (inline == false) {
			parentObj = $(window);
		}
		if (left > parentObj.width() - width) {
			$(this).panel('move', {
				"left" : parentObj.width() - width
			});
		}
		if (top > parentObj.height() - height) {
			$(this).panel('move', {
				"top" : parentObj.height() - height
			});
		}
	}
};

$.fn.window.defaults.onResize = easyuiPanelOnResize;
$.fn.dialog.defaults.onResize = easyuiPanelOnResize;
//$.fn.window.defaults.onMove = easyuiPanelOnMove;
//$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
