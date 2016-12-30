<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>USER LIST</title>
<%@ include file="../include.jsp"%>
<style>
.table th,.table td {
	text-align: center;
}
.table thead {
	font-weight: bold;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	//加载页面数据
	initPageableData(0,false);
	
	//设置数据表格整体高度
	var tcHeight = $(window).height() - $('#dataTable').height() 
		- $('#pageButtons').height() - $('#toolbar').height() - $('h1').height();
	$('#tableContainer').height(tcHeight);
	
	$('#invert').click(function() {
		if($('#all').is(':checked')) {
			$('#all').click();
		} else {
			$(':checkbox.idc').each(function() {
	       		$(this).prop('checked',!$(this).is(':checked'));
	       	});
			checkAll();
		}
	});
	
	$('#add').click(function(){
		location.href = "add";
	});
	
	$('#delete').click(function() {
		var count = howManyChecked();
		if (count == 0) {
			BootstrapDialog.show({
	            message: '请选中至少一条记录!'
	        });
			return;
		}
		
		BootstrapDialog.show({
	        title: '提示信息',
	        message: '您确定要删除选中的'+count+'个用户吗？',
	        buttons: [{
	            label: '确认',
	            cssClass: 'btn-primary',
	            autospin: true,
	            hotkey: 32,
	            action: function(dialog) {
	                $.ajax({
	                    type: 'DELETE',
	                    url: 'delete',
	                    data: getCheckedIds(),
	                    dataType: 'json',
	                    success: function(data) {
	                        dialog.setMessage('删除用户成功!');
	                        dialog.enableButtons(false);
	                        //在页面删除显示的用户记录
	                        //如果此页为最后一页,只删除显示的用户记录
	                        //否则,获取当前激活的页码数和每页显示数,重新刷新当前页数据,以便后面的数据能够展示到当前页来
	                        var allChecked = $('#all').is(':checked');
	                        var last = $('#lastRecord').html();
                        	var total = $('#totalRecords').html();
	                        if(last == total && !allChecked) {
	                        	$(':checkbox.idc').each(function() {
                        			if ($(this).is(':checked')) {
                        				$(this).parent().parent().remove();
                        				last--;
                            			total--;
                        			}
                           		});
	                        	$('#lastRecord').html(last);
	                        	$('#totalRecords').html(total);
	                        } else {
	                        	var currentPageNumber = getCurrentPageNumber();
		                        if(allChecked && currentPageNumber > 0) currentPageNumber--;
		                        initPageableData(currentPageNumber,true,$('#howManyRecords').html());
	                        }
							//给用户一秒钟时间看提示信息	                        
	                        setTimeout(function() {
	                            dialog.close();
	                        },1000);
	                    },
	                    error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        //XMLHttpRequest, textStatus, errorThrown
	                        dialog.setMessage(XMLHttpRequest.responseText);
	                        dialog.enableButtons(false);
	                        setTimeout(function() {
	                            dialog.close();
	                        },5000);
	                    }
	                });
	            }
	        },
	        {
	            label: '取消',
	            action: function(dialogItself) {
	                dialogItself.close();
	            }
	        }]
	    });
	});
});

/**
 * 根据当前页码和是否采用异步方式加载分页数据
 * page: 当前页码 - 第一页从0开始
 * async: 是否采用异步加载方式 - 首次加载页面不能使用异步加载
 *        ,需要等数据加载完以后才执行其他JS方法
 */
