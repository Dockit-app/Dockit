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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onDoubleClick(View view, int position);

        void onLongItemClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector gestureDetector;

    public RecyclerViewClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                final ViewGroup childViewGroup = (ViewGroup) recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (childViewGroup != null && mListener != null) {
                    final List<View> viewHierarchy = new ArrayList<View>();
                    getViewHierarchyUnderChild(childViewGroup, e.getRawX(), e.getRawY(), viewHierarchy);

                    View touchedView = childViewGroup;
                    if (viewHierarchy.size() > 0) {
                        touchedView = viewHierarchy.get(0);
                    }
                    mListener.onItemClick(touchedView, recyclerView.getChildAdapterPosition(childViewGroup));
                    return true;
                }
                return true;
            }


            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    mListener.onDoubleClick(child, recyclerView.getChildAdapterPosition(child));
                }
                return true;
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
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
