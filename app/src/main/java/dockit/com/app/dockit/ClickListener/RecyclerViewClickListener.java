package dockit.com.app.dockit.ClickListener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 28/07/18.
 */

public class RecyclerViewClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

        public void onLongItemClick(View view, int position);
    }

    GestureDetector mGestureDetector;

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }
//
//    @Override
//    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//        View childView = rv.findChildViewUnder(e.getX(), e.getY());
//        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//            mListener.onItemClick(childView, rv.getChildAdapterPosition(childView));
//            return true;
//        }
//        return false;
//    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        if(mGestureDetector.onTouchEvent(e)) {
            // Important: x and y are translated coordinates here
            final ViewGroup childViewGroup = (ViewGroup) rv.findChildViewUnder(e.getX(), e.getY());

            if (childViewGroup != null && mListener != null) {
                final List<View> viewHierarchy = new ArrayList<View>();
                // Important: x and y are raw screen coordinates here
                getViewHierarchyUnderChild(childViewGroup, e.getRawX(), e.getRawY(), viewHierarchy);

                View touchedView = childViewGroup;
                if (viewHierarchy.size() > 0) {
                    touchedView = viewHierarchy.get(0);
                }
                mListener.onItemClick(touchedView, rv.getChildAdapterPosition(childViewGroup)); //.getChildPosition(childViewGroup));
                return true;
            }
        }

        return false;
    }

    private void getViewHierarchyUnderChild(ViewGroup root, float x, float y, List<View> viewHierarchy) {
        int[] location = new int[2];
        final int childCount = root.getChildCount();

        for (int i = 0; i < childCount; ++i) {
            final View child = root.getChildAt(i);
            child.getLocationOnScreen(location);
            final int childLeft = location[0], childRight = childLeft + child.getWidth();
            final int childTop = location[1], childBottom = childTop + child.getHeight();

            if (child.isShown() && x >= childLeft && x <= childRight && y >= childTop && y <= childBottom) {
                viewHierarchy.add(0, child);
            }
            if (child instanceof ViewGroup) {
                getViewHierarchyUnderChild((ViewGroup) child, x, y, viewHierarchy);
            }
        }
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
