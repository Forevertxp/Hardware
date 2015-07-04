# 会议日程查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/cons/contopicSelect|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|否|字符串|用户GID||
|token|否|字符串|验证码||
|gid|是|字符串|会议GID||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "gid":"会议GID"
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
                "gid":"日程GID",
                "parentgid":"所属日程GID",
                "moderator":"主持人",
                "reviewer":"点评人",
                "speaker":"演讲人",
                "sponsor":"赞助商",
                "sponsordesc":"赞助商详情",
                "title":"议题",
                "titlee":"议题(英文)",
                "type":"议题类型",
                "level":"级别（1-议题/2-议程）",
                "datetimebegin":"开始时间",
                "datetimeend":"截止时间"
           },
           {
                "gid":"日程GID",
                "parentgid":"所属日程GID",
                "moderator":"主持人",
                "reviewer":"点评人",
                "speaker":"演讲人",
                "sponsor":"赞助商",
                "sponsordesc":"赞助商详情",
                "title":"议题",
                "titlee":"议题(英文)",
                "type":"议题类型",
                "level":"级别（1-议题/2-议程）",
                "datetimebegin":"开始时间",
                "datetimeend":"截止时间"
           }
       ]

    }



</code>
</pre>
