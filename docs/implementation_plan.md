# Staff-Sync UI 重构与体验优化计划

## 目标
根据用户反馈，对 UI 进行重新设计，实现本地化，并替换原生交互组件。

## 变更详情

### 1. 国际化与文案
- **Login.vue**: 将页脚 "All rights reserved" 修改为 "版权所有"。

### 2. 交互优化 (Modal vs Native Confirm)
- **[NEW] ConfirmModal.vue**: 创建一个通用的确认模态框组件，用于替代 `window.confirm`。
- **Dashboard.vue**: 集成 `ConfirmModal`，在删除员工时使用自定义模态框。

### 3. 视觉升级 (Modern Style)
- **Logo 设计**: 移除 Emoji 图标，使用 CSS 实现现代几何/排版风格的 Logo。
- **顶部导航**: 移除图标，使用纯文本 + 简约 Hover 效果。
- **Login.vue & Dashboard.vue**: 应用新的 Logo 设计。

## 文件变更清单

### [NEW] [ConfirmModal.vue](file:///Users/jack.yan/Downloads/labeleases/staff-sync/frontend/src/components/ConfirmModal.vue)
- 通用确认对话框
- 支持标题、内容、确认/取消回调
- 动画效果与 `EmployeeForm` 保持一致

### [MODIFY] [Login.vue](file:///Users/jack.yan/Downloads/labeleases/staff-sync/frontend/src/pages/Login.vue)
- 修改版权文案
- 重构 Logo 区域

### [MODIFY] [Dashboard.vue](file:///Users/jack.yan/Downloads/labeleases/staff-sync/frontend/src/pages/Dashboard.vue)
- 顶部导航去图标化
- 重构 Logo 区域
- 引入并使用 `ConfirmModal` 替换 `window.confirm`

## 验证计划
1.  **UI 检查**: 确认 Logo 样式、顶部导航样式符合现代简洁风格。
2.  **文案检查**: 登录页页脚显示中文版权。
3.  **交互测试**: 点击删除按钮，应弹出自定义模态框，而非原生 Alert。确认/取消功能正常。

---

## UI 修复计划 (Toast & Validation)

### 目标
修复错误提示被模态框遮挡的问题，并美化提示组件界面，同时加强表单输入的健壮性。

### 变更详情

#### [MODIFY] [Toast.vue](file:///Users/jack.yan/Downloads/labeleases/staff-sync/frontend/src/components/Toast.vue)
- **Z-Index 提升**: 将 `z-index` 从 50 提升至 100，确保显示在 Modal 之上。
- **样式重构**:
    - 位置调整为顶部居中 (Top-Center)。
    - 采用毛玻璃拟态效果 (Backdrop blur)。
    - 圆角胶囊样式，增加阴影。
    - 优化图标和排版。

#### [MODIFY] [EmployeeForm.vue](file:///Users/jack.yan/Downloads/labeleases/staff-sync/frontend/src/components/EmployeeForm.vue)
- **输入验证**:
    - 为姓名和手机号输入框添加 `.trim` 修饰符，自动过滤首尾空格。
    - 确保所有校验提示均为中文。

### 验证计划
1. 打开新增员工弹窗，不输入内容直接保存，验证 Toast 是否悬浮在弹窗之上。
2. 验证 Toast 样式是否美观（居中、毛玻璃）。
3. 输入带空格的内容，验证是否自动去除空格且校验通过。
