# Project documentation

## Some variable/parameter names

Database name: `is3106weddingplanner`

JDBC Connection Pool name: `is3106WeddingPlannerJpa`

- Extract from existing connection parameter: `jdbc:mysql://localhost:3306/is3106weddingplanner?zeroDateTimeBehavior=CONVERT_TO_NULL`
- Datasource classname: `com.mysql.cj.jdbc.MysqlDataSource`
- username: `root` password: `password`


## Session Beans info

#### WeddingChecklistBean

This bean handles the CRUD of WeddingTask in the context of a WeddingChecklist entity owned by a WeddingOrganiser.

This also includes the "checking off / ticking" of a particular task.

The expected effect of "checking off" a task is that it will also check off (or uncheck) all of its subtasks (and all of the subtasks' subtasks, etc.).

**TBD stuff/plan**

- have an attribute in the bean representing the current WeddingChecklist

- and a method to set the current WeddingCheckList

- WeddingChecklistBean is to be used by a main WeddingProjectActionsBean??