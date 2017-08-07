package com.dzy.commemlib.ui.BaseAdapter;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 *
 * Created by dzysg on 2016/5/29 0029.
 */
public  class ContentHolder extends BaseHolder implements View.OnClickListener, View.OnLongClickListener
{
    SparseArray<View> mViews = new SparseArray<>();

    CommonAdapter.OnItemClickListener mOnItemClickListener;
    CommonAdapter.OnItemLongClickListener mOnItemLongClickListener;

    public ContentHolder(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    public void setOnItemClickListener(CommonAdapter.OnItemClickListener onItemClickListener)
    {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(CommonAdapter.OnItemLongClickListener onItemLongClickListener)
    {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public <T extends View> T getView(@IdRes int id)
    {
        View v = mViews.get(id);
        if (v==null)
        {
            v = this.itemView.findViewById(id);
            if (v==null)
                throw new IllegalArgumentException("ResId not found in HolderView");
            mViews.put(id,v);
        }
        return (T)v;
    }

    public BaseHolder setText(@IdRes int id, String content)
    {
        TextView tv = getView(id);
        tv.setText(content);
        return this;
    }

    public BaseHolder setImageResource(@IdRes int id, @DrawableRes int res)
    {
        ImageView iv = getView(id);
        iv.setImageResource(res);
        return this;
    }

    public BaseHolder setImageBitmap(@IdRes int id, Bitmap map)
    {
        ImageView iv = getView(id);
        iv.setImageBitmap(map);
        return this;
    }

    @Override
    public void onClick(View v)
    {
        if (mOnItemClickListener!=null)
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
    }

    @Override
    public boolean onLongClick(View v)
    {
        if (mOnItemLongClickListener!=null)
            return mOnItemLongClickListener.onItemLongClick(v,getAdapterPosition());
        return false;
    }
}
