$package('Gooagoo.samplemenu');
Gooagoo.samplemenu.form = function() {
    var _this = {
        onLoad : function() {
            $("#editWin #editForm input#menuType").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{id:'0',text:'根菜单'},{id:'1',text:'一级菜单'},{id:'3',text:'菜单项'}]                             
            });                            
            $("#editWin #editForm input#menuStatus").combobox({
                required: true,
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{id:'0',text:'可用'},{id:'1',text:'不可用'}]                             
            });                            
        }
    }
    return _this;
}();