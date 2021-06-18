# task

1. 工作流程:
    1. 新增一个流程，生成一个task记录，包含id(UUID),name,中期次数mid_times; 此时process_id = "start",同时，在tbl_task_mid中添加mid_times条记录；
    2. process_id = "start"：新增的同时需要填写立项报告，生成一个task_start记录，包含id(UUID),task_id(task.id), task_name(task.name)
       ,task__main_person等信息,此时tbl_task中的process_id = "start_examine"
    3. process_id = "start_examine"立项报告提交后，部门负责人需要填写部门意见，填写examine_*相关信息，如果通过，则process_id = "start_approval"
       ,进入核准流程；否则，process_id = "start"，项目负责人修改立项报告，重新提交；
    4. process_id = "start_approval",相关负责人填写核准意见，填写approval_*相关信息，如果通过，则process_id = "start_ratify"，进入批准流程；否则，process_id
       = "start",项目负责人修改立项报告，重新提交；
    5. process_id = "start_ratify",相关负责人填写批准意见，填写ratify_*相关信息，如果通过，则process_id = "mid_0"，进入中期流程；否则，process_id = "start"
       ,项目负责人修改立项报告，重新提交；如果该项目的mid_times=0,则直接进入项目验收流程；
    6. process_id = "mid_0"，进入中期验收流程1，项目负责人填写相关信息（到期前5天内每天提醒），当时间超过设置的中期验收时间，并且负责人确认提交相关信息后，task_mid_status = "examine"
       ,审核人员填写信息,如果通过,则task_mid_status = "approval".进入核准流程，否则task_mid_status = "start"
       ，项目负责人修改立项报告，重新提交；通过核准后，task_mid_status = "end"，如果有其他中期流程，则进入下一个中期流程，process_id = "mid_1"，当mid = "mid_times"
       时，进入项目验收流程，"process_id" = "check_start"
    7. process_id = "check_start",项目负责人填写相关信息（到期前5天内每天提醒），进入验收流程，process_id = "check_examine"；
    8. process_id = "check_examine"，进入项目验收流程，相关人员填写验收信息，如果通过，则process_id = "check_approval"，进入核准流程，否则process_id = "
       check_start"，项目负责人重新填写验收报告；
    9. process_id = "check_approval"，进入验收核准流程，相关人员填写验收信息，如果通过，则process_id = "end"，项目结束，否则process_id = "check_start"
       ，项目负责人重新填写验收报告；
    10. process_id = "end"，项目结束，不再可以进行任何操作。

2. 权限：

   可以查看task的人：

   (1) 项目负责人

   (2) 项目参与人

   (3) 项目需要验收、批准、核准时，对应的负责人

   (4) 部门负责人以及管理员

3. API接口设计：
    1. (get)api/admin/task/u/{userid}?pagenum=*  //用户查询自己可以查看的task
    2. (get)api/admin/task/{taskid} //查看项目信息、项目进度等
    3. (get)api/admin/task/d/{deptid}?pagenum=*  //部门负责人查询本部门的所有task
    4. (post)api/admin/task/start //项目负责人新增或修改task立项报告以及项目信息
    5. (post)api/admin/task/start/examine //立项时部门负责人填写意见
    6. (post)api/admin/task/start/approval //立项时负责人填写批准意见
    7. (post)api/admin/task/start/ratify //立项时负责人填写核准意见
    8. (post)api/admin/task/mid/start //项目负责人填写中期报告
    9. (post)api/admin/task/mid/examine //负责人中期审核意见
    10. (post)api/admin/task/mid/approval //负责人填写中期核准意见
    11. (post)api/admin/task/check/start //项目负责人填写验收报告
    12. (post)api/admin/task/check/examine //负责人验收审核意见
    13. (post)api/admin/task/check/approval //负责人填写验收核准意见



新建一张表tbl_user_task_permission,在添加项目负责人、参与人、验收人员等时都添加一条新的记录     
可能会重复添加







权限管理：

1. 是否登陆：api/admin/*的所有接口只有登陆用户才能访问；
2. 根据role-permission-link表判断，只有在表内的关系才能访问；


