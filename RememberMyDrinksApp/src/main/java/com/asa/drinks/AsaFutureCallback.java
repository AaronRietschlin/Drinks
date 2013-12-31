package com.asa.drinks;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.async.future.FutureCallback;

/**
 * An abstract class that implements {@link FutureCallback} from Ion. This is
 * only a convenience and doesn't have to be used. It handles logging, as well
 * as providing useful methods for more clarification. Any errors will be
 * reported to either {@link #onError()} or {@link #onException(Exception)}.
 * 
 * @param <T>
 *            The object that is returned by Ion. This will usually be
 *            {@link JsonObject} (from gson) and must extend {@link JsonElement}
 *            .
 * @param <S>
 *            The type of object that will be returned by
 *            {@link #serialize(JsonElement, Class)}. This will be the
 *            serialized object from gson.
 */
public abstract class AsaFutureCallback<T extends JsonElement, S> implements FutureCallback<T> {
	public static final String TAG = "AsaFutureCallback";

	protected String mUrl;
	private Gson mGson;

	public AsaFutureCallback(String url) {
		mUrl = url;
		mGson = new Gson();
	}

	/**
	 * Serializes the T param to the S param. Uses Gson to do so. Catches
	 * {@link JsonSyntaxException} and will call to
	 * {@link #onException(Exception)} if thrown.
	 * 
	 * @param result
	 * @param clzz
	 * @return
	 */
	protected S serialize(T result, Class<S> clzz) {
		try {
			return mGson.fromJson(result, clzz);
		} catch (JsonSyntaxException e) {
			onException(e);
		}
		return null;
	}

	@Override
	public void onCompleted(Exception e, T result) {
		if (e != null) {
			Crashlytics.log(Log.ERROR, TAG, "URL requested:" + mUrl);
			onException(e);
			return;
		}
		if (result == null) {
			Crashlytics.log(Log.ERROR, TAG, "Ion returned result was null.");
			onError();
			return;
		}
		try {
			onSuccess(result);
		} catch (Exception e1) {
			Crashlytics.log(Log.ERROR, TAG, "Exception occurred in onSuccess of Ion. URL requested:" + mUrl);
			onException(e1);
		}

	}

	/**
	 * If any sort of error occurs that doesn't have an exception (the result is
	 * null for some reason), then this will be called.
	 */
	public abstract void onError();

	/**
	 * If an exception occurs, this will be called. The exception WILL NOT be
	 * logged before calling this method.
	 * 
	 * @param e
	 */
	public abstract void onException(Exception e);

	/**
	 * If everything goes well, this will be called. If an exception occurs in
	 * this method, it will be caught and thrown into
	 * {@link #onException(Exception)}.
	 * 
	 * @param result
	 */
	public abstract void onSuccess(T result);

}
