var Misc = function() {}

Misc.loadSelect = function(parentId, url) {
    var $els = [];
    if ($.trim(parentId).length === 0) {
        $els = $("select.code-select");
    } else {
        $els = $("#" + parentId + " select.code-select");
    }
    if(!$($els)[0]) {
        return;
    }
    for (var i = 0; i < $els.length; ++i) {
        var $el = $els.eq(i);
        var id = $el.attr("id");
        if ($.trim(id).length === 0) {
            id = ("code-select-" + i + new Date().getTime() + Math.random()).replace(".", "");
            $el.attr("id", id)
        }
        var params = {
            dataId: id,
            clazz: $.trim($el.attr("data-clazz"))
        };
        if ("" === params.clazz) {
            continue;
        }

        $.post(url, params, function(data) {
            var html = "";
            var dataId = "";
            if ("undefined" !== typeof(data)) {
                data = eval("(" + data + ")");
                if (data["success"]) {
                    dataId = data["message"];
                    var $dataEl = $("#" + dataId);
                    var empty = $.trim($dataEl.attr("data-empty"));
                    var value = $.trim($dataEl.attr("data-value"));
                    if ($.trim(empty).length !== 0) {
                        if (value === "") {
                            html += "<option value=\"\" selected=\"selected\">" + empty + "</option>";
                        } else {
                            html += "<option value=\"\">" + empty + "</option>";
                        }
                    }

                    data = eval("(" + data["data"] + ")");
                    for (var j = 0; j < data.length; ++j) {
                        if ($.trim(value) === data[j]["code"]) {
                            html += "<option selected=\"selected\" value=\"" + data[j]["code"] + "\">" + data[j]["name"] + "</option>";
                        } else {
                            html += "<option value=\"" + data[j]["code"] + "\">" + data[j]["name"] + "</option>";
                        }
                    }
                    $dataEl.html(html);
                }
            }
        });
    }
}