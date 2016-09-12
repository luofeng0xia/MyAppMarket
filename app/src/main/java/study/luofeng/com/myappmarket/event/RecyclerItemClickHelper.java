package study.luofeng.com.myappmarket.event;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * RecyclerItemClickHelper
 * Created by weixi on 2016/9/11.
 */
public class RecyclerItemClickHelper {

    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private Class viewHolderClass;
    private int startPos;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(recyclerView, recyclerView.getChildAdapterPosition(view)-startPos, view);
            }
            /*if (onItemDetailClickListener != null&&itemPos!=-1) {
                onItemDetailClickListener.onItemDetailClick(itemPos,view);
            }*/
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener != null) {
                return onItemLongClickListener.onItemLongClick(recyclerView, recyclerView.getChildAdapterPosition(view)-startPos, view);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    // 传入的view就是itemView
                    if (viewHolderClass == null ) { //一种item
                        if (onItemClickListener != null) {
                            view.setOnClickListener(onClickListener);
                        }
                        if (onItemLongClickListener != null) {
                            view.setOnLongClickListener(onLongClickListener);
                        }
                    }else { // 多种item
                        RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                        // 跟传入的item的holder一样，才设置监听事件
                        boolean b = (viewHolderClass == holder.getClass());
//                        Log.d("*****",b+"");
//                        Log.d("viewHolderClass",viewHolderClass.toString());
//                        Log.d("getChildViewHolder",recyclerView.getChildViewHolder(view).getClass().toString());
                        if (b){
                            if (onItemClickListener != null) {
                                view.setOnClickListener(onClickListener);
                            }
                            if (onItemLongClickListener != null) {
                                view.setOnLongClickListener(onLongClickListener);
                            }
                        }
                    }

                    /*if (onItemDetailClickListener != null) {
                        itemPos = recyclerView.getChildViewHolder(view).getAdapterPosition();
                        for (View clickView : clickViews) {
                            clickViews[0].setOnClickListener(onClickListener);
                        }
                    }*/
                }

                @Override
                public void onChildViewDetachedFromWindow(View view) {

                }
            };

    private RecyclerItemClickHelper(RecyclerView recyclerView, Class viewHolderClass,int startPos) {
        this.recyclerView = recyclerView;
        this.viewHolderClass = viewHolderClass;
        this.recyclerView.setTag(recyclerView.getId(), this);
        this.startPos=startPos;
        recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }

    /**
     * 多item类型
     * @param recyclerView recyclerView
     * @param viewHolderClass 要设置itemClick的view的Holder类型
     * @param startPos 开始的位置
     * @return RecyclerItemClickHelper
     */
    public static RecyclerItemClickHelper addOn(RecyclerView recyclerView, Class viewHolderClass,int startPos) {
        RecyclerItemClickHelper recyclerItemClickHelper = (RecyclerItemClickHelper) recyclerView.getTag(recyclerView.getId());
        if (recyclerItemClickHelper == null) {
            return new RecyclerItemClickHelper(recyclerView, viewHolderClass,startPos);
        }
        return recyclerItemClickHelper;
    }

    /**
     * 单item类型
     * @param recyclerView recyclerView
     * @return RecyclerItemClickHelper
     */
    public static RecyclerItemClickHelper addOn(RecyclerView recyclerView) {
        return addOn(recyclerView,null,0);
    }

    public static RecyclerItemClickHelper removeFrom(RecyclerView recyclerView) {
        RecyclerItemClickHelper recyclerItemClickHelper = (RecyclerItemClickHelper) recyclerView.getTag(recyclerView.getId());
        if (recyclerItemClickHelper != null) {
            recyclerItemClickHelper.detach(recyclerView);
        }
        return recyclerItemClickHelper;
    }

    private void detach(RecyclerView recyclerView) {
        this.recyclerView.setTag(recyclerView.getId(), null);
        recyclerView.removeOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /*public void setOnItemDetailClickListener(OnItemDetailClickListener onItemDetailClickListener, View[] itemDetailViews) {
        this.onItemDetailClickListener = onItemDetailClickListener;
        clickViews = itemDetailViews;
    }*/

    public interface OnItemClickListener {
        void onItemClick(RecyclerView recyclerView, int position, View itemView);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(RecyclerView recyclerView, int position, View itemView);
    }

    /*public interface OnItemDetailClickListener {
        void onItemDetailClick(int position, View view);
    }*/
}
