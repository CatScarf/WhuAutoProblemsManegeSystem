# WhuAutoProblemsManegeSystem
WHU自动试题管理系统

主要功能

- 从word文档里批量提取试题

- 使用数据库存储数据

- 方便的题库编辑

- 批量处理时支持拖拽快速选择

使用须知

- 运行前需要在根目录下自行创建/application-sql.yml，配置自己的数据库url，用户名和密码
- 数据库格式如下：

数据库：
problems
questions
subjects
tbl_user
types
view_problems
view_questions

problems:
id	int	NO	PRI		auto_increment
subjectid	int	NO			
stemtext	mediumtext	YES			

questions:
id	int	NO	PRI		auto_increment
problemid	int	NO			
typeid	int	NO			
innerorder	int	NO		0	
text	mediumtext	YES			
answer	mediumtext	YES			
options	int	NO			
optiona	varchar(255)	YES			
optionb	varchar(255)	YES			
optionc	varchar(255)	YES			
optiond	varchar(255)	YES			
optione	varchar(255)	YES			
optionf	varchar(255)	YES			
optiong	varchar(255)	YES			
subjectid	int	YES			

subjects:
id	int	NO	PRI		auto_increment
subject	varchar(255)	NO			

tbl_user:
id	int	NO	PRI		auto_increment
username	varchar(255)	NO			
password	varchar(255)	NO			
role	varchar(255)	YES			

types:
id	int	NO	PRI		auto_increment
type	varchar(255)	NO			

view_problems:
id	int	NO		0	
subject	varchar(255)	YES			
brief	varchar(30)	YES			

view_questions:
id	int	NO		0	
innerorder	int	NO		0	
problemid	int	NO			
stembrief	varchar(30)	YES			
subject	varchar(255)	YES			
type	varchar(255)	YES			
textbrief	varchar(100)	YES			
answerbrief	varchar(100)	YES			
options	int	NO			

