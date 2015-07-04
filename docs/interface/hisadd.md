# 新增病例夹
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/rdpatient/rdadd|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|name|是|字符串|姓名||
|sex|是|字符串|性别(1-男/2-女)||
|age|是|字符串|年龄||
|sick|是|字符串|疾病||

###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
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
       "message": "新增成功",
       "result_data":null
    }



</code>
</pre>
