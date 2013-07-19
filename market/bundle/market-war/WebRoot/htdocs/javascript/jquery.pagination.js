(function($){
	var methods = {
		countPage:function(sumcount, pageSize){
			pageNum = Math.floor( sumcount/pageSize );
			return (sumcount%pageSize)>0 ? (pageNum+1) : pageNum;
		},
		writePage:function(obj, opts){
			var html="", temp, i=0;
			var left = (opts.pageShowSize%2==1) ? (opts.pageShowSize-1)/2 : (opts.pageShowSize/2-1);
			left = opts.pageIndex>left ? opts.pageIndex-left+opts.pageShowSize>opts.pageNum ? opts.pageNum-opts.pageShowSize+1<=0? 1 : opts.pageNum-opts.pageShowSize+1 : opts.pageIndex-left : 1;

			var right = left + opts.pageShowSize - 1;
			right = right>opts.pageNum ? opts.pageNum : right;
			
			html += opts.pageIndex===1 ? "<strong class='unclick'><i>◀</i>&nbsp;上一页</strong>" : "<a href='javascript:;' title='上一页' id='pageUp' class='page'><i>◀</i>&nbsp;上一页</a>";
			
			html += left===1 ? "" : "<a class='page' href='javascript:;' title='第1页'>1</a>";
			
			html += left>2 ? "<b>...</b>" : "";
			
			for(var j=left; j<opts.pageIndex; j++){
				html += "<a class='page' href='javascript:;' title='第"+j+"页'>"+j+"</a>";
				i++;
			}
			
			html +="<strong id='currentPage'>"+opts.pageIndex+"</strong>";
			i++;
			
			for(var j=opts.pageIndex+1; j<=opts.pageNum && i!=opts.pageShowSize; j++){
				html += "<a class='page' href='javascript:;' title='第"+j+"页'>"+j+"</a>";
				i++;
			}
			
			html += opts.pageNum-right>=2 ? "<b>...</b>" : "";
			
			html += right===opts.pageNum ? "" : "<a class='page' href='javascript:;' title='第"+opts.pageNum+"页'>"+opts.pageNum+"</a>";

			html += opts.pageIndex===opts.pageNum ? "<strong class='unclick'>下一页&nbsp;<i>▶</i></strong>" : "<a href='javascript:;' title='下一页' id='pageDown' class='page'>下一页&nbsp;<i>▶</i></a>";

			html += "<span><form action='"+opts.formAction+"' methods='post'><label>共"+opts.pageNum+"页&nbsp;";
			html += "到第<input type='text' id='jumpPage' size='3' value='"+opts.pageIndex+"' />页";
			html += "</label><input type='submit' class='button blackbutton' value='确定' />";
			if( opts.pageInfo ) html += "<label>&nbsp;共<b>"+opts.sumcount+"</b>条&nbsp;</label>";
			html += "</form></span>";
			obj.html(html);
		},
		bindEvent:function(obj, opts){
			obj.find("a.page").unbind("click").bind("click", {"opts":opts}, function(){
				var page = opts.pageIndex;
				if( this.id === "pageUp" ) page--;
				else if( this.id === "pageDown" ) page++;
				else page = ~~$(this).html();
				opts.pageIndex = page;
				opts.jumpPage( opts );
			});
			
			var form = obj.find("form");
			form.find("input:text").focus(function(){
				$(this).data("pageIndex", $(this).val());
				$(this).val("");
			}).blur(function(){
				if( this.value === "" ) $(this).val( $(this).data("pageIndex") );
			});
			form.unbind("submit").bind("submit", {"opts":opts}, function(event){
				var value = $("#jumpPage").val();
				if( /([^0-9])/.test(value) ){
					alert("页面输入错误");
					return false;
				}
				value = Number( value );
				if( value <= opts.pageNum && value > 0 ){
					opts.pageIndex = value;
					opts.jumpPage( opts );
				}
				else{
					alert("页面输入错误");
					return false;
				}
				event.preventDefault();
			});
		}
	};
	
	$.fn.pagination = function(options){
		var that = this;
		var opts = $.extend({}, $.fn.pagination.defaults, options);
		opts.pageNum = methods.countPage(opts.sumcount, opts.pageSize);
		if( opts.pageNum === 0 ){
			that.html("");
			return 0;
		}
		if( opts.pageIndex > opts.pageNum ) opts.pageIndex = opts.pageNum;
		methods.writePage(that, opts);
		if( opts.jumpPage ){
			methods.bindEvent(that, opts);
		}
		return opts.pageNum;
	};
	
	$.fn.pagination.defaults = {
		pageIndex:0,
		sumcount:0,
		pageSize:10,
		pageShowSize:10,
		pageInfo:false,
		formAction:"",
		jumpPage:null,
		otherData:null
	};
})(jQuery);