(function(d) {
	var c = {
		required : function() {
			var f = false;
			var h = this.attr("type");
			switch (h) {
			case undefined:
				if (d(this).is("select")) {
					h = (d(this).attr("multiple") == "multiple") ? "select-multiple"
							: "select-one";
					if (h == "select-multiple") {
						f = this.find("option:selected").length > 0 ? true
								: false;
						break;
					} else {
						if (h == "select-one") {
							f = !(!this.val() || this.val() == "0");
							break;
						}
					}
				} else {
					if (d(this).is("textarea")) {
						f = this.val() ? true : false;
						break;
					} else {
						alert("标签类型错误");
						f = true;
						break;
					}
				}
			case "radio":
			case "checkbox":
				var g = this.attr("name");
				f = d("input[name='" + g + "']:checked").size() > 0 ? true
						: false;
				break;
			case "text":
			case "password":
			case "textarea":
			case "file":
			default:
				f = this.val() ? true : false;
				break;
			}
			return f;
		},
		selected : function() {
			var g;
			if (!this.is("select") || this.attr("multiple") != "multiple") {
				alert("标签错误");
				g = false;
			} else {
				var i = arguments.length;
				var f = this.find(":selected").length;
				var j, h;
				switch (i) {
				case 0:
					alert("参数设置错误");
					g = false;
					break;
				case 1:
					j = Number(arguments[0]);
					g = (f >= j) ? true : false;
					break;
				case 2:
					j = Number(arguments[0]);
					h = Number(arguments[1]);
					g = (f >= j && f <= h) ? true : false;
					break;
				default:
					alert("未知异常");
					g = false;
					break;
				}
			}
			return g;
		},
		checkbox : function() {
			var f;
			if (!this.is(":checkbox")) {
				alert("标签错误");
				f = false;
			} else {
				var k = arguments.length;
				var j = this.attr("name");
				var i = d("input[name='" + j + "']:checkbox:checked").length;
				var g, h;
				switch (k) {
				case 0:
					alert("参数设置错误");
					f = false;
					break;
				case 1:
					g = Number(arguments[0]);
					f = (i >= g) ? true : false;
					break;
				case 2:
					g = Number(arguments[0]);
					h = Number(arguments[1]);
					f = (i >= g && i <= h) ? true : false;
					break;
				default:
					alert("未知异常");
					f = false;
					break;
				}
			}
			return f;
		},
		firstChar : function() {
			var h = arguments.length > 0 ? arguments[0] : "^[A-Za-z]";
			var g = new RegExp(h);
			var f = g.test(this.val());
			return f;
		},
		invalidChar : function() {
			var h = arguments.length > 0 ? arguments[0] : "^[a-zA-Z0-9_]*$";
			var g = new RegExp(h);
			var f = g.test(this.val());
			return f;
		},
		isChinese : function() {
			var f = /([^\u4E00-\u9FA5])/.test(this.val());
			return !f;
		},
		length : function() {
			var f, h, g;
			var j = arguments.length;
			var i = this.val().length;
			switch (j) {
			case 0:
				alert("参数设置错误");
				f = false;
				break;
			case 1:
				h = Number(arguments[0]);
				f = (i >= h) ? true : false;
				break;
			case 2:
				h = Number(arguments[0]);
				g = Number(arguments[1]);
				f = (i >= h && i <= g) ? true : false;
				break;
			default:
				alert("未知异常");
				f = false;
				break;
			}
			return f;
		},
		limit : function() {
			var f, h, j;
			var i = arguments.length;
			var g = Number(d(this).val());
			switch (i) {
			case 0:
				alert("参数设置错误");
				f = false;
				break;
			case 1:
				h = Number(arguments[0]);
				f = (g >= h) ? true : false;
				break;
			case 2:
				h = Number(arguments[0]);
				j = Number(arguments[1]);
				f = (g >= h && g <= j) ? true : false;
				break;
			default:
				alert("未知异常");
				f = false;
				break;
			}
			return f;
		},
		decimal : function() {
			var f, l, h;
			var k = arguments.length;
			var j, i;
			switch (k) {
			case 0:
				alert("参数设置错误");
				f = false;
				break;
			case 1:
				l = Number(arguments[0]);
				j = "^([0-9]+)([.]?)([0-9]{0," + l + "})$";
				i = new RegExp(j);
				f = i.test(this.val());
				if (!f) {
					if (this.val().indexOf(".") == -1) {
						f = true;
					}
				}
				break;
			case 2:
				l = Number(arguments[0]);
				h = Number(arguments[1]);
				var g = h - l;
				if (g === 1) {
					g = "{1}";
				} else {
					if (g < 1) {
						alert("参数设置错误");
						break;
					} else {
						g = "{1," + g + "}";
					}
				}
				j = "^([0-9]" + g + ")([.]?)([0-9]{0," + l + "}?)$";
				i = new RegExp(j);
				f = i.test(this.val());
				break;
			default:
				alert("未知异常");
				f = false;
				break;
			}
			return f;
		},
		email : function() {
			var f = /^([A-Za-z0-9_\-\.\'])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,6})$/
					.test(this.val());
			return f;
		},
		idNumber : function() {
			var f = /^\d{6}(?:((?:19|20)\d{2})(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2]\d|3[0-1])\d{3}(?:x|X|\d)|(?:\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2]\d|3[0-1])\d{3}))$/
					.test(this.val());
			return f;
		},
		equal : function(g) {
			var f;
			if (g && d(g)) {
				f = (this.val() === d(g).val());
			} else {
				alert("参数设置错误");
				f = false;
			}
			return f;
		},
		linkage : function() {
			var f = d.validMethods.required.apply(this);
			var h = arguments.length;
			if (h < 1) {
				alert("参数设置错误");
				f = false;
			} else {
				for ( var g = 0; g < h; g++) {
					if (!f) {
						break;
					}
					f = f && d.validMethods.required.apply(d(arguments[g]));
				}
			}
			return f;
		},
		choose : function() {
			var f = d.validMethods.required.apply(this);
			var h = arguments.length;
			if (h < 1) {
				alert("参数设置错误");
				reuslt = false;
			} else {
				for ( var g = 0; g < h; g++) {
					if (f) {
						break;
					}
					f = f || d.validMethods.required.apply(d(arguments[g]));
				}
			}
			return f;
		},
		ajaxValid : function(g, i, l, k) {
			var j = this;
			var f = j.data("valid");
			var n = j.data("text");
			var m = j.data("right");
			if (arguments.length < 3) {
				alert("参数设置错误");
				return false;
			}
			var h = j.attr("name") + "=" + j.val();
			if (k) {
				h += "&" + k;
			}
			d.ajax( {
				url : g,
				data : h,
				type : l,
				error : function() {
					f.wrong.apply(j, [ "由于网络原因，请求发送失败" ]);
					j.data("validResult", false);
				},
				success : function(o) {
					if (o === i) {
						f.right.apply(j, [ m ]);
						j.data("validResult", true);
					} else {
						f.wrong.apply(j, [ n ]);
						j.data("validResult", false);
					}
				}
			});
			return 2;
		}
	};
	var b = {
		username : {
			focus : "请输入6-12位数字、字母、下划线组合，并以字母开头",
			required : "请输入用户名",
			bind : {
				blur : [ "firstChar", "invalidChar", "length(6,12)",
						"ajaxValid(json.txt,POST,0000)" ]
			},
			text : [ "只能字母开头", "只能输入数字、字母、下划线组合", "只能输入6-12位字符",
					"该用户名已被注册，请尝试其他用户名" ],
			right : ""
		},
		password : {
			focus : "请输入6-16位字符，区分大小写",
			required : "请输入密码",
			bind : {
				blur : [ "length(6,16)" ]
			},
			text : [ "只能输入6-16位字符" ],
			right : ""
		},
		repeatPwd : {
			focus : "请再次输入密码",
			required : "请再次输入密码",
			bind : {
				blur : [ "length(6,16)", "equal(#password)" ]
			},
			text : [ "只能输入6-16位字符", "两次密码不一致，请重新输入" ],
			right : ""
		},
		tel : {
			focus : "请输入固定电话，格式如：041188888888",
			bind : {
				blur : [ "lenght(5,20)" ]
			},
			text : [ "请输入固定电话，格式如：041188888888" ],
			right : ""
		},
		phone : {
			focus : "请输入手机号码",
			bind : {
				blur : [ "choose(#email)", "length(10,12)" ]
			},
			text : [ "手机与邮箱必填一项", "请输入有效手机号码" ],
			right : ""
		},
		email : {
			focus : "请输入邮箱",
			required : "请输入电子邮箱",
			bind : {
				blur : [ "choose(#phone)", "email" ]
			},
			text : [ "手机与邮箱必填一项", "格式不正确，正确格式：example@my120.com" ],
			right : ""
		},
		truename : {
			focus : "请输入真实姓名，2-15个汉字",
			required : "请输入真实姓名",
			bind : {
				blur : [ "isChinses", "length(2,15)" ]
			},
			text : [ "只能输入中文", "只能输入2-15个汉字" ],
			right : ""
		},
		idNumber : {
			focus : "证件号码用于核实用户身份，网站不会透露给第三方",
			reuqired : "请输入有效身份证号",
			bind : {
				blur : [ "idNumber" ]
			},
			text : [ "请输入有效身份证号" ],
			right : ""
		},
		address : {
			focus : "省、市、县、详细地址为必填项，乡、村为选填项",
			required : "详细地址不能为空",
			bind : {
				blur : [ "length(0,40)", "linkage(#province,#city,#county)" ]
			},
			text : [ "最长输入40个字符", "省、市、县须全选" ],
			right : ""
		},
		addressSelect : {
			focus : "",
			required : "省、市、县及详细地址为必填项",
			bind : {
				change : [ "required" ]
			},
			text : [ "省、市、县及详细地址为必填项" ],
			right : ""
		},
		postcode : {
			focus : "请输入6位数字",
			bind : {
				blur : [ "postcode" ]
			},
			text : [ "请输入6位数字" ],
			right : ""
		},
		price : {
			focus : "只能输入数字，允许一位小数",
			required : "请输入价格",
			bind : {
				blur : [ "decimal(1)", "limit(0,1000)" ]
			},
			text : [ "只能输入数字，允许一位小数", "请输入0-1000以内的价格" ],
			right : ""
		},
		comment : {
			focus : "仅限200字以内",
			required : "请输入备注",
			bind : {
				blur : [ "length(0,200)" ]
			},
			text : [ "仅限200字以内" ],
			right : ""
		}
	};
	function a(i) {
		var g = /\((.*)\)/.exec(i);
		var f = null;
		var h = {};
		if (g) {
			f = g[1].split(",");
			i = /(.*)\(/.exec(i)[1];
		}
		h.name = i;
		h.args = f;
		return h;
	}
	function e(l) {
		var k = this;
		var n = d(l).data("valid");
		var h = /\((.*)\)/.exec(k.attr("class"));
		k.data("valid", n);
		if (!h) {
			alert("参数设置错误");
			return;
		} else {
			h = d.validType[h[1]];
		}
		k.focus(function() {
			d(this).addClass(n.lockClass);
			if (h.focus) {
				n.focus.apply(d(this), [ h.focus ]);
			}
		});
		var m = [];
		for ( var g in h.bind) {
			for ( var f = 0; f < h.bind[g].length; f++) {
				m[f] = a(h.bind[g][f]);
			}
			k.bind(g, {
				execute : m,
				formOpts : n,
				type : h
			}, function(q) {
				var s = d(this);
				var o = q.data.type;
				if (o.trim) {
					k.val(d.trim(k.val()));
				}
				var r = q.data.formOpts;
				if (s.hasClass(r.lockClass)) {
					if (d.validMethods.required.apply(s)) {
						var i = 0, p = q.data.execute;
						for (; i < p.length; i++) {
							s.data("text", o.text[i]);
							s.data("right", o.right);
							if (p[i].args) {
								result = d.validMethods[p[i].name].apply(s,
										p[i].args);
							} else {
								result = d.validMethods[p[i].name].apply(s);
							}
							if (result === false) {
								r.wrong.apply(s, [ o.text[i] ]);
								s.data("validResult", false);
								break;
							} else {
								if (result === 2) {
									r.focus.apply(s, [ "请稍后，正在发送请求" ]);
								}
							}
						}
						if (i === p.length) {
							r.right.apply(s, [ o.right ]);
							if (s.is(":checkbox") || s.is(":radio")) {
								var j = s.attr("name");
								objs = d("input[name='" + j + "']").data(
										"validResult", true);
							}
							s.data("validResult", true);
						}
					} else {
						if (o.required) {
							r.wrong.apply(s, [ o.required ]);
							s.data("validResult", false);
						} else {
							s.data("validResult", true);
							r.normal.apply(s);
						}
					}
				}
			});
		}
		k.blur(function() {
			d(this).removeClass(n.lockClass);
		});
	}
	d.extend( {
		validType : b,
		validMethods : c
	});
	d.fn.validator = function(f) {
		var k = d.extend( {}, d.fn.validator.defaults, f);
		var j = this.data("valid", k);
		var l = j.find("[class*='validator']");
		var h = l.length;
		for ( var g = 0; g < h; g++) {
			e.apply(l.eq(g), [ j ]);
		}
		return {
			result : function() {
				var o = j.serializeArray();
				var n = {};
				for ( var m = 0; m < o.length; m++) {
					if (n[o[m].name]) {
						n[o[m].name] += "," + o[m].value;
					} else {
						n[o[m].name] = o[m].value;
					}
				}
				return n;
			},
			valid : function() {
				var p, q, m = true;
				for ( var o = 0; o < h; o++) {
					p = /\((.*)\)/.exec(l.eq(o).attr("class"))[1];
					q = d.validType[p].bind;
					for ( var n in q) {
						l.eq(o).addClass(k.lockClass).trigger(n);
						m = m && l.eq(o).data("validResult");
					}
				}
				return m;
			},
			reset : function() {
				j.get(0).reset();
				for ( var m = 0; m < h; m++) {
					k.normal.apply(l.eq(m));
				}
			},
			submit : function(r, p, i, q, m, o) {
				if (this.valid()) {
					if (r == "ajax") {
						var n = {};
						n.url = j.attr("action");
						n.type = j.attr("method");
						n.data = this.result();
						if (p) {
							n.data = d.extend( {}, n.data, p);
						}
						if (i) {
							n.dataType = i;
						}
						if (q) {
							n.success = q;
						}
						if (m) {
							n.error = m;
						}
						if (o) {
							n.beforeSend = o;
						}
						d.ajax(n);
						return false;
					} else {
						j.submit();
					}
				} else {
					return false;
				}
			}
		};
	};
	d.fn.validator.defaults = {
		lockClass : "validFocus",
		normal : null,
		focus : null,
		wrong : null,
		right : null,
		sumbitType : "ajax"
	};
})(jQuery);