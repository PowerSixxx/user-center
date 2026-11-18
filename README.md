# User Center 用户中心

基于 React、Ant Design Pro 和 Spring Boot 的前后端分离用户中心，提供用户注册、登录、退出登录、登录状态查询，以及管理员用户查询与逻辑删除接口。

## 技术栈

| 部分 | 技术 |
| --- | --- |
| 前端 | React 17、TypeScript、Umi 3、Ant Design 4、Ant Design Pro |
| 后端 | Spring Boot 2.7.18、MyBatis Plus 3.5.1、Maven |
| 数据库 | MySQL 8 |
| 登录状态 | HTTP Session + Cookie |

## 项目结构

```text
user_center/
├── myapp/                         # 前端
│   ├── config/                    # 路由、开发代理和布局配置
│   ├── public/bw_logo.png         # 网站 Logo
│   └── src/
│       ├── pages/                 # 登录、注册、欢迎和用户管理页面
│       ├── services/              # 接口请求
│       └── plugins/globalRequest.ts
├── user-center/                   # 后端
│   ├── pom.xml
│   ├── sql/create_table.sql       # 用户表结构
│   └── src/main/
│       ├── java/com/mango/usercenter/
│       └── resources/             # 本地数据库配置（自行创建）
└── README.md
```

## 本地运行

需要准备 JDK 17、Maven 3、Node.js、npm 和 MySQL 8。前端 `package.json` 声明 Node.js 最低版本为 12；本项目使用较早的 Umi 3，安装或启动遇到兼容性问题时，请结合具体报错排查。启动脚本已包含 `--openssl-legacy-provider` 参数。

### 1. 下载项目

```bash
git clone https://github.com/PowerSixxx/user-center.git
cd user-center
```

下文命令均以克隆后的仓库根目录为起点；仓库内的 `user-center/` 子目录是后端。

### 2. 准备数据库

确保 MySQL 已运行，在仓库根目录登录：

```bash
mysql -u root -p
```

在 MySQL 中执行：

```sql
CREATE DATABASE IF NOT EXISTS universe DEFAULT CHARACTER SET utf8mb4;
USE universe;
SOURCE user-center/sql/create_table.sql;
```

如果已有 `user` 表，请跳过导入。该 SQL 只创建表，不提供默认账号。

完成后执行 `exit;` 退出 MySQL。

### 3. 配置后端

数据库配置不随仓库上传。新建 `user-center/src/main/resources/application.yml`，填入以下内容，并将密码替换为本机 MySQL 密码：

```yaml
spring:
  application:
    name: user-center
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/universe
    username: root
    password: "YOUR_MYSQL_PASSWORD"
  session:
    timeout: 86400

server:
  port: 8080
  servlet:
    context-path: /api

mybatis-plus:
  configuration:
    map-underscore-to-camel-case: false
  global-config:
    db-config:
      logic-delete-field: isDelete
      logic-delete-value: 1
      logic-not-delete-value: 0
```

### 4. 启动后端

确保 Maven 使用 JDK 17，可以通过 `mvn -v` 检查。

macOS 安装了多个 JDK 时，可先在当前终端执行：

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"
```

然后在仓库根目录执行：

```bash
cd user-center
mvn spring-boot:run
```

看到 `Started UserCenterApplication` 表示启动成功，后端基础地址为 `http://localhost:8080/api`。基础地址本身没有首页，请访问具体接口。

### 5. 启动前端

另开一个终端，从仓库根目录执行：

```bash
cd myapp
npm install
npm run dev
```

打开以下页面：

- 登录：<http://localhost:8000/user/login>
- 注册：<http://localhost:8000/user/register>

`npm run dev` 关闭 Mock，开发代理会将 `/api/` 请求转发至 `http://localhost:8080`。如果前端实际使用其他端口，以终端输出为准。

保持前后端两个终端运行，分别按 `Ctrl+C` 停止服务。

## 账号与权限

先注册账号，再登录。注册规则：

- 账号至少 4 位，不能包含空白或后端校验规则中的特殊字符。
- 密码至少 8 位，两次输入一致。
- 备注 ID（`noteCode`）必填，最多 5 位，不能重复。
- 新注册用户默认为普通用户，`userRole = 0`；管理员为 `userRole = 1`。

如需在本地测试管理员功能，注册后可在 `universe` 数据库中执行以下 SQL，将账号替换为自己的测试账号，然后退出登录并重新登录：

```sql
UPDATE user SET userRole = 1 WHERE userAccount = 'YOUR_TEST_ACCOUNT';
```

管理员页面路径为 `/admin/user-manage`。后端会对用户搜索、删除接口检查管理员权限。

## 主要接口

| 方法 | 路径 | 用途 |
| --- | --- | --- |
| POST | `/api/user/register` | 注册 |
| POST | `/api/user/login` | 登录 |
| POST | `/api/user/logout` | 退出登录 |
| GET | `/api/user/current` | 查询当前登录用户 |
| GET | `/api/user/search` | 管理员查询用户，可传 `username` |
| POST | `/api/user/delete` | 管理员逻辑删除用户，请求体为用户 ID 数字 |

## 常见问题

- **MySQL 启动提示 `Unable to lock ./ibdata1`**：先检查是否已有 MySQL 进程使用该数据目录，尝试 `mysql -u root -p` 登录；不要删除数据文件。
- **登录、注册时报数据库连接错误**：检查 MySQL 是否运行、`universe.user` 是否存在，以及 `application.yml` 中的账号密码是否正确。后端启动成功不代表数据库读写一定正常。
- **没有用户管理菜单**：检查当前用户是否为管理员，修改角色后需重新登录。
- **Logo 无法显示**：确认 `myapp/public/bw_logo.png` 存在，可直接访问 `/bw_logo.png` 检查。

## 构建说明

前端构建：在 `myapp/` 中执行 `npm run build`，输出到 `dist/`。后端打包：在 `user-center/` 中执行 `mvn package -DskipTests`，输出到 `target/`；该命令跳过测试。

生产部署前需要调整 `myapp/src/plugins/globalRequest.ts` 中的生产接口前缀（目前为 `https://baowenliu.com`），并配置实际数据库和反向代理。开发代理配置只用于本地开发。

## 模板来源

前端基于 Ant Design Pro 模板，保留了部分欢迎页、表格页和多语言示例。前端模板许可证见 [myapp/LICENSE](myapp/LICENSE)。
