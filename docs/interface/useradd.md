# 用户注册
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/regist|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|usermobile|是|字符串|手机号||
|identitycode|是|字符串|验证码||
|pwd|是|字符串|密码||

###请求示例
<pre>
<code>
{
     "usermobile": "1381111111",
     "identitycode": "验证码",
     "pwd":"加密后的密码字符串"

}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "注册成功",
       "result_data":null
    }



</code>
</pre>
