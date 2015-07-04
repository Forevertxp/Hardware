# hisupdate
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/rdpatient/rdupdate|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|repaGid|是|字符串|病人GID||
|name|否|字符串|姓名||
|sex|否|字符串|性别(1-男/2-女)||
|age|否|字符串|年龄||
|sick|否|字符串|疾病||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "repaGid":"病人GID",
     "name":"姓名",
     "sex":"性别(1-男/2-女)",
     "age":"年龄",
     "sick":"疾病"
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
