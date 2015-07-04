# 病程查询（单条）
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/rdpatient/rdselectbypgid|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|gid|是|字符串|病程GID||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "gid":"病程GID"
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
        {
            "Gid":"病程GID",
            "title":"病程标题",
            "detail":"病程内容",
            "date":"病程记录时间",
            "pic":
            [
                {"imgGid":"图片GID", "imgurl":"图片"URL"},
                {"imgGid":"图片GID", "imgurl":"图片"URL"},
                {"imgGid":"图片GID", "imgurl":"图片"URL"}
            ],
            "vid":
            [
                {"vidGid":"视频GID", "imgurl":"视频URL"},
                {"vidGid":"视频GID", "imgurl":"视频URL"},
                {"vidGid":"视频GID", "imgurl":"视频URL"}
            ]
        }

    }



</code>
</pre>
