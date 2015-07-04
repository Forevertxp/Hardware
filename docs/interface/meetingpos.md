# 会议位置信息查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/cons/conlocalSelect|

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
       {
            "localgps":"地图位置",
            "localDetail":"详细信息"
       }

    }



</code>
</pre>
