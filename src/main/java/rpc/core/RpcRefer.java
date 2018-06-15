package rpc.core;

public interface RpcRefer {

	<T> T refer(final Class<T> interfaceClass, final String host, final int port);
}
