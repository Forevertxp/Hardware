# 帖子列表
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel/postselect|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|chGid|否|字符串|频道GID||
|datebegin|是|字符串|开始日期||
|selecttype|是|字符串|查询类型(1-大于当前日期/2-小于当前日期)||
|count|是|字符串|显示条数||
|isfav|是|字符串|是否关注||

###请求示例
<pre>
<code>
{
     "userid":"用户GID",
     "token":"token",
     "chGid":"频道GID",
     "datebegin":"开始日期",
     "selecttype":"查询类型(1-大于当前日期/2-小于当前日期)",
     "count":"显示条数",
     "isfav":"是否关注"
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
            "Gid":"发帖GID",
            "userGid":"发帖用户GID",
            "username":"发帖用户名称",
            "detail":"发帖内容",
            "CreateTime":"发帖时间",
            "lstpic":"图片GID，图片GID",
            "lstvid":"视频GID",
            "favcount":0,
            "lstreplycount":0

        },
        {
            "Gid":"发帖GID",
            "userGid":"发帖用户GID",
            "username":"发帖用户名称",
            "detail":"发帖内容",
            "CreateTime":"发帖时间",
            "lstpic":"图片GID，图片GID",
            "lstvid":"视频GID",
            "favcount":0,
            "lstreplycount":0

        }
        ]

    }



</code>
</pre>
