# 回复查询
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel!postselect.action|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|chGid|否|字符串|频道GID||

###请求示例
<pre>
<code>
{
     "userid":"用户GID",
     "token":"token",
     "chGid":"频道GID"
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
        {
            "pstGid":"发帖GID",
            "pstuserGid":"发帖用户GID",
            "pstusername":"发帖用户名称",
            "pstdetail":"发帖内容",
            "pstCreateTime":"发帖时间",
            "lstpic":
            [
            {"imggid":"图片GID", "imgurl":"图片url"},
            {"imggid":"图片GID", "imgurl":"图片url"},
            {"imggid":"图片GID", "imgurl":"图片url"}
            ],
            "lstvid":
            [
                "视频地址","视频地址","视频地址"
            ]

        }

    }



</code>
</pre>
