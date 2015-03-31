$package('Gooagoo.sample.main');
Gooagoo.sample.main = function() {
    return {
        treeSelect : function() {
            var title = $(this).text();
            var url = $(this).attr('href');
            Gooagoo.sample.main.addTab(title, url);
            return false;
        },
        treeInit : function() {
            var $items = $('#menu-box').find(".menu-item");
            $items.bind('click', this.treeSelect);
        },
        addTab : function(_title, _url) {
            var $tabBox = $('#tab-box');
            if (!$tabBox.tabs('exists', _title)) {
                var _content = "<iframe scrolling='auto' frameborder='0' style='width:100%; height:100%'></iframe>";
                $tabBox.tabs('add', {
                    title : _title,
                    content : _content,
                    closable : true
                });
            }
            var tab = $tabBox.tabs('getTab', _title);
            var index = $tabBox.tabs('getTabIndex', tab);
            $tabBox.tabs('select', index);
            if (tab && tab.find('iframe').length > 0) {
                var _refresh_ifram = tab.find('iframe')[0];
                _refresh_ifram.contentWindow.location.href = _url;
            }
        },
        menuHover : function() {
            // 菜单鼠标进入效果
            $('.menu-item').hover(function() {
                $(this).stop().animate({
                    paddingLeft : "25px"
                }, 200, function() {
                    $(this).addClass("orange");
                });
            }, function() {
                $(this).stop().animate({
                    paddingLeft : "15px"
                }, function() {
                    $(this).removeClass("orange");
                });
            });
        },
        header : {
            modifyPwd : function(){
                var pwdForm = $("#modify-pwd-win #pwdForm");
                if(pwdForm.form('validate')){
                    Gooagoo.saveForm(pwdForm,function(data){
                        $('#modify-pwd-win').dialog('close');
                        window.location= "verify.do";
                    });
                }
            }
        },
        
        init : function() {
            this.treeInit();
            this.menuHover();
			$('.modify-pwd-btn').click(function(){
				$('#modify-pwd-win').dialog('open');
			});
			$('#pwd-win-btn-close').click(function(){
			  $('#modify-pwd-win').find("#pwdForm").resetForm();
				$('#modify-pwd-win').dialog('close');
			});
			$('#pwd-win-btn-submit').click(this.header.modifyPwd);
        }
    };
}();

$(function() {
    Gooagoo.sample.main.init();
});