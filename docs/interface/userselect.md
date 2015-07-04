# UserSelect
|相关信息|内容|
|--|--|
|访问类型|POST|
|访问地址|/user/select|

###请求参数

|参数名称|是否必填|类型|说明|示例数据|
|--|--|--|--|--|--|
|userid|是|字符串|用户GID||
|token|是|字符串|token||
|userselectGID|是|字符串|被查询用户GID||
|chgid|是|字符串|所属频道GID||
|name|是|字符串|名字（模糊查询）||
###请求示例
<pre>
<code>
{
     "userid": "用户GID",
     "token": "token",
     "userselectGID":"被查询用户GID",
     "chgid":"所属频道GID",
     "name":"名字（模糊查询）"
}
</code>
</pre>

###返回结果示例

<pre>
<code>
    {
       "code": 1,
       "message": "更新成功",
       "result_data":
       [
           {
                "usergid":"用户GID",
                "name":"姓名",
                "descript":"个人简介",
                "Title":"社会任职",
                "avatars":"头像链接",
                "hospitalname":"所属医院名称",
                "localname":"驻地名称",
                "jobtitle":"职称",
                "wx":"微信号",
                "wb":"微博号",
                "QQ":"QQ",
                "sex":"性别",
                "ChGId":"所属频道GID",
                "ChName":"所属频道名称",
"pstcount":"发帖数量",
                "pst":
                [
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"}
                ]
           }，
           {
                "usergid":"用户GID",
                "name":"姓名",
                "descript":"个人简介",
                "Title":"社会任职",
                "avatars":"头像链接",
                "hospitalname":"所属医院名称",
                "localname":"驻地名称",
                "jobtitle":"职称",
                "wx":"微信号",
                "wb":"微博号",
                "QQ":"QQ",
                "sex":"性别",
                "ChGId":"所属频道GID",
                "ChGId":"所属频道名称",
                "pstcount":"发帖数量",
                "pst":
                [
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"},
                    {"pstGid":"发帖GID","pstdetail":"发帖内容"}
                ]
           }
        ]
    }



</code>
</pre>
