# 注册登录去除用户名前后空格

> **Workspace:** d:\code\trae\gsb\20260509\B-631-bug修复-1\TestM_4

---

## User
注册时用户名前后不小心加了空格也能成功注册，登录时输入不带空格的用户名却提示密码错误。注册和登录都要去除用户名前后空格，前后端都得处理，保持现有代码风格。

## Assistant
我来帮你修复这个问题。首先让我探索一下代码库，找到注册和登录相关的代码。

让我查看后端和前端的认证相关代码：


现在让我查看前端的登录和注册页面：



现在我已经了解了问题所在。让我创建一个任务列表来修复这个问题：




开始修复。首先处理后端代码：






后端修复完成。现在处理前端代码：







让我验证修改后的代码：



让我检查后端代码是否能正常编译：






后端编译成功。让我也检查前端代码：








已完成修复！以下是修改内容：

## 后端修改 (AuthService.java)

在 `register` 和 `login` 方法中都添加了 `username = username.trim()` 处理，确保即使用户名前后有空格，也会在查询和保存时被去除。

- **register 方法** (第31行)：在查询用户名是否存在和创建用户前，先 trim 用户名
- **login 方法** (第52行)：在查询用户前，先 trim 用户名

## 前端修改

### Login.vue (第118-121行)
修改了 `handleLogin` 方法，在调用登录 API 时对用户名进行 trim 处理：
```javascript
const res = await authApi.login({
  username: form.username.trim(),
  password: form.password
})
```

### Register.vue (第129-132行)
修改了 `handleRegister` 方法，在调用注册 API 时对用户名进行 trim 处理：
```javascript
const res = await authApi.register({
  username: form.username.trim(),
  password: form.password
})
```

## 效果
- 注册时：即使用户输入 "  admin  "，实际保存的是 "admin"
- 登录时：用户输入 "admin"、" admin "、"admin " 等都能正确匹配到用户 "admin"

这样就解决了前后端不一致的问题，保持了现有代码风格。

## User
把用户名前后的空格先处理掉，再去做注册和登录判断

## Assistant
我来检查一下当前的代码，确保在所有判断之前都先处理了用户名的空格。让我看看前端的注册页面，确保在密码验证之前也处理了空格：





让我优化一下，在函数最开始就处理用户名的空格，确保所有逻辑都使用处理后的值：








同样修改 Login.vue，保持代码风格一致：







已完成优化！现在在注册和登录函数的最开始就处理了用户名的空格：

## Register.vue (第118-121行)
```javascript
const handleRegister = async () => {
  if (loading.value) return

  const username = form.username.trim()  // 首先处理空格
  // ...后续逻辑都使用处理后的 username
}
```

## Login.vue (第113-116行)
```javascript
const handleLogin = async () => {
  if (loading.value) return

  const username = form.username.trim()  // 首先处理空格
  // ...后续逻辑都使用处理后的 username
}
```

这样确保了：
1. **前端**：在函数最开始就去除空格，所有后续逻辑（包括发送请求）都使用处理后的值
2. **后端**：在 `register` 和 `login` 方法入口处也做了 `trim()`，形成双重保障

即使前端被绕过，后端也会自动去除用户名前后的空格再进行查询和保存。

## User
把后端注册和登录收到用户名后的第一步统一改成先去空格，再把注册页提示和后端长度校验都按去掉空格后的用户名来算，保证页面提交和直接调接口时结果一致

## Assistant
让我检查一下后端的校验逻辑和前端注册页面的长度校验：






现在我明白了问题所在。后端的 `@Size` 校验和前端的 `minlength/maxlength` 都是在 trim 之前执行的，导致校验不一致。让我修改代码，确保 trim 后再校验：








先修改后端 AuthService，在 trim 后添加长度校验：





现在修改前端 Register.vue，在提交前先 trim 用户名再校验长度：







同样更新 Login.vue，保持代码风格一致：






现在验证代码是否能正常编译/构建：








前端构建失败是因为没有安装依赖，不是代码问题。让我先安装依赖：








已完成所有修改！现在前后端都统一在第一步处理用户名空格，并且按去掉空格后的长度做校验。

## 后端修改 (AuthService.java)

**register 方法** (第30-38行)：
```java
public Result<Void> register(String username, String password) {
    username = username.trim();  // 第一步先去空格

    if (username.length() < 3 || username.length() > 20) {  // 按trim后的长度校验
        return Result.error("用户名长度应在3-20之间");
    }
    if (password.length() < 6 || password.length() > 20) {
        return Result.error("密码长度应在6-20之间");
    }
    // ...后续逻辑
}
```

**login 方法** (第51-52行)：
```java
public Result<Map<String, Object>> login(String username, String password) {
    username = username.trim();  // 第一步先去空格
    // ...后续逻辑
}
```

## 前端修改

**Register.vue** (第118-142行)：
```javascript
const handleRegister = async () => {
  if (loading.value) return

  const username = form.username.trim()  // 第一步先去空格
  const password = form.password

  if (username.length < 3 || username.length > 20) {  // 按trim后的长度校验
    toastStore.error('用户名长度应在3-20之间')
    return
  }
  // ...其他校验
}
```

**Login.vue** (第113-123行)：
```javascript
const handleLogin = async () => {
  if (loading.value) return

  const username = form.username.trim()  // 第一步先去空格
  const password = form.password
  // ...后续逻辑
}
```

## 效果
- 无论用户在用户名前后加多少空格，都会先去除再做判断
- 页面提交和直接调用接口的校验结果完全一致
- 用户名长度校验按去掉空格后的字符数计算

