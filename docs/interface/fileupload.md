# 图片上传
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/common!picupload.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|lstPic|是|list|图片||

###请求示例
<pre>
<code>
{

    {
        "imgdata":"图片二进制，
        "imgangle":"拍摄角度",
        "width":"高度",
        "height":"宽度"
    }

}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "新增成功",
       "result_data":
       {
            "imggid":"图片GID"
       }
    }



</code>
</pre>
