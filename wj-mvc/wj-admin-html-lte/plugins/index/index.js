$(function() {
	$("#homeUrl").attr("href", '#' + encryptUrl('page/home'));
	addClick($("#homeUrl"));
	initUrl();
	post(urlConfig.getMenus, {}, function(data) {
		actionMenus(data.data);
		selectMenus(location.href.split('#')[1]);
	}, function(data) {

	});
});

var initUrl = function() {
	var hrefs = location.href.split('#');
	if(hrefs.length < 2 || hrefs[1] == '') {
		location.href = hrefs[0] + $("#homeUrl").attr("href");
	}
}

var initMenus = function(menus, $li) {
	var $ul = $("<ul></ul>").addClass("treeview-menu");
	for(var i = 0; i < menus.length; i++) {
		var menu = menus[i];
		var li = $("<li></li>");
		var $a = $("<a></a>");
		var $i = $("<i></i>").addClass(menu.icon);
		var $span = $("<span></span>").html(menu.text);
		$a.attr("href", (menu.url == null || menu.url == '' ? 'javascript:;' : '#' + encryptUrl('page/' + menu.url)));
		$a.append($i).append($span);
		li.append($a);
		$ul.append(li);
		$li.append($ul);
		if(menu.nodes != null && menu.nodes.length > 0) {
			li.addClass("treeview");
			$a.append('<span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>');
			initMenus(menu.nodes, li);
		} else {
			addClick($a);
		}
	}
}

var selectMenus = function(u) {
	var url = '#' + u;
	$("#menus-content li.active").removeClass('active').removeClass('menu-open');
	var aa = $("#menus-content a[href='" + url + "']").parents("li");
	$("#page-menu-bar").html("");
	var $li = $('<li></li>');
	var $a = $('<a href="#' + encryptUrl('page/home') + '"><i class="fa fa-dashboard"></i> 首页</a>');
	addClick($a);
	$li.append($a);
	$("#page-menu-bar").append($li)
	for(var i = aa.length - 1; i >= 0; i--) {
		var li = $('<li><a href="javascript:;">' + $(aa.get(i)).find("a:eq(0)").text() + '</a></li>');
		if(i == 0) {
			li.addClass('active');
		}
		$("#page-menu-bar").append(li);
	}

	$("#menus-content a[href='" + url + "']").parents("li").addClass('active').addClass('menu-open');
	$('.sidebar-menu').tree();
	var durl = 'none';
	try {
		durl = decodeUrl(u).substring(5);

		if(!durl || durl == '')
			durl = 'none';

	} catch(e) {}

	$.ajax({
		url: "./pages/" + durl + ".html",
		dataType: 'html',
		success: function(data) {
			$("#page-content").html(data);
			var title = $("#page-content").find("title").html();
			$("title:eq(0)").html(title);
			var desc = $("page-desc").html();
			$("#page-desc").html(desc);

			var pageTitle = $("page-title").html();
			$("#page-title").html(pageTitle);
			$("#page-content").find("title,page-desc,page-title").remove();
		},
		error: function(error) {
			$("#page-desc").html("");
			$("#page-title").html("");
			switch(error.status) {
				case 404:
					$("#page-content").load("./pages/404.html");
					$("title:eq(0)").html("页面找不到");
					break;
				default :
					$("#page-content").load("./pages/500.html");
					$("title:eq(0)").html("网络错误");
				break;
			}
		}
	});
};

var addClick = function($a) {
	$a.click(function() {
		var href = $(this).attr("href");
		if(href != null && href != '') {
			selectMenus(href.substring(1));
		}
	});
}

var actionMenus = function(menus) {
	if(menus) {
		for(var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			var li = $("<li></li>");
			var $a = $("<a></a>").append();
			var $i = $("<i></i>").addClass(menu.icon);
			var $span = $("<span></span>").html(menu.text);
			$a.attr("href", (menu.url == null || menu.url == '' ? 'javascript:;' : '#' + encryptUrl('page/' + menu.url)));
			$a.append($i).append($span);
			li.append($a);
			$('#menus-content').append(li);
			if(menu.nodes != null && menu.nodes.length > 0) {
				li.addClass("treeview");
				$a.append('<span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>');
				initMenus(menu.nodes, li);
			} else {
				addClick($a);
			}
		}
	}
};

var encryptUrl = function(url) {
	return window.btoa(url);
}

var decodeUrl = function(url) {
	return window.atob(url);
};