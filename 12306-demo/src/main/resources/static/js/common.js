/**
 * Created by an_wch on 2018/1/30.
 */
function getCookie(name) {
    var arr = document.cookie.match(new RegExp("(^| )" +name + "=([^;]*)(;|$)"));
    if (arr != null) {
        return decodeURI(unescape(arr[2], "utf-8"))
    }
    return null
}
var ua = navigator.userAgent.toLowerCase();
var UA = {
    Android : function() {
        return ua.match(/android/i) ? true : false;
    },
    BlackBerry : function() {
        return ua.match(/blackberry/i) ? true : false;
    },
    iOS : function() {
        return ua.match(/iphone|ipad|ipod/i) ? true : false;
    },
    Windows : function() {
        return ua.match(/iemobile/i) ? true : false;
    },
    Safari : function() {
        return ua.match(/safari/i) && ua.match(/mac os/i) ? true : false;
    },
    isMobile : function() {
        return ua.match(/android|webos|iphone|ipad|windows phone|ipod|blackberry|symbianos|nokia|mobile/i) ? true : false;
    }
};

if ((typeof ResponseStatus) == "undefined" || !(typeof ResponseStatus)) { //枚举
    var ResponseStatus = new Object();
    ResponseStatus['SUCCESS'] = 'SUCCESS'; //请求成功
    ResponseStatus['ERROR'] = 'ERROR'; //请求出错
    ResponseStatus['TIMEOUT'] = "TIMEOUT"; //Session 过期
}
function clientWidth() {
    return document.documentElement.clientWidth || document.body.clientWidth;
}
function clientHeight() {
    return document.documentElement.clientHeight || document.body.clientHeight;
}

var CONTEXT_PATH = "";

/**
 * 判断input 的val是否是 placeholder的值
 */
function isPlaceholder(input) {
    var val = input.val();
    var placeholder = input.attr("placeholder");
    if (placeholder && placeholder === val) {
        return true;
    }
    return false;
}

var rbracket = /\[\]$/,
    rCRLF = /\r?\n/g,
    rsubmitterTypes = /^(?:submit|button|image|reset|file)$/i,
    rsubmittable = /^(?:input|select|textarea|keygen)/i;

var manipulation_rcheckableType = /^(?:checkbox|radio)$/i;

jQuery.fn.extend({
    serializeF : function() {
        return jQuery.param(this.serializeArrayF());
    },
    serializeArrayF : function() {
        return this.map(function() {
            // Can add propHook for "elements" to filter or add form elements
            var elements = jQuery.prop(this, "elements");
            return elements ? jQuery.makeArray(elements) : this;
        })
            .filter(function() {
                var type = this.type;
                // Use .is(":disabled") so that fieldset[disabled] works
                return this.name && !jQuery(this).is(":disabled") && !isPlaceholder(jQuery(this)) &&
                    rsubmittable.test(this.nodeName) && !rsubmitterTypes.test(type) &&
                    (this.checked || !manipulation_rcheckableType.test(type));
            })
            .map(function(i, elem) {
                var val = jQuery(this).val();

                return val == null ?
                    null :
                    jQuery.isArray(val) ?
                        jQuery.map(val, function(val) {
                            return {
                                name : elem.name,
                                value : val.replace(rCRLF, "\r\n")
                            };
                        }) :
                        {
                            name : elem.name,
                            value : val.replace(rCRLF, "\r\n")
                        };
            }).get();
    }
});
/**
 * 登录
 * @param formId: 登录表单ID
 */
function login(formId) {
    //alert(formId);
    var form = $("#" + formId);
    var params = JSON.stringify(form.serializeObject());;
    console.log(params);
    $.ajax({
        type : "POST",
        url : "login",
        data : params,
        contentType: "application/json;charset=utf-8;",
        success : function(data) {
            alert(data);
        },
    });
}

/**
 *
 * @param
 */
function get(url) {
    $.ajax({
        type : "GET",
        url : url,
        contentType: "application/json;charset=utf-8;",
        success : function(data) {
            console.log(data);
        },
    });
}

$(function() {
    // 如果不支持placeholder，用jQuery来完成
    if (!isSupportPlaceholder()) {
        // 遍历所有input对象, 除了密码框
        $('input').not("input[type='password']").each(
            function() {
                var self = $(this);
                var val = self.attr("placeholder");
                if (val) {
                    input(self, val);
                }
            }
        );
        /**/ /* 对password框的特殊处理
         * 1.创建一个text框
         * 2.获取焦点和失去焦点的时候切换
         */
        $('input[type="password"]').each(
            function() {
                var pwdField = $(this);
                var pwdVal = pwdField.attr('placeholder');
                if (!pwdVal) {
                    return;
                }
                var _cls = pwdField.attr("class"); //这里获取class值
                var pwdId = pwdField.attr('id');
                // 重命名该input的id为原id后跟1
                pwdField.after('<input id="' + pwdId + '1" type="text" value=' + pwdVal + ' class=' + _cls + ' autocomplete="off" />');
                var pwdPlaceholder = $('#' + pwdId + '1');
                pwdPlaceholder.show();
                pwdField.hide();

                pwdPlaceholder.focus(function() {
                    pwdPlaceholder.hide();
                    pwdField.show();
                    pwdField.focus();
                });

                pwdField.blur(function() {
                    if (pwdField.val() == '') {
                        pwdPlaceholder.show();
                        pwdField.hide();
                    }
                });
            }
        );
    }
});

// 判断浏览器是否支持placeholder属性
function isSupportPlaceholder() {
    var input = document.createElement('input');
    return 'placeholder' in input;
}

// jQuery替换placeholder的处理
function input(obj, val) {
    var $input = obj;
    var val = val;
    $input.attr({
        value : val
    });
    $input.focus(function() {
        if ($input.val() == val) {
            $(this).attr({
                value : ""
            });
        }
    }).blur(function() {
        if ($input.val() == "") {
            $(this).attr({
                value : val
            });
        }
    });
}
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};