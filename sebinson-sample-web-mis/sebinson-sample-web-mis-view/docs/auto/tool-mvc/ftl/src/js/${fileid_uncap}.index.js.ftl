$package('Gooagoo.${fileid_uncap}');
Gooagoo.${fileid_uncap}.index = function() {
    var _this = {
        config : {
            dataGrid : {
                url : basePath + '${fileid_uncap_first}.do?method=<#if (X_FILEPARA1?index_of("P")>-1)>page<#else>list</#if>',               
                columns : [[<#list list as column><#if column.x_showInList?lower_case=='y'>{
                    field : '${column.x_cname?uncap_first}'<#if column.x_cpattern?lower_case=='hidden'>,
                    checkbox : true<#else>,
                    title : '${column.x_ccomment}'</#if><#if column.x_cpattern?lower_case=='datebox'>,
                    formatter : formatDate</#if><#if column.x_cpattern?lower_case=='datetimebox'>,
                    formatter : formatDatetime</#if>                   
                }<#if ((list?size-1)>column_index)>,</#if></#if></#list>] ],
                <#list list as column>
                <#if column.x_ckey?? && column.x_ckey=='PRI'>
                idField : '${column.x_cname?uncap_first}',
                <#assign primaryKey = '${column.x_cname?uncap_first}'>
                <#break>
                </#if>
                </#list>
                pagination : true,
            },
            action: {
                save: basePath + '/${fileid_uncap}/save.do',
                byId: basePath + '/${fileid_uncap}/byId.do',
                remove: basePath + '/${fileid_uncap}/remove.do'
            }
        },        
        initSearchForm : function(){
            <#list list as column>               
                <#if column.x_searchField?upper_case=='Y' && column.x_cpattern?contains('combo')>
                    <#if column.x_cvalue??>
                        <#if column.x_cvalue?contains('[')>
            ${'$'}("#searchForm input#${column.x_cname}").combobox({
                valueField: '${column.x_cvalue?split("|")[1]!"id"}',
                textField: '${column.x_cvalue?split("|")[2]!"text"}',
                panelHeight : 'auto',
                editable : false ,
                data:${column.x_cvalue?split("|")[0]!"[]"}                             
            });                            
                        </#if>
                        <#if column.x_cvalue?contains('.do')>
            ${'$'}("#searchForm input#${column.x_cname}").combobox({
                valueField: '${column.x_cvalue?split("|")[1]!"id"}',
                textField: '${column.x_cvalue?split("|")[2]!"text"}',
                panelHeight : 'auto',
                editable : false ,
                url:'${column.x_cvalue?split("|")[0]!"#"}'                 
            });
                        </#if>
                    </#if>
                </#if>               
            </#list>
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
    Gooagoo.${fileid_uncap}.index.init();
});