$package('Gooagoo.samplemenuoperation');
Gooagoo.samplemenuoperation.index = function() {
    var _this = {
        config : {
            dataGrid : {
                //title : "系统管理-菜单管理",
                url : basePath + '/samplemenuoperation/list.do',               
                columns : [ [
                {   
                    field : 'operId',
                    checkbox : true,
                },
                {   	
                    field : 'menuId',
                    title : '所属菜单标标识'
                },
                {   
                    field : 'operCode',
                    title : '操作码'
                },
                {   
                    field : 'operName',
                    title : '操作名称'
                },
                {   
                    field : 'operType',
                    title : '操作类型'
                },
                {   
                    field : 'operActions',
                    title : '操作URI'
                }] ],
                idField : 'operId',
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
    Gooagoo.samplemenuoperation.index.init();
});