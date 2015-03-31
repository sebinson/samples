$package('Gooagoo.samplerole');
Gooagoo.samplerole.index = function() {
    var _this = {
        config : {
            dataGrid : {
                //title : "系统管理-菜单管理",
                url : basePath + '/samplerole/list.do',               
                columns : [ [
                {   
                    field : 'roleId',
                    checkbox : true,
                    title : 'UUID角色标识'
                },
                {   
                    field : 'roleCode',
                    title : '角色编号，规律性编码，要求唯一性'
                },
                {   
                    field : 'roleName',
                    title : '角色名称'
                },
                {   
                    field : 'roleDesc',
                    title : '角色描述'
                }] ],
                idField : 'roleId',
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
    Gooagoo.samplerole.index.init();
});