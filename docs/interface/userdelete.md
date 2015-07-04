# 用户删除
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/userexit|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|token||
|deleteusergid|是|字符串|注销的用户GID||

###请求示例
<pre>
<code>
{
     "usergid": "用户GID",
     "token": "token",
     "deleteusergid":"注销的用户GID"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "注销成功",
       "result_data":null
    }



</code>
</pre>
