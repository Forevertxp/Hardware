# 病程更新
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/rdpatient/rdrecordupdate|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|gid|是|字符串|病人GID||
|datebegin|是|字符串|开始时间||
|selecttype|是|字符串|查询类型(1-大于当前日期/2-小于当前日期)||
|count|是|字符串|查询条数||

###请求示例
<pre>
<code>
{
    "userid":"用户GID",
    "token":"token",
    "Gid":"病程GID",
    "title":"病程标题",
    "detail":"病程内容",
    "date":"病程记录时间",
    "lstpic":"图片GID,图片GID",
    "lstvid":"视频GID,视频GID"
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
