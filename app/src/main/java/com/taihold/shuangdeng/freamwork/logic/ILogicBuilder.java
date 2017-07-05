package com.taihold.shuangdeng.freamwork.logic;

import android.os.Handler;

/**
 * LogicBuilder的接口<BR>
 * 
 * @author 牛凡
 */
public interface ILogicBuilder
{

	/**
	 * 通过Logic接口返回对应的Logic对象
	 * 
	 * @param interfaceClass
	 *            Logic接口Class
	 * @return
	 */
	public ILogic getLogicByInterface(Class<?> interfaceClass);

	/**
	 * 对缓存中的所有logic对象增加hander<BR>
	 * 对缓存中的所有logic对象增加hander，在该UI的onCreated时被框架执行，
	 * 如果该logic对象里执行了sendMessage方法，则所有的活动的UI对象接收到通知。
	 * 
	 * @param handler
	 *            UI的handler对象
	 */
	public void addHandlerToAllLogic(Handler handler);

	/**
	 * 对缓存中的所有logic对象移除hander对象<BR>
	 * 在该UI的onDestory时被框架执行，如果该logic对象 执行了sendMessage方法，则所有的UI接收到通知
	 * 
	 * @param handler
	 *            UI的handler对象
	 */
	public void removeHandlerFromAllLogic(Handler handler);

}
