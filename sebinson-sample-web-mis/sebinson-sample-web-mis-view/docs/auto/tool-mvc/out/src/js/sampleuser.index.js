$package('Gooagoo.sampleuser');
Gooagoo.sampleuser.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'sampleUser.do?method=page',               
                columns : [[{
                    field : 'userId',
                    checkbox : true                   
                },{
                    field : 'userName',
                    title : '用户名'                   
                },{
                    field : 'password',
                    title : '登入密码'                   
                },{
                    field : 'cdt',
                    title : '创建日期',
                    formatter : formatDatetime                   
                },{
                    field : 'udt',
                    title : '更新日期',
                    formatter : formatDatetime                   
                }] ],
                idField : 'userId',
                pagination : true,
            },
            action: {
                save: basePath + '/sampleuser/save.do',
                byId: basePath + '/sampleuser/byId.do',
                remove: basePath + '/sampleuser/remove.do'
            }
        },        
        initSearchForm : function(){
        },       
        init : function() {
            this.initSearchForm();
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