# 意见反馈新增
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/feedback/feedbackadd|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|detail|是|字符串|反馈内容||
|datetime|是|字符串|反馈时间||
|status|是|字符串|是否接受随访（0-否/1-是）||
|lstpic|是|list|图片||

###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "detail":"反馈内容",
    "datetime":"反馈时间",
    "status":"是否接受随访（0-否/1-是）",
    "lstpic":"图片GID,图片GID"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "新增成功",
       "result_data":null

    }



</code>
</pre>