function initPageableData(page,async,size) {
	var isSelectRecordNumber = true;
	//首次加载数据时或每次点击页码按钮时
	if (!size) {
		//如果用户再次点选了已经选中的页码按钮
		//比如,在第一页点选页码按钮1
		//防止重新获取重复的数据
		if(isAccessCurrentPage(page,async)) {
			return;
		}
		//确定不是点选每页展示数量菜单中按钮进入此方法
		//有可能不需要重新初始化记录数量信息提示条
		isSelectRecordNumber = false;
		//获取默认每页记录数量
		//第一次加载页面时获取默认值(async为false时为第一次加载页码)
		//其他通过页码按钮加载页码数据时获取用户选择的每页加载数量
		size = async ? $('#howManyRecords').html() 
				: $('#firstRecordNumber').html();
	}
	$.ajax({
	    headers: {
	        'Accept': 'application/json',
	        'Content-Type': 'application/json'
	    },
	    cache: false,
        async: async,
	    type: 'POST',
	    url: 'listByPage',
	    data: "{\"page\":"+page+",\"size\":"+size+"}",
	    dataType: 'json',
	    success: function(data) {
	    	data = data.pageableData;
	    	var html = '';
	    	html += '<div id="tableContainer"><table id="dataTable" class="table table-bordered table-hover">';
	    	html += '<thead><tr><th><input type="checkbox" id="all"></th>';
	    	html += '<th name="id">编号</th><th name="username">用户名</th><th name="password">密码</th><th name="sex">性别</th><th name="address">地址</th><th name="status">状态</th>';
	    	html += '</tr></thead><tbody>';
	    	
	    	for(var i=0;i<data.numberOfElements;i++) {
	    		var user = data.content[i];
	    		html += '<tr onclick="selectRow(this)"><td><input type="checkbox" onclick="beforeSelectRow(this)" class="idc" id="';
	    		html += user.id;
	    		html += '"></td><td name="id">';
	    		html += user.id;
	    		html += '</td><td name="username">';
	    		html += user.username;
	    		html += '</td><td name="password">';
	    		html += user.password;
	    		html += '</td><td name="sex">';
	    		html += user.sex ? '男' : '女';  
	    		html += '</td><td name="address">';
	    		html += user.address;
	    		html += '</td><td name="status"><input type="checkbox" id="';
	    		html += user.id;
	    		html += '" value="';
	    		html += user.status;
	    		html += '" class="stc" data-toggle="toggle" data-on="可用" data-off="不可用" data-size="mini" data-style="testtoggle"></tr>';
	    	}
	    	if(html == '') {
	    		html = '<tr><td colspan="7">未查询到用户记录...</td></tr>';
	    	}
	    	html += '</tbody></table></div>';
	    	$('#tableContainer').html(html);

	    	//加载数据完成后,重新初始化全选按钮状态,默认为未选中
	    	$('#all').prop('checked',false);
			//加载完数据后,重新初始化页码按钮的超链接和激活状态(底色) 	
	    	initPageButtons(page,data,((data.first && !async) || isSelectRecordNumber));
			//加载完数据后,重新将状态checkbox渲染为toogle控件
	    	drawToogle();
	    },
	    error: function(XMLHttpRequest, textStatus, errorThrown) {
	        alert(XMLHttpRequest.responseText);
	    }
	});
}

/**
 * 获取当前被选中的页码按钮所代表的页数
 * 返回值currentPage从0开始
 */
function getCurrentPageNumber() {
	var currentPage = 0;
	$('.pagination').find('li').each(function(){
		if($(this).hasClass('active')) {
			currentPage = $(this).find('a').eq(0).html() - 1;
		}
	});
	return currentPage;
}

/**
 * 判断是否用户在众多页码按钮中点选了已经成为当前页码的按钮
 * 比如,当页码按钮1被选中的时候,点选页码按钮1
 * async: 为true时,页面不是首次加载
 * page: 当前需要加载的页码,从0开始
 */
function isAccessCurrentPage(page,async) {
	return (async && page == getCurrentPageNumber());
}

/**
 * 重新初始化页码按钮的超链接和激活状态(底色)	
 */
