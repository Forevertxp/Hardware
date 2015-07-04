# 帖子详情
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/channel/postdetailselect|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|验证码||
|gid|否|字符串|帖子GID||

###请求示例
<pre>
<code>
{
     "userid":"用户GID",
     "token":"token",
     "gid":"帖子GID"
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
            "Gid":"发帖GID",
            "userGid":"发帖用户GID",
            "username":"发帖用户名称",
            "detail":"发帖内容",
            "CreateTime":"发帖时间",
            "lstpic":"图片GID，图片GID",
            "lstvid":"视频GID",
            "lstreply":
            [
                {
                    "rpyGID":"回复GID",
                    "rpyuserGid":"回复人GID",
                    "rpyusername":"回复人名称",
                    "rpytousergid":"被回复人GID",
                    "rpytousername":"被回复人名称",
                    "rpydetail":"回复内容",
                    "rpyCreateTime":"回复时间"
                }，
                {
                    "rpyGID":"回复GID",
                    "rpyuserGid":"回复人GID",
                    "rpyusername":"回复人名称",
                    "userava":"回复人头像",
                    "rpytousergid":"被回复人GID",
                    "rpytousername":"被回复人名称",
                    "rpydetail":"回复内容",
                    "rpyCreateTime":"回复时间"
                }，
                {
                    "rpyGID":"回复GID",
                    "rpyuserGid":"回复人GID",
                    "rpyusername":"回复人名称",
                    "userava":"回复人头像",
                    "rpytousergid":"被回复人GID",
                    "rpytousername":"被回复人名称",
                    "rpydetail":"回复内容",
                    "rpyCreateTime":"回复时间"
                }
            ]

        }

    }



</code>
</pre>
