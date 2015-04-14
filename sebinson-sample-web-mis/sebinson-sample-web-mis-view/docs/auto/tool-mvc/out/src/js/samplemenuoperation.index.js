$package('Gooagoo.samplemenuoperation');
Gooagoo.samplemenuoperation.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'sampleMenuOperation.do?method=page',               
                columns : [[{
                    field : 'operId',
                    checkbox : true                   
                },{
                    field : 'menuId',
                    title : '菜单标识'                   
                },{
                    field : 'operCode',
                    title : '操作码'                   
                },{
                    field : 'operName',
                    title : '操作名称'                   
                },{
                    field : 'operType',
                    title : '操作类型'                   
                },{
                    field : 'operActions',
                    title : '操作URI'                   
                }] ],
                idField : 'operId',
                pagination : true,
            },
            action: {
                save: basePath + '/samplemenuoperation/save.do',
                byId: basePath + '/samplemenuoperation/byId.do',
                remove: basePath + '/samplemenuoperation/remove.do'
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
    Gooagoo.samplemenuoperation.index.init();
});