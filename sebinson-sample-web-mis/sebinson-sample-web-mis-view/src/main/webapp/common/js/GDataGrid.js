var GDataGrid = function(config) {
	config = config || {};
	var dataGrid = config.dataGrid || {}
	// Actions
	var actionUrl = config.action || {}
	var Action = {
		'save' : actionUrl.save || 'save.do',
		'byId' : actionUrl.byId || 'byId.do',
		'remove' : actionUrl.remove || 'remove.do'
	}

	// Grid DataList
	var Grid = $("#dataList");
	// Form
	var Form = {
		search : $("#searchForm"),
		edit : $("#editForm")
	}
	// Win 窗口
	var Win = {
		edit : $("#editWin")
	}

	// 处理函数
	var Handler = {
		// serach 查询事件
		search : function(callback) {
			Events.refresh();
			// 回调函数
			if (jQuery.isFunction(callback)) {
				callback();
			}
			return false;
		},
		// add按钮事件
		add : function(callback) {
			Win.edit.panel('open');
			// 回调函数
			if (jQuery.isFunction(callback)) {
				callback();
			}
		},
		// edit 按钮事件
		edit : function(callback) {
			var record = Utils.getCheckedRows();
			if (Utils.checkSelectOne(record)) {
				Gooagoo.progress();
				var data = {};
				var idKey = dataGrid.idField || 'id'; // 主键名称
				data['id'] = (record[0][idKey]);
				Gooagoo.getById(Action.byId, data, function(result) {
					Gooagoo.closeProgress();
					Win.edit.panel('open');
					Form.edit.form('load', result.data);
					// 回调函数
					if (jQuery.isFunction(callback)) {
						callback(result);
					}
				});
			}
			Gooagoo.closeProgress();
		},
		// add + edit 按钮事件
		ae : function(callback) {
			var record = Utils.getCheckedRows();
			if (Utils.isUnselected(record)) {
				Handler.add(callback);
			} else {
				Handler.edit(callback);
			}
		},
		// 刷新Grid 数据
		refresh : function(callback) {
			var param = Form.search.serializeObject();
			Grid.datagrid('reload', param);
			// 回调函数
			if (jQuery.isFunction(callback)) {
				callback();
			}
		},
		// 删除记录
		remove : function(callback) {
			var records = Utils.getCheckedRows();
			if (Utils.checkSelect(records)) {
				$.messager.confirm('确认', '确定要删除选中记录吗?', function(r) {
					if (r) {
						var arr = [], idKey = dataGrid.idField || 'id'; // 主键名称
						$.each(records, function(i, record) {
							arr.push('ids=' + record[idKey]);
						});
						var data = arr.join("&");
						Gooagoo.deleteForm(Action.remove, data, function(result) {
							Grid.datagrid('uncheckAll');
							if (result.success) {
								Gooagoo.alert('提示', result.msg, 'info');
							} else {
								Gooagoo.alert('提示', result.msg, 'error');
							}
							Events.refresh();
							// 回调函数
							if (jQuery.isFunction(callback)) {
								callback(result);
							}
						});
					}
				});
			}
		},// 保存调用方法
		save : function(callback) {
			if (Form.edit.form('validate')) {
				Gooagoo.progress();
				Form.edit.attr('action', Action.save);
				var parentId = $('#search_parentId').val();
				$("#edit_parentId").val(parentId)
				Gooagoo.saveForm(Form.edit, function(data) {
					Gooagoo.closeProgress();
					Win.edit.panel('close');
					if (data.success) {
						Gooagoo.alert('提示', data.msg, 'info');
					} else {
						Gooagoo.alert('提示', data.msg, 'error');
					}
					Events.refresh();
					Form.edit.resetForm();
					// Form.edit.form('clear');
					// 回调函数
					if (jQuery.isFunction(callback)) {
						callback(data);
					}
				});
			}
		},
		// 关闭按钮事件
		close : function(callback) {
			$.messager.confirm('确认', '确定要关闭该窗口吗?', function(r) {
				if (r) {
					Form.edit.resetForm();
					Win.edit.panel('close');
					// 回调函数
					if (jQuery.isFunction(callback)) {
						callback(data);
					}
				}
			});
		},
		// 查询重置事件
		reset : function(callback) {
			Form.search.form('reset');
			Events.refresh();
			if (jQuery.isFunction(callback)) {
				callback(data);
			}
		}
	}

	// Grid 工具类
	var Utils = {
		getCheckedRows : function() {
			return Grid.datagrid('getChecked');
		},
		checkSelect : function(rows) {// 检查grid是否有勾选的行, 有返回 true,没有返回true
			var records = rows;
			if (records && records.length > 0) {
				return true;
			}
			Gooagoo.alert('警告', '未选中记录.', 'warning');
			return false;
		},
		checkSelectOne : function(rows) {// 检查grid是否只勾选了一行,是返回 true,否返回true
			var records = rows;
			if (!Utils.checkSelect(records)) {
				return false;
			}
			if (records.length == 1) {
				return true;
			}
			Gooagoo.alert('警告', '只能选择一行记录.', 'warning');
			return false;
		},
		isUnselected : function(rows) {
			var records = rows;
			if (records && records.length > 0) {
				return false;
			}
			return true;
		},
		setUnchecked : function() {
			Grid.datagrid('uncheckAll');
		}
	}

	// 自定义事件
	var evt = config.event || {};

	// 默认事件
	var Events = {
		// serach 查询事件
		search : evt.search || Handler.search,
		// add按钮事件
		add : evt.add || Handler.add,
		// edit 按钮事件
		edit : evt.edit || Handler.edit,
		// add + edit 按钮事件
		ae : evt.ae || Handler.ae,
		// 刷新Grid 数据
		refresh : evt.refresh || Handler.refresh,
		// 删除记录
		remove : evt.remove || Handler.remove,
		// 保存调用方法
		save : evt.save || Handler.save,
		// 关闭按钮事件
		close : evt.close || Handler.close,
		// 查询重置事件
		reset : evt.reset || Handler.reset
	}

	// 按钮控制 btnType 用来控制按钮是否显示,后台根据授权控制是否显示
	var bar_add = {
		id : 'btnadd',
		text : '新增',
		iconCls : 'icon-add',
		btnType : 'add',
		handler : Events.add
	};
	var bar_edit = {
		id : 'btnedit',
		text : '编辑',
		iconCls : 'icon-edit',
		btnType : 'edit',
		handler : Events.edit
	};
	var bar_remove = {
		id : 'btnremove',
		text : '删除',
		iconCls : 'icon-remove',
		btnType : 'remove',
		handler : Events.remove
	};
	// add / edit
	var bar_ae = {
		id : '增编',
		text : 'Add/Edit',
		iconCls : 'icon-ae',
		btnType : 'ae',
		handler : Events.ae
	};

	var toolbarConfig = [ bar_add, bar_edit, bar_remove ];
	var getToolbar = function() {
		var tbars = [];
		if (dataGrid.toolbar != undefined && dataGrid.toolbar.length > 0) {
			for (var i = 0; i < dataGrid.toolbar.length; i++) {
				var bar = dataGrid.toolbar[i];
				if (!bar) {
					continue;
				}
				if (bar.btnType == 'add') {
					tbars.push({
						id : bar.id || bar_add.id,
						text : bar.text || bar_add.text,
						iconCls : bar.iconCls || bar_add.iconCls,
						btnType : bar.btnType || bar_add.btnType,
						handler : bar.handler || bar_add.handler
					});
					continue;
				}
				if (bar.btnType == 'edit') {
					tbars.push({
						id : bar.id || bar_edit.id,
						text : bar.text || bar_edit.text,
						iconCls : bar.iconCls || bar_edit.iconCls,
						btnType : bar.btnType || bar_edit.btnType,
						handler : bar.handler || bar_edit.handler
					});
					continue;
				}
				if (bar.btnType == 'ae') {
					tbars.push({
						id : bar.id || bar_ae.id,
						text : bar.text || bar_ae.text,
						iconCls : bar.iconCls || bar_ae.iconCls,
						btnType : bar.btnType || bar_ae.btnType,
						handler : bar.handler || bar_ae.handler
					});
					continue;
				}
				if (bar.btnType == 'remove') {
					tbars.push({
						id : bar.id || bar_remove.id,
						text : bar.text || bar_remove.text,
						iconCls : bar.iconCls || bar_remove.iconCls,
						btnType : bar.btnType || bar_remove.btnType,
						handler : bar.handler || bar_remove.handler
					});
					continue;
				}
				tbars.push({
					id : bar.id,
					text : bar.text,
					iconCls : bar.iconCls,
					btnType : bar.btnType,
					handler : bar.handler,
					disabled : bar.disabled
				});
			}
		} else if (dataGrid.toolbar != undefined && dataGrid.toolbar.length == 0) {
			tbars = undefined;// 添加不设置toolbar功能选项 不想定义toobar时 可添加属性 toolbar:[]
			// 默认情况下若不定义toolbar时则默认为三项（增删改）toolbar;
		} else {
			tbars = toolbarConfig;
		}
		return tbars;
	}

	// 初始化表格
	var initGrid = function() {
		var options = {
			title : dataGrid.title || '数据列表',
			iconCls : dataGrid.iconCls || '',
			fit : true,
			border : false,
			nowrap : true,
			autoRowHeight : false,
			striped : false,
			collapsible : false,
			remoteSort : false,
			pagination : dataGrid.pagination ? true : false,
			rownumbers : true,
			singleSelect : false,
			checkOnSelect : true,
			selectOnCheck : false,
			fitColumns : dataGrid.fitColumns ? true : false,
			url : dataGrid.url,
			method : dataGrid.method || 'post',
			loadMsg : dataGrid.loadMsg || '加载中...',
			idField : dataGrid.idField,
			columns : dataGrid.columns,
			sortName : dataGrid.sortName || '',
			sortOrder : dataGrid.sortOrder || '',
			onLoadSuccess : dataGrid.onLoadSuccess || function() {
				Grid.datagrid('unselectAll');
				Grid.datagrid('uncheckAll');
			},
			onSelect : function(rowIndex, rowData) {
				// 选择一行
				var rows = Grid.datagrid('getRows');
				$.each(rows, function(i) {
					if (i != rowIndex) {
						Grid.datagrid('uncheckRow', i);
						Grid.datagrid('unselectRow', i);
					}
				})
				Grid.datagrid('checkRow', rowIndex);
			}
		};
		Grid.datagrid(options);
	}
	// 初始化Grid按钮 按钮控制
	var initTbar = function() {
		var tbars = getToolbar();
		var _url = basePath + '/operAuth.do';
		var data = {
			'url' : window.location.href
		};
		$.getJSON(_url, data, function(data) {

			if (data.allType) {
				Grid.datagrid({
					'toolbar' : tbars
				});// 显示所有button
			} else {

				var newBars = [];
				for (var i = 0; i < tbars.length; i++) {
					var bar = tbars[i];
					// btnType 为空显示
					if (!bar.btnType) {
						newBars.push(bar);
					} else {
						// 判断btnType是否存在,存在则显示
						if ($.inArray(bar.btnType, data.types) >= 0) {
							newBars.push(bar);
						}
					}
				}
				if (newBars.length > 0) {
					Grid.datagrid({
						'toolbar' : newBars
					});
				}
			}
		});
	}

	// 初始话form
	var initForm = function() {
		if (Form.search && Form.search[0]) {
			Form.search.closest(".search-panel").find("#btn-search").click(Events.search); // 保存事件
			Form.search.closest(".search-panel").find("#btn-reset").click(Events.reset); // 重置事件
		}
	}

	var initWin = function() {
		if (Win.edit && Win.edit[0]) {
			// 判断页面是否设置buttons，如果没有设置默认按钮
			var btns = Win.edit.attr("buttons");
			if (btns == undefined || btns == 'false') {
				// 设置 保存,关闭按钮
				Win.edit.dialog({
					buttons : [ {
						text : '保存',
						handler : Events.save
					}, {
						text : '关闭',
						handler : Events.close
					} ]
				});
			} else if (btns == "true") {
				Win.edit.find("#btn-submit").click(Events.save); // 保存事件
				Win.edit.find("#btn-close").click(Events.close);// 关闭窗口
			}
		}
	}

	// this 返回属性
	this.win = Win;
	this.form = Form;
	this.grid = Grid;
	this.utils = Utils;
	this.handler = Handler;

	// 初始化方法
	this.init = function() {
		initGrid();
		initTbar();
		initForm();
		initWin();
	}
	// 调用初始化
	return this;
};