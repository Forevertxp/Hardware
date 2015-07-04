# 意见反馈更新
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/feedback!feedbackupdate.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|fbGid|是|字符串|意见反馈GID||
|fbdetail|否|字符串|反馈内容||
|fbdatetime|否|字符串|反馈时间||
|fbstatus|否|字符串|是否接受随访（0-否/1-是）||
|lstpic|否|list|图片||


###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "fbGid":"意见反馈GID",
    "fbdetail":"反馈内容",
    "fbdatetime":"反馈时间",
    "fbstatus":"是否接受随访（0-否/1-是）",
    "lstpic":
    ["图片二进制流字符串","图片二进制流字符串","图片二进制流字符串"]
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "更新成功",
       "result_data":null

    }



</code>
</pre>
