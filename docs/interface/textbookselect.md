# 骨科指南查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/textbook/textsummary|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|否|字符串|用户GID||
|token|否|字符串|验证码||
|keyword|否|字符串|查询输入的内容（为空则全部查询）||
|datebegin|是|字符串|开始日期||
|selecttype|是|字符串|查询类型(1-大于当前日期/2-小于当前日期)||
|count|是|字符串|查询输入的内容（为空则全部查询）||
|category|是|字符串|所属类型||


###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "datebegin":"开始时间",
     "selecttype":"查询类型(1-大于当前日期/2-小于当前日期)",
     "count":"显示条目",
     "keyword":"关键字",
     "category":"所属类型"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "查询成功",
       "result_data":
       [
            {
                "gid":"文章GID",
                "rz1":"图片地址1（缩略图）",
                "rz2":"图片地址2(正图)",
                "publish":"发布时间",
                "author":"作者",
                "title":"标题",
                "selected":"1"
            },
            {
                "gid":"文章GID",
                "rz1":"图片地址1（缩略图）",
                "rz2":"图片地址2(正图)",
                "publish":"发布时间",
                "author":"作者",
                "title":"标题",
                "selected":"1"
            },
            {
                "gid":"文章GID",
                "rz1":"图片地址1（缩略图）",
                "rz2":"图片地址2(正图)",
                "publish":"发布时间",
                "author":"作者",
                "title":"标题",
                "selected":"1"
            }
       ]

    }



</code>
</pre>
