# 用户登录
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/login|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|usermobile|是|字符串|手机号||
|pwd|是|字符串|加密后的密码字符串||
|hardosver|否|字符串|终端操作系统版本||
|hardsn|否|字符串|终端SN号||
|ipv4|否|字符串|ipv4地址||
|ipv6|否|字符串|加密后的密码字符串||
|softver|否|字符串|加密后的密码字符串||

###请求示例
<pre>
<code>
{
     "usermobile": "1381111111",
     "pwd": "加密后的密码字符串",
     "hardosver":"终端操作系统版本"，
     "hardsn":"终端SN号",
     "ipv4":"ipv4地址",
     "ipv6":"ipv6地址",
     "softver":"App版本"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "登录成功",
       "result_data":
       {
           "userGID": "用户GID",
           "token": "用户token值",
           "tokenendtime":"token过期时间"
        }
    }



</code>
</pre>
