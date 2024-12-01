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
│   │   │                   ├── ui/
│   │   │                   │   ├── NoteEditor.java
│   │   │                   │   ├── NotesList.java
│   │   │                   │   ├── TitleEditor.java
│   │   │                   │   └── OutputText.java
│   │   │                   ├── database/
│   │   │                   │   ├── NotePadProvider.java
│   │   │                   │   └── NoteSearch.java
│   │   │                   ├── models/
│   │   │                   │   ├── NotePad.java
│   │   │                   │   └── MyCursorAdapter.java
│   │   │                   ├── utils/
│   │   │                   │   └── LinedEditText.java
│   │   │                   └── NotesLiveFolder.java
│   │   ├── res/
│   │   │   ├── drawable/
│   │   │   │   └── app_notes/
│   │   │   │       ├── bg_rounded_border.xml
│   │   │   │       ├── card_background.xml
│   │   │   │       └── classify.png
│   │   │   ├── layout/
│   │   │   │   ├── note_editor.xml
│   │   │   │   ├── note_search_list.xml
│   │   │   │   └── noteslist_item.xml
│   │   │   ├── menu/
│   │   │   ├── values/
│   │   │   │   ├── color.xml
│   │   │   │   └── strings.xml
├── build.gradle
└── README.md



