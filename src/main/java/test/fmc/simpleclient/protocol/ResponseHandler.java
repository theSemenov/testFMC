package test.fmc.simpleclient.protocol;

public interface ResponseHandler<T> {
	T processResponse(Object o) throws Throwable;
	void onSuccess(T object);
	void onFault(Throwable t);

}
