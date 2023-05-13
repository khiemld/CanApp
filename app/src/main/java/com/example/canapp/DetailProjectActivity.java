package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.adapter.ItemAdapter;
import com.example.canapp.api.PlanApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.project.ProjectResponse;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.type.Type;
import com.example.canapp.model.user.User;
import com.woxthebox.draglistview.BoardView;
import com.woxthebox.draglistview.ColumnProperties;
import com.woxthebox.draglistview.DragItem;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProjectActivity extends AppCompatActivity {

    static InputMethodManager imm;
    BoardView mBoardView;
    ImageView projectMoreInfo;
    List<Type> types = new ArrayList<>();
    List<Task> tasks = new ArrayList<>();
    List<List<Task>> detailTasks = new ArrayList<>();
    List<ArrayList<Pair<Long, String>>> dataLists = new ArrayList<>();

    TextView cancelButton;
    TextView projectName, projectOwner;

    // Tổng số cột hiện có (phải trừ đi 1 do có 1 cột ảo ở cuối)
    private static int mColumns;

    private static int sCreatedItems = 0;
    final String TAG = "DetailProjectActivity";

    static boolean isLastColumnOrigin = true;

    // Biến kiểm tra cột cuối có phải từ trạng thái thêm -> bình thường hong
    static int isLastColumnChanged = -1;

    final static int ORIGIN = -1;
    final static int CANCLED = 0;
    final static int ADDED = 1;

    ProjectInProjectDetail mProject;

    User mManager;

    final String projectID = "645e6570c566c344b3605790";

    PlanApi planApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_project);

        //Khoi tao apiService
        planApi = RetrofitClient.getRetrofit().create(PlanApi.class);
        handleGetPlanDetailInfo();

        initialMapping();

        handleMoreInfo();

        // Lấy các type - ĐANG FAKE
        initialTypes();

        // Lấy các task - ĐANG FAKE
        initialTask();

        // Sắp xếp các type theo index tăng dần
        sortType();

        // Tạo list data để đưa vo apdater
        createDataLists();

        // Setting một chút cho cái Board View (cái bảng mà kéo qua kéo lại á)
        initialBoardView();

        // Thêm các cột dữ liệu dô bảng
        addAllColumn();

        // Thêm cái cột cuối cùng (cái cột chỉ có cái chữ thêm thẻ mới á)
        addFakeColumn();

        handleFakeColumn();
        handleFooter();
    }

    void handleGetPlanDetailInfo() {
        Call<ProjectDetailResponse> call = planApi.getPlanDetail(projectID);
        call.enqueue(new Callback<ProjectDetailResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailResponse> call,
                                   Response<ProjectDetailResponse> response) {
                if (response.isSuccessful()) {

                    mProject = response.body().getPlan();
                    mManager = mProject.getManager().get(0);

                    // Xử lý giao diện khi load thành công
                    projectName.setText(mProject.getName());
                    projectOwner.setText(mManager.getName());

                } else {
                    try {
                        Toast.makeText(DetailProjectActivity.this, response.errorBody().string(),
                                Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(DetailProjectActivity.this, t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleMoreInfo() {
        projectMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý nhấn dô cái nút ba chấm nè
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void addFakeColumn() {

        final ArrayList<Pair<Long, String>> mItemArray = new ArrayList<>();

        final ItemAdapter listAdapter =
                new ItemAdapter(mItemArray, R.layout.task, R.id.task_item, true);

        final View header = View.inflate(getApplicationContext(), R.layout.column_fake, null);

        final View alterHeader =
                View.inflate(getApplicationContext(), R.layout.column_add_category, null);
        alterHeader.setVisibility(View.GONE);

        LinearLayoutManager
                layoutManager = new LinearLayoutManager(getApplicationContext());

        @SuppressLint("ResourceAsColor") ColumnProperties columnProperties =
                ColumnProperties.Builder.newBuilder(listAdapter).setLayoutManager(layoutManager)
                        .setHasFixedItemSize(false)
                        .setHeader(header)
                        .setColumnDragView(header)
                        .build();

        mBoardView.addColumn(columnProperties);

        // Set cái background của column thành màu trắng
        // Lấy cái layout ra
        LinearLayout columnContain =
                (LinearLayout) mBoardView.getRecyclerView(mColumns).getParent();

        // Set màu trắng
        columnContain.setBackgroundColor(R.color.white);

        // Cái này khá vô dụng
        columnContain.setBackgroundResource(R.drawable.background_fake_column);

        //Set chế độ hoà trộn để ra đúng màu trắng mà mình thêm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            columnContain.setBackgroundTintBlendMode(BlendMode.SRC);
        }

        // Set tint color để nó pha màu
        columnContain.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.white, getTheme())));

        // Set cái cột cho nó vừa đủ content
        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) columnContain.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;


           /* if (mColumns == types.size() - 1) {
                params.setMarginEnd(16);
            }*/
/*            columnContain.setScaleX(0.5f);
            columnContain.setScaleY(0.5f);*/
        columnContain.setLayoutParams(params);
        columnContain.setPadding(0, 16, 0, 16);

        // Tăng biến đếm số cột lên
        mColumns++;
    }


    void sortType() {
        // Sắp xếp các type theo thứ tự index
        types.sort((t1, t2) -> -t2.getIndex() + t1.getIndex());
        sortTaskList();
    }

    void sortTaskList() {
        detailTasks.clear();
        for (Type type : types) {
            List<Task> mTask = new ArrayList<>();
            for (Task task : tasks) {
                if (task.getTypeId() == type.getId()) {
                    Task newTask = new Task();
                    newTask = task;
                    mTask.add(newTask);
                }
            }
            detailTasks.add(mTask);
        }
        for (List<Task> i : detailTasks) {
            i.sort((t1, t2) -> -t2.getIndex() + t1.getIndex());
        }
    }

    void createDataLists() {
        dataLists.clear();
        for (List<Task> taskList : detailTasks) {
            ArrayList<Pair<Long, String>> mItemArray = new ArrayList<>();
            for (Task task : taskList) {
                long id = sCreatedItems++;
                mItemArray.add(new Pair<>(id, task.getName()));
            }
            dataLists.add(mItemArray);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addAllColumn() {
        // Với mỗi type sẽ là một cột nên sẽ chạy hết các type
        for (Type type : types) {
            addColumn(type);
        }
    }

    @SuppressLint("ResourceAsColor")
    private void addColumn(Type type) {
        ArrayList<Pair<Long, String>> mItemArray = dataLists.get(type.getIndex());

        final ItemAdapter listAdapter =
                new ItemAdapter(mItemArray, R.layout.task, R.id.task_item, true);

        final View header = View.inflate(getApplicationContext(), R.layout.column_header, null);
        ((TextView) header.findViewById(R.id.text)).setText(type.getName());

        // Xử lý khi click vào header của mấy cái list
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity.this, "Đang click header " +
                                ((TextView) header.findViewById(R.id.text)).getText().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        final View footer = View.inflate(getApplicationContext(), R.layout.column_footer, null);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        @SuppressLint("ResourceAsColor") ColumnProperties columnProperties =
                ColumnProperties.Builder.newBuilder(listAdapter).setLayoutManager(layoutManager)
                        .setHasFixedItemSize(false)
                        .setHeader(header)
                        .setFooter(footer)
                        .setColumnDragView(header)
                        .build();
        /* mBoardView.addColumn(columnProperties);*/
        mBoardView.addColumn(columnProperties);

        // Đổi cái background của column
        LinearLayout columnContain =
                (LinearLayout) mBoardView.getRecyclerView(mColumns).getParent();

        // Set màu trắng
        columnContain.setBackgroundColor(R.color.white);

        columnContain.setBackgroundResource(R.drawable.background_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            columnContain.setBackgroundTintBlendMode(BlendMode.SRC);
        }

        columnContain.setBackgroundTintList(
                ColorStateList.valueOf(getResources().getColor(R.color.white, getTheme())));

        LinearLayout.LayoutParams params =
                (LinearLayout.LayoutParams) columnContain.getLayoutParams();

        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
           /* if (mColumns == types.size() - 1) {
                params.setMarginEnd(16);
            }*/
/*            columnContain.setScaleX(0.5f);
            columnContain.setScaleY(0.5f);*/
        columnContain.setLayoutParams(params);
        columnContain.setPadding(0, 16, 0, 16);

        // Tăng biến đếm số cột
        mColumns++;
    }

    private static class MyDragItem extends DragItem {

        MyDragItem(Context context, int layoutId) {
            super(context, layoutId);
        }

        /*Được sử dụng để tùy chỉnh giao diện của view khi nó được kéo thả (dragged) trên màn hình.*/
        @Override
        public void onBindDragView(View clickedView, View dragView) {
            /*
            clickedView: View gốc được nhấn để bắt đầu quá trình kéo thả.
            dragView: View được tạo ra để đại diện cho view gốc trong quá trình kéo thả.
            */
            CharSequence text = ((TextView) clickedView.findViewById(R.id.taskName)).getText();
            ((TextView) dragView.findViewById(R.id.taskName)).setText(text);
            ConstraintLayout dragCard = dragView.findViewById(R.id.task_item);
            ConstraintLayout clickedCard = clickedView.findViewById(R.id.task_item);

            /*Độ nâng theo chiều Z khi mà move item*/
            /*dragCard.setMaxCardElevation(40);
            dragCard.setCardElevation(clickedCard.getCardElevation());*/

            /*Hiển thị foreground (cái khung) khi move item*/
            // I know the dragView is a FrameLayout and that is why I can use setForeground below api level 23
            /*dragCard.setForeground(
                    clickedView.getResources().getDrawable(R.drawable.card_view_drag_foreground));*/

            /*Animation mà nó nghiêng cái item khi move*/
            dragCard.animate().rotation(5).start();
        }

        /*Này chưa hiểu lắm nhưng mà là kiểu tính kích thước của cái item khi move á*/
        @Override
        public void onMeasureDragView(View clickedView, View dragView) {

            /*
            clickedView: View gốc được nhấn để bắt đầu quá trình kéo thả.
            dragView: View được tạo ra để đại diện cho view gốc trong quá trình kéo thả.
            */

            ConstraintLayout dragCard = dragView.findViewById(R.id.task_item);
            ConstraintLayout clickedCard = clickedView.findViewById(R.id.task_item);
            int widthDiff = dragCard.getPaddingLeft() - clickedCard.getPaddingLeft() +
                    dragCard.getPaddingRight() - clickedCard.getPaddingRight();
            int heightDiff = dragCard.getPaddingTop() - clickedCard.getPaddingTop() +
                    dragCard.getPaddingBottom() - clickedCard.getPaddingBottom();
            int width = clickedView.getMeasuredWidth() + widthDiff;
            int height = clickedView.getMeasuredHeight() + heightDiff;
            dragView.setLayoutParams(new FrameLayout.LayoutParams(width, height));

            int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
            int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            dragView.measure(widthSpec, heightSpec);
        }

        /*Animation khi chuyển từ đứng im -> move*/
        @Override
        public void onStartDragAnimation(View dragView) {
            /*ConstraintLayout dragCard = dragView.findViewById(R.id.task_item);
            ObjectAnimator anim =
                    ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(),
                            40);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(ANIMATION_DURATION);
            anim.start();*/
        }

        /*Animation khi chuyển từ move -> đứng im*/
        @Override
        public void onEndDragAnimation(View dragView) {
            /*ConstraintLayout dragCard = dragView.findViewById(R.id.task_item);
            ObjectAnimator anim =
                    ObjectAnimator.ofFloat(dragCard, "CardElevation", dragCard.getCardElevation(),
                            6);
            anim.setInterpolator(new DecelerateInterpolator());
            anim.setDuration(ANIMATION_DURATION);
            anim.start();*/
        }
    }

    void initialMapping() {
        mBoardView = findViewById(R.id.mainBoardView);
        projectMoreInfo = findViewById(R.id.projectMoreInfo);
        cancelButton = findViewById(R.id.tv_cancelCreateCate);
        projectName = findViewById(R.id.projectName);
        projectOwner = findViewById(R.id.projectOwner);
    }

    void initialTypes() {
        types.add(new Type(1, "todo", 0));
        types.add(new Type(2, "doing", 1));
        types.add(new Type(3, "done", 2));
        /*mColumns = types.size();*/
    }

    void initialTask() {
        tasks.add(new Task(1, 1, 0, "Task task task 1"));
        tasks.add(new Task(1, 2, 1, "Task task task 2"));
        tasks.add(new Task(1, 3, 2, "Task task task 3"));
        tasks.add(new Task(2, 4, 0, "Task task task 4"));
        tasks.add(new Task(2, 5, 1, "Task task task 5"));
        tasks.add(new Task(3, 6, 0, "Task task task 6"));
        tasks.add(new Task(3, 7, 1, "Task task task 7"));
        tasks.add(new Task(3, 8, 2, "Task task task 8"));
    }

    void initialBoardView() {

        mBoardView.setBoardEdge(32);
        mBoardView.setSnapToColumnsWhenScrolling(true);
        mBoardView.setSnapToColumnWhenDragging(true);
        mBoardView.setSnapDragItemToTouch(true);
        mBoardView.setSnapToColumnInLandscape(false);
        mBoardView.setColumnSnapPosition(BoardView.ColumnSnapPosition.CENTER);
        mBoardView.setBoardListener(new BoardView.BoardListener() {
            @Override
            public void onItemDragStarted(int column, int row) {
                //Toast.makeText(getContext(), "Start - column: " + column + " row: " + row, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemDragEnded(int fromColumn, int fromRow, int toColumn, int toRow) {
                /*if (fromColumn != toColumn || fromRow != toRow) {
                    //Toast.makeText(getContext(), "End - column: " + toColumn + " row: " + toRow, Toast.LENGTH_SHORT).show();
                }*/

                /*Đổi trong cùng một cột*/
                if (fromColumn == toColumn) {
                    List<Task> currentColumn = detailTasks.get(fromColumn);
                    Task currentTask = detailTasks.get(fromColumn).get(fromRow);
                    if (fromRow > toRow) {
                        for (int i = toRow; i < fromRow; i++) {
                            detailTasks.get(fromColumn).get(i).setIndex(i + 1);
                        }

                    } else if (fromRow < toRow) {
                        for (int i = fromRow + 1; i <= toRow; i++) {
                            detailTasks.get(fromColumn).get(i).setIndex(i - 1);
                        }
                    } else {

                    }
                    for (Task iTask : tasks) {
                        if (iTask.getId() == currentTask.getId()) {
                            iTask.setIndex(toRow);
                            break;
                        }
                    }
                }

                /*Đổi khác cột*/
                else {
                    for (Task task : detailTasks.get(toColumn)) {
                        if (task.getIndex() < toRow) {
                            continue;
                        }
                        task.setIndex(task.getIndex() + 1);
                    }
                    for (Task task : detailTasks.get(fromColumn)) {
                        if (task.getIndex() <= fromRow) {
                            continue;
                        }
                        task.setIndex(task.getIndex() - 1);
                    }
                    Log.e(TAG,
                            "Số lượng item cột xuất phát: " + detailTasks.get(fromColumn).size());

                    /*detailTasks.get(toColumn).add(task);
                    detailTasks.get(fromColumn).remove(fromRow);*/

                    Task currentTask =
                            detailTasks.get(fromColumn).get(fromRow);
                    currentTask.setIndex(toRow);
                    currentTask.setTypeId(types.get(toColumn).getId());
                    int removeIndex = -1;
                    for (Task iTask : tasks) {
                        if (iTask.getId() == currentTask.getId()) {
                            removeIndex = tasks.indexOf(iTask);
                            break;
                        }
                    }
                    tasks.remove(removeIndex);
                    tasks.add(currentTask);
                }
/*                detailTasks.get(toColumn).sort((t1, t2) -> -t2.getIndex() + t1.getIndex());
                detailTasks.get(fromColumn)
                        .sort((t1, t2) -> -t2.getIndex() + t1.getIndex());*/

                sortTaskList();
                createDataLists();
                for (int i = 0; i < detailTasks.size(); i++) {
                    mBoardView.getAdapter(i).setItemList(dataLists.get(i));
                    mBoardView.getAdapter(i).notifyDataSetChanged();
                }
                for (Type iType :
                        types) {
                    Log.e(TAG, "Type: name " + iType.getName() + ", index " + iType.getIndex() +
                            ", id " + iType.getId());
                }

                for (List<Task> iList : detailTasks) {
                    String taskName = "";
                    for (Task iTask :
                            iList) {
                        taskName = taskName + iTask.getName() + ", ";
                    }
                    Log.e(TAG, "Task: " + taskName);
                }
                Log.e(TAG, String.valueOf("Di chuyển từ cột " + fromColumn + ", hàng " + fromRow +
                        " tới cột " + toColumn + ", hàng " + toRow));
            }

            @Override
            public void onItemChangedPosition(int oldColumn, int oldRow, int newColumn,
                                              int newRow) {
                //Toast.makeText(mBoardView.getContext(), "Position changed - column: " + newColumn + " row: " + newRow, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onItemChangedColumn(int oldColumn, int newColumn) {
               /* TextView itemCount1 =
                        mBoardView.getHeaderView(oldColumn).findViewById(R.id.item_count);
                itemCount1.setText(String.valueOf(mBoardView.getAdapter(oldColumn).getItemCount()));
                TextView itemCount2 =
                        mBoardView.getHeaderView(newColumn).findViewById(R.id.item_count);
                itemCount2.setText(String.valueOf(mBoardView.getAdapter(newColumn).getItemCount()));*/
            }

            @Override
            public void onFocusedColumnChanged(int oldColumn, int newColumn) {
                //Toast.makeText(getContext(), "Focused column changed from " + oldColumn + " to " + newColumn, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragStarted(int position) {
                //Toast.makeText(getContext(), "Column drag started from " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragChangedPosition(int oldPosition, int newPosition) {
                //Toast.makeText(getContext(), "Column changed from " + oldPosition + " to " + newPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onColumnDragEnded(int fromPosition, int toPosition) {
                //Toast.makeText(getContext(), "Column drag ended at " + position, Toast.LENGTH_SHORT).show();

                if (fromPosition > toPosition) {
                    for (Type type : types) {
                        if (type.getIndex() < toPosition || type.getIndex() >= fromPosition) {
                            continue;
                        }
                        type.setIndex(type.getIndex() + 1);
                    }
                } else {
                    for (Type type : types) {
                        if (type.getIndex() <= fromPosition || type.getIndex() > toPosition) {
                            continue;
                        }
                        type.setIndex(type.getIndex() - 1);
                    }
                }
                types.get(fromPosition).setIndex(toPosition);
                sortType();
                for (int i = 0; i < mColumns - 1; i++) {
                    setAlterFooter(i);
                }

                for (Type iType :
                        types) {
                    Log.e(TAG, "Type: name " + iType.getName() + ", index " + iType.getIndex() +
                            ", id " + iType.getId());
                }
                for (List<Task> iList : detailTasks) {
                    String taskName = "";
                    for (Task iTask :
                            iList) {
                        taskName = taskName + iTask.getName() + ", ";
                    }
                    Log.e(TAG, "Task: " + taskName);
                }
            }
        });
        mBoardView.setBoardCallback(new BoardView.BoardCallback() {
            // Xử lý không cho move cột cuối cùng - cột ảo
            @Override
            public boolean canDragItemAtPosition(int column, int dragPosition) {
                // Add logic here to prevent an item to be dragged
                if (column == mColumns - 1) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean canDropItemAtPosition(int oldColumn, int oldRow, int newColumn,
                                                 int newRow) {
                // Add logic here to prevent an item to be dropped
                if (newColumn == mColumns - 1) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean canDragColumnAtPosition(int index) {
                // Add logic here to prevent a column to be dragged
                if (index == mColumns - 1) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean canDropColumnAtPosition(int oldIndex, int newIndex) {
                // Add logic here to prevent a column to be dropped
                if (newIndex == mColumns - 1 || oldIndex == mColumns - 1) {
                    return false;
                }
                return true;
            }
        });
        mBoardView.setCustomDragItem(new MyDragItem(getApplicationContext(), R.layout.task));
    }

    void handleFakeColumn() {

        View header = mBoardView.getHeaderView(mColumns - 1);

        final View alterHeader =
                View.inflate(getApplicationContext(), R.layout.column_add_category, null);
        alterHeader.setVisibility(View.GONE);

        LinearLayout parent = (LinearLayout) header.getParent();
        parent.addView(alterHeader);
        // Gắn animation nhìn cho nó đẹp :))
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(250);
        parent.setLayoutTransition(layoutTransition);


        EditText editText = (EditText) alterHeader.findViewById(R.id.edt_categoryName);

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alterHeader.setVisibility(View.VISIBLE);
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, 0);
                editText.requestFocus();
                header.setVisibility(View.GONE);
                isLastColumnOrigin = false;
            }
        });

        TextView cancelButton =
                (TextView) alterHeader.findViewById(R.id.tv_cancelCreateCate);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                header.setVisibility(View.VISIBLE);
                alterHeader.setVisibility(View.GONE);
                isLastColumnOrigin = true;
                isLastColumnChanged = CANCLED;
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(header.getWindowToken(),
                        0);
                parent.requestFocus();
            }
        });

        TextView addCateButton = (TextView) alterHeader.findViewById(R.id.tv_createCate);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = charSequence.toString().trim();
                if (!isTypeNameValid(name)) {
                    ((TextView) alterHeader.findViewById(R.id.tv_cateNameError)).setVisibility(
                            View.VISIBLE);
                } else {
                    ((TextView) alterHeader.findViewById(R.id.tv_cateNameError)).setVisibility(
                            View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        addCateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý khi tạo một Cate mới
                Type type =
                        new Type(mColumns, editText.getText().toString().trim(), mColumns - 1);
                Log.e(TAG, "Thêm mới Type: index " + type.getIndex() + ", id " + type.getId());

                if (((TextView) alterHeader.findViewById(
                        R.id.tv_cateNameError)).getVisibility() ==
                        View.INVISIBLE && !editText.getText().toString().trim().isEmpty()) {
                    // Thêm một loại mới
                    types.add(type);
                    sortType();

                    for (Type iType :
                            types) {
                        Log.e(TAG, "Type: name " + iType.getName() + ", index " + iType.getIndex() +
                                ", id " + iType.getId());
                    }

                    createDataLists();
                    for (List<Task> iList : detailTasks) {
                        String taskName = "";
                        for (Task iTask :
                                iList) {
                            taskName = taskName + iTask.getName() + ", ";
                        }
                        Log.e(TAG, "Task: " + taskName);
                    }

                    /*header.setVisibility(View.VISIBLE);
                    alterHeader.setVisibility(View.GONE);*/

                    // Thêm cột mới nhập
                    addColumn(type);

                    // Thêm fake column cữ
                    addFakeColumn();

                    isLastColumnOrigin = true;
                    isLastColumnChanged = ADDED;

                    // Focus dô cái list mới tạo
                    View newHeader = mBoardView.getHeaderView(mColumns - 2);
                    ((View) newHeader.getParent()).requestFocus();

                    // Xoá fake column cũ
                    mBoardView.removeColumn(mColumns - 3);

                    // Giảm số đếm số cột
                    mColumns--;

                    // Thiết lập footer cho cột mới
                    setAlterFooter(mColumns - 2);

                    // Thiết lập fake column
                    handleFakeColumn();
                } else {
                    Toast.makeText(DetailProjectActivity.this, "Tên hông được trùng hoặc trống!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean isTypeNameValid(String name) {
        for (Type type : types) {
            if (type.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    void setAlterFooter(int index) {
        View footer = mBoardView.getFooterView(index);

        final View alterFooter =
                View.inflate(getApplicationContext(), R.layout.footer_add_task, null);
        alterFooter.setVisibility(View.GONE);

        LinearLayout parent = (LinearLayout) footer.getParent();

        if (parent.getChildCount() == 4) {
            parent.removeViewAt(3);
            footer.setVisibility(View.VISIBLE);
        }
        parent.addView(alterFooter);

        // Gắn animation nhìn cho nó đẹp :))
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(250);
        parent.setLayoutTransition(layoutTransition);

        EditText editText = (EditText) alterFooter.findViewById(R.id.edt_taskName);

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ẩn cái footer hiện tại đi
                footer.setVisibility(View.GONE);
                alterFooter.setVisibility(View.VISIBLE);
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText,
                        0);
                editText.requestFocus();

                Log.e(TAG, "Số child trong cột vừa huỷ: " + parent.getChildCount());
            }
        });

        TextView cancelTask = alterFooter.findViewById(R.id.tv_cancelCreateTask);
        cancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "nhấn nút huỷ tạo task");
                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(),
                        0);
                alterFooter.setVisibility(View.GONE);
                footer.setVisibility(View.VISIBLE);
            }
        });

        TextView createTaskButton = alterFooter.findViewById(R.id.tv_createTask);
        int finalIndex = index;
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().trim().isEmpty()) {
                    Type currentType = types.get(mBoardView.getColumnOfFooter(footer));
                    Log.e(TAG,
                            "Tạo task ("
                                    + editText.getText().toString().trim()
                                    + ") tại cột id " +
                                    currentType.getId());

                    Task newTask = new Task(currentType.getId(),
                            tasks.size() + 1,
                            detailTasks.get(currentType.getIndex()).size(),
                            editText.getText().toString().trim());
                    tasks.add(newTask);
                    sortTaskList();
                    createDataLists();
                    Pair<Long, String> newItem =
                            new Pair<>((long) sCreatedItems++, newTask.getName());
                    mBoardView.getAdapter(currentType.getIndex())
                            .setItemList(dataLists.get(finalIndex));
                    /*mBoardView.getAdapter(currentType.getIndex()).notifyDataSetChanged();*/
                    for (int i = 0; i < detailTasks.size(); i++) {
                        mBoardView.getAdapter(i).notifyDataSetChanged();
                    }
                    editText.setText("");
                    imm.hideSoftInputFromWindow(editText.getWindowToken(),
                            0);
                    alterFooter.setVisibility(View.GONE);
                    footer.setVisibility(View.VISIBLE);


                    String taskName = "";
                    for (Task iTask :
                            detailTasks.get(currentType.getIndex())) {
                        taskName = taskName + iTask.getName() + ", ";
                    }
                    Log.e(TAG, "Task: " + taskName);


                } else {
                    Toast.makeText(DetailProjectActivity.this, "Tên trống!!", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    void setAllAlterFooter() {
        for (int i = 0; i < mColumns - 1; i++) {
            setAlterFooter(i);
        }
    }

    void setAllFooterClick() {
        for (int i = 0; i < mColumns - 1; i++) {
            addFooterClickAtColumn(i);
        }
    }

    void addFooterClickAtColumn(int index) {
        View footer = mBoardView.getFooterView(index);
        View header = mBoardView.getHeaderView(index);
        LinearLayout parent = (LinearLayout) footer.getParent();
        View alterFooter = parent.getChildAt(parent.getChildCount() - 1);


        // Xử lý click footer
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailProjectActivity.this, "Đang click footer  " +
                                ((TextView) header.findViewById(R.id.text)).getText().toString(),
                        Toast.LENGTH_SHORT).show();

                // Ẩn cái footer hiện tại đi
                footer.setVisibility(View.GONE);

                LinearLayout parent = (LinearLayout) footer.getParent();

                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                /*imm.showSoftInput(((EditText) alterFooter.findViewById(R.id.edt_taskName)),
                        InputMethodManager.SHOW_IMPLICIT);
                ((EditText) alterFooter.findViewById(R.id.edt_taskName)).requestFocus();*/
                // Gắn animation nhìn cho nó đẹp :))
                LayoutTransition layoutTransition = new LayoutTransition();
                layoutTransition.setDuration(250);
                parent.setLayoutTransition(layoutTransition);

                parent.getChildAt(parent.getChildCount() - 1).setVisibility(View.VISIBLE);
            }
        });
    }

    void handleFooter() {
        for (int index = 0; index < mColumns - 1; index++) {
            setAlterFooter(index);
        }
    }
}

