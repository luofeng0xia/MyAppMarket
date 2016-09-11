package study.luofeng.com.myappmarket.event;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import study.luofeng.com.myappmarket.global.MyApplication;

/**
 * MyOnItemTouchListener
 * Created by weixi on 2016/9/11.
 */
public class MyOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private final GestureDetectorCompat compat;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        boolean onItemLongClick(View view, int position);
    }

    public MyOnItemTouchListener(RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
        compat = new GestureDetectorCompat(MyApplication.context, new SimpleGestureListener());
    }


    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        compat.onTouchEvent(e);
        return false;
    }

    class SimpleGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (child != null && onItemClickListener != null) {
                onItemClickListener.onItemLongClick(child,
                        recyclerView.getChildAdapterPosition(child));
            }
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (child != null && onItemClickListener != null) {
                onItemClickListener.onItemClick(child, recyclerView.getChildAdapterPosition(child));
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }
}
