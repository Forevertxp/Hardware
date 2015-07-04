# 意见反馈查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/feedback!feedbackselect.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token"
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
                "userid":"用户GID",
                "token":"token",
                "fbdetail":"反馈内容",
                "fbdatetime":"反馈时间",
                "fbstatus":"是否接受随访（0-否/1-是）",
                "fdpic":
                [
                    {"imgGid":"图片GID", "imgurl":"图片"URL"},
                    {"imgGid":"图片GID", "imgurl":"图片"URL"},
                    {"imgGid":"图片GID", "imgurl":"图片"URL"}
                ]

            },
            {
                "userid":"用户GID",
                "token":"token",
                "fbdetail":"反馈内容",
                "fbdatetime":"反馈时间",
                "fbstatus":"是否接受随访（0-否/1-是）",
                "fdpic":
                [
                    {"imgGid":"图片GID", "imgurl":"图片"URL"},
                    {"imgGid":"图片GID", "imgurl":"图片"URL"},
                    {"imgGid":"图片GID", "imgurl":"图片"URL"}
                ]

            }
        ]

    }



</code>
</pre>
