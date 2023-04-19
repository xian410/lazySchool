# lazySchool
##河北工业大学-移动应用开发大实验-校园跑腿app易校园

使用操作：
    1.android文件通过android studio打开，java文件通过idea打开
    2.通过.sql文件生成本地数据库
    3.将java中yml的数据库地址改为本地数据库地址
    将 \java\src\main\java\com\example\demo\common\AutoMapper中的地址改为本地数据库地址
    将\android\app\src\main\java\com\example\lazysch\utils\ApiUrls中的
    服务器地址的localhost改为本主机ip（因为androidstudio是用虚拟机运行的，所以localhost没有用）
    bilibili视频链接：https://www.bilibili.com/video/BV1AY411r7Ps/?spm_id_from=333.999.0.0
    
项目简介：本项目为技术栈为android+springboot+mysql，实现了登录注册，发布订单，骑手接单，消息提醒等功能，引入了高德地图sdk，使用有问题者可以改为正常输入，不过仍有许多接口功能没有实现，有意者可以下下来摆弄一下，欢迎提issue。
注：地图功能暂时失效，改为普通输入