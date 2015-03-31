$package('Gooagoo.samplemenu');
Gooagoo.samplemenu.index = function() {
    var _this = {
        config : {
            dataGrid : {
                //title : "系统管理-菜单管理",
                url : basePath + '/samplemenu/list.do',               
                columns : [ [
                {   
                    field : 'menuId',
                    checkbox : true,
                    title : '菜单唯一标识，UUID'
                },
                {   
                    field : 'menuCode',
                    title : '菜单编码'
                },
                {   
                    field : 'menuType',
                    title : '菜单类型：0 根菜单，1 级菜单，2 菜单项'
                },
                {   
                    field : 'menuName',
                    title : '菜单名称'
                },
                {   
                    field : 'menuStatus',
                    title : '菜单状态：0 可用，1 不可用'
                },
                {   
                    field : 'menuParentId',
                    title : '上级菜单标识'
                },
                {   
                    field : 'menuOrder',
                    title : '同级菜单序号'
                },
                {   
                    field : 'menuUri',
                    title : '菜单URI'
                },
                {   
                    field : 'menuActions',
                    title : '操作项的相对URI，权限随当前菜单，不可定制授权，多操作以'|'分割 eg：add.do|remove.do|update.do'
                },
                {   
                    field : 'menuDesc',
                    title : '菜单描述'
                }] ],
                idField : 'menuId',
                pagination : true,
            }
        },
        init : function() {
            var dg = new GDataGrid(_this.config);
            dg.init();
            return dg;
        }
    }
    return _this;
}();

$(function() {
    Gooagoo.samplemenu.index.init();
});