function initPageButtons(page,data,isRegenerateButtons) {
	//根据当前用户选择的页码将该页码按钮颜色变蓝
	//,而其他页码按钮颜色恢复成默认颜色
	$('.pagination').find('li').each(function(){
		if($(this).hasClass('active')) {
			$(this).removeClass('active');
		}
	});
	$('#'+page+'li').addClass('active');
	
	//当第一次加载页面或重新选择每页显示记录数量时,根据数据的总页码数生成若干页码按钮
	if(isRegenerateButtons) {
		generatePageButtons(data.totalPages);
	}
	
	//当选择第一页和最后一页时,调整首页和尾页两个按钮的可用状态和链接地址
	if(data.last) {
		$('#last').addClass('disabled');
		$('#lastA').attr('href','#');
	} else {
		$('#last').removeClass('disabled');
		$('#lastA').attr('href',
				'javascript:initPageableData('+(data.totalPages-1)+',true)');
	}
	if(!data.first) {
		$('#first').removeClass('disabled');
	}
	if(page > 0) {
		$('#firstA').attr('href','javascript:initPageableData(0,true)');
	} else {
		$('#firstA').attr('href','#');
		$('#first').addClass('disabled');
	}
	
	//给页码解释赋值
	var base = page * data.size;
	$('#firstRecord').html(base + 1);
	$('#lastRecord').html(base + data.numberOfElements);
	$('#totalRecords').html(data.totalElements);
	$('#howManyRecords').html(data.size);
}

/**
 * 重新将状态checkbox渲染为toogle控件 
 */
function drawToogle() {
	$(':checkbox.stc').each(function() {
		$(this).bootstrapToggle($(this).val() == '1' ? 'on' : 'off');
    });
    
    $('#all').click(function() {
    	var state = $('#all').is(':checked');
   		$(':checkbox.idc').each(function() {
       		$(this).prop('checked',state);
       	});
    });
}

/**
 * 根据总页码数量生成页码按钮
 * ,只有第一次加载页面时才被使用
 */
function generatePageButtons(totalPages){
	var html = '<li id="first"><a id="firstA">&laquo;</a></li>';
	for(var i=0;i<totalPages;i++) {
		if (i == 0) {
			html += '<li id="'+(i+'li')+'" class="active"><a href="javascript:initPageableData('+i+',true)">'+(i+1)+'</a></li>';
			$('#first').addClass('disabled');
		} else {
			html += '<li id="'+(i+'li')+'"><a href="javascript:initPageableData('+i+',true)">'+(i+1)+'</a></li>';
		}
	}
	html += '<li id="last"><a id="lastA" href="javascript:initPageableData('+(totalPages-1)+',true)">&raquo;</a></li>';
	$('.pagination').html(html);
}

/**
 * 当点选数据行头部的复选框时,复选框将被选中或取消选择
 * BugResolve: 如不加此方法,点选头部复选框将有几率无法响应选中事件
 */
function beforeSelectRow(element) {
	$(element).prop('checked',!$(element).is(':checked'));
}

/**
 * 当点选数据行时,选中或取消选中当前行的复选框
 * 检查是否页面所有复选框已经被选中
 */
function selectRow(element) {
	var checkbox = $(element).children('td').eq(0).find('input');
	$(checkbox).prop('checked',!$(checkbox).is(':checked'));
	checkAll();
}

/**
 * 获取多少记录被选中
 */
function howManyChecked() {
	var count = 0;
	$(':checkbox.idc').each(function() {
		if ($(this).is(':checked')) {
			count++;
		}
   	});
	return count;
}

/**
 * 获取所有被选中记录的id
 */
function getCheckedIds() {
	var ids = '';
	$(':checkbox.idc').each(function() {
		if ($(this).is(':checked')) {
			ids += $(this).attr('id')+',';
		}
   	});
	return ids == '' ? ids : ids.substring(0,ids.length-1);
}

/**
 * 检查当所有页面记录复选框被选中时,将全选勾中
 */
function checkAll() {
	var state = true;
	$(':checkbox.idc').each(function(){
		if(!$(this).is(':checked')) state = false;
	});
	$('#all').prop("checked",state);
}

/**
 * 当用户点击用户可用状态按钮时
 * 先弹出确认提示,如果确认将更新数据
 */
