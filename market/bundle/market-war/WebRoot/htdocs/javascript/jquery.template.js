(function($){
	var extend = {
		"template"	:function(selector, template, data, callback, append){
			var obj = $(selector+" > ."+template).clone().removeClass(template).show();
			if( append ) obj.prependTo(selector);
			else obj.appendTo(selector);
			for(var key in data){
				obj.find("[lang='"+key+"']").html(data[key]);
			}
			if( callback ) callback( obj, data );
			return obj;
		}
	};
	$.extend(extend);	
})(jQuery);