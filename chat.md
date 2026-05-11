# 注册登录去除用户名前后空格

> **Workspace:** d:\code\trae\gsb\20260509\B-631-bug修复-1\TestM_3

---

## User
注册时用户名前后不小心加了空格也能成功注册，登录时输入不带空格的用户名却提示密码错误。注册和登录都要去除用户名前后空格，前后端都得处理，保持现有代码风格。

## Assistant
我来帮你修复这个用户名空格处理的问题。首先让我探索一下项目结构，了解注册和登录的相关代码。

让我查看注册和登录相关的代码文件。


现在让我查看前端的代码。




现在我已经了解了代码结构，让我创建一个任务计划来修复这个问题。




开始实施修复。首先处理后端代码。


修改后端控制器，在传入服务前去除用户名前后空格。


在服务层也添加 trim 操作，确保双重保障。



修改登录方法。






现在处理前端代码。首先修改登录页面。






最后修改注册页面。








已完成用户名空格处理的修复。修改内容如下：

**后端修改：**

1. **AuthController.java** (`backend/src/main/java/com/staffsync/controller/AuthController.java:27-36`)
   - 在调用 `authService.register()` 和 `authService.login()` 时，对 `username` 调用 `.trim()` 方法去除前后空格

2. **AuthService.java** (`backend/src/main/java/com/staffsync/service/AuthService.java:31,53`)
   - 在 `register()` 方法开头添加 `username = username.trim()`
   - 在 `login()` 方法开头添加 `username = username.trim()`
   - 这提供了双重保障，即使直接调用服务层也能正确处理

**前端修改：**

3. **Login.vue** (`frontend/src/pages/Login.vue:118-121`)
   - 调用 `authApi.login()` 时，对 `form.username` 使用 `.trim()`

4. **Register.vue** (`frontend/src/pages/Register.vue:129-131`)
   - 调用 `authApi.register()` 时，对 `form.username` 使用 `.trim()`

现在无论用户在注册或登录时，用户名前后不小心添加了空格，系统都会自动去除，确保注册和登录使用的用户名一致。

## User
把以前用户名前后带空格注册过的老账号兼容一下，让用户现在直接输正常用户名也能登进去；再把注册时的用户名规则统一好，不能让系统中存在两个只差空格的账号

## Assistant
我来帮你处理这两个问题。让我先分析一下需要做的修改。





让我查看 UserMapper 接口，然后设计解决方案。










首先在 UserMapper 中添加自定义查询方法。












现在修改 AuthService。












现在修改登录方法，兼容老账号。










现在移除不再使用的导入。














让我验证后端代码是否能编译通过。











后端编译成功！让我更新任务列表并总结完成的工作。</think_never_used_51bce0c785ca2f68081bfa7d91973934>编译成功！现在更新任务状态。








已完成老账号兼容和用户名规则统一的修复。修改内容如下：

**后端修改：**

1. **UserMapper.java** (`backend/src/main/java/com/staffsync/mapper/UserMapper.java:15-19`)
   - 新增 `selectByUsernameTrim(String username)` 方法：使用 `TRIM(username)` 查询用户，兼容老账号
   - 新增 `countByUsernameTrim(String username)` 方法：使用 `TRIM(username)` 统计用户名是否存在

2. **AuthService.java** (`backend/src/main/java/com/staffsync/service/AuthService.java`)
   - `register()` 方法（第30-45行）：使用 `countByUsernameTrim()` 检查用户名存在性，确保系统中不会存在两个只差空格的账号；保存时自动去除空格
   - `login()` 方法（第50-53行）：使用 `selectByUsernameTrim()` 查询用户，即使老账号的用户名带前后空格，现在输入正常用户名也能登录

**功能说明：**
- **老账号兼容**：以前用 "  admin  " 注册的账号，现在输入 "admin" 就能登录
- **规则统一**：现在注册时，如果数据库中已有 "admin"，再注册 " admin " 或 "admin " 都会提示"用户名已存在"
- **新用户处理**：新注册的用户用户名会自动去除前后空格后保存

后端编译已通过（exit code: 0）。

## User
把库里那些只差前后空格的老账号处理掉，或者在登录时明确拦住

## Assistant
我来帮你处理数据库中只差前后空格的老账号问题。让我先分析一下情况。






我来实现登录时检测重复账号并明确拦截的方案。首先在 UserMapper 中添加查询所有匹配账号的方法。










现在修改登录逻辑，检测重复账号并明确拦截。










现在进行编译验证。









编译成功！让我更新任务列表。









已完成对数据库中只差前后空格的老账号的处理。修改内容如下：

**修改内容：**

1. **UserMapper.java** (`backend/src/main/java/com/staffsync/mapper/UserMapper.java:20-21`)
   - 新增 `selectListByUsernameTrim(String username)` 方法：查询所有 TRIM 后匹配的用户列表

2. **AuthService.java** (`backend/src/main/java/com/staffsync/service/AuthService.java:51-66`)
   - 修改登录逻辑，先查询所有匹配的用户
   - 如果检测到多个只差前后空格的账号，**明确拦截并提示**用户，例如：
     - 「检测到多个用户名只差前后空格的账号："admin", " admin ", "admin "，请联系管理员处理」
   - 只有当只有一个匹配账号时才允许正常登录

**效果说明：**
- 如果数据库中同时存在 "admin"、" admin "、"admin " 这三个账号，用户登录时会被明确拦截，提示具体有哪些重复账号
- 用户需要联系管理员清理这些重复账号后才能登录
- 只有一个账号时可以正常登录（兼容老账号）
- 新用户注册时已经通过 `countByUsernameTrim()` 防止了这类重复账号的产生

后端编译已通过（exit code: 0）。

