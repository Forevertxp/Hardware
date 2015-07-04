# 频道删除
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel!channeldelete.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|chGid|是|字符串|频道GID||

###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "chGid":"频道GID"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "删除成功",
       "result_data":null

    }



</code>
</pre>
