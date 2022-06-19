使用Mybatis-plus的步骤
1.新建springboot工程
2.指定maven的mp坐标
    指定数据库驱动
3.创建实体类，定义属性，指定主键类型
4.创建dao接口，继承baseMapper<实体.class>
5.在springboot的启动类上，加入@MapperScan扫描Dao包
6.测试类