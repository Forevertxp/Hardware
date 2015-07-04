# 用户信息修改
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/update|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|token||
|email|否|字符串|电子邮箱||
|chgid|否|字符串|所在频道GID||
|sex|否|字符串|性别(1-男/2-女)||
|birthday|否|字符串|生日||
|local|否|字符串|驻地编码||
|descript|否|字符串|个人简介||
|avatars|否|字符串|头像图片GID||
|hospital|否|字符串|医院||
|title|否|字符串|社会任职||
|jobtitle|否|字符串|职称||
|wx|否|字符串|微信号||
|wb|否|字符串|微博号||
|qq|否|字符串|QQ||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "email":"邮箱",
     "chgid":"所在频道GID",
     "sex":"性别(1-男/2-女)",
     "birthday":"生日",
     "local":"驻地编码",
     "descript":"个人简介",
     "avatars":"头像图片GID",
     "hospital":"医院",
     "title":"社会任职",
     "jobtitle":"职称",
     "wx":"微信号",
     "wb":"微博号",
     "qq":"QQ"
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
