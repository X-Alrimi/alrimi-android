package com.example.capstone2.feature.notification

import android.content.Context
import android.graphics.Canvas
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlin.math.max
import kotlin.math.min

/***
 * ItemTouchHelper is a utility class to add swipe to dismiss and drag & drop support to RecyclerView.
 * It works with a RecyclerView and a Callback class, which configures what type of interactions are enabled and also receives events when user performs these actions. (From. 공식 문서)
 */
class SwipeHelperCallback : ItemTouchHelper.Callback() {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f  // View를 swipe시 버튼이 보이도록 고정하는 변수

    // 이동 방향을 결정하는 함수
    override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
    ): Int {
        // drag는 사용하지 않으므로 0, swipe는 왼쪽 방향
        return makeMovementFlags(0, LEFT or RIGHT)
    }

    // Drag 될 때 호출되는 함수
    override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // Swipe 될 때 호출되는 함수
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f
        getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.adapterPosition
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    /***
     * ItemTouchHelper는 일정 범위 밖이나 일정 속도 이상으로 swipe가 될 시 View 밖으로 escape 되도록 구현되어있는데 getSwipeEscapeVelocity와 getSwipeThreshold를 override하여 escape를 막는다.
     */
    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 10
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val isClamped = getTag(viewHolder)
        // 현재 View가 고정되어 있지 않고 사용자가 -clamp 이상 swipe시 isClamped를 true로 변경
        setTag(viewHolder, !isClamped && currentDx <= -clamp)
        return 2f
    }

    override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
    ) {
        if(actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            currentDx = x
            getDefaultUIUtil().onDraw(
                    c,
                    recyclerView,
                    view,
                    x,
                    dY,
                    actionState,
                    isCurrentlyActive
            )
        }
    }

    // swipe 범위 관련
    private fun clampViewPositionHorizontal(
            view: View,
            dX: Float,
            isClamped: Boolean,
            isCurrentlyActive: Boolean
    ) : Float {
        // View의 가로 길이의 절반까지만 swipe 되도록
        val min: Float = -view.width.toFloat()/2
        // RIGHT 방향으로 swipe 막기
        val max: Float = 0f

        val x = if (isClamped) {
            // View가 고정되었을 때 swipe되는 영역 제한
            if (isCurrentlyActive) dX - clamp else -clamp
        } else {
            dX
        }

        return min(max(min, x), max)
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        // isClamped를 view의 tag로 관리
        viewHolder.itemView.tag = isClamped
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder) : Boolean {
        // isClamped를 view의 tag로 관리
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    // 전체 뷰가 아닌 push_item_container 부분만 swipe 될 수 있도록, push_item_container를 가져오는 함수.
    private fun getView(viewHolder: RecyclerView.ViewHolder) : View {
        return (viewHolder as NotificationRecyclerAdapter.NotificationViewHolder).itemView.notification_item_container
    }

    fun setClamp(clamp: Float) {
        this.clamp = clamp
    }

    // 다른 View가 swipe 되거나 터치되면 고정 해제
    fun removePreviousClamp(recyclerView: RecyclerView) {
        if (currentPosition == previousPosition)
            return
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).translationX = 0f
            setTag(viewHolder, false)
            previousPosition = null
        }
    }

    // 기기마다 같은 비율을 배정해주는 dp값을 px값으로 바꿔주는 함수
    fun dpToPx(context : Context, dp : Float) : Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    }
}