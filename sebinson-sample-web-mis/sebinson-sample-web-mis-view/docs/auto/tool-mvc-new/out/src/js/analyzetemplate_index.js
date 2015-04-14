$package('Gooagoo.analyzetemplate');
Gooagoo.analyzetemplate.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + 'analyzeTemplate.do?method=page',               
                columns : [ [{
                    field : 'analyzeTemplateId',
                    checkbox : true                   
                },{
                   field : 'type',
                    title : '模板名称类型'                   
                },{
                   field : 'name',
                    title : '模板名称'                   
                },{
                   field : 'url',
                    title : '模板访问路径'                   
                },{
                   field : 'status',
                    title : '状态'                   
                },{
                   field : 'ver',
                    title : '版本号'                   
                },{
                   field : 'templateMd5',
                    title : 'md5'                   
                },{
                   field : 'remark',
                    title : '备注'                   
                },{
                   field : 'isDel',
                    title : '是否删除'                   
                },{
                   field : 'createTime',
                    title : '创建时间',
                    formatter : formatDatetime                   
                },{
                   field : 'cTimeStamp',
                    title : '最后一次修改时间',
                    formatter : formatDatetime                   
                }] ],
                idField : 'analyzeTemplateId',
                pagination : true,
            },
            action: {
                'save' : basePath+'analyzeTemplate.do?method=add',
                'byId' : basePath+'analyzeTemplate.do?method=formU',
                'remove' : basePath+'analyzeTemplate.do?method=delete'
            },
            event : {
                edit : function() {
                    var rows = $('#dataList').datagrid('getChecked');
                    var $dialog = $("#editWin");
                    if (rows && rows.length == 0) {
                        Gooagoo.alert('警告', '请选择一行记录.', 'warning');
                    } else if (rows && rows.length == 1) {
                        $dialog.dialog('open');                        
                        $dialog.dialog('setTitle', '编辑');
                        $dialog.dialog('refresh', 'analyzeTemplate.do?method=byId&analyzeTemplateId=' + rows[0]['analyzeTemplateId']);
                    } else if (rows && rows.length > 1) {
                        Gooagoo.alert('警告', '仅能选择一行记录.', 'warning');
                    }
                    return;
                }
            }
        },        
        initSearchForm : function(){
            $("#searchForm input#type").combobox({
                valueField: 'id',
                textField: 'text',
                panelHeight : 'auto',
                editable : false ,
                data:[{'id':'1','text':'prf格式'},{'id':'2','text':'配置模板'},{'id':'3','text':'ocr格式'}]                             
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
    Gooagoo.analyzetemplate.index.init();
});