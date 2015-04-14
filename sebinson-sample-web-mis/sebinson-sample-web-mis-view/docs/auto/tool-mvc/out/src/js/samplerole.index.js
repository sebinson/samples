$package('Gooagoo.samplerole');
Gooagoo.samplerole.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'sampleRole.do?method=page',               
                columns : [[{
                    field : 'roleId',
                    checkbox : true                   
                },{
                    field : 'roleCode',
                    title : '角色编号'                   
                },{
                    field : 'roleName',
                    title : '角色名称'                   
                },{
                    field : 'roleDesc',
                    title : '角色描述'                   
                }] ],
                idField : 'roleId',
                pagination : true,
            },
            action: {
                save: basePath + '/samplerole/save.do',
                byId: basePath + '/samplerole/byId.do',
                remove: basePath + '/samplerole/remove.do'
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
    Gooagoo.samplerole.index.init();
});