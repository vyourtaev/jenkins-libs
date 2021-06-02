def call(String args){
    def env =  new com.callfire.BuildEnv()
    env.construct(args)
    return env
}
