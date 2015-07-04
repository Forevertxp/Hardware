# 学术会议概要信息查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/cons/consummary|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|否|字符串|用户GID||
|token|否|字符串|验证码||
|datebegin|是|字符串|开始日期||
|selecttype|是|字符串|查询类型(1-大于当前日期/2-小于当前日期)||
|count|是|数字|显示条数||
|isfav|否|数字|是否收藏（0-所有/1-收藏的/2-未收藏的）||

###请求示例
<pre>
<code>
{
    "userid": "用户GID",
     "token": "token",
     "datebegin":"2015-03-02",
     "selecttype":1,
     "count":10,
     "isfav":1
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
                "gid":"会议GID",
                "local":"地点",
                "cfdate":"会议开始时间",
                "title":"标题",
                "sponsor":"发起人",
                "isfav":1
            },
            {
                "gid":"会议GID",
                "local":"地点",
                "cfdate":"会议开始时间",
                "title":"标题",
                "sponsor":"发起人",
                "isfav":1
            }
       ]

    }



</code>
</pre>
