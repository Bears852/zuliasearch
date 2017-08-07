package io.zulia.client.pool;

import com.google.common.util.concurrent.ListenableFuture;
import io.zulia.client.config.LumongoPoolConfig;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LumongoBaseWorkPool extends WorkPool {

	private LumongoPool lumongoPool;

	private static AtomicInteger counter = new AtomicInteger(0);

	public LumongoBaseWorkPool(LumongoPoolConfig lumongoPoolConfig) throws Exception {
		this(new LumongoPool(lumongoPoolConfig),
				lumongoPoolConfig.getPoolName() != null ? lumongoPoolConfig.getPoolName() : "lumongoPool-" + counter.getAndIncrement());
	}

	public LumongoBaseWorkPool(LumongoPool lumongoPool) {
		this(lumongoPool, "lumongoPool-" + counter.getAndIncrement());
	}

	public LumongoBaseWorkPool(LumongoPool lumongoPool, String poolName) {
		super(lumongoPool.getMaxConnections(), lumongoPool.getMaxConnections() * 10, poolName);
		this.lumongoPool = lumongoPool;
	}

	public <R extends Result> ListenableFuture<R> executeAsync(Command<R> command) {
		CallableCommand<R> callableCommand = new CallableCommand<>(lumongoPool, command);
		return executeAsync(callableCommand);
	}

	public <R extends Result> R execute(Command<R> command) throws Exception {
		CallableCommand<R> callableCommand = new CallableCommand<>(lumongoPool, command);
		return execute(callableCommand);
	}

	public void updateMembers(List<LMMember> members) throws Exception {
		lumongoPool.updateMembers(members);
	}

	@Override
	public void shutdown() throws Exception {
		super.shutdown();
		lumongoPool.close();
	}
}
