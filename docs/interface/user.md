# 用户接口
|相关信息|内容|
|--|--|
|访问地址|user!Login.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|mobile|是|字符串|手机号码||
|pwd|是|字符串|密码||

###请求示例
<pre>
<code>
{
     "mobile": "13811111111",
     "pwd": "加密后密码字符串"
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
       [
           {
               "hospital_name": "安庆市第二人民医院",
               "hospital_level": "二级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "安庆市第一人民医院",
               "hospital_level": "三级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "安庆市海军医院",
               "hospital_level": "二级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "安庆市立医院",
               "hospital_level": "三级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "安庆市石化医院",
               "hospital_level": "二级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "太湖县人民医院",
               "hospital_level": "二级甲等",
               "hospital_city": "安庆"
           },
           {
               "hospital_name": "望江县人民医院",
               "hospital_level": "二级甲等",
               "hospital_city": "安庆"
           }
       ]
    }
</code>
</pre>
