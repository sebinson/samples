$package('Gooagoo.sampleuser');
Gooagoo.sampleuser.index = function() {
    var _this = {
        config : {
            dataGrid : {
                //title : "系统管理-菜单管理",
                url : basePath + '/sampleuser/list.do',               
                columns : [ [
                {   
                    field : 'userId',
                    checkbox : true,
                    title : 'UUID 唯一标识'
                },
                {   
                    field : 'userName',
                    title : '用户名，账户名，登入名'
                },
                {   
                    field : 'password',
                    title : '登入密码'
                },
                {   
                    field : 'cdt',
                    formatter : formatDatetime,
                    title : '创建日期'
                },
                {   
                    field : 'udt',
                    formatter : formatDatetime,
                    title : '更新日期'
                }] ],
                idField : 'userId',
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
    Gooagoo.sampleuser.index.init();
});