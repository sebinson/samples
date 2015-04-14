$package('Gooagoo.samplemenu');
Gooagoo.samplemenu.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'sampleMenu.do?method=page',               
                columns : [[{
                    field : 'menuId',
                    checkbox : true                   
                },{
                    field : 'menuCode',
                    title : '菜单编码'                   
                },{
                    field : 'menuType',
                    title : '菜单类型'                   
                },{
                    field : 'menuName',
                    title : '菜单名称'                   
                },{
                    field : 'menuStatus',
                    title : '菜单状态'                   
                },{
                    field : 'menuParentId',
                    title : '上级菜单标识'                   
                },{
                    field : 'menuOrder',
                    title : '同级菜单序号'                   
                },{
                    field : 'menuUri',
                    title : '菜单URI'                   
                },{
                    field : 'menuActions',
                    title : '操作项URI'                   
                },{
                    field : 'menuDesc',
                    title : '菜单描述'                   
                }] ],
                idField : 'menuId',
                pagination : true,
            },
            action: {
                save: basePath + '/samplemenu/save.do',
                byId: basePath + '/samplemenu/byId.do',
                remove: basePath + '/samplemenu/remove.do'
            }
        },        
        initSearchForm : function(){
            $("#searchForm input#menuType").combobox({
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{id:'0',text:'根菜单'},{id:'1',text:'一级菜单'},{id:'3',text:'菜单项'}]                             
            });                            
            $("#searchForm input#menuStatus").combobox({
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{id:'0',text:'可用'},{id:'1',text:'不可用'}]                             
            });                            
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
    Gooagoo.samplemenu.index.init();
});