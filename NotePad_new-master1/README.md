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

## 数据库设计
Notes表

  | 列名      | 数据类型     | 备注          |
| ----------- | ------------ | ------------- |
| `_ID`| INTEGER      | 主键          |
| `TITLE` | TEXT      | 标题          |
| `NOTE`  | TEXT      | 笔记内容      |
| `CREATE_DATE` | INTEGER | 创建日期  |
| `MODIFICATION_DATE` | INTEGER | 修改日期  |

Classify表

|列名	| 类型	| 说明 |
| ----- | ----- | ------ |
|`_ID`	| INTEGER	|主键 |
| `name`	| TEXT	| 分类名称 |



## 目录结构

![image](https://github.com/user-attachments/assets/ad301588-9c89-4dae-9e36-cad9968cae64)  
![image](https://github.com/user-attachments/assets/986f5e39-a961-42b9-b9d7-f4397fb34f28)


## 功能实现
### 基础功能
 * 时间戳的实现
  
  ![image](https://github.com/user-attachments/assets/56db50d8-0ee0-4dac-b911-0fb10bd9a02b)  
  代码部分：
  在notelist_item.xml中添加一个TextView来显示时间戳  
 
            <TextView
                android:id="@+id/timestamp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@android:id/text1"
                android:layout_marginTop="8dp"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:layout_alignParentEnd="true"
                android:text="2024-11-20 14:00"
                android:textSize="14sp"
                android:textColor="#757575" />
 然后在NoteList添加modifytime的字段  

 ![image](https://github.com/user-attachments/assets/96e73f0d-ef21-43f7-a30d-e79463330a26)  
 在NoteList补全oncreate函数  

 ![image](https://github.com/user-attachments/assets/cec84a93-69e1-40fd-ad3f-f1bb99674a06)   
 在notePadprivated中修改这一部分  
 
 ![image](https://github.com/user-attachments/assets/17a8189c-e497-462d-b814-59eff032501b)  
 在再NoteEditor中修改  

 ![image](https://github.com/user-attachments/assets/de5777a0-acf8-4f12-8eec-cc51d5a47735)


   * 笔记搜索功能

 ![image](https://github.com/user-attachments/assets/fc19503a-54cd-4fcd-b23b-f1f81bb12fb0)  
 代码部分：在list_options_menu.xml，添加一个搜索的item，搜索图标用安卓自带的图标，设为总是显示：  
 <item
    android:id="@+id/menu_search"
    android:title="@string/menu_search"
    android:icon="@android:drawable/ic_search_category_default"
    android:showAsAction="always">
</item>
 
 在layout中添加note_search_list.xml  

 <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="16dp"
    android:background="#f5f5f5">

    <!-- 搜索框 -->
    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:iconifiedByDefault="false"
        android:queryHint="输入搜索内容..."
        android:background="@drawable/search_view_background"
        android:padding="8dp"
        android:layout_marginBottom="12dp"
        android:textColor="#000000"
        />

    <!-- 列表 -->
    <ListView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:divider="#DDDDDD"
        android:dividerHeight="1dp"
        android:background="@drawable/list_view_background"
        android:layout_marginTop="8dp"
        android:scrollbarStyle="insideOverlay"/>
</LinearLayout>
然后再添加NoteSearch.class文件  

![image](https://github.com/user-attachments/assets/07a92265-f767-4fe3-adb2-a8a1448d9ebd)

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_search_list);
        
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(NotePad.Notes.CONTENT_URI);
        }

        SearchView searchview = (SearchView) findViewById(R.id.search_view);
        // 为查询文本框注册监听器
        searchview.setOnQueryTextListener(NoteSearch.this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String selection = NotePad.Notes.COLUMN_NAME_TITLE + " LIKE ? ";
        String[] selectionArgs = { "%" + newText + "%" };

        Cursor cursor = managedQuery(
                getIntent().getData(),
                PROJECTION,
                selection,
                selectionArgs,
                NotePad.Notes.DEFAULT_SORT_ORDER
        );

        String[] dataColumns = { NotePad.Notes.COLUMN_NAME_TITLE, NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE };
        int[] viewIDs = { android.R.id.text1, R.id.timestamp };

        MyCursorAdapter adapter = new MyCursorAdapter(
                this,
                R.layout.noteslist_item,
                cursor,
                dataColumns,
                viewIDs
        );

        setListAdapter(adapter);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
        String action = getIntent().getAction();

        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
 }  
 最后要在AndroidManifest.xml注册NoteSearch：  
 <!--添加搜索activity-->
    <activity
        android:name="NoteSearch"
        android:label="@string/title_notes_search">
    </activity>  
    
   * Ui的美化
      * 主界面的美化
        
        ![image](https://github.com/user-attachments/assets/8b23a233-5a9f-4c31-87a9-81e1f7e45e8c)
        
        代码：

        <?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:divider="@null"
    android:listSelector="#00000000">

    <!-- 卡片视图 -->
    <androidx.cardview.widget.CardView
        android:id="@+id/cardView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"
        app:cardBackgroundColor="#B0C4DE"
        app:cardCornerRadius="15dp"
        app:cardElevation="10dp">

        <!-- 内部布局 -->
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:padding="16dp">

            <!-- 主标题 -->
            <TextView
                android:id="@android:id/text1"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:layout_marginTop="10dp"
                android:text="笔记标题"
                android:textSize="18sp"
                android:textStyle="bold"
                android:textColor="#212121"
                android:ellipsize="end"
                android:singleLine="true" />

            <!-- 时间戳 -->
            <TextView
                android:id="@+id/timestamp"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_below="@android:id/text1"
                android:layout_marginTop="8dp"
                android:layout_marginStart="10dp"
                android:layout_marginEnd="10dp"
                android:layout_alignParentEnd="true"
                android:text="2024-11-20 14:00"
                android:textSize="14sp"
                android:textColor="#757575" />
        </RelativeLayout>
    </androidx.cardview.widget.CardView>
</LinearLayout>  

编辑界面的美化（添加了标题的输入框）  
![image](https://github.com/user-attachments/assets/0df59313-7b1f-4846-94d1-49a153fdd400)  

![image](https://github.com/user-attachments/assets/9d94591b-b940-4789-b88e-75b2a7183154)  

代码部分：  
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="#F8F8F8">

    <!-- 标题输入框模块 -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:background="@drawable/card_background"
        android:padding="16dp"
        android:layout_margin="16dp"
        android:elevation="4dp">

        <EditText
            android:id="@+id/note_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="请输入标题"
            android:fontFamily="sans-serif-medium"
            android:textSize="18sp"
            android:textColor="#000000"
            android:textColorHint="#888888"
            android:background="@android:color/transparent"
            android:padding="4dp"
            android:singleLine="true" />
    </LinearLayout>

    <!-- 正文输入框模块 -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="474dp"
        android:layout_margin="16dp"
        android:background="@drawable/card_background"
        android:elevation="4dp"
        android:orientation="vertical"
        android:padding="16dp">

        <EditText
            android:id="@+id/note"
            android:layout_width="match_parent"
            android:layout_height="438dp"
            android:background="@android:color/transparent"
            android:fontFamily="sans-serif"
            android:gravity="top"
            android:hint="在这里写你的笔记..."
            android:lineSpacingExtra="6dp"
            android:minHeight="200dp"
            android:padding="4dp"
            android:scrollbars="vertical"
            android:textColor="#333333"
            android:textSize="16sp" />
    </LinearLayout>
</LinearLayout>  
搜索的美化：  

![image](https://github.com/user-attachments/assets/222cff7f-8b04-477c-9a92-2592b25e52ce)  
 * 背景颜色展现
   首先添加一个dialog_color_picker的xml文件
   <?xml version="1.0" encoding="utf-8"?>
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:columnCount="6"
    android:padding="16dp"
    android:background="@android:color/black"
    android:id="@+id/color_grid">

    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#FFFFFF"
        android:layout_margin="8dp"
        android:tag="#FFFFFF"
        android:clickable="true" />

    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#FFFF99"
        android:layout_margin="8dp"
        android:tag="#FFFF99"
        android:clickable="true" />

    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#FFC0CB"
        android:layout_margin="8dp"
        android:tag="#FFC0CB"
        android:clickable="true" />

    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#ADD8E6"
        android:layout_margin="8dp"
        android:tag="#ADD8E6"
        android:clickable="true" />

    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#FFF0F5"
        android:layout_margin="8dp"
        android:tag="#FFF0F5"
        android:clickable="true" />
    <View
        android:layout_width="40dp"
        android:layout_height="50dp"
        android:background="#BDFCC9"
        android:layout_margin="8dp"
        android:tag="#BDFCC9"
        android:clickable="true" />

</GridLayout>  
然后在NoteEditor里添加修改颜色的方法  

![image](https://github.com/user-attachments/assets/052e1d60-db71-4159-8c09-4cd2b52fecea)  



    
 并且添加onOptionsItemSelected中的case语句：  
 ![image](https://github.com/user-attachments/assets/ac0b072b-5c90-4325-a440-5064ddf8f0db)  

 成果展示：  
 ![image](https://github.com/user-attachments/assets/69bd0f6a-1d2b-4b47-9767-e63bcf8cba30)  
 ![image](https://github.com/user-attachments/assets/3064885a-2d12-4feb-84ba-e95328680311)

    

 *文件导出  
 ![image](https://github.com/user-attachments/assets/d8d40d19-6d38-4e2c-ac53-231625454a2a)  
 ![image](https://github.com/user-attachments/assets/95478a29-172f-4e6f-80c7-aa439dc6c1ea)


 
 首先在NoteEditor里添加onOptionsItemSelected中的case语句 ：  

 ![image](https://github.com/user-attachments/assets/052e1d60-db71-4159-8c09-4cd2b52fecea)  
 然后在editor_options_menu.xml添加一个导出笔记的选项  
 
 <item android:id="@+id/menu_output"
    android:title="@string/menu_output" />  
    在NoteEditor中添加函数outputNote()：  
    ![image](https://github.com/user-attachments/assets/33b03510-1b4b-4aa8-9698-301e1821e33b)   
    新建布局output_text.xml   
      ![image](https://github.com/user-attachments/assets/19bbe47c-2f11-499e-83de-fe6830167e64)   
      
 创建OutPutText（）函数：  
 ![image](https://github.com/user-attachments/assets/30418c36-d73e-4271-bcd8-cecbddfcc138)  
 ![image](https://github.com/user-attachments/assets/fb7b6ccf-6d06-4700-bfde-f585336e9723)  
 ![image](https://github.com/user-attachments/assets/fe268695-74cc-4987-baf0-ee0830afe793)



 在AndroidManifest.xml中将这个Acitvity主题定义为对话框样式，并且加入权限：  
 <!--添加导出activity-->
        <activity android:name="OutputText"
            android:label="@string/output_name"
            android:theme="@android:style/Theme.Holo.Dialog"
            android:windowSoftInputMode="stateVisible">
        </activity>  
         <!-- 在SD卡中创建与删除文件权限 -->
    <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
    <!-- 向SD卡写入数据权限 -->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  
  * 笔记分类排序
    
    首先在editor_options_menu.xml中添加：
    ![image](https://github.com/user-attachments/assets/3ff67c81-288f-42a4-a852-e809654b44ec)
    然后在NoteList中的onOptionsItemSelected函数中添加case
    
    case R.id.menu_search:
              Intent intent = new Intent();
              intent.setClass(NotesList.this,NoteSearch.class);
              NotesList.this.startActivity(intent);
              return true;
            // 按创建时间排序

            case R.id.menu_sort1:
                cursor = managedQuery(
                        getIntent().getData(),            // 获取URI
                        PROJECTION,                      // 查询字段
                        null,                            // 没有 WHERE 条件
                        null,                            // 没有参数
                        NotePad.Notes._ID                // 按 ID 排序
                );
                break;

            case R.id.menu_sort2:
                // 按修改时间排序
                cursor = managedQuery(
                        getIntent().getData(),
                        PROJECTION,
                        null,
                        null,
                        NotePad.Notes.DEFAULT_SORT_ORDER // 按修改时间排序
                );
                break;

            default:
            return super.onOptionsItemSelected(item);
        }
        // 更新适配器
        adapter = new MyCursorAdapter(
                this,
                R.layout.noteslist_item, // 适配器布局
                cursor,                  // 更新的cursor
                dataColumns,             // 数据列
                viewIDs                  // 显示列ID
        );
        setListAdapter(adapter);  // 设置新适配器
        return true;
    
    ![image](https://github.com/user-attachments/assets/6d6570e7-6412-472c-a67a-59fb359b5982)
    
    创建时间：
    
    ![image](https://github.com/user-attachments/assets/004713eb-4fc2-4cf4-a143-22c69fcefd48)
    
    修改时间：
    
    ![image](https://github.com/user-attachments/assets/f7ee5462-f7c9-4e32-8617-38fba1135bf4)



  
















   

   





