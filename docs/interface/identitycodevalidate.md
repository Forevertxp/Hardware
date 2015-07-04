# 验证码
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/identitycodevalidate|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|usermobile|是|字符串|用户手机号||

###请求示例
<pre>
<code>
{
     "usermobile": "用户手机号"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "验证码发送成功",
       "result_data":null
    }



</code>
</pre>
