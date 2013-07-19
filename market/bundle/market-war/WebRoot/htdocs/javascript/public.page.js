$(document).ready(function(){
	var dataList = {};
	dataList.dataList = ".dataList";
	dataList.searchForm = ".searchForm";
	dataList.pageshow = ".showpage";
	dataList.pagination = ".pagination";
	dataList.pageSizeForm = "form.pageSize";
	dataList.formAction = AjaxPageRequest;
	dataList.pageIndex = 1;
	dataList.pageSize = 20;
	dataList.pageShowSize = 10;
	dataList.pageInfo = true;
	dataList.jumpPage = publicListPage;
	dataList.otherData = {};
	dataList.otherData.type = "";
	initDataList( dataList );
});

function initDataList(opts){
	if( opts.searchForm ){
		var formData = $( opts.searchForm ).validator();
		if( !opts.otherData ) opts.otherData = {};
		opts.otherData = $.extend({}, opts.otherData, formData.result());

		$( opts.searchForm ).submit(function(event){
			opts.pageIndex = 1;
			opts.otherData = $.extend({}, opts.otherData, formData.result());
			publicListPage(opts);
			event.preventDefault();
			return false;
		});
	}

	if( opts.pageshow ){
		$( opts.pageshow+" a").click(function(){
			var obj = $(this);
			opts.otherData.type = obj.attr("lang");
			$( opts.pageshow ).find(".labelOn").removeClass("labelOn");
			var index = obj.index();
			$( opts.pageshow ).each(function(){
				$(this).find("a").eq(index).addClass("labelOn");
			});
			publicListPage(opts);
		});
	}

	if( opts.pageSizeForm ){
        $( opts.pageSizeForm ).submit(function(event){
            var pagesize = $(this).find(".listnumber").val();
            if( /([^0-9])/.test( pagesize ) ){
                art.dialog({
                    id:"msg", title:"提示信息", content:"请输入1-100之间的数字", lock:true, fixed:true,
                    okValue:"确定", ok:function(){}
                });
                $(this).find(":text").val( $(opts.pageSizeForm).not(this).find(":text").val() );
                return false;
            }
            pagesize = Number( pagesize );
            if( pagesize < 1 || pagesize > 100 ){
                art.dialog({
                    id:"msg", title:"提示信息", content:"请输入1-100之间的数字", lock:true, fixed:true,
                    okValue:"确定", ok:function(){}
                });
                $(this).find(":text").val( $(opts.pageSizeForm).not(this).find(":text").val() );
                return false;
            }
            opts.pageSize = pagesize;
            opts.pageIndex = 1;
            publicListPage(opts);
            $( opts.pageSizeForm ).find(".listnumber").val( opts.pageSize );
            return false;
        });
	}
	
	publicListPage(opts);
}

function publicListPage(opts){
	var pageData = {};
	pageData.pageindex = opts.pageIndex;
	pageData.pagesize = opts.pageSize;
	
	if( opts.otherData ) pageData = $.extend({}, pageData, opts.otherData);

	if( $( opts.dataList ).length ){
		$.ajax({
			url:opts.formAction,
			data:pageData,
			type:"post",
			dataType:"json",
			error:function(){
				alert("由于网络原因，数据传输失败");
			},
			success:function(data){
//				alert(data.JSONObject.count);
//				alert(data.JSONObject.data);
				page(data.JSONObject.count,data.JSONObject.data, opts);
			}
		});
	}
}

function page(count,data, opts){
	if(  Number( count ) !== 0 && data ){
		opts.sumcount = Number( count );
		$( opts.dataList + ">.example").siblings().remove();
		for(var i=0; i<data.length; i++){
			$.template( opts.dataList, "example", data[i], setDetail);
		}
		lister();
		$( opts.pagination ).pagination(opts);
	}
	else{
		noInfo(opts);
		$( opts.pagination ).html("");
	}
}

function noInfo(opts){
	$( opts.dataList ).find(".example").siblings().remove();
	var length = $( opts.dataList ).prev().find("th").length;
	$( opts.dataList ).append("<tr class='no-message'><td colspan='"+length+"'><div class='searchno'>没有数据</div></td></tr>");
}

function setDetail(obj, data){
}

