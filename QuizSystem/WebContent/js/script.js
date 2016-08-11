$(document).ready(function() {
    var operation = {
        operation_menu: function() {
            $('.chapter').each(function(index, el) {
                $(this).find('h3').bind('click',
                function(event) {
                    var parent = $(this).parent('.chapter');
                    if ($(this).hasClass('chapter-active')) {
                        parent.css({
                            height: 35
                        });
                        $(this).removeClass('chapter-active');
                        $(this).addClass('chapter-noactive');
                    }

                    else {
                        var ul_height = parent.find('ul').height();
                        parent.css({
                            height: ul_height + 55
                        });
                        $(this).removeClass('chapter-noactive');
                        $(this).addClass('chapter-active');
                    }
                });
            });
        },
        operation_content_nav: function() {
            $('.content-nav').find('li').bind('click',
            function(event) {
                $(this).parent('ul').find('li').removeClass('content-nav-active');
                $(this).addClass('content-nav-active');
            });
        },
        menu_hover: function(object, classname) {
            object.find('li').each(function(index, el) {
                $(this).bind('mouseover',
                function(event) {
                    if ($(this).hasClass('item-active')) {
                        return false;
                    }
                    $(this).addClass(classname);
                });

                $(this).bind('mouseout',
                function(event) {
                    if ($(this).hasClass('item-active')) {
                        return false;
                    }
                    $(this).removeClass(classname);
                })
            });
        },
        menu_click: function(object, classname) {
            object.find('a').each(function(index, el) {
                $(this).bind('click',
                function(event) {
                    object.find('li').removeClass(classname);
                    $(this).parent('li').addClass(classname);
                });
            });
        },
        tab_change:function(){
            $('.content-nav').find('li').each(function(index, el) {
                $(this).bind('click', function(event) {
                    $('.content').find('div[name="editor"]').addClass('hide');
                    $('.content').find('div[name="editor"]').eq($(this).index()).removeClass('hide'); 
                });
            });
        },
        menu_slide:function(){
            $('#question-title').on('click', function(event) {
                if($(this).hasClass('downarrow')){
                    $(this).removeClass('downarrow');
                    $(this).addClass('leftarrow');
                    $('#question-body').slideUp();
                }else{
                    $(this).removeClass('leftarrow');
                    $(this).addClass('downarrow');
                    $('#question-body').slideDown();
                }
            });
            $('#answer-title').on('click', function(event) {
                if($(this).hasClass('downarrow')){
                    $(this).removeClass('downarrow');
                    $(this).addClass('leftarrow');
                    $('#answer-body').slideUp();
                }else{
                    $(this).removeClass('leftarrow');
                    $(this).addClass('downarrow');
                    $('#answer-body').slideDown();
                }
            });
        },
        operation_main: function() {
            this.tab_change();
            this.operation_menu();
            this.operation_content_nav();
            this.menu_hover($('.menu'), 'menu-hover');
            this.menu_click($('.menu'), 'item-active');
            this.menu_slide();
        }
    }

    operation.operation_main();
    
});