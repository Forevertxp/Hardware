# 新增频道
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel!channeladd.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|否|字符串|用户GID||
|token|是|字符串|验证码||
|chname|是|字符串|频道名称||
|chslogan|是|字符串|频道简介||
|ch_avatars|是|字符串|频道头像||
|lstuser|是|list|用户列表||

###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "chname":"频道名称",
    "chslogan":"频道简介",
    "ch_avatars":"频道头像",
    "lstuser":
    ["用户GID","用户GID","用户GID"]
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
