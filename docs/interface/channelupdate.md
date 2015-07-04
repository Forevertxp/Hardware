# 更新频道
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel/channelupdate|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|gid|是|字符串|频道GID||
|slogan|是|字符串|频道简介||

###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "gid":"频道GID",
    "slogan":"频道简介"
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
