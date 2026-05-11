# 注册登录去除用户名前后空格

> **Workspace:** d:\code\trae\gsb\20260509\B-631-bug修复-1\TestM_2

---

## User
注册时用户名前后不小心加了空格也能成功注册，登录时输入不带空格的用户名却提示密码错误。注册和登录都要去除用户名前后空格，前后端都得处理，保持现有代码风格。

## Assistant
让我检查一下相关的注册和登录文件——包括后端和前端：

我现在对代码库有了清晰的了解。让我来实施修复。

**问题**：注册时用户名前后空格会被原样存入数据库，登录时不带空格就找不到用户，提示密码错误。

**修复方案**：
- **后端 DTO**：自定义 `setUsername` 方法自动 trim
- **前端**：提交时对 `form.username` 做 trim

后端 DTO 使用了 Lombok `@Data`，会自动生成 getter/setter。为了在 setter 中添加 trim，需要手动覆盖 setter 方法。

后端已完成。现在修改前端：

前端修改完成。现在验证修改结果：

没有任何诊断错误。让我最终确认一下所有修改后的文件内容：

修复完成，以下是修改总结：

## 问题原因

用户注册时输入 `" admin "` (带前后空格)，空格被原样存入数据库。登录时输入 `admin` (不带空格)，数据库中查不到 `" admin "` 这个用户，导致提示"用户名或密码错误"。

## 修改内容

### 后端（2个文件）

**`LoginRequest.java`** (`:18-20`) — 手动覆盖 Lombok `@Data` 生成的 `setUsername`，在 setter 中对 username 做 `trim()`：
```java
public void setUsername(String username) {
    this.username = username != null ? username.trim() : null;
}
```

**`RegisterRequest.java`** (`:21-23`) — 同样手动覆盖 `setUsername`，确保注册时存入数据库的用户名没有前后空格：
```java
public void setUsername(String username) {
    this.username = username != null ? username.trim() : null;
}
```

> 选择在 setter 层面处理而非 Controller/Service 层，是因为 Jackson 反序列化 JSON 时会调用 setter，这样无论请求从哪里进来（包括未来新增的接口），username 都会被自动 trim，且 `@NotBlank` 和 `@Size` 校验也是在 setter 之后执行的，校验的是 trim 后的值，逻辑更合理。

### 前端（2个文件）

**`Login.vue`** (`:118-121`) — 提交登录时对 username 调用 `.trim()`：
```js
const res = await authApi.login({
  username: form.username.trim(),
  password: form.password
})
```

**`Register.vue`** (`:129-132`) — 提交注册时对 username 调用 `.trim()`：
```js
const res = await authApi.register({
  username: form.username.trim(),
  password: form.password
})
```

前后端双重处理，确保即使绕过前端直接调 API，后端也能正确去除空格。

