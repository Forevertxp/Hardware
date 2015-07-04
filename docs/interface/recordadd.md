# 病程新增
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/rdpatient/rdrecordadd|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|token||
|gid|是|字符串|病人GID||
|title|是|字符串|标题||
|recdate|是|字符串|记录日期||
|detail|是|字符串|病程描述||
|lstpic|否|list|图片||
|lstvid|否|list|视频||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "gid":"病人GID",
     "title":"标题",
     "recdate":"记录日期",
     "detail":"病程描述",
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
       "message": "新增成功",
       "result_data":null
    }



</code>
</pre>
