# Android 期中实验

## 项目概述

  * 本实验室一个基于NotePad的功能拓展，在原来的基础上添加了时间戳，搜索，美化ui，文件导出，分类等操作。 

## 项目功能

 * 必加功能
   * 时间戳添加
   * 搜索功能（支持模糊搜索）
 * 扩展功能
   * UI的美化
   * 背景颜色的更换
   * 文件导出
   * 笔记分类排序

## 目录结构

app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── android/
│   │   │               └── notepad/
│   │   │                   ├── LinedEditText.java       # 自定义控件：带有行间距的文本框
│   │   │                   ├── MyCursorAdapter.java     # 自定义游标适配器，用于连接数据库和界面
│   │   │                   ├── NoteEditor.java          # 笔记编辑逻辑
│   │   │                   ├── NotePad.java             # 定义全局常量（列名、URI等）
│   │   │                   ├── NotePadProvider.java     # SQLite 数据库访问与内容提供者
│   │   │                   ├── NoteSearch.java          # 实现笔记的搜索功能
│   │   │                   ├── NotesList.java           # 显示所有笔记的主界面
│   │   │                   ├── NotesLiveFolder.java     # 实现动态文件夹，显示实时内容
│   │   │                   ├── OutputText.java          # 将文本输出到另一个界面
│   │   │                   └── TitleEditor.java         # 编辑笔记标题
│   │   ├── res/
│   │   │   ├── color/
│   │   │   │   └── bg_note_item_hover.xml               # 列表项的悬浮背景颜色
│   │   │   ├── drawable/
│   │   │   │   ├── app_notes/
│   │   │   │   │   ├── bg_rounded_border.xml            # 列表项圆角边框
│   │   │   │   │   ├── card_background.xml              # 卡片背景
│   │   │   │   │   └── classify.png                     # 分类图标
│   │   │   │   ├── ic_menu_compose.xml                  # 编辑菜单图标
│   │   │   │   ├── ic_menu_delete.xml                   # 删除菜单图标
│   │   │   │   ├── ic_menu_edit.xml                     # 编辑菜单图标
│   │   │   │   ├── ic_menu_revert.xml                   # 回滚操作图标
│   │   │   │   ├── ic_menu_save.xml                     # 保存图标
│   │   │   │   ├── list_view_background.xml             # 列表背景
│   │   │   │   └── live_folder_notes/
│   │   │   │       ├── rounded_button.xml               # 圆角按钮样式
│   │   │   │       ├── rounded_edittext.xml             # 圆角输入框样式
│   │   │   │       └── search_view_background.xml       # 搜索框背景样式
│   │   │   ├── layout/
│   │   │   │   ├── dialog_color_picker.xml              # 颜色选择对话框布局
│   │   │   │   ├── note_editor.xml                      # 笔记编辑界面布局
│   │   │   │   ├── note_search_list.xml                 # 笔记搜索结果列表布局
│   │   │   │   ├── noteslist_item.xml                   # 笔记列表项布局
│   │   │   │   ├── output_text.xml                      # 文本输出界面布局
│   │   │   │   └── title_editor.xml                     # 标题编辑界面布局
│   │   │   ├── menu/
│   │   │   │   ├── editor_options_menu.xml              # 编辑选项菜单
│   │   │   │   ├── list_context_menu.xml                # 列表上下文菜单
│   │   │   │   └── list_options_menu.xml                # 列表操作菜单
│   │   │   ├── values/
│   │   │   │   ├── color.xml                            # 全局颜色资源
│   │   │   │   └── strings.xml                          # 字符串资源
│   └── build.gradle
└── README.md                                            # 项目说明文档


