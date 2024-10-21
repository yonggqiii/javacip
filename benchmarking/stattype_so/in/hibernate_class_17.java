	public class hibernate_class_17 {

		private static final String SHARD_CFG_0 = "/com/hibshards/config/shard0.hibernate.cfg.xml";
		private static final String SHARD_CFG_1 = "/com/hibshards/config/shard1.hibernate.cfg.xml";
		private static final String SHARDED_TABLE = "com/hibshards/orm/weather.hbm.xml";

		public SessionFactory createSessionFactory() throws NamingException {
			Configuration prototypeConfig = new Configuration().configure(SHARD_CFG_0);
			prototypeConfig.addResource(SHARDED_TABLE);

			List<ShardConfiguration> shardConfigs = new ArrayList<ShardConfiguration>();
			shardConfigs.add(buildShardConfig(SHARD_CFG_0));
			shardConfigs.add(buildShardConfig(SHARD_CFG_1));

			ShardStrategyFactory shardStrategyFactory = buildShardStrategyFactory();
			ShardedConfiguration shardedConfig = new ShardedConfiguration(prototypeConfig, shardConfigs,
					shardStrategyFactory);
			return shardedConfig.buildShardedSessionFactory();
		}

		private ShardStrategyFactory buildShardStrategyFactory() {

			ShardStrategyFactory shardStrategyFactory = new A();
			return shardStrategyFactory;
		}

		private ShardConfiguration buildShardConfig(String configFile) {
			Configuration config = new Configuration().configure(configFile);
			return new ConfigurationToShardConfigurationAdapter(config);
		}
	}

      class A implements ShardStrategyFactory {
				public ShardStrategy newShardStrategy1(List<ShardId> shardIds) {
					RoundRobinShardLoadBalancer loadBalancer = new RoundRobinShardLoadBalancer(shardIds);
					ShardSelectionStrategy pss = new RoundRobinShardSelectionStrategy(loadBalancer);
					ShardResolutionStrategy prs = new AllShardsShardResolutionStrategy(shardIds);
					ShardAccessStrategy pas = new SequentialShardAccessStrategy();
					return new ShardStrategyImpl(pss, prs, pas);
				}

				@Override
				public ShardStrategy newShardStrategy(List<ShardId> arg0) {
					// TODO Auto-generated method stub
					return null;
				}
      }
