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

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                onItemClickListener.onItemClick(recyclerView, holder.getAdapterPosition(), view);
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
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                return onItemLongClickListener.onItemLongClick(recyclerView, holder.getAdapterPosition(), view);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener onChildAttachStateChangeListener =
            new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(View view) {
                    // 传入的view就是itemView
                    if (onItemClickListener != null) {
                        view.setOnClickListener(onClickListener);
                    }
                    if (onItemLongClickListener != null) {
                        view.setOnLongClickListener(onLongClickListener);
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

    private RecyclerItemClickHelper(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.recyclerView.setTag(recyclerView.getId(), this);
        recyclerView.addOnChildAttachStateChangeListener(onChildAttachStateChangeListener);
    }

    public static RecyclerItemClickHelper addOn(RecyclerView recyclerView) {
        RecyclerItemClickHelper recyclerItemClickHelper = (RecyclerItemClickHelper) recyclerView.getTag(recyclerView.getId());
        if (recyclerItemClickHelper == null) {
            return new RecyclerItemClickHelper(recyclerView);
        }
        return recyclerItemClickHelper;
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
