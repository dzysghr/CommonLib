package com.dzy.commemlib.mvp;

/**
 * Created by dengziyue on 2017/8/31.
 */

public interface IView<T extends IPresenter> {
    
   void setPresenter(T presenter);
    
}