$(document).on('click.bs.toggle', 'div.testtoggle',
function(e) {
    e.stopImmediatePropagation();
    var $checkbox = $(this).find(':checkbox.stc');
    BootstrapDialog.show({
        title: '提示信息',
        message: '您确定要改变该用户的可用状态吗？',
        buttons: [{
            label: '确认',
            cssClass: 'btn-primary',
            autospin: true,
            hotkey: 32,
            action: function(dialog) {
                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    type: 'POST',
                    url: 'updateStatus',
                    data: "{\"id\":" + $checkbox.attr('id') + ",\"status\":" + $checkbox.attr('value') + "}",
                    dataType: 'json',
                    success: function(data) {
                        $checkbox.attr('value', data.status);
                        dialog.setMessage('更新用户可用状态成功!');
                        dialog.enableButtons(false);
                        setTimeout(function() {
                            dialog.close();
                        },
                        1000);
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        //XMLHttpRequest, textStatus, errorThrown
                        $checkbox.bootstrapToggle('toggle');
                        dialog.setMessage(XMLHttpRequest.responseText);
                        dialog.enableButtons(false);
                        setTimeout(function() {
                            dialog.close();
                        },
                        5000);
                    }
                });
            }
        },
        {
            label: '取消',
            action: function(dialogItself) {
                $checkbox.bootstrapToggle('toggle');
                dialogItself.close();
            }
        }]
    });
});
</script>
</head>
<body class="container">
	<h1>LIST</h1>
	<hr>
	<div class="table-responsive">
		<div class="btn-group col-md-4">
			<button type="button" class="btn btn-default btn-sm" id="invert">
			<span class="glyphicon glyphicon-check"></span>
			反选</button>
			<button type="button" class="btn btn-default btn-sm" id="add">
			<span class="glyphicon glyphicon-plus"></span>
			添加</button>
			<button type="button" class="btn btn-default btn-sm" id="update">
			<span class="glyphicon glyphicon-edit"></span>
			修改</button>
			<button type="button" class="btn btn-default btn-sm" id="delete">
			<span class="glyphicon glyphicon-remove"></span>
			删除</button>
		</div>
		<div id="toolbar" class="form-group has-feedback col-md-offset-10" id="searchInput">
			<input type="text" placeholder="过滤" class="form-control"></input>
			<span class="glyphicon glyphicon-search form-control-feedback"></span>
		</div>
		
		<div id="tableContainer">
		<!--
			<table id="dataTable" class="table table-bordered table-hover">
				<thead>
	  				<tr>
	  					<th><input type="checkbox" id="all"></th>
	  					<th>编号</th>
	  					<th>用户名</th>
	  					<th>密码</th>
	  					<th>性别</th>
	  					<th>地址</th>
	  					<th>状态</th>
	  				</tr>
	  			</thead>
	  			<tbody>
	  			</tbody>
			</table>
		-->
		</div>
		
		<div id="pageButtons" style="height: 60px">
			<div class="col-md-6 dropup" style="padding-top: 30px">
			显示第 <kbd id="firstRecord"></kbd> 到第<kbd id="lastRecord"></kbd> 条记录，总共<kbd id="totalRecords"></kbd> 条记录，每页显示
			<kbd id="howManyRecords" class="dropdown-toggle" data-toggle="dropdown"></kbd>
			<ul id="selectRecordNumber" class="dropdown-menu pull-right">
				<li><a href="javascript:initPageableData(0,true,2)"><kbd id="firstRecordNumber">2</kbd></a></li>
				<li><a href="javascript:initPageableData(0,true,5)"><kbd id="secordRecordNumber">5</kbd></a></li>
				<li><a href="javascript:initPageableData(0,true,10)"><kbd id="lastRecordNumber">10</kbd></a></li>
			</ul>
			  条记录
			</div>
			<div class="col-md-6" style="text-align: right;">
				<ul class="pagination">
				</ul>
			</div>	
		</div>
	</div>
</body>
</html>
