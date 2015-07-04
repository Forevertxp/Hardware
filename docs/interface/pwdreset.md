# 重置密码
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/resetpwd|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|usergid|是|字符串|用户Gid||
|oldpwd|是|字符串|原密码||
|newpwd|是|字符串|新密码||

###请求示例
<pre>
<code>
{
     "usergid": "用户GID",
     "oldpwd": "原密码",
     "newpwd":"新密码"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "密码修改成功",
       "result_data":null
    }



</code>
</pre>
